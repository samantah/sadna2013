package Sadna.Client;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.*;
import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

public class ConnectionHandler implements ClientCommunicationHandlerInterface{
	private Socket clientSocket;
	private DataOutputStream stringToServer;
	private BufferedReader stringFromServer;
	private ObjectOutputStream objectToServer;
	private ObjectInputStream objectFromServer;
	private String msgToSend;
	private String reciviedMsg;

	public ConnectionHandler(String host, int port) throws UnknownHostException, IOException{
		clientSocket = new Socket(host, port);
		stringToServer = new DataOutputStream(clientSocket.getOutputStream());
		stringFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		objectToServer = new ObjectOutputStream(clientSocket.getOutputStream()); 
		objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
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
		try {stringToServer.writeBytes(msgToSend);}
		catch(IOException e){}
		try {reciviedMsg = stringFromServer.readLine();}
		catch (IOException e) {}
		if(reciviedMsg.contains("200ok")){
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
			try {stringToServer.writeBytes(msgToSend);}
			catch(IOException e){}
			try {reciviedMsg = stringFromServer.readLine();}
			catch (IOException e) {}
			if(reciviedMsg.contains("200ok")){
				loggedInMember = new Member(userName,password,null,forumName, this);
			}
		}
		return loggedInMember;
	}

	private boolean passwordValidity(String password) {
		return (password.length()<16 && password.length()>8 && range(password));
	}

	private boolean range(String password){
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
		try {stringToServer.writeBytes(msgToSend);}
		catch(IOException e){}
		try {returnedSF = (SubForum)objectFromServer.readObject();}
		catch (IOException e) {}
		catch (ClassNotFoundException e){}
		return returnedSF;
	}

	@Override
	public List<SubForum> getSubForumsList(String forumName) {
		List<SubForum> returnedList = new ArrayList<SubForum>();
		SubForum tmp = null;
		msgToSend = "GETSFL\n"+"forumName: "+forumName+"\n";
		try {stringToServer.writeBytes(msgToSend);}
		catch(IOException e){}
		try {
			reciviedMsg = stringFromServer.readLine();
			if(reciviedMsg.contains("200ok")){
				reciviedMsg = stringFromServer.readLine();
				while(reciviedMsg!=null){
					tmp = new SubForum(forumName, reciviedMsg);
					returnedList.add(tmp);
					reciviedMsg = stringFromServer.readLine();
				}
			}
		}
		catch(IOException e){}
		return returnedList;
	}

	@Override
	public List<ThreadMessage> getThreadsList(String forumName,
			String subForumName) {
		List<ThreadMessage> returnedList = new ArrayList<ThreadMessage>();
		ThreadMessage tmp = null;
		msgToSend = "GETTML\n"+"forumName: "+forumName+"\n"+"subForumName: "+subForumName+"\n";
		try {stringToServer.writeBytes(msgToSend);}
		catch(IOException e){}
		try {
			reciviedMsg = stringFromServer.readLine();
			if(reciviedMsg.contains("200ok")){
				reciviedMsg = stringFromServer.readLine();
				while(reciviedMsg!=null){
					tmp = new ThreadMessage(forumName, subForumName, reciviedMsg);
					returnedList.add(tmp);
					reciviedMsg = stringFromServer.readLine();
				}
			}
		}
		catch(IOException e){}
		return returnedList;
	}

	@Override
	public ThreadMessage getThreadMessage(String forumName,
			String subForumName, String threadMessage) {
		ThreadMessage returnedTM = null;
		msgToSend = "GETTM\n"+"forumName: "+forumName+"\n" +
		"subForumName: "+subForumName+"\n"+"treadMessage: "+threadMessage+"\n";
		try {stringToServer.writeBytes(msgToSend);}
		catch(IOException e){}
		try {returnedTM = (ThreadMessage)objectFromServer.readObject();}
		catch (IOException e) {}
		catch (ClassNotFoundException e){}
		return returnedTM;
	}

	@Override
	public boolean postComment(Post post, Member member) {
		boolean isPosted = false;
		ThreadMessage tm = post.getThread();
		SubForum sf = tm.getSubForum();
		Forum f = sf.getForum();
		String posterName = member.getUserName();
		msgToSend = "POST\n"+"forumName: "+f+"\n" +
		"subForumName: "+sf+"\n"+"ThreadMessage: "+tm+"\n"+"posterName: "+posterName+"\n";
		try {stringToServer.writeBytes(msgToSend);}
		catch(IOException e){}
		try {reciviedMsg = stringFromServer.readLine();}
		catch (IOException e) {}
		if(reciviedMsg.contains("200ok")){
			isPosted = true;
		}
		return isPosted;
	}

	@Override
	public boolean publishThread(ThreadMessage newThread, Member member) {
		boolean isPublished = false;
		SubForum sf = newThread.getSubForum();
		Forum f = sf.getForum();
		String posterName = member.getUserName();
		msgToSend = "THREAD\n"+"forumName: "+f+"\n" +
		"subForumName: "+sf+"\n"+"posterName: "+posterName+"\n";
		try {stringToServer.writeBytes(msgToSend);}
		catch(IOException e){}
		try {reciviedMsg = stringFromServer.readLine();}
		catch (IOException e) {}
		if(reciviedMsg.contains("200ok")){
			isPublished = true;
		}
		return isPublished;
	}

	@Override
	public User logout(String forumName, String userName) {
		User loggedOutMember = null;
		msgToSend = "LOGOUT\n"+"forumName: "+forumName+"\n" +
		"userName: "+userName+"\n";
		try {stringToServer.writeBytes(msgToSend);}
		catch(IOException e){}
		try {reciviedMsg = stringFromServer.readLine();}
		catch (IOException e) {}
		if(reciviedMsg.contains("200ok")){
			loggedOutMember = new User(this);
		}
		return loggedOutMember;
	}

}