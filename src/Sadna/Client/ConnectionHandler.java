package Sadna.Client;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.io.*;
import java.net.*;


import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Server.ForumNotification;
import Sadna.db.Forum;
import Sadna.db.Message;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionHandler implements ClientCommunicationHandlerInterface {

	private Socket clientSocket;
	private PrintWriter stringToServer;
	private BufferedReader stringFromServer;
	private ObjectInputStream objectFromServer;
	private String msgToSend;
	private String receivedMsg;
	private String delimiter = "\0";
	private Pattern emailPattern;
	private Matcher emailMatcher;
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


	// removeModerator()
	public ConnectionHandler(String host, int port) {
		try {
			clientSocket = new Socket(host, port);
			stringToServer = new PrintWriter(clientSocket.getOutputStream(), true);
			InputStream inputStream = clientSocket.getInputStream();
			stringFromServer = new BufferedReader(new InputStreamReader(inputStream));
			objectFromServer = new ObjectInputStream(inputStream);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Got exception in ConnectionHandler constructor");
		}
	}

	@Override
	public boolean finishCommunication() {
		try {
			clientSocket.close();
			return true;
		} catch (IOException ex) {
			String message = ex.getMessage();
			System.out.println(message);
		}
		return false;
	}

	@Override
	public Member login(String forumName, String userName, String password) {
		Member loggedInMember = null;
		msgToSend = "LOGIN\n" + "forumName:\n" + forumName + "\n"
				+ "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg.contains("200ok")) {
			loggedInMember = new Member(userName, password, null, forumName, this);
		}
		if (receivedMsg.contains("201ok")) {
			loggedInMember = new Moderator(userName, password, null, forumName, this);
		}
		if (receivedMsg.contains("202ok")) {
			loggedInMember = new Admin(userName, password, null, forumName, this);
		}
		return loggedInMember;
	}

	@Override
	public SuperAdmin loginAsSuperAdmin(String userName, String password) {
		SuperAdmin loggedInSuperAdmin = null;
		msgToSend = "LOGINSUPER\n" + "forumName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg.contains("200ok")) {
			loggedInSuperAdmin = new SuperAdmin(userName, password, null, this);
		}
		return loggedInSuperAdmin;
	}

	@Override
	public Member register(String forumName, String userName, String password,
			String email) {
		Member loggedInMember = null;
		if (passwordValidity(password)) {
			msgToSend = "REGISTER\n" + "forumName:\n" + forumName + "\n" + "userName:\n" + userName + "\n"
					+ "password:\n" + password + "\n" + "email:\n" + email + "\n";
			msgToSend += delimiter;
			stringToServer.println(msgToSend);
			try {
				receivedMsg = stringFromServer.readLine();
			} catch (IOException e) {
			}
			if (receivedMsg.contains("200ok")) {
				loggedInMember = new Member(userName, password, null, forumName, this);
			}
		}
		return loggedInMember;
	}

	private boolean passwordValidity(String password) {
		return (password.length() <= 16 && password.length() >= 8 && range(password));
	}

	private boolean range(String password) {
		boolean charFlag = false;
		boolean numFlag = false;
		char currchar;
		for (int i = 0; i < password.length() && !(numFlag && charFlag); i++) {
			currchar = password.charAt(i);
			if (currchar >= '0' && currchar <= '9') {
				numFlag = true;
			}
			if ((currchar >= 'a' && currchar <= 'z') || (currchar >= 'A' && currchar <= 'Z')) {
				charFlag = true;
			}
		}
		return (charFlag && numFlag);
	}

	public boolean EmailValidator(String email) {
		emailPattern = Pattern.compile(EMAIL_PATTERN);
		emailMatcher = emailPattern.matcher(email);
		return emailMatcher.matches();
	}

	@Override
	public SubForum getSubForum(String forum, String subForumName) {
		SubForum returnedSF = null;
		msgToSend = "GETSF\n" + "forumName:\n" + forum + "\n"
				+ "subForumName:\n" + subForumName + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			returnedSF = (SubForum) objectFromServer.readObject();
			if (returnedSF == null) {
				System.out.println("111");
			}
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return returnedSF;
	}

	@Override
	public List<SubForum> getSubForumsList(String forumName) {
		List<SubForum> returnedList = new ArrayList<SubForum>();
		msgToSend = "GETSFL\n" + "forumName:\n" + forumName + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			returnedList = (List<SubForum>) objectFromServer.readObject();
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		return returnedList;
	}

	@Override
	public List<ThreadMessage> getThreadsList(String forumName,
			String subForumName) {
		List<ThreadMessage> returnedList = new ArrayList<ThreadMessage>();
		ThreadMessage tmp = null;
		msgToSend = "GETTL\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			returnedList = (List<ThreadMessage>) objectFromServer.readObject();
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		return returnedList;
	}

	@Override
	public ThreadMessage getThreadMessage(String forumName,
			String subForumName, int msgID) {
		ThreadMessage returnedTM = null;
		msgToSend = "GETTM\n" + "forumName:\n" + forumName + "\n"
				+ "subForumName:\n" + subForumName + "\n" + "treadMessageID:\n" + msgID + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			returnedTM = (ThreadMessage) objectFromServer.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return returnedTM;
	}

	@Override
	public boolean postComment(Post post) {
		boolean isPosted = false;
		if (legalMsg(post)) {
			ThreadMessage tm = post.getThread();
			SubForum sf = tm.getSubForum();
			Forum f = sf.getForum();
			String posterName = post.getPublisher();
			msgToSend = "POST\n" + "forumName:\n" + f.getForumName() + "\n"
					+ "subForumName:\n" + sf.getSubForumName() + "\n" + "ThreadMessage:\n" + tm.getId() + "\n"
					+ "posterName:\n" + posterName + "\n" + "postTitle:\n" + post.getTitle() + "\n"
					+ "postContent:\n" + post.getContent() + "\n";
			msgToSend += delimiter;
			stringToServer.println(msgToSend);
			try {
				receivedMsg = stringFromServer.readLine();
			} catch (IOException e) {
			}
			if (receivedMsg.contains("200ok")) {
				isPosted = true;
			}
		}
		return isPosted;
	}

	@Override
	public boolean publishThread(ThreadMessage newThread) {
		boolean isPublished = false;
		if (legalMsg(newThread)) {
			SubForum sf = newThread.getSubForum();
			Forum f = sf.getForum();
			String posterName = newThread.getPublisher();
			msgToSend = "THREAD\n" + "forumName:\n" + f.getForumName() + "\n"
					+ "subForumName:\n" + sf.getSubForumName() + "\n" + "posterName:\n" + posterName + "\n" + "threadTitle:\n" + newThread.getTitle() + "\n"
					+ "threadContent:\n" + newThread.getContent() + "\n";
			msgToSend += delimiter;
			stringToServer.println(msgToSend);
			try {
				receivedMsg = stringFromServer.readLine();
			} catch (IOException e) {
			}
			if (receivedMsg.contains("200ok")) {
				isPublished = true;
			}
		}
		return isPublished;
	}

	@Override
	public User logout(String forumName, String userName) {
		User loggedOutMember = null;
		msgToSend = "LOGOUT\n" + "forumName:\n" + forumName + "\n"
				+ "userName:\n" + userName + "\n";
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg.contains("200ok")) {
			loggedOutMember = new User(this);
		}
		return loggedOutMember;
	}

	@Override
	public List<Forum> getForumsList() {
		List<Forum> returnedList = new ArrayList<Forum>();
		msgToSend = "GETFL\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			returnedList = (List<Forum>) objectFromServer.readObject();
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
		}
		return returnedList;
	}

	@Override
	public Forum getForum(String forumName) {

		Forum returnedSF = null;
		msgToSend = "GETF\n" + "forumName:\n" + forumName + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			returnedSF = (Forum) objectFromServer.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return returnedSF;
	}

	@Override
	public boolean addSubForum(SubForum subForum, List<Moderator> lm) {
		boolean added = false;
		Forum f = subForum.getForum();
		msgToSend = "ADDSF\n" + "forumName:\n" + f.getForumName() + "\n"
				+ "subForumName:\n" + subForum.getSubForumName() + "\n" + "size:\n" + lm.size() + "\n";
		for (Moderator md : lm) {
			msgToSend += "moderator:\n" + md.getUserName() + "\n";
		}
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg.contains("200ok")) {
			added = true;
		}
		return added;
	}

	@Override
	public boolean initiateForum(String forumName, String adminName, String AdminPassword, String superAdminName, String superAdminPasswaord) {
		boolean initiated = false;
		msgToSend = "ADDF\n" + "forumName:\n" + forumName + "\n"
				+ "adminName:\n" + adminName + "\n" + "adminPassword:\n" + AdminPassword + "\n"
				+ "superAdminName:\n" + superAdminName+"\n"
				+ "superAdminPasswaord:\n" + superAdminPasswaord+"\n";
				
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg.contains("200ok")) {
			initiated = true;
		}
		return initiated;
	}

	private boolean legalMsg(Message m) {
		return m.getContent().length() <= 1000;
	}

	@Override
	public List<Post> getAllPosts(ThreadMessage tm) {
		List<Post> allPosts = null;
		SubForum subForum = tm.getSubForum();
		Forum forum = subForum.getForum();
		String forumName = forum.getForumName();
		String subForumName = subForum.getSubForumName();
		int threadID = tm.getId();
		msgToSend = "GETAP\n" + "forumName:\n" + forumName + "\n"
				+ "SubForumName:\n" + subForumName + "\n" + "TheadID:\n" + threadID + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			allPosts = (List<Post>) objectFromServer.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return allPosts;
	}

	@Override
	public boolean deleteForum(String forumName, String userName, String password) {
		boolean deleted = false;
		msgToSend = "DELF\n" + "forumName:\n" + forumName + "\n" + "userName:\n"
				+ userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg.contains("200ok")) {
			deleted = true;
		}
		return deleted;
	}

	@Override
	public boolean deleteSubForum(String forumName, String subForumName, String userName, String password) {

		boolean deleted = false;
		msgToSend = "DELSF\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n" + "userName:\n"
				+ userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg.contains("200ok")) {
			deleted = true;
		}
		return deleted;
	}

	@Override
	public boolean deleteThreadMessage(ThreadMessage tm, String userName, String password) {
		boolean deleted = false;
		SubForum subForum = tm.getSubForum();
		String subForumName = subForum.getSubForumName();
		Forum forum = subForum.getForum();
		String forumName = forum.getForumName();
		int tmId = tm.getId();
		msgToSend = "DELTHRD\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n"
				+ "threadId:\n" + tmId + "\n" + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg.contains("200ok")) {
			deleted = true;
		}
		return deleted;
	}

	@Override
	public boolean deletePost(Post p, String userName, String password) {
		boolean deleted = false;
		int pId = p.getId();
		ThreadMessage tm = p.getThread();
		SubForum subForum = tm.getSubForum();
		String subForumName = subForum.getSubForumName();
		Forum forum = subForum.getForum();
		String forumName = forum.getForumName();
		int tmId = tm.getId();
		msgToSend = "DELPST\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n"
				+ "threadId:\n" + tmId + "\n" + "postId:\n" + pId + "\n" + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg.contains("200ok")) {
			deleted = true;
		}
		return deleted;
	}

	@Override
	public boolean editThread(ThreadMessage tm, String newTest,
			String userName, String password) {
		boolean edited = false;
		SubForum subForum = tm.getSubForum();
		String subForumName = subForum.getSubForumName();
		Forum forum = subForum.getForum();
		String forumName = forum.getForumName();
		int tmId = tm.getId();
		msgToSend = "EDTTHRD\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n"
				+ "threadId:\n" + tmId + "\n" + "newText:\n" + newTest + "\n" + "userName:\n" + userName
				+ "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg.contains("200ok")) {
			edited = true;
		}
		return edited;
	}

	@Override
	public boolean editPost(Post p, String newText, String userName,
			String password) {
		boolean deleted = false;
		int pId = p.getId();
		ThreadMessage tm = p.getThread();
		SubForum subForum = tm.getSubForum();
		String subForumName = subForum.getSubForumName();
		Forum forum = subForum.getForum();
		String forumName = forum.getForumName();
		int tmId = tm.getId();
		msgToSend = "DELPST\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n"
				+ "threadId:\n" + tmId + "\n" + "postId:\n" + pId + "newText:\n" + newText + "\n" + "userName:\n"
				+ userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg.contains("200ok")) {
			deleted = true;
		}
		return deleted;
	}

	@Override
	public boolean addModerator(String forumName, String subForumName,
			String newModerator, String userName, String password) {
		boolean added = false;
		msgToSend = "ADDMOD\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n"
				+ "newModerator:\n" + newModerator + "\n" + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
		}
		if (receivedMsg != null && receivedMsg.contains("200ok")) {
			added = true;
		}
		return added;
	}

	@Override
	public List<ForumNotification> getNotification(String forumName, String userName, String password) {
		List<ForumNotification> notifications = null;
		msgToSend = "NOTI\n" + "forumName:\n" + forumName + "\n" + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			notifications = (List<ForumNotification>) objectFromServer.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return notifications;
	}

	@Override
	public boolean removeModerator(String forumName, String subForum, 
			String moderatorName, String userName, String password) {
		boolean remove = false;
		msgToSend = "REMMOD\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForum + "\n" 
				+ "moderatorName:\n" + moderatorName + "\n" + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
			System.out.println("ConnectionHandler(removeModerator) " + e);
		}
		if (receivedMsg.contains("200ok")) {
			remove = true;
		}
		return remove;
	}
	@Override
	public int getThreadCounter(String forumName, String userName, String password) {
		int counter = 0;
		msgToSend = "GETTHRCOUNT\n" + "forumName:\n" + forumName + "\n" + "userName:\n" + userName + "\n"
				+ "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
			System.out.println("ConnectionHandler(getThreadCounter) " + e);
		}
		counter = Integer.parseInt(receivedMsg);
		return counter;
	}
	@Override
	public int getNumOfUserThreads(String forumName, String generalUserName, 
			String userName, String password) {
		int counter = 0;
		msgToSend = "GETNUMUSRTHR\n" + "forumName:\n" + forumName + "\n" + "generalUserName:\n" + generalUserName + "\n" + "userName:\n" + userName + "\n"
				+ "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
			System.out.println("ConnectionHandler(getNumOfUserThread) " + e);
		}
		counter = Integer.parseInt(receivedMsg);
		return counter;
	}

	/*
	 * MAP(username:String, users that post:Vector<String>)
	 */
	@Override
	public List<List<String>> getUsersPostToUser(String forumName, 
			String userName, String password) {
		List<List<String>> map = null;
		msgToSend = "GETUSRSPOSTUSER\n" + "forumName:\n" + forumName + "\n"+ "userName:\n" 
				+ userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			map = (List<List<String>>)objectFromServer.readObject();
		} catch (IOException e) {
			System.out.println("ConnectionHandler(getUsersPostToUser) " + e);
		} catch (ClassNotFoundException e) {
			System.out.println("ConnectionHandler(getUsersPostToUser) error in objectFromServer " + e);
		}
		return map;
	}
	@Override
	public int getForumCounter(String userName, String password) {
		int counter = 0;
		msgToSend = "GETFRMCOUNT\n" + "\n" + "userName:\n" + userName + "\n"
				+ "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			receivedMsg = stringFromServer.readLine();
		} catch (IOException e) {
			System.out.println("ConnectionHandler(getForumCounter) " + e);
		}
		counter = Integer.parseInt(receivedMsg);
		return counter;
	}

	/*
	 * MAP(forum name:String, number of members in forum:Integer)
	 */

	@Override
	public List<String> getCommonMembers(String superAdminName, 
			String password) {
		List<String> map = null;
		msgToSend = "GETCOMMEM\n" + "\n"+ "userName:\n" 
				+ superAdminName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			map = (List<String>) objectFromServer.readObject();
		} catch (IOException e) {
			System.out.println("ConnectionHandler(getNumMembersInEachForum) " + e);
		} catch (ClassNotFoundException e) {
			System.out.println("ConnectionHandler(getNumMembersInEachForum) " +
					"error in objectFromServer " + e);
		}
		return map;
	}

	@Override
	public List<Member> getAllForumMembers(String forum, String userName,
			String password) {
		List<Member> members = null;
		msgToSend = "GETALLMEM\n" + "\n"+ "forum:\n" 
		+ forum +"\n"+ "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {
			members = (List<Member>) objectFromServer.readObject();
		} catch (IOException e) {
			System.out.println("ConnectionHandler(getNumMembersInEachForum) " + e);
		} catch (ClassNotFoundException e) {
			System.out.println("ConnectionHandler(getNumMembersInEachForum) " +
					"error in objectFromServer " + e);
		}
		return members;
	}
	//re
} 
