package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Server.ForumNotification;
import Sadna.db.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientConnectionHandler implements ClientCommunicationHandlerInterface {

	private Socket clientSocket;
	private PrintWriter stringToServer;
	private BufferedReader stringFromServer;
	private ObjectInputStream objectFromServer;
	private String msgToSend;
	private String receivedMsg;
	private String delimiter = "\0";
	private Pattern emailPattern;
	private Matcher emailMatcher;
	private Logger reportLogger;
	private static String XML_LOGGER_PATH = "./log4j.xml";


	private static final String EMAIL_PATTERN =
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	String log;

	public ClientConnectionHandler() {
	}

	public ClientConnectionHandler(String host, int port) {
		try {
			DOMConfigurator.configure(XML_LOGGER_PATH);
			reportLogger = Logger.getLogger(ClientConnectionHandler.class);
			clientSocket = new Socket(host, port);
			stringToServer = new PrintWriter(clientSocket.getOutputStream(), true);
			log = "start Communication";
			reportLogger.log(Level.INFO, log);
			stringToServer.print("HELLO\n" + delimiter);
			stringToServer.flush();
			InputStream inputStream = clientSocket.getInputStream();
			stringFromServer = new BufferedReader(new InputStreamReader(inputStream));
			objectFromServer = new ObjectInputStream(inputStream);
			objectFromServer.readObject();

		} catch (Exception e) {
			log = "start Communication: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
	}

	public static ClientConnectionHandler create(String host, int port) {
		ClientConnectionHandler cch = new ClientConnectionHandler();
		try {
			DOMConfigurator.configure(XML_LOGGER_PATH);
			cch.reportLogger = Logger.getLogger(ClientConnectionHandler.class);
			cch.clientSocket = new Socket(host, port);
			cch.log = "start Communication";
			cch.reportLogger.log(Level.INFO, cch.log);
			cch.stringToServer = new PrintWriter(cch.clientSocket.getOutputStream(), true);
			InputStream inputStream = cch.clientSocket.getInputStream();
			cch.stringFromServer = new BufferedReader(new InputStreamReader(inputStream));
			cch.objectFromServer = new ObjectInputStream(cch.clientSocket.getInputStream());
		} catch (Exception e) {
			cch.log = "start Communication: "+e.getMessage();
			cch.reportLogger.log(Level.ERROR, cch.log);
		}
		return cch;
	}

	@Override
	public boolean finishCommunication() {
		try {
			clientSocket.close();
			String log ="finish Communication";
			reportLogger.log(Level.INFO ,log);
			return true;
		} catch (IOException e) {
			log = "finishCommunication: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		return false;
	}

	@Override
	public Member login(String forumName, String userName, String password) {
		Member loggedInMember = null;
		msgToSend = "LOGIN\n" + "forumName:\n" + forumName + "\n"
		+ "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		log = userName+ " is trying to login to forum "+ forumName +"...";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "login: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "login: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log =userName+ " is logged in to forum"+ forumName +" as member";
			reportLogger.log(Level.INFO ,log);
			loggedInMember = new Member(userName, password, null, forumName, this);
		}
		else if (receivedMsg.contains("201ok")) {
			log =userName+ " is logged in to forum"+ forumName +" as moderator";
			reportLogger.log(Level.INFO ,log);
			loggedInMember = new Moderator(userName, password, null, forumName, this);
		}
		else if (receivedMsg.contains("202ok")) {
			log =userName+ " is logged in to forum"+ forumName +" as admin";
			reportLogger.log(Level.INFO ,log);
			loggedInMember = new Admin(userName, password, null, forumName, this);
		}
		else{
			log = "login: fail";
			reportLogger.log(Level.DEBUG, log);
		}
		return loggedInMember;
	}

	@Override
	public SuperAdmin loginAsSuperAdmin(String userName, String password) {
		SuperAdmin loggedInSuperAdmin = null;
		msgToSend = "LOGINS\n" + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		log =userName+ " is trying to login as superAdmin...\n";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "loginAsSuperAdmin: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "loginAsSuperAdmin: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log =userName+ " is logged in as superAdmin\n";
			reportLogger.log(Level.INFO ,log);
			loggedInSuperAdmin = new SuperAdmin(userName, password, null, this);
		}
		else{
			log = "loginAsSuperAdmin: fail";
			reportLogger.log(Level.DEBUG, log);
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
			log =userName+ " is trying to register to "+ forumName +"\n";
			reportLogger.log(Level.INFO ,log);
			stringToServer.print(msgToSend);
			stringToServer.flush();
			try {
				objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
				receivedMsg = (String) objectFromServer.readObject();
			} catch (IOException e) {
				log = "register: "+e.getMessage();
				reportLogger.log(Level.ERROR, log);
			} catch (ClassNotFoundException e) {
				log = "register: "+e.getMessage();
				reportLogger.log(Level.ERROR, log);
			}
			if (receivedMsg.contains("200ok")) {
				log =userName+ " is logged in to forum"+ forumName +" after a successful registration\n";
				reportLogger.log(Level.INFO ,log);
				loggedInMember = new Member(userName, password, null, forumName, this);
			}
			else{
				log = "register: fail";
				reportLogger.log(Level.DEBUG, log);
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
		log ="a \"getSubForum\" request was sent. forum: "+ forum+ "\n";
		reportLogger.log(Level.TRACE ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			returnedSF = (SubForum) objectFromServer.readObject();
			if(returnedSF==null){
				log = "getSubForum: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = "a \"getSubForum\" was successfuly answered. forum: "+ forum+ "\n";
				reportLogger.log(Level.TRACE ,log);
			}
		} catch (IOException e) {
			log = "register: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "register: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		return returnedSF;
	}

	@Override
	public List<SubForum> getSubForumsList(String forumName) {
		List<SubForum> returnedList = new ArrayList<SubForum>();
		msgToSend = "GETSFL\n" + "forumName:\n" + forumName + "\n";
		msgToSend += delimiter;
		log ="a \"getSubForumsList\" request was sent. forum: "+ forumName+ "\n";
		reportLogger.log(Level.TRACE ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			returnedList = (List<SubForum>) objectFromServer.readObject();
			if(returnedList==null){
				log = "getSubForumsList: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = "a \"getSubForumsList\" was successfuly answered. forum: "+ forumName+ "\n";
				reportLogger.log(Level.TRACE ,log);
			}
		} catch (IOException e) {
			log = "register: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "register: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
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
		log = "a \"getThreadsList\" request was sent. forum: "+ forumName+
		"subForum: "+ subForumName+ "\n";
		reportLogger.log(Level.TRACE ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			returnedList = (List<ThreadMessage>) objectFromServer.readObject();
			if(returnedList==null){
				log = "getThreadsList: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = "a \"getThreadsList\" was successfuly answered. forum: "+ forumName+
				"subForum: "+ subForumName+ "\n";
				reportLogger.log(Level.TRACE ,log);
			}
		} catch (IOException e) {
			log = "getThreadsList: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getThreadsList: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
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
		log = "a \"getThreadMessage\" request was sent. forum: "+ forumName+
		"subForum: "+ subForumName+ "threadID: " + msgID+"\n";
		reportLogger.log(Level.TRACE ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			returnedTM = (ThreadMessage) objectFromServer.readObject();
			if(returnedTM==null){
				log = "getThreadMessage: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = "a \"getThreadMessage\" was successfuly answered. forum: "+ forumName+
				"subForum: "+ subForumName+ "threadID: " + msgID+"\n";
				reportLogger.log(Level.TRACE ,log);
			}
		} catch (IOException e) {
			log = "getThreadMessage: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getThreadMessage: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		return returnedTM;
	}

	@Override
	public boolean postComment(Post post, String password) {
		boolean isPosted = false;
		if (legalMsg(post)) {

			ThreadMessage tm = post.getThread();
			int tmid = tm.getId();
			SubForum sf = tm.getSubForum();
			String sfname = sf.getSubForumName();
			Forum f = sf.getForum();
			String fname = f.getForumName();
			String posterName = post.getPublisher();
			String title = post.getTitle();
			String content = post.getContent();
			msgToSend = "POST\n" + "forumName:\n" + fname + "\n"
			+ "subForumName:\n" + sfname + "\n" + "ThreadMessage:\n" + tmid + "\n"
			+ "posterName:\n" + posterName + "\n" + "postTitle:\n" + title + "\n"
			+ "postContent:\n" + content + "\n" + "password:\n" + password + "\n";
			msgToSend += delimiter;
			log = ""+ posterName+ " is trying to post a comment to "
			+ fname +" ,"+sfname+" ,"+tmid+"\n";
			reportLogger.log(Level.INFO ,log);
			stringToServer.print(msgToSend);
			stringToServer.flush();
			try {
				objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
				receivedMsg = (String) objectFromServer.readObject();
			} catch (IOException e) {
				log = "postComment: "+e.getMessage();
				reportLogger.log(Level.ERROR, log);
			} catch (ClassNotFoundException e) {
				log = "postComment: "+e.getMessage();
				reportLogger.log(Level.ERROR, log);
			}
			if (receivedMsg.contains("200ok")) {
				log = ""+ posterName+ " has successfully posted a comment to "
				+ fname +" ,"+sfname+" ,"+tmid+"\n";
				reportLogger.log(Level.INFO ,log);
				isPosted = true;
			}
			else{
				log = "postComment: fail";
				reportLogger.log(Level.DEBUG ,log);
			}
		}
		return isPosted;
	}

	@Override
	public boolean publishThread(ThreadMessage newThread, String password) {
		boolean isPublished = false;
		if (legalMsg(newThread)) {
			String newThreadTitle = newThread.getTitle();
			String newThreadContent = newThread.getContent();
			SubForum sf = newThread.getSubForum();
			String sfName = sf.getSubForumName();
			Forum f = sf.getForum();
			String fName = f.getForumName();
			String posterName = newThread.getPublisher();
			msgToSend = "THREAD\n" + "forumName:\n" + fName + "\n"
			+ "subForumName:\n" + sfName + "\n" + "posterName:\n" + posterName + "\n"
			+ "threadTitle:\n" + newThreadTitle + "\n"
			+ "threadContent:\n" + newThreadContent + "\n"
			+ "requsterPassword:\n" + password + "\n";
			log = ""+ posterName+ " is trying to publish a new thread to "
			+ fName +" ,"+sfName+"\n";
			reportLogger.log(Level.INFO ,log);
			msgToSend += delimiter;
			stringToServer.print(msgToSend);
			stringToServer.flush();
			try {
				objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
				receivedMsg = (String) objectFromServer.readObject();
			} catch (IOException e) {
				log = "publishThread: "+e.getMessage();
				reportLogger.log(Level.ERROR, log);
			} catch (ClassNotFoundException e) {
				log = "publishThread: "+e.getMessage();
				reportLogger.log(Level.ERROR, log);
			}
			if (receivedMsg.contains("200ok")) {
				log = ""+ posterName+ " has successfully publish a new thread to "
				+ fName +" ,"+sfName+"\n";
				reportLogger.log(Level.INFO ,log);
				isPublished = true;
			}
			else{
				log = "publishThread: fail";
				reportLogger.log(Level.DEBUG, log);
			}
		}
		return isPublished;
	}

	@Override
	public User logout(String forumName, String userName) {
		User loggedOutMember = null;
		msgToSend = "LOGOUT\n" + "forumName:\n" + forumName + "\n"
		+ "userName:\n" + userName + "\n";
		log = ""+ userName+ " is trying to logout from"
		+ forumName +"...\n";
		reportLogger.log(Level.INFO ,log);
		msgToSend += delimiter;
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "logout: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "logout: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = ""+ userName+ "logout successfully from"
			+ forumName +"\n";
			reportLogger.log(Level.INFO ,log);
			loggedOutMember = new User(this);
		}
		else{
			log = "logout: fail";
			reportLogger.log(Level.DEBUG, log);
		}
		return loggedOutMember;
	}

	@Override
	public List<Forum> getForumsList() {
		List<Forum> returnedList = new ArrayList<Forum>();
		msgToSend = "GETFL\n";
		msgToSend += delimiter;
		log = "a \"getForumsList\" request was sent\n";
		reportLogger.log(Level.TRACE ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			returnedList = (List<Forum>) objectFromServer.readObject();
			if(returnedList==null){
				log = "getForumsList: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = "a \"getForumsList\" was successfuly answered\n";
				reportLogger.log(Level.TRACE ,log);
			}
		} catch (IOException e) {
			log = "getForumsList: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getForumsList: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		
		return returnedList;
	}

	@Override
	public Forum getForum(String forumName) {

		Forum returnedSF = null;
		msgToSend = "GETF\n" + "forumName:\n" + forumName + "\n";
		msgToSend += delimiter;
		log = "a \"getForum\" request was sent. forum: "+forumName+"\n";
		reportLogger.log(Level.TRACE ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			returnedSF = (Forum) objectFromServer.readObject();
			if(returnedSF==null){
				log = "getForum: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = "a \"getForum\" was successfuly answered. forum: "+forumName+"\n";
				reportLogger.log(Level.TRACE ,log);
			}
		} catch (IOException e) {
			log = "getForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		
		return returnedSF;
	}

	@Override
	public boolean addSubForum(SubForum subForum, List<Member> lm, String userName, String password) {
		boolean added = false;
		Forum f = subForum.getForum();
		String fName = f.getForumName();
		String sfName = subForum.getSubForumName();
		msgToSend = "ADDSF\n" + "forumName:\n" + fName + "\n"
		+ "subForumName:\n" + sfName + "\n" + "size:\n" + lm.size() + "\n";
		for (Member m : lm) {
			msgToSend += "member:\n" + m.getUserName() + "\n";
		}
		msgToSend += "requester:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		log = ""+ userName+ " is trying to add a new subForum "+sfName+"to "+fName+".\n";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "addSubForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "addSubForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = ""+ userName+ " succeeded to add a new subForum "+sfName+"to "+fName+".\n";
			reportLogger.log(Level.INFO ,log);
			added = true;
		}
		else{
			log = "addSubForum: fail";
			reportLogger.log(Level.DEBUG, log);
		}
		return added;
	}

	@Override
	public boolean initiateForum(String forumName, String adminName, String AdminPassword, Policy policy, String superAdminName, String superAdminPasswaord) {
		boolean initiated = false;
		msgToSend = "ADDF\n" + "forumName:\n" + forumName + "\n"
		+ "adminName:\n" + adminName + "\n" + "adminPassword:\n" + AdminPassword + "\n"
		+ "policy:\n"
		+ policy.getImidOrArgeNotiPolicy() + "\n"
		+ policy.getFriendsNotiPolicy() + "\n"
		+ policy.getDeletePolicy() + "\n"
		+ policy.getAssignModeratorPolicy() + "\n"
		+ policy.getSeniority() + "\n"
		+ policy.getMinPublish() + "\n"
		+ policy.getCancelModeratorPolicy() + "\n"
		+ "superAdminName:\n" + superAdminName + "\n"
		+ "superAdminPasswaord:\n" + superAdminPasswaord + "\n";
		msgToSend += delimiter;
		log = ""+ adminName+ " is trying to initiate a new forum "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "initiateForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "initiateForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = ""+ adminName+ " succeeded to initiate a new forum "+forumName+".\n";
			reportLogger.log(Level.INFO ,log);
			initiated = true;
		}
		else{
			log = "initiateForum: fail";
			reportLogger.log(Level.DEBUG, log);
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
		log = "a \"getAllPosts\" request was sent\n";
		reportLogger.log(Level.TRACE ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			allPosts = (List<Post>) objectFromServer.readObject();
			if(allPosts==null){
				log = "getAllPosts: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = "a \"getAllPosts\" was successfuly answered\n";
				reportLogger.log(Level.TRACE ,log);
			}
		} catch (IOException e) {
			log = "getAllPosts: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getAllPosts: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		return allPosts;
	}

	@Override
	public boolean deleteForum(String forumName, String userName, String password) {
		boolean deleted = false;
		msgToSend = "DELF\n" + "forumName:\n" + forumName + "\n" + "userName:\n"
		+ userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		log = ""+ userName+ " is trying to delete the forum "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "deleteForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "deleteForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = ""+ userName+ " succeeded to delete the forum "+forumName+".\n";
			reportLogger.log(Level.INFO ,log);
			deleted = true;
		}
		else{
			log = "deleteForum: fail";
			reportLogger.log(Level.DEBUG, log);
		}
		return deleted;
	}

	@Override
	public boolean deleteSubForum(String forumName, String subForumName, String userName, String password) {

		boolean deleted = false;
		msgToSend = "DELSF\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n" + "userName:\n"
		+ userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		log = ""+ userName+ " is trying to delete the subforum "+subForumName+
		"from "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "deleteSubForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "deleteSubForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = ""+ userName+ " succeeded to delete the subforum "+subForumName+
			"from "+forumName+".\n";
			reportLogger.log(Level.INFO ,log);
			deleted = true;
		}
		else{
			log = "deleteSubForum: fail";
			reportLogger.log(Level.DEBUG, log);
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
		log = ""+ userName+ " is trying to delete the "+ tmId+" thread from "+subForumName+
		", "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		msgToSend += delimiter;
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "deleteThreadMessage: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "deleteThreadMessage: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = ""+ userName+ " succeed to delete the "+ tmId+" thread from "+subForumName+
			", "+forumName+".\n";
			reportLogger.log(Level.INFO ,log);
			deleted = true;
		}
		else{
			log = "deleteThreadMessage: fail";
			reportLogger.log(Level.DEBUG, log);
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
		+ "threadId:\n" + tmId + "\n" + "postId:\n" + pId + "\n" + "editor:\n" + userName + "\n"
		+ "password:\n" + password + "\n";
		log = ""+ userName+ " is trying to delete the "+ pId+" post from "+tmId+", "+subForumName+
		", "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		msgToSend += delimiter;
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "deletePost: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "deletePost: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = ""+ userName+ " succeed to delete the "+ pId+" post from "+tmId+", "+subForumName+
			", "+forumName+".\n";
			reportLogger.log(Level.INFO ,log);
			deleted = true;
		}
		else{
			log = "deletePost: fail";
			reportLogger.log(Level.DEBUG, log);
		}
		return deleted;
	}

	@Override
	public boolean editThread(ThreadMessage newTM, String editorName, String editorPassword) {
		boolean edited = false;
		SubForum subForum = newTM.getSubForum();
		String subForumName = subForum.getSubForumName();
		Forum forum = subForum.getForum();
		String forumName = forum.getForumName();
		int tid = newTM.getId();
		String title = newTM.getTitle();
		String content = newTM.getContent();
		msgToSend = "EDTTHRD\n" + "forumName:\n" + forumName + "\n"
		+ "subForumName:\n" + subForumName + "\n" + "threadId:\n" + tid + "\n" + "threadTitle:\n" + title + "\n"
		+ "threadContent:\n" + content + "\n" + "editorName:\n" + editorName + "\n" + "editorPassword:\n" + editorPassword + "\n";
		msgToSend += delimiter;
		log = ""+ editorName+ " is trying to edit the "+ tid+" thread from "+subForumName+
		", "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "editThread: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "editThread: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = ""+ editorName+ " succeed to delete the "+ tid+" thread from "+subForumName+
			", "+forumName+".\n";
			reportLogger.log(Level.INFO ,log);
			edited = true;
		}
		else{
			log = "editThread: fail";
			reportLogger.log(Level.DEBUG, log);
		}
		return edited;
	}

	@Override
	public boolean editPost(Post newP, String editorName, String editorPassword) {
		boolean edited = false;
		ThreadMessage newTM = newP.getThread();
		int TMid = newTM.getId();
		int pID = newP.getId();
		SubForum subForum = newTM.getSubForum();
		String subForumName = subForum.getSubForumName();
		Forum forum = subForum.getForum();
		String forumName = forum.getForumName();
		String title = newP.getTitle();
		String content = newP.getContent();
		msgToSend = "EDTPST\n" + "forumName:\n" + forumName + "\n"
		+ "subForumName:\n" + subForumName + "\n" + "threadId:\n" + TMid + "\n"
		+ "postId:\n" + pID + "\n"+ "postTitle:\n" + title + "\n"
		+ "postContent:\n" + content + "\n" + "editorName:\n" + editorName + "\n"
		+ "editorPassword:\n" + editorPassword + "\n";
		log = ""+ editorName+ " is trying to edit the "+ pID+" post from "
		+TMid+", "+subForumName+", "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		msgToSend += delimiter;
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "editPost: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "editPost: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = ""+ editorName+ " succeed to edit the "+ pID+" post from "
			+TMid+", "+subForumName+", "+forumName+".\n";
			reportLogger.log(Level.INFO ,log);
			edited = true;
		}
		else{
			log = "editPost: fail";
			reportLogger.log(Level.DEBUG, log);
		}
		return edited;
	}

	@Override
	public boolean addModerator(String forumName, String subForumName,
			String newModerator, String userName, String password) {
		boolean added = false;
		msgToSend = "ADDMOD\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n"
		+ "newModerator:\n" + newModerator + "\n"
		+ "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		log = ""+ userName+ " is trying to add"+ newModerator +
		" as a new moderator to "+subForumName+", "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "addModerator: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "addModerator: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg != null && receivedMsg.contains("200ok")) {
			log = ""+ userName+ " succeed to add"+ newModerator +
			" as a new moderator to "+subForumName+", "+forumName+"...\n";
			reportLogger.log(Level.INFO ,log);
			added = true;
		}
		else{
			log = "addModerator: fail";
			reportLogger.log(Level.DEBUG, log);
		}
		return added;
	}

	@Override
	public List<ForumNotification> getNotification(String forumName, String userName, String password) {
		List<ForumNotification> notifications = null;
		msgToSend = "NOTI\n" + "forumName:\n" + forumName + "\n"
		+ "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		log = "a \"getNotification\" request was sent\n";
		reportLogger.log(Level.TRACE ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			notifications = (List<ForumNotification>) objectFromServer.readObject();
			if(notifications==null){
				log = "getNotification: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = "a \"getNotification\" request was successfuly answered\n";
				reportLogger.log(Level.TRACE ,log);
			}
		} catch (IOException e) {
			log = "getNotification: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getNotification: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
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
		log = ""+ userName+ " is trying to remove"+ moderatorName +
		" as a moderator from "+subForum+", "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "removeModerator: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "removeModerator: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = ""+ userName+ " succeed to remove"+ moderatorName +
			" as a moderator from "+subForum+", "+forumName+"...\n";
			reportLogger.log(Level.INFO ,log);
			remove = true;
		}
		else{
			log = "removeModerator: fail";
			reportLogger.log(Level.DEBUG, log);
		}
		return remove;
	}

	@Override
	public int getNumOfThreadsInForum(String forumName, String userName, String password) {
		int counter = 0;
		msgToSend = "GETCOUNT\n" + "forumName:\n" + forumName + "\n" + "userName:\n" + userName + "\n"
		+ "password:\n" + password + "\n";
		log = ""+ userName+ " is trying to get the number of threads in forum "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		msgToSend += delimiter;
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = objectFromServer.readObject() + "";
			if(receivedMsg==null){
				log = "getNumOfThreadsInForum: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = ""+ userName+ " succeed to get the number of threads in forum "+forumName+".\n";
				reportLogger.log(Level.INFO ,log);
			}
		} catch (IOException e) {
			log = "getNumOfThreadsInForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getNumOfThreadsInForum: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		counter = Integer.parseInt(receivedMsg);
		return counter;
	}

	@Override
	public int getNumOfUserThreads(String forumName, String userName,
			String requester, String password) {
		int counter = 0;
		msgToSend = "GETNUT\n" + "forumName:\n" + forumName + "\n"
		+ "userName:\n" + userName + "\n" + "requester:\n" + requester + "\n"
		+ "passwordRequester:\n" + password + "\n";
		log = ""+ requester+ " is trying to get the number of threads that"
		+userName+ " published in "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		msgToSend += delimiter;
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = objectFromServer.readObject() + "";
			if(receivedMsg==null){
				log = "getNumOfUserThreads: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = ""+ requester+ " succeed to get the number of threads that"
				+userName+ " published in "+forumName+".\n";
				reportLogger.log(Level.INFO ,log);
			}
		} catch (IOException e) {
			log = "getNumOfUserThreads: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getNumOfUserThreads: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		counter = Integer.parseInt(receivedMsg);
		return counter;
	}

	@Override
	public int getNumOfForums(String userName, String password) {
		int counter = 0;
		msgToSend = "GETNF\n" + "\n" + "userName:\n" + userName + "\n"
		+ "password:\n" + password + "\n";
		msgToSend += delimiter;
		log = ""+ userName+ " is trying to get the number of forums...\n";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = objectFromServer.readObject() + "";
			if(receivedMsg==null){
				log = "getNumOfForums: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = ""+ userName+ " succeed to get the number of forums.\n";
				reportLogger.log(Level.INFO ,log);
			}
		} catch (IOException e) {
			log = "getNumOfForums: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getNumOfForums: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
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
		msgToSend = "GETCOM\n" + "userName:\n"
		+ superAdminName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		log = ""+ superAdminName+ " is trying to get the number of user " +
		"which published in more than one thread...\n";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			map = (List<String>) objectFromServer.readObject();
			if(map==null){
				log = "getCommonMembers: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = ""+ superAdminName+ " succeed to get the number of user " +
				"which published in more than one thread.\n";
				reportLogger.log(Level.INFO ,log);
			}
		} catch (IOException e) {
			log = "getCommonMembers: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getCommonMembers: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		return map;
	}

	@Override
	public List<Member> getAllForumMembers(String forum, String userName,
			String password) {
		List<Member> members = null;
		msgToSend = "GETAM\n" + "forum:\n"
		+ forum + "\n" + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		log = ""+ userName+ " is trying to get the number of users in "+forum+"...\n";
		reportLogger.log(Level.INFO ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			members = (List<Member>) objectFromServer.readObject();
			if(members==null){
				log = "getAllForumMembers: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = ""+ userName+ " succeed to get the number of users in "+forum+".\n";
				reportLogger.log(Level.INFO ,log);
			}
		} catch (IOException e) {
			log = "getAllForumMembers: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getAllForumMembers: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		return members;
	}

	@Override
	public HashMap<String, List<String>> getUsersPostToUser(String forumName,
			String userName, String password) {
		HashMap<String, List<String>> map = null;
		msgToSend = "GETUPU\n" + "forumName:\n" + forumName + "\n" + "userName:\n"
		+ userName + "\n" + "password:\n" + password + "\n";
		log = ""+ userName+ " is trying to get a list of users," +
		" which replyed to other users in "+forumName+"...\n";
		reportLogger.log(Level.INFO ,log);
		msgToSend += delimiter;
		stringToServer.print(msgToSend);
		stringToServer.flush();
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			map = (HashMap<String, List<String>>) objectFromServer.readObject();
			if(map==null){
				log = "getUsersPostToUser: fail";
				reportLogger.log(Level.DEBUG, log);
			}
			else{
				log = ""+ userName+ " succeed to get a list of users," +
				" which replyed to other users in "+forumName+".\n";
				reportLogger.log(Level.INFO ,log);
			}
		} catch (IOException e) {
			log = "getUsersPostToUser: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "getUsersPostToUser: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		return map;
	}

	@Override
	public boolean listenToServer() {
		boolean remove = false;
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());

			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "listenToServer: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "listenToServer: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = "listenToServer\n";
			reportLogger.log(Level.INFO ,log);
			remove = true;
		}
		else{
			log = "listenToServer: fail";
			reportLogger.log(Level.DEBUG, log);
		}
		return remove;

	}

	@Override
	public boolean sendListenerIdentifier() {
		String msg = "LISTENING\n\0";
		this.stringToServer.print(msg);
		this.stringToServer.flush();
		boolean remove = false;
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			receivedMsg = (String) objectFromServer.readObject();
		} catch (IOException e) {
			log = "sendListenerIdentifier: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "sendListenerIdentifier: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		if (receivedMsg.contains("200ok")) {
			log = "sendListenerIdentifier\n";
			reportLogger.log(Level.INFO ,log);
			remove = true;
		}
		else{
			log = "sendListenerIdentifier: fail";
			reportLogger.log(Level.DEBUG, log);
		}
		return remove;
	}

	@Override
	public boolean hasNotifications(String forum, String userName, String password) {
		msgToSend = "HASNOTIF\n" + "forumName:\n" + forum + "\n"
		+ "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
		msgToSend += delimiter;
		log = userName+" is ask if there are notifications for him\n";
		reportLogger.log(Level.TRACE ,log);
		stringToServer.print(msgToSend);
		stringToServer.flush();
		Object recieved = null;
		Boolean hasNotifications = null;
		try {
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
			recieved = objectFromServer.readObject();
			if(recieved==null || recieved instanceof String){
				log = "hasNotifications: fail";
				reportLogger.log(Level.DEBUG, log);
				return false;
			}
			else{
				log = userName+" got answer if there are notifications for him\n";
				reportLogger.log(Level.TRACE ,log);
			}
			hasNotifications = (Boolean) recieved;
		} catch (IOException e) {
			log = "hasNotifications: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		} catch (ClassNotFoundException e) {
			log = "hasNotifications: "+e.getMessage();
			reportLogger.log(Level.ERROR, log);
		}
		return hasNotifications.booleanValue();

	}
}
