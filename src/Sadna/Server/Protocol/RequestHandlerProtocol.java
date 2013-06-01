package Sadna.Server.Protocol;


import Sadna.Client.Member;
import Sadna.Server.API.ServerInterface;
import Sadna.Server.Encryptor;
import Sadna.Server.ForumNotification;
import Sadna.Server.NotificationsFactory;
import Sadna.Server.Reactor.Reactor;
import Sadna.Server.StringMessagesToClient;
import Sadna.Server.Tokenizer.StringMessage;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import Sadna.db.PolicyEnums.enumAssignModerator;
import Sadna.db.PolicyEnums.enumCancelModerator;
import Sadna.db.PolicyEnums.enumDelete;


import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import dbTABLES.*;

/**
 * a simple implementation of the server protocol interface
 */
public class RequestHandlerProtocol implements AsyncServerProtocol<StringMessage> {

	private boolean _shouldClose = false;
	private boolean _connectionTerminated = false;
	private ServerInterface _si;
	private StringMessagesToClient _msgToClient;
	private NotificationsFactory _notificationsFactory;
	private SocketChannel _socketChannel;
	

	public RequestHandlerProtocol(ServerInterface _si) {
		this._si = _si;
		this._msgToClient = new StringMessagesToClient();
		this._notificationsFactory = new NotificationsFactory(this._si);
	}

	public RequestHandlerProtocol(ServerInterface _si, SocketChannel sc) {
		this._si = _si;
		this._msgToClient = new StringMessagesToClient();
		this._notificationsFactory = new NotificationsFactory(this._si);
		this._socketChannel = sc;

	}

	/**
	 * processes a message<BR>
	 * this simple interface prints the message to the screen, then composes a
	 * simple reply and sends it back to the client
	 *
	 * @param msg the message to process
	 * @return the reply that should be sent to the client, or null if no reply
	 * needed
	 */
	@Override
	public Object processMessage(StringMessage msg) {
		if (this._connectionTerminated) {
			return null;
		}
		if (this.isEnd(msg)) {
			this._shouldClose = true;
			return new StringMessage("Ok, bye bye");
		}
		String request = msg.getMessage();
		System.out.println("-- Message Received -- \n" + request);
		return parseAndHandleRequest(request);
	}

	/**
	 * detetmine whether the given message is the termination message
	 *
	 * @param msg the message to examine
	 * @return false - this simple protocol doesn't allow termination...
	 */
	@Override
	public boolean isEnd(StringMessage msg) {
		return msg.equals("end");
	}

	/**
	 * Is the protocol in a closing state?. When a protocol is in a closing
	 * state, it's handler should write out all pending data, and close the
	 * connection.
	 *
	 * @return true if the protocol is in closing state.
	 */
	@Override
	public boolean shouldClose() {
		return this._shouldClose;
	}

	/**
	 * Indicate to the protocol that the client disconnected.
	 */
	@Override
	public void connectionTerminated() {
		this._connectionTerminated = true;
	}

	private Object parseAndHandleRequest(String request) {
		String[] parsedReq = request.split("\n");
		Subforumdb sf;
		Forumdb fr;
		Threaddb tm;
		Postdb p;
		String forumName;
		String userName;
		String password;
		Memberdb m;
		switch (parsedReq[0]) {
		case "HELLO":
			return "HELLO";
		case "LISTENING":
			return handleListening();
		case "LOGIN":
			return handleLogin(parsedReq[2], parsedReq[4], parsedReq[6]);
		case "REGISTER":
			return handleRegister(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8]);
		case "GETSF":
			return handleGetSubForum(parsedReq[2], parsedReq[4]);
		case "GETSFL":
			return handleGetSubForumsList(parsedReq[2]);
		case "GETTL":
			return handleGetThreadsList(parsedReq[2], parsedReq[4]);
		case "GETTM":
			return handleGetThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
		case "GETFL":
			return handleGetForumsList();
		case "GETF":
			return handleGetForum(parsedReq[2]);
		case "ADDSF":
			List<Memberdb> members = new ArrayList<Memberdb>();
			int i;
			for (i = 8; i < 8 + 2 * (Integer.parseInt(parsedReq[6])); i = i + 2) {
				System.out.println("inside list of members");
				Memberdb member = _si.getMember(parsedReq[2], parsedReq[i]);
				members.add(member);
			}
			Forumdb forum = _si.getForum(parsedReq[2]);
			Subforumdb subF = new Subforumdb(forum, parsedReq[4], new HashSet<Memberdb>(), new HashSet<Threaddb>());
			return handleAddSubForum(subF, members, parsedReq[i], parsedReq[i + 2]);
		case "ADDF":
			return handleInitiateForum(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[9],
					parsedReq[10], parsedReq[11], parsedReq[12], parsedReq[13], parsedReq[14], parsedReq[16], parsedReq[18]);
		case "GETAP":
			return handleGetAllPosts(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
		case "POST":
			tm = _si.getThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
			Memberdb publisher = _si.getMember(parsedReq[2], parsedReq[8]);
			p = new Postdb(0, tm, publisher, parsedReq[10], parsedReq[12]);
			return handlePostComment(p, parsedReq[8], parsedReq[14]);
		case "THREAD":
			return handlePublishThread(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10], parsedReq[12]);
		case "DELPST":
			p = _si.getPost(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]), Integer.parseInt(parsedReq[8]));
			return handleDeletePost(p, parsedReq[10], parsedReq[12]);
		case "DELTHRD":
			tm = _si.getThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
			return handleDeleteThread(tm, parsedReq[8], parsedReq[10]);
		case "DELSF":
			sf = _si.getSubForum(parsedReq[2], parsedReq[4]);
			return handleDeleteSubForum(sf, parsedReq[6], parsedReq[8]);
		case "DELF":
			fr = _si.getForum(parsedReq[2]);
			return handleDeleteForum(fr, parsedReq[4], parsedReq[6]);
		case "ADDMOD":
			sf = _si.getSubForum(parsedReq[2], parsedReq[4]);
			return handleAddModerator(sf, parsedReq[6], parsedReq[8], parsedReq[10]);
		case "NOTI":
			forumName = parsedReq[2];
			userName = parsedReq[4];
			password = parsedReq[6];
			m = _si.getMember(forumName, userName);
			return handleNotification(m, password);
		case "HASNOTIF":
			forumName = parsedReq[2];
			userName = parsedReq[4];
			password = parsedReq[6];
			m = _si.getMember(forumName, userName);
			return handleHasNotification(m, password);
		case "REMMOD":
			return handleRemoveModerator(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10]);
		case "GETCOUNT":
			return handleThreadCounter(parsedReq[2], parsedReq[4], parsedReq[6]);
		case "GETNUT":
			return handleGetNumberOfUserThreads(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8]);
		case "GETUPU":
			return handleGetUsersPostToUser(parsedReq[2], parsedReq[4], parsedReq[6]);
		case "GETNF":
			return handleForumCounter(parsedReq[2], parsedReq[4]);
		case "GETCOM":
			return handleCommonMembers(parsedReq[2], parsedReq[4]);
		case "LOGOUT":
			return handleLogout(parsedReq[2], parsedReq[4]);
		case "GETAM":
			return handleGetAllForumMembers(parsedReq[2], parsedReq[4], parsedReq[6]);
		case "LOGINS":
			return handleLoginAsSuperAdmin(parsedReq[2], parsedReq[4]);
		case "EDTTHRD":
			return handleEditThread(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10], parsedReq[12], parsedReq[14]);
		case "EDTPST":
			return handleEditPost(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10], parsedReq[12], parsedReq[14], parsedReq[16]);
		case "CLRDB":
			return handleClearDB(parsedReq[2], parsedReq[4]);
		default:
			return null;
		}

	}

	private Object handleClearDB(String superAdminUserName, String plainPassword) {
		Memberdb superAdmin = this._si.getSuperAdmin();
		if(!checkPassword(plainPassword, superAdmin.getPassword())){
			return _msgToClient.sendErrorNoAuthorized();
		}
		else{
			if(this._si.clearDB())
				return _msgToClient.sendOK();
			return _msgToClient.sendErrorInServer();
		}
		
	}

	private Object handleEditPost(String forumName, String subForumName,
			String TMid, String pid, String title, String content,
			String editorName, String editorPassword) {
		Memberdb editor = _si.getMember(forumName, editorName);
		if(editor==null || !(checkPassword(editorPassword, editor.getPassword()))){
			return _msgToClient.sendErrorNoAuthorized();
		}
		Postdb postToEdit = _si.getPost(forumName, subForumName, Integer.parseInt(TMid), Integer.parseInt(pid));
		if(postToEdit.getMemberdb().getUserName().equals(editorName) || editor.getRoll().equals("Moderator") || editor.getRoll().equals("Admin")){
			postToEdit.setContent(content);
			postToEdit.setTitle(title);
			if(_si.editPost(postToEdit)){
				_notificationsFactory.sendNotifications(postToEdit, "edited");
				Reactor.NotifyAllListeners();
				return _msgToClient.sendOK();
			}
			return _msgToClient.sendErrorNoAuthorized();
		}
		else{
			return _msgToClient.sendErrorNoAuthorized();
		}
	}


	private Object handleEditThread(String forumName, String subForumName,
			String tid, String title, String content, String editorName,
			String editorPassword) {
		Memberdb editor = _si.getMember(subForumName, editorName);
		if(editor==null || !(checkPassword(editorPassword, editor.getPassword()))){
			return _msgToClient.sendErrorNoAuthorized();
		}
		Threaddb threadToEdit = _si.getThreadMessage(forumName, subForumName, Integer.parseInt(tid));
		if(threadToEdit.getMemberdb().getUserName().equals(editorName) || editor.getRoll().equals("Moderator") || editor.getRoll().equals("Admin")){
			threadToEdit.setContent(content);
			threadToEdit.setTitle(title);
			if(_si.editThread(threadToEdit)){
				_notificationsFactory.sendNotifications(threadToEdit, "edited");
				Reactor.NotifyAllListeners();
				return _msgToClient.sendOK();
			}
			return _msgToClient.sendErrorNoAuthorized();
		}
		else{
			return _msgToClient.sendErrorNoAuthorized();
		}
	}



	private Object handleLogout(String forumName, String userName) {
		if (_si.logout(forumName, userName)) {
			return _msgToClient.sendOK();
		} else {
			return _msgToClient.sendErrorInServer();
		}
	}

	private Object handleLoginAsSuperAdmin(String userName, String password) {
		boolean logedIn = _si.loginAsSuperAdmin(userName, password);
		if (logedIn) {
			Memberdb member = _si.getSuperAdmin();
			if (member != null && member.getRoll().equals("SuperAdmin")) {
				return _msgToClient.sendSuperAdminOK();
			}
		} else {
			return _msgToClient.sendNotFound();
		}
		return null;
	}

	private Object handleGetAllForumMembers(String forumName, String userName,
			String password) {
		Memberdb admin = _si.getMember(forumName, userName);

		if (admin != null && !(checkPassword(password, admin.getPassword()))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		if (!(admin.getRoll().equals("Admin"))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		List<Memberdb> members = _si.getAllForumMembers(forumName, userName, password);
		List<Member> ans = new ArrayList<Member>();
		for (Memberdb memberdb : members) {
			ans.add(memberdb.convertToMember());
		}
		return ans;
	}

	private Object handleCommonMembers(String adminName, String password) {
		Memberdb superAdmin = _si.getSuperAdmin();
		if (!(checkPassword(password, superAdmin.getPassword()))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		return _si.getCommonMembers(adminName, password);
	}

	private Object handleForumCounter(String superAdminName, String password) {
		Memberdb superAdmin = _si.getSuperAdmin();
		if (!(checkPassword(password, superAdmin.getPassword()))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		return _si.getForumCounter(superAdminName, password);
	}

	private Object handleGetUsersPostToUser(String forumName, String adminName, String password) {
		Memberdb admin = _si.getMember(forumName, adminName);
		if (!(checkPassword(password, admin.getPassword()))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		if (!(admin.getRoll().equals("Admin"))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		HashMap<String,List<String>> ans = new HashMap<String,List<String>>();
		HashMap<String,Set<String>> users = _si.getUsersPostToUser(forumName, adminName, password);
		Iterator it = users.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        List<String> tmp = new ArrayList<String>();
	        tmp.addAll((Set<String>) pairs.getValue());
	        ans.put((String) pairs.getKey(),tmp);
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		return ans;
	}

	private Object handleGetNumberOfUserThreads(String forumName, String userName, String adminName, String password) {
		Memberdb admin = _si.getMember(forumName, adminName);
		if (!(checkPassword(password, admin.getPassword()))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		if (!(admin.getRoll().equals("Admin"))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		return _si.getNumberOfUserThreads(forumName, userName, adminName, password);
	}

	private Object handleThreadCounter(String forumName, String userName, String password) {
		Memberdb admin = _si.getMember(forumName, userName);
		if (!(checkPassword(password, admin.getPassword()))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		if (!(admin.getRoll().equals("Admin"))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		return _si.getNumberOfThreadsInForum(forumName, userName, password);
	}

	private Object handleRemoveModerator(String forumName, String subForumName,
			String moderatorName, String userName, String password) {
		Memberdb admin = _si.getMember(forumName, userName);
		Forumdb forum = _si.getForum(forumName);
		if (!(checkPassword(password, admin.getPassword()))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		if (!(admin.getRoll().equals("Admin"))) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		List<Memberdb> mList = _si.getModerators(forumName, subForumName);
		if (mList.size() <= 1
				&& forum.getEnumCancelModerator().equals(enumCancelModerator.RESTRICTED)) {
			return _msgToClient.sendIsTheOnlyModeratorInTheSubForum();
		}
		Memberdb m = null;
		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(i).getUserName().equals(moderatorName)) {
				m = mList.get(i);
				break;
			}

		}
		if (m == null) {
			return _msgToClient.sendErrorInServer();
		}
		if (_si.deleteModerator(m, subForumName, moderatorName, userName, password)) {
			return _msgToClient.sendOK();
		} else {
			return _msgToClient.sendErrorInServer();
		}
	}

	private Object handleNotification(Memberdb m, String password) {
		if (checkPassword(password, m.getPassword())) {
			String forumName = m.getForumdb().getForumName();
			String userName = m.getUserName();
			List<ForumNotification> notifications = _si.getNotifications(forumName, userName, password);
			List<ForumNotification> res = new ArrayList<ForumNotification>();
			for (Iterator<ForumNotification> iterator = notifications.iterator(); iterator.hasNext();) {
				ForumNotification forumNotification = (ForumNotification) iterator.next();
				res.add(forumNotification);
			}
			m.setNotification(""); // clear the notifications list
			_si.updateMember(m);
			return res;
		} else {
			return _msgToClient.sendErrorNoAuthorized();
		}
	}
	//with encryption of the user password
	public Object handleRegister(String forumName, String userName, String password,
			String email) {
		String encryptedPassword = Encryptor.encrypt(password);
		boolean isAdded = _si.register(forumName, userName, encryptedPassword, email);
		if (isAdded) {
			return _msgToClient.sendOK();
		} else {
			return _msgToClient.sendErrorInServer();
		}
	}

	public Object handleLogin(String forumName, String userName, String password) {
		Memberdb member = _si.getMember(forumName, userName);
		if((checkPassword(password, member.getPassword()))){
			boolean logedIn = _si.login(forumName, userName, password);
			if (logedIn) {
				if (member.getRoll().equals("Admin")) {
					return _msgToClient.sendAdminOK();
				} else if (member.getRoll().equals("Moderator")) {
					return _msgToClient.sendModeratorOK();
				} else if (member.getRoll().equals("Member")) {
					return _msgToClient.sendOK();
				}
			} else {
				return _msgToClient.sendNotFound();
			}
		}
		else {
			return _msgToClient.sendNotFound();
		}
		return null;
	}

	public Object handleInitiateForum(String forumName, String adminName,
			String adminPassword, String imidiOrAgrePolicy, String notiFriendsPolicy,
			String deletePolicy, String assignModerPolicy, String seniority, String minPublish,
			String cancelModerPolicy, String superAdminUserName, String superAdminPassword) {
		boolean isAdded = false;

		isAdded = _si.initiateForum(adminName, adminPassword, forumName, imidiOrAgrePolicy, notiFriendsPolicy,
				deletePolicy, assignModerPolicy, seniority, minPublish, cancelModerPolicy, superAdminUserName,
				superAdminPassword);
		if (isAdded) {
			//System.out.println("isAdded");
			return _msgToClient.sendOK();
			//System.out.println("Sent ok");
		} else {
			//System.out.println("notAdded");
			return _msgToClient.sendErrorNoAuthorized();
			//System.out.println("Sent ErrorInServer");
		}
	}

	public Object handleAddSubForum(Subforumdb subForum, List<Memberdb> members, String username, String password) {
		boolean subForumIsAdded = false;
		subForumIsAdded = _si.addSubForum(subForum, members, username, password);
		if (subForumIsAdded) {
			return _msgToClient.sendOK();
		} else {
			return _msgToClient.sendErrorInServer();
		}
	}

	public Object handlePublishThread(String forumName, String subForumName,
			String posterName, String threadTitle, String threadContent, String password) {
		if(checkPassword(password, _si.getMember(forumName, posterName).getPassword())){
			boolean succeeded = false;
			Threaddb threadM = null;
			Subforumdb sf = _si.getSubForum(forumName, subForumName);
			if (sf != null && _si.memberExistsInForum(forumName, posterName)) {
				Memberdb publisher = _si.getMember(subForumName, posterName);
				threadM = new Threaddb( -1, sf, publisher, threadTitle, threadContent, new HashSet<Postdb>());
				succeeded = _si.publishThread(threadM, posterName, password);
			}
			if (succeeded) {
				_notificationsFactory.sendNotifications(sf);
				Reactor.NotifyAllListeners();
				return _msgToClient.sendOK();
			} else {
				return _msgToClient.sendErrorInServer();
			}
		}
		else{
			return _msgToClient.sendErrorNoAuthorized();
		}
	}

	public Object handleDeletePost(Postdb p, String requester, String password) {
		String publisher = p.getMemberdb().getUserName();
		Forumdb forum = p.getThreaddb().getSubforumdb().getForumdb();
		String forumName = forum.getForumName();
		Memberdb member = _si.getMember(forumName, publisher);
		if (member != null) {
			String publisherName = member.getUserName();
			String publisherPassword = member.getPassword();
			if (requester.equals(publisherName) && checkPassword(password, publisherPassword)) {
				return deletePostAndSendOk(p, requester, password);
			}
		}
		if (forum.getEnumDelete().equals(enumDelete.EXTENDED)) {
			Subforumdb subForum = p.getThreaddb().getSubforumdb();
			String subForumName = subForum.getSubForumName();
			List<Memberdb> moderators = _si.getModerators(forumName, subForumName);
			for (Iterator<Memberdb> it = moderators.iterator(); it.hasNext();) {
				Memberdb currModerator = it.next();
				String moderatorName = currModerator.getUserName();
				String moderatorPassword = currModerator.getPassword();
				if (requester.equals(moderatorName) && checkPassword(password, moderatorPassword)) {
					return deletePostAndSendOk(p, moderatorName, moderatorPassword);
				}
			}
		}
		Memberdb admin = _si.getForum(forumName).getMemberdb();
		if (admin != null) {
			String adminName = admin.getUserName();
			String adminPassword = admin.getPassword();
			if (requester.equals(adminName) && checkPassword(password, adminPassword)) {
				return deletePostAndSendOk(p, adminName, adminPassword);
			}
		}
		return _msgToClient.sendErrorNoAuthorized();
	}

	public Object handlePostComment(Postdb post, String userName, String password) {
		boolean succeeded = false;
		Object result = null;
		String publisherPassword = post.getMemberdb().getPassword();
		if(checkPassword(password, publisherPassword)){
		succeeded = _si.postComment(post, userName, password);
		if (succeeded) {
			result = _msgToClient.sendOK();
		} else {
			result = _msgToClient.sendErrorInServer();
		}
		_notificationsFactory.sendNotifications(post, "added");
		Reactor.NotifyAllListeners();
		}
		else{
			result = _msgToClient.sendErrorNoAuthorized();
		}
		return result;
	}

	public Object handleGetForumsList() {
		List<Forumdb> forums = _si.getForumsList();
		List<Forum> ans = new ArrayList<Forum>();
		for (Forumdb forum : forums) {
			ans.add(forum.convertToForum());
		}
		return ans;
	}

	private Object handleGetAllPosts(String forumName, String subForumName, int threadId) { 
		List<Postdb> posts = _si.getAllPosts(forumName, subForumName, threadId);
		List<Post> ans = new ArrayList<Post>();
		for (Postdb post : posts) {
			ans.add(post.convertToPost());
		}
		return ans;
	}
	
	public Object handleGetForum(String forumName) {
		return _si.getForum(forumName).convertToForum();
	}

	public Object handleGetSubForumsList(String forumName) {
		List<SubForum> ans = new ArrayList<SubForum>();
		List<Subforumdb> subForums = _si.getSubForumsList(forumName);
		for (Subforumdb subforumdb : subForums) {
			ans.add(subforumdb.convertToSubForum());
		}		
		return ans;
	}

	public Object handleGetSubForum(String forum, String subForumName) {
		return _si.getSubForum(forum, subForumName).convertToSubForum();
	}

	public Object handleGetThreadsList(String forumName,
			String subForumName) {
		List<ThreadMessage> ans = new ArrayList<ThreadMessage>();
		List<Threaddb> threads = _si.getThreadsList(forumName, subForumName);
		for (Threaddb threaddb : threads) {
			ans.add(threaddb.convertToThread());
		}
		return ans;
	}

	public Object handleGetThreadMessage(String forumName, String subForumName,
			int messageID) {
		return _si.getThreadMessage(forumName, subForumName, messageID).convertToThread();
	}

	public Object handleDeleteThread(Threaddb tm, String requester, String password) {
		String publisher = tm.getMemberdb().getUserName();
		Forumdb forum = tm.getSubforumdb().getForumdb();
		String forumName = forum.getForumName();
		Memberdb member = _si.getMember(forumName, publisher);
		if (member != null) {
			String publisherName = member.getUserName();
			String publisherPassword = member.getPassword();
			if (requester.equals(publisherName) && checkPassword(password, publisherPassword)) {
				return deleteThreadAndSendOk(tm, requester, password);

			}
		}
		if (forum.getEnumDelete().equals(enumDelete.EXTENDED)) {
			Subforumdb subForum = tm.getSubforumdb();
			String subForumName = subForum.getSubForumName();
			List<Memberdb> moderators = _si.getModerators(forumName, subForumName);
			for (Iterator<Memberdb> it = moderators.iterator(); it.hasNext();) {
				Memberdb currModerator = it.next();
				String moderatorName = currModerator.getUserName();
				String moderatorPassword = currModerator.getPassword();
				if (requester.equals(moderatorName) && checkPassword(password, moderatorPassword)) {
					return deleteThreadAndSendOk(tm, moderatorName, moderatorPassword);
				}
			}
		}
		Memberdb admin = _si.getForum(forumName).getMemberdb();
		if (admin != null) {
			String adminName = admin.getUserName();
			String adminPassword = admin.getPassword();
			if (requester.equals(adminName) && checkPassword(password, adminPassword)) {
				return deleteThreadAndSendOk(tm, adminName, adminPassword);
			}
		}
		return _msgToClient.sendErrorNoAuthorized();
	}

	private Object deletePostAndSendOk(Postdb p, String userName, String password) {
		_notificationsFactory.sendNotifications(p, "deleted");
		_si.deleteComment(p, userName, password);
		Reactor.NotifyAllListeners();
		return _msgToClient.sendOK();
	}

	private Object deleteThreadAndSendOk(Threaddb tm, String userName, String password) {
		_notificationsFactory.sendNotifications(tm, "deleted");
		_si.deleteThread(tm, userName, password);
		Reactor.NotifyAllListeners();
		return _msgToClient.sendOK();
	}

	private Object handleDeleteSubForum(Subforumdb sf, String requester, String password) {
		Memberdb admin = sf.getForumdb().getMemberdb();
		boolean equals = admin.getUserName().equals(requester) && checkPassword(password, admin.getPassword());
		if (equals) {
			_notificationsFactory.sendNotifications(sf);
			_si.deleteSubForum(sf, requester, password);
			Reactor.NotifyAllListeners();
			return _msgToClient.sendOK();
		}
		return _msgToClient.sendErrorNoAuthorized();
	}

	private Object handleAddModerator(Subforumdb sf, String moderatorToAdd,
			String userName, String password) {
		String forumName = sf.getForumdb().getForumName();
		Forumdb forum = _si.getForum(forumName);
		Memberdb admin = forum.getMemberdb();
		if (admin == null || !admin.getUserName().equals(userName)
				|| !checkPassword(password, admin.getPassword())) {
			return _msgToClient.sendErrorNoAuthorized();
		}
		Memberdb member = _si.getMember(forumName, moderatorToAdd);
		if (member == null) {
			return _msgToClient.sendNotFound();
		}

		if (forum.getEnumAssignModerator().equals(enumAssignModerator.MIN_PUBLISH)) {
			System.out.println("in min publish");
			int numberOfUserThreads = _si.getNumberOfUserThreads(forumName, userName, null, null);
			int minPublish = forum.getMinPublish();
			if (numberOfUserThreads < minPublish) {
				return _msgToClient.sendErrorNoAuthorized();//consider change to someting more inducative
			}
		}
		if (forum.getEnumAssignModerator().equals(enumAssignModerator.SENIORITY)) {
			long dateOfJoining = Long.parseLong(member.getDateJoin());
			int seniorityAsDays = calcNumOfDaysSinceJoining(dateOfJoining);
			int seniority = forum.getSeniority();
			if (seniorityAsDays < seniority) {
				return _msgToClient.sendErrorNoAuthorized();//consider change to someting more inducative
			}
		}

		List<Memberdb> moderators = _si.getModerators(forumName, sf.getSubForumName());
		List<String> moderetorsAsString = new ArrayList<String>();
		for (Iterator<Memberdb> it = moderators.iterator(); it.hasNext();) {
			Memberdb currMember = it.next();
			moderetorsAsString.add(currMember.getUserName());
		}
		if (moderetorsAsString.contains(moderatorToAdd)) {
			return _msgToClient.sendOK();
		}
		member.setRoll("Moderator");
		
		boolean addModerator = _si.addModerator(member, sf, member.getUserName(), member.getPassword());
		if (addModerator) {
			return _msgToClient.sendOK();
		}
		return null;
	}

	private Object handleDeleteForum(Forumdb fr, String userName, String password) {
		Memberdb superAdmin = _si.getSuperAdmin();
		if (superAdmin == null || !checkPassword(password, superAdmin.getPassword())) {
			return _msgToClient.sendErrorNoAuthorized();
		} else {
			return _msgToClient.deleteForum(_si.deleteForum(fr.getForumName(), userName, password));
		}
	}

	private int calcNumOfDaysSinceJoining(long dateOfJoining) {
		return (int) ((dateOfJoining - new Date().getTime()) / (1000 * 60 * 60 * 24));
	}

	private Object handleHasNotification(Memberdb m, String password) {
		if (checkPassword(password, m.getPassword())) {
			boolean ret = false;
			String notifications = m.getNotification();
			if (!(notifications.equals(""))) {
				ret = true;
			}
			return new Boolean(ret);
		} else {
			return _msgToClient.sendErrorNoAuthorized();
		}
	}



	private Object handleListening() {
		boolean SocketsListAdd = Reactor.SocketsListAdd(_socketChannel);
		if (SocketsListAdd) {
			return _msgToClient.sendOK();
		}
		return _msgToClient.sendErrorInServer();
	}

	private boolean checkPassword(String password, String encrypted){
		if (Encryptor.checkPassword(password, encrypted)) {
			return true;
		}
		return false;
	}
}
