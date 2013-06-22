package Sadna.Server.Protocol;


import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Server.API.ServerInterface;
import Sadna.Server.EmailPassCode;
import Sadna.Server.EmailSender;
import Sadna.Server.Encryptor;
import Sadna.Server.ForumNotification;
import Sadna.Server.NotificationsFactory;
import Sadna.Server.PairUserForum;
import Sadna.Server.Reactor.Reactor;
import Sadna.Server.StringMessagesToClient;
import Sadna.Server.Tokenizer.StringMessage;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

import net.sf.classifier4J.ClassifierException;
import net.sf.classifier4J.bayesian.WordsDataSourceException;
import net.sf.classifier4J.vector.HashMapTermVectorStorage;
import net.sf.classifier4J.vector.TermVectorStorage;
import net.sf.classifier4J.vector.VectorClassifier;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sun.org.apache.regexp.internal.recompile;


import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;



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
	private Logger _reportLogger;
	private String _logMsg;
	private EmailSender _emailSender;


	public RequestHandlerProtocol(ServerInterface _si) {
		this._si = _si;
		this._msgToClient = new StringMessagesToClient();
		this._notificationsFactory = new NotificationsFactory(this._si);
		this._reportLogger = (Logger) Reactor.getLogger();
		this._emailSender = new EmailSender();


	}

	public RequestHandlerProtocol(ServerInterface _si, SocketChannel sc) {
		this._si = _si;
		this._msgToClient = new StringMessagesToClient();
		this._notificationsFactory = new NotificationsFactory(this._si);
		this._socketChannel = sc;
		this._reportLogger = (Logger) Reactor.getLogger();
		this._emailSender = new EmailSender();
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
		Object retVal = parseAndHandleRequest(request);
		if (retVal!=null)
			return retVal;
		return _msgToClient.sendNotFound();
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
			return handleHello();
		case "LISTENING":
			return handleListening();
		case "LOGIN":
			return handleLogin(parsedReq[2], parsedReq[4], parsedReq[6]);
		case "REGISTER":
			return handleRegister(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8]);
		case "EMAIL":
			return handleEmail(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8]);
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
			_logMsg = "recieved: ADDSF";
			_reportLogger.log(Level.INFO ,_logMsg);
			List<Memberdb> members = new ArrayList<Memberdb>();
			int i;
			for (i = 8; i < 8 + 2 * (Integer.parseInt(parsedReq[6])); i = i + 2) {
				System.out.println("inside list of members");
				_si.openSession();
				Memberdb member = _si.getMember(parsedReq[2], parsedReq[i]);
				if (member==null){
					_logMsg = "as a respond to ADDSF- sending: null";
					_reportLogger.log(Level.DEBUG ,_logMsg);
					return null;
				}
				_si.closeSession();
				members.add(member);
			}
			_si.openSession();
			Forumdb forum = _si.getForum(parsedReq[2]);
			_si.closeSession();
			if (forum==null){
				_logMsg = "as a respond to ADDSF- sending: "+_msgToClient.sendNotFound();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				return _msgToClient.sendNotFound();
			}
			Subforumdb subF = new Subforumdb(forum, parsedReq[4], new HashSet<Memberdb>(), new HashSet<Threaddb>());
			return handleAddSubForum(subF, members, parsedReq[i], parsedReq[i + 2]);
		case "ADDF":
			return handleInitiateForum(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[9],
					parsedReq[10], parsedReq[11], parsedReq[12], parsedReq[13], parsedReq[14], parsedReq[15],
					parsedReq[16], parsedReq[17], parsedReq[19], parsedReq[21], parsedReq[23]);
		case "GETAP":
			return handleGetAllPosts(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
		case "POST":
			_logMsg = "recieved: POST";
			_reportLogger.log(Level.INFO ,_logMsg);
			_si.openSession();
			tm = _si.getThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
			_si.closeSession();
			if (tm==null){
				_logMsg = "as a respond to POST- sending: null";
				_reportLogger.log(Level.DEBUG ,_logMsg);
				return null;
			}
			_si.openSession();
			Memberdb publisher = _si.getMember(parsedReq[2], parsedReq[8]);
			_si.closeSession();
			p = new Postdb(tm, publisher, parsedReq[10], parsedReq[12]);
			if (publisher==null){
				_logMsg = "as a respond to POST- sending: null";
				_reportLogger.log(Level.DEBUG ,_logMsg);
				return null;
			}
			return handlePostComment(p, parsedReq[8], parsedReq[14]);
		case "THREAD":
			return handlePublishThread(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10], parsedReq[12]);
		case "DELPST":
			_logMsg = "recieved: DELPST";
			_reportLogger.log(Level.INFO ,_logMsg);
			_si.openSession();
			p = _si.getPost(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]), Integer.parseInt(parsedReq[8]));
			if (p==null){
				_logMsg = "as a respond to DELPST- sending: null";
				_reportLogger.log(Level.DEBUG ,_logMsg);
				_si.closeSession();
				return null;
			}
			return handleDeletePost(p, parsedReq[10], parsedReq[12]);
		case "DELTHRD":
			_logMsg = "recieved: DELTHRD";
			_reportLogger.log(Level.INFO ,_logMsg);
			_si.openSession();
			tm = _si.getThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
			if (tm==null){
				_logMsg = "as a respond to DELTHRD- sending: null";
				_reportLogger.log(Level.DEBUG ,_logMsg);
				_si.closeSession();
				return null;
			}
			return handleDeleteThread(tm, parsedReq[8], parsedReq[10]);
		case "DELSF":
			_logMsg = "recieved: DELSF";
			_reportLogger.log(Level.INFO ,_logMsg);
			_si.openSession();
			sf = _si.getSubForum(parsedReq[2], parsedReq[4]);
			if (sf==null){
				_logMsg = "as a respond to DELSF- sending: null";
				_reportLogger.log(Level.DEBUG ,_logMsg);
				_si.closeSession();
				return null;
			}
			return handleDeleteSubForum(sf, parsedReq[6], parsedReq[8]);
		case "DELF":
			_logMsg = "recieved: DELF";
			_reportLogger.log(Level.INFO ,_logMsg);
			_si.openSession();
			fr = _si.getForum(parsedReq[2]);
			if (fr==null){
				_logMsg = "as a respond to DELF- sending: null";
				_reportLogger.log(Level.DEBUG ,_logMsg);
				_si.closeSession();
				return null;
			}
			return handleDeleteForum(fr, parsedReq[4], parsedReq[6]);
		case "ADDMOD":
			_logMsg = "recieved: ADDMOD";
			_reportLogger.log(Level.INFO ,_logMsg);
			_si.openSession();
			sf = _si.getSubForum(parsedReq[2], parsedReq[4]);
			if (sf==null){
				_logMsg = "as a respond to ADDMOD- sending: null";
				_reportLogger.log(Level.DEBUG ,_logMsg);
				_si.closeSession();
				return null;
			}
			return handleAddModerator(sf, parsedReq[6], parsedReq[8], parsedReq[10]);
		case "NOTI":
			_logMsg = "recieved: NOTI";
			_reportLogger.log(Level.TRACE ,_logMsg);
			forumName = parsedReq[2];
			userName = parsedReq[4];
			password = parsedReq[6];
			_si.openSession();
			m = _si.getMember(forumName, userName);
			if (m==null){
				_logMsg = "as a respond to NOTI- sending: null";
				_reportLogger.log(Level.DEBUG ,_logMsg);
				_si.closeSession();
				return null;
			}
			return handleNotification(m, password);
		case "HASNOTIF":
			_logMsg = "recieved: HASNOTIF";
			_reportLogger.log(Level.TRACE ,_logMsg);
			forumName = parsedReq[2];
			userName = parsedReq[4];
			password = parsedReq[6];
			_si.openSession();
			m = _si.getMember(forumName, userName);
			_si.closeSession();
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
		case "GETALLMOD":
			return handleGetAllModerators(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8]);
		default:
			return null;
		}

	}

	private Object handleEmail(String forumName, String username, String password, String randomCode) {
		//if(_si.getForum(forumName).getEnumSecurityPolicy().equals("VERIFY_EMAIL")){
		_logMsg = "recieved: EMAIL";
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.openSession();
		PairUserForum pair = new PairUserForum(username, forumName);
		EmailPassCode threeSome = Reactor.getUnverifiedMember(pair);
		if(threeSome!=null && threeSome.getCode().equals(randomCode) && threeSome.getPassword().equals(password)){
			String encryptedPassword = Encryptor.encrypt(password);
			boolean isAdded = _si.register(forumName, username, encryptedPassword, threeSome.getEmail());
			if (isAdded) {
				Reactor.removeUnverifiedMember(pair);
				_logMsg = "as a respond to EMAIL- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendOK();
			} else {
				_logMsg = "as a respond to EMAIL- sending: "+_msgToClient.sendErrorInServer();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendErrorInServer();
			}
		}
		else{
			_logMsg = "as a respond to EMAIL- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
	}

	private Object handleGetAllModerators(String forumName, String subForumName,
			String adminUserName, String adminPassword) {
		_logMsg = "recieved: GETALLMOD";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		Memberdb admin = _si.getMember(forumName, adminUserName);

		if (admin != null && !(checkPassword(adminPassword, admin.getPassword()))) {
			_logMsg = "as a respond to GETALLMOD- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		if (!(admin.getRoll().equals("Admin"))) {
			_logMsg = "as a respond to GETALLMOD- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		List<Memberdb> members = _si.getAllModeratorsInSubforum(forumName, subForumName, adminUserName, adminPassword);
		List<Moderator> ans = new ArrayList<Moderator>();
		for (Memberdb memberdb : members) {
			ans.add(memberdb.convertToModerator());
		}
		_logMsg = "as a respond to GETALLMOD- sending: requested list";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.closeSession();
		return ans;
	}

	private Object handleHello() {
		_logMsg = "recieved: HELLO";
		_reportLogger.log(Level.TRACE ,_logMsg);
		_logMsg = "as a respond to HELLO- sending: HELLO";
		_reportLogger.log(Level.TRACE ,_logMsg);
		return "HELLO";
	}

	private Object handleClearDB(String superAdminUserName, String plainPassword) {
		_logMsg = "recieved: CLRDB";
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.openSession();
		Memberdb superAdmin = this._si.getSuperAdmin();
		if(!checkPassword(plainPassword, superAdmin.getPassword())){
			_logMsg = "as a respond to CLRDB- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		else{
			if(this._si.clearDB()){
				_logMsg = "as a respond to CLRDB- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.TRACE ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendOK();

			}
			_logMsg = "as a respond to CLRDB- sending: "+ _msgToClient.sendErrorInServer();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorInServer();
		}

	}

	private Object handleEditPost(String forumName, String subForumName,
			String TMid, String pid, String title, String content,
			String editorName, String editorPassword) {
		_logMsg = "recieved: EDTPST";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		Forumdb forum = _si.getForum(forumName);
		List<String> forbiddenWordsAsList = forum.forbiddenWordsStringToList();
		for (String forbiddenWord : forbiddenWordsAsList) {
			if (title.contains(forbiddenWord) || content.contains(forbiddenWord)){
				_logMsg = "as a respond to EDTPST- sending: "+_msgToClient.sendErrorNoAuthorized();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendErrorNoAuthorized();
			}
		}
		TermVectorStorage storage = new HashMapTermVectorStorage();
		VectorClassifier vc = new VectorClassifier(storage);
		if (forum.getEnumMsgFilter().equals("FILTERED")){
			try {
				StringBuilder sb = new StringBuilder();
				Subforumdb sf = _si.getSubForum(forumName, subForumName);
				sb.append(sf.getSubForumName());
				sb.append(" ");
				Set<Threaddb> setOfThreads = sf.getThreaddbs();
				int numberOfMessages = setOfThreads.size();
				for (Threaddb threaddb : setOfThreads) {
					List<Postdb> setOfPosts = _si.getAllPosts(forumName, subForumName, 
							threaddb.getIdthread());
					for (Postdb postdb : setOfPosts) {
						sb.append(postdb.getTitle());
						sb.append(" ");
						sb.append(postdb.getContent());
						sb.append(" ");
						numberOfMessages++;
					}
					sb.append(threaddb.getTitle());
					sb.append(" ");
					sb.append(threaddb.getContent());
					sb.append(" ");
				}

				setVCCutOff(vc, numberOfMessages);
				vc.teachMatch(sb.toString());
				if (!vc.isMatch(title + " " + content)){
					_logMsg = "as a respond to EDTPST- sending: "+_msgToClient.sendErrorNoAuthorized();
					_reportLogger.log(Level.DEBUG ,_logMsg);
					this._si.closeSession();
					return _msgToClient.sendErrorNoAuthorized();
				}
			} catch (ClassifierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		Memberdb editor = _si.getMember(forumName, editorName);
		if(editor==null || !(checkPassword(editorPassword, editor.getPassword()))){
			_logMsg = "as a respond to EDTPST- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		Postdb postToEdit = _si.getPost(forumName, subForumName, Integer.parseInt(TMid), Integer.parseInt(pid));
		boolean moderatorPermission = false;
		if(_si.getForum(forumName).getEnumModeratorPermission().equals("EXTENDED")){
			for (Memberdb member : _si.getModerators(forumName, subForumName)) {
				if(member.getIdmember()==editor.getIdmember() && member.getPassword().equals(editor.getPassword())){
					moderatorPermission=true;
				}
			}
		}
		if(postToEdit.getMemberdb().getUserName().equals(editorName) || moderatorPermission ||editor.getRoll().equals("Admin")){
			postToEdit.setContent(content);
			postToEdit.setTitle(title);
			if(_si.editPost(postToEdit)){
				_notificationsFactory.sendNotifications(postToEdit, "edited");
				Reactor.NotifyAllListeners();
				_logMsg = "as a respond to EDTPST- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendOK();
			}
			_logMsg = "as a respond to EDTPST- sending: "+ _msgToClient.sendErrorInServer();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorInServer();
		}
		else{
			_logMsg = "as a respond to EDTPST- sending: "+ _msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
	}


	private Object handleEditThread(String forumName, String subForumName,
			String tid, String title, String content, String editorName,
			String editorPassword) {
		_logMsg = "recieved: EDTTHRD";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		Forumdb forum = _si.getForum(forumName);
		List<String> forbiddenWordsAsList = forum.forbiddenWordsStringToList();
		for (String forbiddenWord : forbiddenWordsAsList) {
			if (title.contains(forbiddenWord) || content.contains(forbiddenWord)){
				_logMsg = "as a respond to EDTTHRD- sending: "+_msgToClient.sendErrorNoAuthorized();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendErrorNoAuthorized();
			}
		}
		TermVectorStorage storage = new HashMapTermVectorStorage();
		VectorClassifier vc = new VectorClassifier(storage);
		if (forum.getEnumMsgFilter().equals("FILTERED")){
			try {
				StringBuilder sb = new StringBuilder();
				Subforumdb sf = _si.getSubForum(forumName, subForumName);
				sb.append(sf.getSubForumName());
				sb.append(" ");
				Set<Threaddb> setOfThreads = sf.getThreaddbs();
				int numberOfThreads = setOfThreads.size();
				for (Threaddb threaddb : setOfThreads) {
					sb.append(threaddb.getTitle());
					sb.append(" ");
					sb.append(threaddb.getContent());
					sb.append(" ");
				}
				setVCCutOff(vc, numberOfThreads);
				vc.teachMatch(sb.toString());
				if (!vc.isMatch(title + " " + content)){
					_logMsg = "as a respond to EDTTHRD- sending: "+_msgToClient.sendErrorNoAuthorized();
					_reportLogger.log(Level.DEBUG ,_logMsg);
					this._si.closeSession();
					return _msgToClient.sendErrorNoAuthorized();
				}
			} catch (ClassifierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		Memberdb editor = _si.getMember(forumName, editorName);
		if(editor==null || !(checkPassword(editorPassword, editor.getPassword()))){
			_logMsg = "as a respond to EDTTHRD- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		boolean moderatorPermission = false;
		if(_si.getForum(forumName).getEnumModeratorPermission().equals("EXTENDED")){
			for (Memberdb member : _si.getModerators(forumName, subForumName)) {
				if(member.getIdmember()==editor.getIdmember() && member.getPassword().equals(editor.getPassword())){
					moderatorPermission=true;
				}
			}
		}
		Threaddb threadToEdit = _si.getThreadMessage(forumName, subForumName, Integer.parseInt(tid));
		if(threadToEdit.getMemberdb().getUserName().equals(editorName) || moderatorPermission || editor.getRoll().equals("Admin")){
			threadToEdit.setContent(content);
			threadToEdit.setTitle(title);
			if(_si.editThread(threadToEdit)){
				_notificationsFactory.sendNotifications(threadToEdit, "edited");
				Reactor.NotifyAllListeners();
				_logMsg = "as a respond to EDTTHRD- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendOK();
			}
			_logMsg = "as a respond to EDTTHRD- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		else{
			_logMsg = "as a respond to EDTTHRD- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
	}



	private Object handleLogout(String forumName, String userName) {
		_logMsg = "recieved: LOGOUT  userName: "+userName+ " forum: "+ forumName;
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();

		if (_si.logout(forumName, userName)) {
			_logMsg = "as a respond to LOGOUT- sending: "+_msgToClient.sendOK();
			_reportLogger.log(Level.INFO ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendOK();
		} else {
			_logMsg = "as a respond to LOGOUT- sending: "+_msgToClient.sendErrorInServer();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorInServer();
		}
	}

	private Object handleLoginAsSuperAdmin(String userName, String password) {
		_logMsg = "recieved: LOGINS";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		boolean logedIn = _si.loginAsSuperAdmin(userName, password);
		if (logedIn) {
			Memberdb member = _si.getSuperAdmin();
			if (member != null && member.getRoll().equals("SuperAdmin")) {
				_logMsg = "as a respond to LOGINS- sending: "+_msgToClient.sendSuperAdminOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendSuperAdminOK();
			}
		} else {
			_logMsg = "as a respond to LOGINS- sending: "+ _msgToClient.sendNotFound();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendNotFound();
		}
		_logMsg = "as a respond to LOGINS- sending: null";
		_reportLogger.log(Level.DEBUG ,_logMsg);
		this._si.closeSession();
		return null;
	}

	private Object handleGetAllForumMembers(String forumName, String userName,
			String password) {
		_logMsg = "recieved: GETAM";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		Memberdb admin = _si.getMember(forumName, userName);

		if (admin != null && !(checkPassword(password, admin.getPassword()))) {
			_logMsg = "as a respond to GETAM- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		if (!(admin.getRoll().equals("Admin"))) {
			_logMsg = "as a respond to GETAM- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		List<Memberdb> members = _si.getAllForumMembers(forumName, userName, password);
		List<Member> ans = new ArrayList<Member>();
		for (Memberdb memberdb : members) {
			ans.add(memberdb.convertToMember());
		}
		_logMsg = "as a respond to GETAM- sending: requested list";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.closeSession();
		return ans;
	}

	private Object handleCommonMembers(String adminName, String password) {
		_logMsg = "recieved: GETCOM";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		Memberdb superAdmin = _si.getSuperAdmin();
		if (!(checkPassword(password, superAdmin.getPassword()))) {
			_logMsg = "as a respond to GETCOM- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		List<String> ans = _si.getCommonMembers(adminName, password);
		_logMsg = "as a respond to GETCOM- sending: requested list";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.closeSession();
		return ans;
	}

	private Object handleForumCounter(String superAdminName, String password) {
		_logMsg = "recieved: GETNF";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		Memberdb superAdmin = _si.getSuperAdmin();
		if (!(checkPassword(password, superAdmin.getPassword()))) {
			_logMsg = "as a respond to GETNF- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		int ans = _si.getForumCounter(superAdminName, password);
		_logMsg = "as a respond to GETNF- sending: the forum counter is: "+ ans;
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.closeSession();
		return ans;
	}

	private Object handleGetUsersPostToUser(String forumName, String adminName, String password) {
		_logMsg = "recieved: GETUPU";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		Memberdb admin = _si.getMember(forumName, adminName);
		if (!(checkPassword(password, admin.getPassword()))) {
			_logMsg = "as a respond to GETUPU- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		if (!(admin.getRoll().equals("Admin"))) {
			_logMsg = "as a respond to GETUPU- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
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
		_logMsg = "as a respond to GETUPU- sending: the requested list";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.closeSession();
		return ans;
	}

	private Object handleGetNumberOfUserThreads(String forumName, String userName, String adminName, String password) {
		_logMsg = "recieved: GETNUT forum"+ forumName+", userName"+userName;
		_reportLogger.log(Level.INFO ,_logMsg);

		this._si.openSession();
		Memberdb admin = _si.getMember(forumName, adminName);
		if (!(checkPassword(password, admin.getPassword()))) {
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		if (!(admin.getRoll().equals("Admin"))) {
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		int ans = _si.getNumberOfUserThreads(forumName, userName, adminName, password);
		this._si.closeSession();
		return ans;
	}

	private Object handleThreadCounter(String forumName, String userName, String password) {
		_logMsg = "recieved: GETCOUNT forum"+ forumName;
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		Memberdb admin = _si.getMember(forumName, userName);
		if (!(checkPassword(password, admin.getPassword()))) {
			_logMsg = "as a respond to GETCOUNT- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		if (!(admin.getRoll().equals("Admin"))) {
			_logMsg = "as a respond to GETCOUNT- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		int ans = _si.getNumberOfThreadsInForum(forumName, userName, password);
		_logMsg = "as a respond to GETCOUNT- sending: "+ans;
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.closeSession();
		return ans;
	}

	private Object handleRemoveModerator(String forumName, String subForumName,
			String moderatorName, String userName, String password) {
		_logMsg = "recieved: REMMOD forum"+ forumName +", subForum " +subForumName+", moderator"+moderatorName;
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.openSession();
		Memberdb admin = _si.getMember(forumName, userName);
		Forumdb forum = _si.getForum(forumName);
		if (!(checkPassword(password, admin.getPassword()))) {
			_logMsg = "as a respond to REMMOD- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		if (!(admin.getRoll().equals("Admin"))) {
			_logMsg = "as a respond to REMMOD- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		List<Memberdb> mList = _si.getModerators(forumName, subForumName);
		if (mList.size() <= 1
				&& forum.getEnumCancelModerator().equals("RESTRICTED")) {
			_logMsg = "as a respond to REMMOD- sending: "+ _msgToClient.sendIsTheOnlyModeratorInTheSubForum();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
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
			_logMsg = "as a respond to REMMOD- sending: "+ _msgToClient.sendErrorInServer();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorInServer();
		}
		if (_si.deleteModerator(m, subForumName, moderatorName, userName, password)) {
			_logMsg = "as a respond to REMMOD- sending: "+  _msgToClient.sendOK();
			_reportLogger.log(Level.INFO ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendOK();
		} else {
			_logMsg = "as a respond to REMMOD- sending: "+ _msgToClient.sendErrorInServer();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
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
			_logMsg = "as a respond to NOTI- sending: requested list";
			_reportLogger.log(Level.TRACE ,_logMsg);
			this._si.closeSession();
			return res;
		} else {
			_logMsg = "as a respond to NOTI- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
	}
	//with encryption of the user password
	public Object handleRegister(String forumName, String userName, String password,
			String email) {
		_logMsg = "recieved: REGISTER from "+userName + " forum "+ forumName;
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		if(_si.isValidUserData(forumName, userName, email)){
			Forumdb forum = _si.getForum(forumName);
			if(forum.getEnumSecurityPolicy().equals("NOT_USED_EMAIL")){
				if(!_si.isUniqeEmail(email, forumName)){
					_logMsg = "as a respond to REGISTER- sending: "+_msgToClient.sendErrorNoAuthorized();
					_reportLogger.log(Level.DEBUG ,_logMsg);
					this._si.closeSession();
					return _msgToClient.sendErrorNoAuthorized();
				}
			}
			if(forum.getEnumSecurityPolicy().equals("VERIFY_EMAIL")){
				String code = ((int)Math.random()*1000000)+"";
				try {
					_emailSender.sendCode(email, code);
				} catch (MessagingException e) {
					_logMsg = "as a respond to REGISTER- sending: "+_msgToClient.sendErrorInServer();
					_reportLogger.log(Level.DEBUG ,_logMsg);
					this._si.closeSession();
					return _msgToClient.sendErrorInServer();
				}
				Reactor.addUnverifiedMember(new PairUserForum(userName, forumName), new EmailPassCode(email, password, code));
				
				_logMsg = "as a respond to REGISTER- sending: "+_msgToClient.verification();
				_reportLogger.log(Level.INFO ,_logMsg);
				this._si.closeSession();
				return _msgToClient.verification();
			}

			String encryptedPassword = Encryptor.encrypt(password);
			boolean isAdded = _si.register(forumName, userName, encryptedPassword, email);
			if (isAdded) {
				_logMsg = "as a respond to REGISTER- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendOK();
			} else {
				_logMsg = "as a respond to REGISTER- sending: "+_msgToClient.sendErrorInServer();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendErrorInServer();
			}
		}
		else{
			_logMsg = "as a respond to REGISTER- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
	}

	public Object handleLogin(String forumName, String userName, String password) {
		_logMsg = "recieved: LOGIN from "+userName + " forum "+ forumName;
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		Memberdb member = _si.getMember(forumName, userName);
		if (member==null){
			_logMsg = "as a respond to LOGIN- sending: "+_msgToClient.sendNotFound();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendNotFound();
		}
		if((checkPassword(password, member.getPassword()))){
			boolean logedIn = _si.login(forumName, userName, password);
			if (logedIn) {
				if (member.getRoll().equals("Admin")) {
					_logMsg = "as a respond to LOGIN- sending: "+_msgToClient.sendAdminOK();
					_reportLogger.log(Level.INFO ,_logMsg);
					this._si.closeSession();
					return _msgToClient.sendAdminOK();
				} else if (member.getRoll().equals("Moderator")) {
					_logMsg = "as a respond to LOGIN- sending: "+_msgToClient.sendModeratorOK();
					_reportLogger.log(Level.INFO ,_logMsg);
					this._si.closeSession();
					return _msgToClient.sendModeratorOK();
				} else if (member.getRoll().equals("Member")) {
					_logMsg = "as a respond to LOGIN- sending: "+_msgToClient.sendOK();
					_reportLogger.log(Level.INFO ,_logMsg);
					this._si.closeSession();
					return _msgToClient.sendOK();
				}
			} else {
				_logMsg = "as a respond to LOGIN- sending: "+_msgToClient.sendNotFound();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendNotFound();
			}
		}
		else {
			_logMsg = "as a respond to LOGIN- sending: "+_msgToClient.sendNotFound();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendNotFound();
		}
		_logMsg = "as a respond to LOGIN- sending: null";
		_reportLogger.log(Level.DEBUG ,_logMsg);
		this._si.closeSession();
		return null;
	}

	public Object handleInitiateForum(String forumName, String adminName,
			String adminPassword, String imidiOrAgrePolicy, String notiFriendsPolicy,
			String deletePolicy, String assignModerPolicy, String seniority, String minPublish,
			String cancelModerPolicy, String MessageContentPolicy, String ModeratorPermissionsPolicy,
			String EmailPolicy,String forbiddenWords, String superAdminUserName, String superAdminPassword) {
		_logMsg = "recieved: ADDF";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		boolean isAdded = false;

		isAdded = _si.initiateForum(adminName, adminPassword, forumName, imidiOrAgrePolicy, notiFriendsPolicy,
				deletePolicy, assignModerPolicy, seniority, minPublish, cancelModerPolicy, MessageContentPolicy,
				ModeratorPermissionsPolicy, EmailPolicy, forbiddenWords, superAdminUserName, superAdminPassword);
		if (isAdded) {
			//System.out.println("isAdded");
			_logMsg = "as a respond to ADDF- sending: "+_msgToClient.sendOK();
			_reportLogger.log(Level.INFO ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendOK();
			//System.out.println("Sent ok");
		} else {
			//System.out.println("notAdded");
			_logMsg = "as a respond to ADDF- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
			//System.out.println("Sent ErrorInServer");
		}
	}

	public Object handleAddSubForum(Subforumdb subForum, List<Memberdb> moderatorsToAdd, String username, String password) {
		this._si.openSession();
		boolean subForumIsAdded = false;
		List<String> forbiddenWordsAsList = subForum.getForumdb().forbiddenWordsStringToList();
		for (String forbiddenWord : forbiddenWordsAsList) {
			if (subForum.getSubForumName().contains(forbiddenWord)){
				_logMsg = "as a respond to ADDSF- sending: "+_msgToClient.sendErrorNoAuthorized();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendErrorNoAuthorized();
			}
		}
		subForumIsAdded = _si.addSubForum(subForum, moderatorsToAdd, username, password);
		if (subForumIsAdded) {
			_logMsg = "as a respond to ADDSF- sending: "+_msgToClient.sendOK();
			_reportLogger.log(Level.INFO ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendOK();
		} else {
			_logMsg = "as a respond to ADDSF- sending: "+_msgToClient.sendErrorInServer();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorInServer();
		}
	}

	public Object handlePublishThread(String forumName, String subForumName,
			String posterName, String threadTitle, String threadContent, String password) {
		_logMsg = "recieved: THREAD";
		_reportLogger.log(Level.INFO ,_logMsg);
		this._si.openSession();
		Forumdb forum = _si.getForum(forumName);
		List<String> forbiddenWordsAsList = forum.forbiddenWordsStringToList();
		for (String forbiddenWord : forbiddenWordsAsList) {
			if (threadTitle.contains(forbiddenWord) || threadContent.contains(forbiddenWord)){
				_logMsg = "as a respond to THREAD- sending: "+_msgToClient.sendErrorNoAuthorized();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendErrorNoAuthorized();
			}
		}
		TermVectorStorage storage = new HashMapTermVectorStorage();
		VectorClassifier vc = new VectorClassifier(storage);
		if (forum.getEnumMsgFilter().equals("FILTERED")){
			try {
				StringBuilder sb = new StringBuilder();
				Subforumdb sf = _si.getSubForum(forumName, subForumName);
				sb.append(sf.getSubForumName());
				sb.append(" ");
				Set<Threaddb> setOfThreads = sf.getThreaddbs();
				int numberOfThreads = setOfThreads.size();
				for (Threaddb threaddb : setOfThreads) {
					sb.append(threaddb.getTitle());
					sb.append(" ");
					sb.append(threaddb.getContent());
					sb.append(" ");
				}
				setVCCutOff(vc, numberOfThreads);
				vc.teachMatch(sb.toString());
				if (!vc.isMatch(threadTitle + " " + threadContent)){
					_logMsg = "as a respond to THREAD- sending: "+_msgToClient.sendErrorNoAuthorized();
					_reportLogger.log(Level.DEBUG ,_logMsg);
					this._si.closeSession();
					return _msgToClient.sendErrorNoAuthorized();
				}
			} catch (ClassifierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if(checkPassword(password, _si.getMember(forumName, posterName).getPassword())){
			boolean succeeded = false;
			Threaddb threadM = null;
			Subforumdb sf = _si.getSubForum(forumName, subForumName);
			if (sf != null && _si.memberExistsInForum(forumName, posterName)) {
				Memberdb publisher = _si.getMember(forumName, posterName);
				threadM = new Threaddb(sf, publisher, threadTitle, threadContent, new HashSet<Postdb>());
				succeeded = _si.publishThread(threadM, posterName, password);
			}
			if (succeeded) {
				_notificationsFactory.sendNotifications(threadM, "added");
				Reactor.NotifyAllListeners();
				_logMsg = "as a respond to THREAD- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendOK();
			} else {
				_logMsg = "as a respond to THREAD- sending: "+_msgToClient.sendErrorInServer();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendErrorInServer();
			}
		}
		else{
			_logMsg = "as a respond to THREAD- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
	}

	private void setVCCutOff(VectorClassifier vc, int numberOfMessages) {
		if (numberOfMessages>=0 && numberOfMessages <= 3)
			vc.setMatchCutoff(0.1);
		if (numberOfMessages>3 && numberOfMessages<=10)
			vc.setMatchCutoff(0.4);
		if (numberOfMessages>10 && numberOfMessages<50)
			vc.setMatchCutoff(0.6);
		if (numberOfMessages>50)
			vc.setMatchCutoff(0.85);
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
				_logMsg = "as a respond to DELPST- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				this._si.closeSession();
				return deletePostAndSendOk(p, requester, password);
			}
		}
		if (forum.getEnumDelete().equals("EXTENDED")) {
			Subforumdb subForum = p.getThreaddb().getSubforumdb();
			String subForumName = subForum.getSubForumName();
			List<Memberdb> moderators = _si.getModerators(forumName, subForumName);
			for (Iterator<Memberdb> it = moderators.iterator(); it.hasNext();) {
				Memberdb currModerator = it.next();
				String moderatorName = currModerator.getUserName();
				String moderatorPassword = currModerator.getPassword();
				if (requester.equals(moderatorName) && checkPassword(password, moderatorPassword)) {
					_logMsg = "as a respond to DELPST- sending: "+_msgToClient.sendOK();
					_reportLogger.log(Level.INFO ,_logMsg);
					this._si.closeSession();
					return deletePostAndSendOk(p, moderatorName, moderatorPassword);
				}
			}
		}
		Memberdb admin = _si.getForum(forumName).getMemberdb();
		if (admin != null) {
			String adminName = admin.getUserName();
			String adminPassword = admin.getPassword();
			if (requester.equals(adminName) && checkPassword(password, adminPassword)) {
				_logMsg = "as a respond to DELPST- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				this._si.closeSession();
				return deletePostAndSendOk(p, adminName, adminPassword);
			}
		}
		_logMsg = "as a respond to DELPST- sending: "+_msgToClient.sendErrorNoAuthorized();
		_reportLogger.log(Level.DEBUG ,_logMsg);
		this._si.closeSession();
		return _msgToClient.sendErrorNoAuthorized();
	}

	public Object handlePostComment(Postdb post, String userName, String password) {
		this._si.openSession();

		Subforumdb sf = post.getThreaddb().getSubforumdb();
		Forumdb forum= sf.getForumdb();
		List<String> forbiddenWordsAsList = forum.forbiddenWordsStringToList();
		for (String forbiddenWord : forbiddenWordsAsList) {
			if (post.getTitle().contains(forbiddenWord) || post.getContent().contains(forbiddenWord)){
				_logMsg = "as a respond to POST- sending: "+_msgToClient.sendErrorNoAuthorized();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendErrorNoAuthorized();
			}
		}
		TermVectorStorage storage = new HashMapTermVectorStorage();
		VectorClassifier vc = new VectorClassifier(storage);
		if (forum.getEnumMsgFilter().equals("FILTERED")){
			try {
				StringBuilder sb = new StringBuilder();
				sb.append(sf.getSubForumName());
				sb.append(" ");
				Set<Threaddb> setOfThreads = sf.getThreaddbs();
				int numberOfMessages = setOfThreads.size();
				for (Threaddb threaddb : setOfThreads) {
					List<Postdb> setOfPosts = _si.getAllPosts(forum.getForumName(),
							sf.getSubForumName(), threaddb.getIdthread());
					for (Postdb postdb : setOfPosts) {
						sb.append(postdb.getTitle());
						sb.append(" ");
						sb.append(postdb.getContent());
						sb.append(" ");
						numberOfMessages++;
					}
					sb.append(threaddb.getTitle());
					sb.append(" ");
					sb.append(threaddb.getContent());
					sb.append(" ");
				}

				setVCCutOff(vc, numberOfMessages);
				vc.teachMatch(sb.toString());
				if (!vc.isMatch(post.getTitle() + " " + post.getContent())){
					_logMsg = "as a respond to POST- sending: "+_msgToClient.sendErrorNoAuthorized();
					_reportLogger.log(Level.DEBUG ,_logMsg);
					this._si.closeSession();
					return _msgToClient.sendErrorNoAuthorized();
				}
			} catch (ClassifierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		boolean succeeded = false;
		Object result;
		String publisherPassword = post.getMemberdb().getPassword();
		if(checkPassword(password, publisherPassword)){
			succeeded = _si.postComment(post, userName, password);
			if (succeeded) {
				_logMsg = "as a respond to POST- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				result = _msgToClient.sendOK();
			} else {
				_logMsg = "as a respond to POST- sending: "+_msgToClient.sendErrorInServer();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				result = _msgToClient.sendErrorInServer();
			}
			_notificationsFactory.sendNotifications(post, "added");
			Reactor.NotifyAllListeners();
		}
		else{
			_logMsg = "as a respond to POST- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			result = _msgToClient.sendErrorNoAuthorized();
		}

		this._si.closeSession();
		return result;
	}

	public Object handleGetForumsList() {
		_logMsg = "recieved: GETFL";
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.openSession();
		List<Forumdb> forums = _si.getForumsList();
		List<Forum> ans = new ArrayList<Forum>();
		for (Forumdb forum : forums) {
			ans.add(forum.convertToForum());
		}
		_logMsg = "as a respond to GETFL- sending: requested forum's list";
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.closeSession();
		return ans;
	}

	private Object handleGetAllPosts(String forumName, String subForumName, int threadId) {
		_logMsg = "recieved: GETAP from forum "+ forumName+", subForum "+ subForumName+", threadId "+ threadId;
		_reportLogger.log(Level.TRACE ,_logMsg);

		this._si.openSession();
		List<Postdb> posts = _si.getAllPosts(forumName, subForumName, threadId);
		List<Post> ans = new ArrayList<Post>();
		for (Postdb post : posts) {
			ans.add(post.convertToPost());
		}
		this._si.closeSession();
		_logMsg = "as a respond to GETAP- sending: requested list from forum "+ forumName+", subForum "+ subForumName+", threadId "+ threadId;
		_reportLogger.log(Level.TRACE ,_logMsg);
		return ans;
	}

	public Object handleGetForum(String forumName) {
		_logMsg = "recieved: GETF forum"+ forumName;
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.openSession();
		Forumdb forum = _si.getForum(forumName);
		this._si.closeSession();
		if (forum==null){
			_logMsg = "as a respond to GETF- sending: null";
			_reportLogger.log(Level.DEBUG ,_logMsg);
			return null;
		}
		_logMsg = "as a respond to GETF- sending: requested forum"+ forumName;
		_reportLogger.log(Level.TRACE ,_logMsg);
		return forum.convertToForum();

	}

	public Object handleGetSubForumsList(String forumName) {
		_logMsg = "recieved: GETSFL from forum"+ forumName;
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.openSession();
		List<SubForum> ans = new ArrayList<SubForum>();
		List<Subforumdb> subForums = _si.getSubForumsList(forumName);
		for (Subforumdb subforumdb : subForums) {
			ans.add(subforumdb.convertToSubForum());
		}		
		_logMsg = "as a respond to GETSFL- sending: requested list from forum"+ forumName;
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.closeSession();
		return ans;
	}

	public Object handleGetSubForum(String forum, String subForumName) {
		_logMsg = "recieved: GETSF forum"+ forum +", subForum " +subForumName;
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.openSession();
		Subforumdb sf = _si.getSubForum(forum, subForumName);
		this._si.closeSession();
		if (sf==null){
			_logMsg = "as a respond to GETSF- sending: null";
			_reportLogger.log(Level.DEBUG ,_logMsg);
			return null;
		}
		_logMsg = "as a respond to GETSF- sending: forum"+ forum +", subForum " +subForumName;
		_reportLogger.log(Level.TRACE ,_logMsg);
		return sf.convertToSubForum();
	}

	public Object handleGetThreadsList(String forumName,
			String subForumName) {
		_logMsg = "recieved: GETTL forum"+ forumName +", subForum " +subForumName;
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.openSession();
		List<ThreadMessage> ans = new ArrayList<ThreadMessage>();
		List<Threaddb> threads = _si.getThreadsList(forumName, subForumName);
		for (Threaddb threaddb : threads) {
			ans.add(threaddb.convertToThread());
		}
		_logMsg = "as a respond to GETFL- sending: list of threads from forum"+ forumName +", subForum " +subForumName;
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.closeSession();
		return ans;
	}

	public Object handleGetThreadMessage(String forumName, String subForumName,
			int messageID) {
		_logMsg = "recieved: GETTM forum"+ forumName +", subForum " +subForumName+", messageID"+messageID;
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.openSession();
		Threaddb tm = _si.getThreadMessage(forumName, subForumName, messageID);
		ThreadMessage ans = tm.convertToThread();
		_logMsg = "as a respond to GETFL- sending: requested thread from forum"+ forumName +", subForum " +subForumName+", messageID"+messageID;
		_reportLogger.log(Level.TRACE ,_logMsg);
		this._si.closeSession();
		return ans;
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
				_logMsg = "as a respond to DELTHRD- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				this._si.closeSession();
				return deleteThreadAndSendOk(tm, requester, password);
			}
		}
		if (forum.getEnumDelete().equals("EXTENDED")) {
			Subforumdb subForum = tm.getSubforumdb();
			String subForumName = subForum.getSubForumName();
			List<Memberdb> moderators = _si.getModerators(forumName, subForumName);
			for (Iterator<Memberdb> it = moderators.iterator(); it.hasNext();) {
				Memberdb currModerator = it.next();
				String moderatorName = currModerator.getUserName();
				String moderatorPassword = currModerator.getPassword();
				if (requester.equals(moderatorName) && checkPassword(password, moderatorPassword)) {
					_logMsg = "as a respond to DELTHRD- sending: "+_msgToClient.sendOK();
					_reportLogger.log(Level.INFO ,_logMsg);
					this._si.closeSession();
					return deleteThreadAndSendOk(tm, moderatorName, moderatorPassword);
				}
			}
		}
		Memberdb admin = _si.getForum(forumName).getMemberdb();
		if (admin != null) {
			String adminName = admin.getUserName();
			String adminPassword = admin.getPassword();
			if (requester.equals(adminName) && checkPassword(password, adminPassword)) {
				_logMsg = "as a respond to DELTHRD- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				this._si.closeSession();
				return deleteThreadAndSendOk(tm, adminName, adminPassword);
			}
		}
		_logMsg = "as a respond to DELTHRD- sending: "+_msgToClient.sendErrorNoAuthorized();
		_reportLogger.log(Level.DEBUG ,_logMsg);
		this._si.closeSession();
		return _msgToClient.sendErrorNoAuthorized();
	}

	private Object deletePostAndSendOk(Postdb p, String userName, String password) {
		this._si.openSession();
		_notificationsFactory.sendNotifications(p, "deleted");
		_si.deleteComment(p, userName, password);
		Reactor.NotifyAllListeners();
		this._si.closeSession();
		return _msgToClient.sendOK();
	}

	private Object deleteThreadAndSendOk(Threaddb tm, String userName, String password) {
		this._si.openSession();
		_notificationsFactory.sendNotifications(tm, "deleted");
		_si.deleteThread(tm, userName, password);
		Reactor.NotifyAllListeners();
		this._si.closeSession();
		return _msgToClient.sendOK();
	}

	private Object handleDeleteSubForum(Subforumdb sf, String requester, String password) {
		Memberdb admin = sf.getForumdb().getMemberdb();
		boolean equals = admin.getUserName().equals(requester) && checkPassword(password, admin.getPassword());
		if (equals) {
			_notificationsFactory.sendNotifications(sf, "deleted");
			_si.deleteSubForum(sf, requester, password);
			Reactor.NotifyAllListeners();
			_logMsg = "as a respond to DELSF- sending: "+_msgToClient.sendOK();
			_reportLogger.log(Level.INFO ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendOK();
		}
		_logMsg = "as a respond to DELSF- sending: "+_msgToClient.sendErrorNoAuthorized();
		_reportLogger.log(Level.DEBUG ,_logMsg);
		this._si.closeSession();
		return _msgToClient.sendErrorNoAuthorized();
	}

	private Object handleAddModerator(Subforumdb sf, String moderatorToAdd,
			String userName, String password) {
		String forumName = sf.getForumdb().getForumName();
		Forumdb forum = _si.getForum(forumName);
		Memberdb admin = forum.getMemberdb();
		if (admin == null || !admin.getUserName().equals(userName)
				|| !checkPassword(password, admin.getPassword())) {
			_logMsg = "as a respond to ADDMOD- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		}
		Memberdb member = _si.getMember(forumName, moderatorToAdd);
		if (member == null) {
			_logMsg = "as a respond to ADDMOD- sending: "+_msgToClient.sendNotFound();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendNotFound();
		}

		if (forum.getEnumAssignModerator().equals("MIN_PUBLISH")) {
			System.out.println("in min publish");
			int numberOfUserThreads = _si.getNumberOfUserThreads(forumName, userName, null, null);
			int minPublish = forum.getMinPublish();
			if (numberOfUserThreads < minPublish) {
				_logMsg = "as a respond to ADDMOD- sending: "+_msgToClient.sendErrorNoAuthorized();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendErrorNoAuthorized();//consider change to something more inductive
			}
		}
		if (forum.getEnumAssignModerator().equals("SENIORITY")) {
			long dateOfJoining = Long.parseLong(member.getDateJoin());
			int seniorityAsDays = _si.calcNumOfDaysSinceJoining(dateOfJoining);
			int seniority = forum.getSeniority();
			if (seniorityAsDays < seniority) {
				_logMsg = "as a respond to ADDMOD- sending: "+_msgToClient.sendErrorNoAuthorized();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				this._si.closeSession();
				return _msgToClient.sendErrorNoAuthorized();//consider change to something more inductive
			}
		}

		List<Memberdb> moderators = _si.getModerators(forumName, sf.getSubForumName());
		List<String> moderetorsAsString = new ArrayList<String>();
		for (Iterator<Memberdb> it = moderators.iterator(); it.hasNext();) {
			Memberdb currMember = it.next();
			moderetorsAsString.add(currMember.getUserName());
		}
		if (moderetorsAsString.contains(moderatorToAdd)) {
			_logMsg = "as a respond to ADDMOD- sending: "+_msgToClient.sendOK();
			_reportLogger.log(Level.INFO ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendOK();
		}
		member.setRoll("Moderator");

		boolean addModerator = _si.addModerator(member, sf, member.getUserName(), member.getPassword());
		if (addModerator) {
			_logMsg = "as a respond to ADDMOD- sending: "+_msgToClient.sendOK();
			_reportLogger.log(Level.INFO ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendOK();
		}
		_logMsg = "as a respond to ADDMOD- sending: null";
		_reportLogger.log(Level.DEBUG ,_logMsg);
		this._si.closeSession();
		return null;
	}

	private Object handleDeleteForum(Forumdb fr, String userName, String password) {
		Memberdb superAdmin = _si.getSuperAdmin();
		if (superAdmin == null || !checkPassword(password, superAdmin.getPassword())) {
			_logMsg = "as a respond to DELF- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			return _msgToClient.sendErrorNoAuthorized();
		} else {
			boolean ans = _si.deleteForum(fr.getForumName(), userName, password);
			_logMsg = "as a respond to DELF- sending: "+_msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			this._si.closeSession();
			if(ans){
				_logMsg = "as a respond to DELF- sending: "+_msgToClient.sendOK();
				_reportLogger.log(Level.INFO ,_logMsg);
				return _msgToClient.sendOK();
			}
			else{
				_logMsg = "as a respond to DELF- sending: "+ _msgToClient.sendErrorInServer();
				_reportLogger.log(Level.DEBUG ,_logMsg);
				return _msgToClient.sendErrorInServer();
			}
		}
	}



	private Object handleHasNotification(Memberdb m, String password) {
		if (checkPassword(password, m.getPassword())) {
			boolean ret = false;
			String notifications = m.getNotification();
			if (!(notifications.equals(""))) {
				ret = true;
			}
			_logMsg = "as a respond to HASNOTIF- sending:  "+ret;
			_reportLogger.log(Level.TRACE ,_logMsg);
			return new Boolean(ret);
		} else {
			_logMsg = "as a respond to HASNOTIF- sending: "+ _msgToClient.sendErrorNoAuthorized();
			_reportLogger.log(Level.DEBUG ,_logMsg);
			return _msgToClient.sendErrorNoAuthorized();
		}
	}



	private Object handleListening() {
		_logMsg = "recieved: LISTENING";
		_reportLogger.log(Level.TRACE ,_logMsg);
		boolean SocketsListAdd = Reactor.SocketsListAdd(_socketChannel);
		if (SocketsListAdd) {
			_logMsg = "as a respond to LISTENING- sending:"+_msgToClient.sendOK();
			_reportLogger.log(Level.TRACE ,_logMsg);
			return _msgToClient.sendOK();
		}
		_logMsg = "as a respond to LISTENING- sending: "+_msgToClient.sendErrorInServer();
		_reportLogger.log(Level.DEBUG ,_logMsg);
		return _msgToClient.sendErrorInServer();
	}

	private boolean checkPassword(String password, String encrypted){
		if (Encryptor.checkPassword(password, encrypted)) {
			return true;
		}
		return false;
	}
}
