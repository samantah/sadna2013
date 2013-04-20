package Sadna.Client;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.*;


import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.Forum;
import Sadna.db.Message;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
 
public class ConnectionHandler implements ClientCommunicationHandlerInterface{
	private Socket clientSocket;
	private PrintWriter stringToServer;
	private BufferedReader stringFromServer;
	private ObjectInputStream objectFromServer;
	private String msgToSend;
	private String receivedMsg;
	private String delimiter = "\0";
	
	public ConnectionHandler(String host, int port){
		try{
			clientSocket = new Socket(host, port);
			stringToServer = new PrintWriter(clientSocket.getOutputStream(), true);
			stringFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
		}catch(Exception e){
			System.out.println("Got exception in ConnectionHandler constructor");
		}
	}

	public boolean finishCommunication() throws IOException{
		clientSocket.close();
		return true;
	}
	@Override
	public Member login(String forumName, String userName, String password){
		Member loggedInMember = null;
		msgToSend = "LOGIN\n"+"forumName: "+forumName+"\n" +
		"userName: "+userName+"\n"+"password: "+password+"\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {receivedMsg = stringFromServer.readLine();}
		catch (IOException e) {}
		if(receivedMsg.contains("200ok")){
			loggedInMember = new Member(userName,password,null,forumName, this);
		}
		return loggedInMember;
	}

	@Override
	public Member register(String forumName, String userName, String password,
			String email) {
		Member loggedInMember = null;
		if(passwordValidity(password)){
			msgToSend = "REGISTER\n"+"forumName: "+forumName+"\n"+"userName: "+userName+"\n" +
			"password: "+password+"\n"+"email: "+email+"\n";
			msgToSend += delimiter;
			stringToServer.println(msgToSend);
			try {receivedMsg = stringFromServer.readLine();}
			catch (IOException e) {}
			if(receivedMsg.contains("200ok")){
				loggedInMember = new Member(userName,password,null,forumName, this);
			}
		}
		return loggedInMember;
	}

	public boolean passwordValidity(String password) {
		return (password.length()<=16 && password.length()>=8 && range(password));
	}

	public boolean range(String password){
		boolean charFlag = false;
		boolean numFlag = false;
		char currchar;
		for(int i=0; i<password.length() && !(numFlag && charFlag);i++){
			currchar = password.charAt(i);
			if(currchar>='0' && currchar<='9'){
				numFlag = true;
			}
			if((currchar>='a' && currchar <='z') || (currchar>='A' && currchar <='Z')){
				charFlag = true;
			}
		}
		return (charFlag && numFlag);
	}

	@Override
	public SubForum getSubForum(String forum, String subForumName) {
		SubForum returnedSF = null;
		msgToSend = "GETSF\n"+"forumName: "+forum+"\n" +
		"subForumName: "+subForumName+"\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {returnedSF = (SubForum)objectFromServer.readObject();}
		catch (IOException e) {}
		catch (ClassNotFoundException e){}
		return returnedSF;
	}

	@Override
	public List<SubForum> getSubForumsList(String forumName) {
		List<SubForum> returnedList = new ArrayList<SubForum>();
		msgToSend = "GETSFL\n"+"forumName: "+forumName+"\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {returnedList = (List<SubForum>)objectFromServer.readObject();}
		catch (ClassNotFoundException e) {}
		catch (IOException e) {}
		return returnedList;
	}

	@Override
	public List<ThreadMessage> getThreadsList(String forumName,
			String subForumName) {
		List<ThreadMessage> returnedList = new ArrayList<ThreadMessage>();
		ThreadMessage tmp = null;
		msgToSend = "GETTL\n"+"forumName: "+forumName+"\n"+"subForumName: "+subForumName+"\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {returnedList = (List<ThreadMessage>)objectFromServer.readObject();}
		catch (ClassNotFoundException e) {}
		catch (IOException e) {}
		return returnedList;
	}

	@Override
	public ThreadMessage getThreadMessage(String forumName,
			String subForumName, int msgID) {
		ThreadMessage returnedTM = null;
		msgToSend = "GETTM\n"+"forumName: "+forumName+"\n" +
		"subForumName: "+subForumName+"\n"+"treadMessageID: "+msgID+"\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {returnedTM = (ThreadMessage)objectFromServer.readObject();}
		catch (IOException e) {}
		catch (ClassNotFoundException e){}
		return returnedTM;
	}

	@Override
	public boolean postComment(Post post) {
		boolean isPosted = false;
		if(legalMsg(post)){
			ThreadMessage tm = post.getThread();
			SubForum sf = tm.getSubForum();
			Forum f = sf.getForum();
			String posterName = post.getPublisher();
			msgToSend = "POST\n"+"forumName: "+f.getForumName()+"\n" +
			"subForumName: "+sf.getSubForumName()+"\n"+"ThreadMessage: "+tm.getId()+"\n"+
			"posterName: "+posterName+"\n"+"postTitle: "+post.getTitle()+"\n"+
			"postContent: "+post.getContent()+"\n";
			msgToSend += delimiter;
			stringToServer.println(msgToSend);
			try {receivedMsg = stringFromServer.readLine();}
			catch (IOException e) {}
			if(receivedMsg.contains("200ok")){
				isPosted = true;
			}
		}
		return isPosted;
	}

	@Override
	public boolean publishThread(ThreadMessage newThread) {
		boolean isPublished = false;
		if(legalMsg(newThread)){
			SubForum sf = newThread.getSubForum();
			Forum f = sf.getForum();
			String posterName = newThread.getPublisher();
			msgToSend = "THREAD\n"+"forumName: "+f+"\n" +
			"subForumName: "+sf+"\n"+"posterName: "+posterName+"\n"+"threadTitle: "+newThread.getTitle()+"\n"+
			"threadContent: "+newThread.getContent()+"\n";
			msgToSend += delimiter;
			stringToServer.println(msgToSend);
			try {receivedMsg = stringFromServer.readLine();}
			catch (IOException e) {}
			if(receivedMsg.contains("200ok")){
				isPublished = true;
			}
		}
		return isPublished;
	}

	@Override
	public User logout(String forumName, String userName) {
		/*User loggedOutMember = null;
		msgToSend = "LOGOUT\n"+"forumName: "+forumName+"\n" +
		"userName: "+userName+"\n";
		stringToServer.println(msgToSend);
		try {reciviedMsg = stringFromServer.readLine();}
		catch (IOException e) {}
		if(reciviedMsg.contains("200ok")){
			loggedOutMember = new User(this);
		}
		return loggedOutMember;
		*///FOR FUTURE USE
		return new User(this);
	}

	@Override
	public List<Forum> getForumsList() {
		List<Forum> returnedList = new ArrayList<Forum>();
		msgToSend = "GETFL\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {returnedList = (List<Forum>)objectFromServer.readObject();}
		catch (ClassNotFoundException e) {}
		catch (IOException e) {}
		return returnedList;
	}

	@Override
	public Forum getForum(String forumName) {

		Forum returnedSF = null;
		msgToSend = "GETF\n"+"forumName: "+forumName+"\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {returnedSF = (Forum)objectFromServer.readObject();}
		catch (IOException e) {}
		catch (ClassNotFoundException e){}
		return returnedSF;
	}


	@Override
	public boolean addSubForum(SubForum subForum, List<Moderator> lm) {
		boolean added = false;
		Forum f = subForum.getForum();
		msgToSend = "ADDSF\n"+"forumName: "+f.getForumName()+"\n" +
		"subForumName: "+subForum.getSubForumName()+"\n"+"size: "+lm.size()+"\n";
		for(Moderator md: lm){
			msgToSend+="moderator: "+md.getUserName()+"\n";
		}
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {receivedMsg = stringFromServer.readLine();}
		catch (IOException e) {}
		if(receivedMsg.contains("200ok")){
			added = true;
		}
		return added;
	}

	@Override
	public boolean initiateForum(String forumName, String adminName, String AdminPassword) {
		boolean initiated = false;
		msgToSend = "ADDF\n"+"forumName: "+forumName+"\n"+
		"adminName: "+adminName+"\n"+"adminPassword: "+AdminPassword+"\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {receivedMsg = stringFromServer.readLine();}
		catch (IOException e) {}
		if(receivedMsg.contains("200ok")){
			initiated = true;
		}
		return initiated;
	}

	private boolean legalMsg(Message m){
		return m.getContent().length()<=1000;
	}

	@Override
	public List<Post> getAllPosts(ThreadMessage tm) {
		List<Post> allPosts = null;
		SubForum subForum = tm.getSubForum();
		String forumName = subForum.getSubForumName();
		String subForumName = subForum.getSubForumName();
		int threadID = tm.getId();
		msgToSend = "GETAP\n"+"forumName: "+forumName+"\n"+
		"SubForumName: "+subForumName+"\n"+"TheadID: "+threadID+"\n";
		msgToSend += delimiter;
		stringToServer.println(msgToSend);
		try {allPosts = (List<Post>)objectFromServer.readObject();}
		catch (IOException e) {}
		catch (ClassNotFoundException e){}
		return allPosts;
	}
}