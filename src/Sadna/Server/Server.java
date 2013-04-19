/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server;

import Sadna.Client.Admin;
import Sadna.Client.Member;
import Sadna.Client.User;
import Sadna.Server.API.ConnectionHandlerServerInterface;
import Sadna.Server.API.ServerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class Server {

	private ConnectionHandlerServerInterface _ch;
	private ServerInterface _si;

	public Server(ConnectionHandlerServerInterface ch, ServerInterface si) {
		_ch = ch;
		_si = si;
	}

	private boolean handleRegister(String forumName, String userName, String password, 
			String email){
		boolean isAdded = false;
		isAdded = _si.register(forumName, userName, password, email);
		if(isAdded){
			_ch.sendOK();
		}
		else{
			_ch.sendErrorInServer();
		}
		return isAdded;
	}

	private boolean handleLogin(String forumName, String userName, String password){
		boolean logedIn = _si.login(forumName, userName, password);
		if(logedIn){
			_ch.sendOK();
		}
		else{
			_ch.sendNotFound();
		}
		return logedIn;
	}

	private boolean handleInitiateForum(String forumName, String adminName, 
			String adminPassword){
		boolean isAdded = false;
		isAdded = _si.initiateForum(adminName, adminPassword, forumName);		
		if(isAdded){
			_ch.sendOK();
		}
		else{
			_ch.sendErrorInServer();
		}

		return isAdded;
	}

	private boolean handleAddSubForum(SubForum subForum){
		boolean isAdded = false;
		isAdded = _si.addSubForum(subForum);
		if(isAdded){
			_ch.sendOK();
		}
		else{
			_ch.sendErrorInServer();
		}
		return isAdded;
	}

	private boolean handlePublishThread(ThreadMessage newThread){
		boolean succeeded = false;
		succeeded = _si.publishThread(newThread);
		if(succeeded){
			_ch.sendOK();
		}
		else{
			_ch.sendErrorInServer();
		}
		return succeeded;
	}

	private boolean handlePostComment(Post post){
		boolean succeeded = false;
		succeeded = _si.postComment(post);
		if(succeeded){
			_ch.sendOK();
		}
		else{
			_ch.sendErrorInServer();
		}
		return succeeded;
	}

	private void handleGetForumsList(){
		_ch.sendForumsList(_si.getForumsList());
	}
	
	private void handleGetForum(String forumName){
		_ch.sendForum(_si.getForum(forumName));
	}

	private void handleGetSubForumsList(String forumName){
		_ch.sendSubForumsList(_si.getSubForumsList(forumName));
	}

	private void handleGetSubForum(String forum ,String subForumName){
		_ch.sendSubForum(_si.getSubForum(forum, subForumName));
	}

	private void handleGetThreadsList(String forumName, 
			String subForumName){
		_ch.sendThreadsList(_si.getThreadsList(forumName, subForumName));
	}

	private void handleGetThreadMessage(String forumName ,String subForumName,
			int messageID){
		_ch.sendThreadMeassage(_si.getThreadMessage(forumName, subForumName, messageID));
	}


	
	
/*  For later use..
 
  	private User handleLogout(String forumName, String userName){ 
		return null;
	}
*/

}


//		public static void main(String args[]) throws Exception {

//		int firsttime = 1;
//		String clientSentence;
//		String capitalizedSentence = "";
//		ServerSocket welcomeSocket = new ServerSocket(3248);
//		Socket connectionSocket = welcomeSocket.accept();
//		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
//		PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);
//		ObjectOutputStream objout = new ObjectOutputStream(connectionSocket.getOutputStream());
//		SubForum sub = new SubForum("forum1", "subForum1");
//		ThreadMessage t = new ThreadMessage(sub, "ani t1", "zubi");
//		ThreadMessage t2 = new ThreadMessage(sub, "ani t2", "zubi");
//		sub.addThreadMessage(t);
//		sub.addThreadMessage(t2);
//		Admin a = new Admin("userName", "password", "email", "forum", null);
//		Forum f = new Forum(a, "myForum");
//		for(int i =0; i<3; i++){
//			sub = new SubForum(f, "subForumName"+i);
//			f.addSubForum(sub);
//		}
//		List<ThreadMessage> tl = new ArrayList<ThreadMessage>();
//		for(int i =0; i<3; i++){
//			ThreadMessage t1 = new ThreadMessage(sub, ""+i+i+i+i+i+i+i, "zubi");
//			tl.add(t1);
//		}
//		List<Forum> fl = new ArrayList<Forum>();
//		for(int i =0; i<10; i++){
//			Forum f1 = new Forum(a, "forumName"+i);
//			fl.add(f1);
//		}
//		while (true) {
//
//			clientSentence = inFromClient.readLine();
//			if (clientSentence == null) {
//				continue;
//			}
//			
//			capitalizedSentence = "200ok\n";
//			System.out.println("sendind..");
//			if (clientSentence.contains("GETFL")){
//				objout.writeObject(fl);
//			}
//			System.out.println("finished");
//		}
//	}