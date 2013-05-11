/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server;

import Sadna.Client.Admin;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.Server.API.ConnectionHandlerServerInterface;
import Sadna.Server.API.ServerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author fistuk
 */
public class ServerRequestHandler implements Runnable {

	private ConnectionHandlerServerInterface _ch;
	private ServerInterface _si;
	private NotificationsFactory notificationsFactory;

	public ServerRequestHandler(ConnectionHandlerServerInterface ch, ServerInterface si) {
		_ch = ch;
		_si = si;
		this.notificationsFactory = new NotificationsFactory(si);
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {

		while (true) {
			//
			// Read a message sent by client application
			//
			String request = _ch.receiveRequestFromClient();
			if (request != null) {
				if (request.equals("end")) {
					_ch.sendErrorInServer();
					break;
				}
				System.out.println("-- Message Received -- \n" + request);
				parseAndHandleRequest(request);
			} else {
				continue;
			}
		}
		_ch.closeSocket();
		System.out.println("closed connection with a client");

	}

	private void parseAndHandleRequest(String request) {
		String[] parsedReq = request.split("\n");
		SubForum sf;
		ThreadMessage tm;
		Post p;
		switch (parsedReq[0]) {
		case "LOGIN":
			handleLogin(parsedReq[2], parsedReq[4], parsedReq[6]);
			break;
		case "REGISTER":
			handleRegister(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8]);
			break;
		case "GETSF":
			handleGetSubForum(parsedReq[2], parsedReq[4]);
			break;
		case "GETSFL":
			handleGetSubForumsList(parsedReq[2]);
			break;
		case "GETTL":
			handleGetThreadsList(parsedReq[2], parsedReq[4]);
			break;
		case "GETTM":
			handleGetThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
			break;
		case "GETFL":
			handleGetForumsList();
			break;
		case "GETF":
			handleGetForum(parsedReq[2]);
			break;
		case "ADDSF":
			List<Moderator> moderators = new ArrayList<Moderator>();
			for (int i = 8; i < Integer.parseInt(parsedReq[6]); i = i + 2) {
				System.out.println("inside");
				moderators.add(new Moderator(parsedReq[i], "", "", parsedReq[2], null));
			}
			Forum foru = _si.getForum(parsedReq[2]);
			SubForum subF = new SubForum(foru, parsedReq[4]);
			handleAddSubForum(subF, moderators);
			break;
		case "ADDF":
			if (handleInitiateForum(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10])) {
				System.out.println("After add forum");
			}
			break;
		case "GETAP":
			handleGetAllPosts(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
			break;
		case "POST":
			tm = _si.getThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
			p = new Post(tm, parsedReq[10], parsedReq[12], parsedReq[8]);
			handlePostComment(p);
			break;
		case "THREAD":
			handlePublishThread(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10]);
			break;
		case "DELPST":
			p = _si.getPost(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]), Integer.parseInt(parsedReq[8]));
			handleDeletePost(p, parsedReq[10], parsedReq[12]);
			break;
		case "DELTHRD":
			tm = _si.getThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
			handleDeleteThread(tm, parsedReq[8], parsedReq[10]);
			break;
		case "DELSF":
			sf = _si.getSubForum(parsedReq[2], parsedReq[4]);
			handleDeleteSubForum(sf, parsedReq[6], parsedReq[8]);
			break;
		case "DELF":
			//@Todo
			break;
		case "ADDMOD":
			sf = _si.getSubForum(parsedReq[2], parsedReq[4]);
			handleAddModerator(sf, parsedReq[6], parsedReq[8], parsedReq[10]);
			break;
		case "NOTI":
			String forumName = parsedReq[2];
			String userName = parsedReq[4];
			String password = parsedReq[6];
			Member m = _si.getMember(forumName, userName);
			handleNotification(m, password);
			break;
		case "REMMOD":
			handleRemoveModerator(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10]);
			break;
		case "GETCOUNT":
			handleThreadCounter(parsedReq[2], parsedReq[4], parsedReq[6]);
			break;
		case "GETNUMUSRTHR":
			handleGetNumberOfUserThreads(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8]);
			break;
		case "GETUSRSPOSTUSER":
			handleGetUsersPostToUser(parsedReq[2], parsedReq[4], parsedReq[6]);
			break;
		case "GETFRMCOUNT":
			handleForumCounter(parsedReq[2], parsedReq[4]);
			break;
		case "GETCOMMEM":
			handleCommonMembers(parsedReq[2], parsedReq[4]);
			break;
		case "LOGOUT":
			//TO DO
			break;
		case "GETALLMEM":
			handleGetAllMembers(parsedReq[2], parsedReq[4], parsedReq[6]);
		case "LOGINSUPER":
			handleLoginAsSuperAdmin(parsedReq[2], parsedReq[4]);
		default:
			break;
		}

	}
	private void handleLoginAsSuperAdmin(String userName, String password) {
		boolean logedIn = _si.loginAsSuperAdmin(userName, password);
		if (logedIn) {
			Member member = _si.getSuperAdmin();
			if(member != null && member instanceof SuperAdmin)
				_ch.sendSuperAdminOK();
		} else {
			_ch.sendNotFound();
		}
	}

	private void handleGetAllMembers(String forumName, String userName,
			String password) {
		Member admin = _si.getMember(forumName, userName);
		if (!admin.getPassword().equals(password)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		if (!(admin instanceof Admin)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		_ch.sendAllMembers(_si.getAllMembers(forumName));			
	}

	private void handleCommonMembers(String adminName, String password) {
		SuperAdmin superAdmin = _si.getSuperAdmin();
		if (!superAdmin.getPassword().equals(password)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		_ch.sendCommonMembers(_si.getCommonMembers());	
	}

	private void handleForumCounter(String superAdminName, String password) {
		SuperAdmin superAdmin = _si.getSuperAdmin();
		if (!superAdmin.getPassword().equals(password)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		_ch.sendNumberOfForums(_si.getForumCounter());	
	}
	
	
	private void handleGetUsersPostToUser(String forumName, String adminName, String password) {
		Member admin = _si.getMember(forumName, adminName);
		if (!admin.getPassword().equals(password)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		if (!(admin instanceof Admin)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		_ch.sendUsersPostToUser(_si.getUsersPostToUser(forumName));	
	}

	private void handleGetNumberOfUserThreads(String forumName, String userName, String adminName, String password) {
		Member admin = _si.getMember(forumName, adminName);
		if (!admin.getPassword().equals(password)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		if (!(admin instanceof Admin)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		_ch.sendUserThreadsCounter(_si.getNumberOfUserThreads(forumName, _si.getMember(forumName, userName)));	
	}


	private void handleThreadCounter(String forumName, String userName, String password) {
		Member admin = _si.getMember(forumName, userName);
		if (!admin.getPassword().equals(password)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		if (!(admin instanceof Admin)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		_ch.sendThreadCounter(_si.getNumberOfThreadsInForum(forumName));
	}

	private void handleRemoveModerator(String forumName, String subForumName, String moderatorName, String userName, String password) {
		Member admin = _si.getMember(forumName, userName);
		if (!admin.getPassword().equals(password)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		if (!(admin instanceof Admin)) {
			_ch.sendErrorNoAuthorized();
			return;
		}
		List<Member> mList = _si.getModerators(forumName, subForumName);
		if (mList.size() == 1) {
			_ch.sendIsTheOnlyModeratorInTheSubForum();
			return;
		}
		Moderator m = null;
		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(i).getUserName().equals(moderatorName)) {
				m = (Moderator) mList.get(i);
				break;
			}

		}
		if (_si.deleteModerator(m, subForumName)) {
			_ch.sendOK();
		} else {
			_ch.sendErrorInServer();
		}
	}

	private void handleNotification(Member m, String password) {
		if (m.getPassword().equals(password)) {
			String forumName = m.getForum();
			String userName = m.getUserName();
			List<ForumNotification> notifications = _si.getNotifications(forumName, userName);
			_ch.sendNotifications(notifications);
			 m.clearNotifications();
			_si.addMember(m);
		} else {
			_ch.sendErrorNoAuthorized();
		}
	}

	public boolean handleRegister(String forumName, String userName, String password,
			String email) {
		boolean isAdded = false;
		isAdded = _si.register(forumName, userName, password, email);
		if (isAdded) {
			_ch.sendOK();
		} else {
			_ch.sendErrorInServer();
		}
		return isAdded;
	}

	public boolean handleLogin(String forumName, String userName, String password) {
		boolean logedIn = _si.login(forumName, userName, password);
		if (logedIn) {
			Member member = _si.getMember(forumName, userName);
			if(member instanceof Admin)
				_ch.sendAdminOK();
			else if(member instanceof Moderator)
				_ch.sendModeratorOK();
			else if(member instanceof Member)
				_ch.sendOK();
		} else {
			_ch.sendNotFound();
		}
		return logedIn;
	}

	public boolean handleInitiateForum(String forumName, String adminName,
			String adminPassword, String superAdminUserName, String superAdminPassword) {
		boolean isAdded = false;
		isAdded = _si.initiateForum(adminName, adminPassword, forumName, superAdminUserName, superAdminPassword);
		if (isAdded) {
			//System.out.println("isAdded");
			_ch.sendOK();
			//System.out.println("Sent ok");
		} else {
			//System.out.println("notAdded");
			_ch.sendErrorNoAuthorized();
			//System.out.println("Sent ErrorInServer");
		}

		return isAdded;
	}

	public boolean handleAddSubForum(SubForum subForum, List<Moderator> moderators) {
		boolean subForumIsAdded = false;
		subForumIsAdded = _si.addSubForum(subForum, moderators);
		if (subForumIsAdded) {
			_ch.sendOK();
		} else {
			_ch.sendErrorInServer();
		}
		return subForumIsAdded;
	}

	public boolean handlePublishThread(String forumName, String subForumName,
			String posterName, String threadTitle, String threadContent) {
		boolean succeeded = false;
		SubForum sf = _si.getSubForum(forumName, subForumName);
		if (sf != null && _si.memberExistsInForum(forumName, posterName)) {
			ThreadMessage threadM = new ThreadMessage(sf, threadTitle, threadContent, posterName);
			succeeded = _si.publishThread(threadM);
		}
		if (succeeded) {
			_ch.sendOK();
		} else {
			_ch.sendErrorInServer();
		}
		return succeeded;
	}

	public boolean handleDeletePost(Post p, String requester, String password) {
		String publisher = p.getPublisher();
		String forumName = p.getThread().getSubForum().getForum().getForumName();
		Member member = _si.getMember(forumName, publisher);
		if (member != null) {
			String publisherName = member.getUserName();
			String publisherPassword = member.getPassword();
			if (requester.equals(publisherName) && password.equals(publisherPassword)) {
				return deletePostAndSendOk(p);
			}
		}
		SubForum subForum = p.getThread().getSubForum();
		String subForumName = subForum.getSubForumName();
		List<Member> moderators = _si.getModerators(forumName, subForumName);
		for (Iterator<Member> it = moderators.iterator(); it.hasNext();) {
			Member currModerator = it.next();
			String moderatorName = currModerator.getUserName();
			String moderatorPassword = currModerator.getPassword();
			if (requester.equals(moderatorName) && password.equals(moderatorPassword)) {
				return deletePostAndSendOk(p);
			}
		}
		Admin admin = _si.getForum(forumName).getAdmin();
		if (admin != null) {
			String adminName = admin.getUserName();
			String adminPassword = admin.getPassword();
			if (requester.equals(adminName) && password.equals(adminPassword)) {
				return deletePostAndSendOk(p);
			}
		}
		_ch.sendErrorNoAuthorized();
		return false;
	}

	public boolean handlePostComment(Post post) {
		boolean succeeded = false;
		succeeded = _si.postComment(post);
		if (succeeded) {
			_ch.sendOK();
		} else {
			_ch.sendErrorInServer();
		}
		notificationsFactory.sendNotifications(post);
		return succeeded;
	}

	public void handleGetForumsList() {
		_ch.sendForumsList(_si.getForumsList());
	}

	private void handleGetAllPosts(String forumName, String subForumName, int threadId) {
		_ch.sendAllPosts(_si.getAllPosts(forumName, subForumName, threadId));

	}

	public void handleGetForum(String forumName) {
		_ch.sendForum(_si.getForum(forumName));
	}

	public void handleGetSubForumsList(String forumName) {
		_ch.sendSubForumsList(_si.getSubForumsList(forumName));
	}

	public void handleGetSubForum(String forum, String subForumName) {
		_ch.sendSubForum(_si.getSubForum(forum, subForumName));
	}

	public void handleGetThreadsList(String forumName,
			String subForumName) {
		_ch.sendThreadsList(_si.getThreadsList(forumName, subForumName));
	}

	public void handleGetThreadMessage(String forumName, String subForumName,
			int messageID) {
		_ch.sendThreadMeassage(_si.getThreadMessage(forumName, subForumName, messageID));
	}

	/*  For later use..

     private User handleLogout(String forumName, String userName){ 
     return null;
     }
	 */
	 public boolean handleDeleteThread(ThreadMessage tm, String requester, String password) {
		 String publisher = tm.getPublisher();
		 String forumName = tm.getSubForum().getForum().getForumName();
		 Member member = _si.getMember(forumName, publisher);
		 if (member != null) {
			 String publisherName = member.getUserName();
			 String publisherPassword = member.getPassword();
			 if (requester.equals(publisherName) && password.equals(publisherPassword)) {
				 return deleteThreadAndSendOk(tm);
			 }
		 }
		 SubForum subForum = tm.getSubForum();
		 String subForumName = subForum.getSubForumName();
		 List<Member> moderators = _si.getModerators(forumName, subForumName);
		 for (Iterator<Member> it = moderators.iterator(); it.hasNext();) {
			 Member currModerator = it.next();
			 String moderatorName = currModerator.getUserName();
			 String moderatorPassword = currModerator.getPassword();
			 if (requester.equals(moderatorName) && password.equals(moderatorPassword)) {
				 return deleteThreadAndSendOk(tm);
			 }
		 }
		 Admin admin = _si.getForum(forumName).getAdmin();
		 if (admin != null) {
			 String adminName = admin.getUserName();
			 String adminPassword = admin.getPassword();
			 if (requester.equals(adminName) && password.equals(adminPassword)) {
				 return deleteThreadAndSendOk(tm);
			 }
		 }
		 _ch.sendErrorNoAuthorized();
		 return false;
	 }

	 private boolean deletePostAndSendOk(Post p) {
		 notificationsFactory.sendNotifications(p);
		 _si.deleteComment(p);
		 _ch.sendOK();
		 return true;
	 }

	 private boolean deleteThreadAndSendOk(ThreadMessage tm) {
		 notificationsFactory.sendNotifications(tm);
		 _si.deleteThread(tm);
		 _ch.sendOK();
		 return true;
	 }

	 private boolean handleDeleteSubForum(SubForum sf, String requester, String password) {
		 Admin admin = sf.getForum().getAdmin();
		 boolean equals = admin.getUserName().equals(requester) && admin.getPassword().equals(password);
		 if (equals) {
			 notificationsFactory.sendNotifications(sf);
			 _si.deleteSubForum(sf);
			 _ch.sendOK();
			 return true;
		 }
		 _ch.sendErrorNoAuthorized();
		 return false;
	 }

	 private void handleAddModerator(SubForum sf, String moderatorToAdd,
			 String userName, String password) {
		 String forumName = sf.getForum().getForumName();
		 Forum forum = _si.getForum(forumName);
		 Admin admin = forum.getAdmin();
		 if (admin == null || !admin.getUserName().equals(userName)
				 || !admin.getPassword().equals(password)) {
			 _ch.sendErrorNoAuthorized();
			 return;
		 }
		 Member member = _si.getMember(forumName, moderatorToAdd);
		 if (member == null) {
			 _ch.sendNotFound();
			 return;
		 }
		 List<Member> moderators = _si.getModerators(forumName, sf.getSubForumName());
		 List<String> moderetorsAsString = new ArrayList<String>();
		 for (Iterator<Member> it = moderators.iterator(); it.hasNext();) {
			 Member currMember = it.next();
			 moderetorsAsString.add(currMember.getUserName());
		 }
		 if (moderetorsAsString.contains(moderatorToAdd)) {
			 _ch.sendOK();
			 return;
		 }
		 Moderator moderator = new Moderator(member);
		 boolean addModerator = _si.addModerator(moderator, sf);
		 if (addModerator) {
			 _ch.sendOK();
		 }
	 }
}