/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server;


import java.util.ArrayList;
import java.util.List;

import Sadna.Server.API.ConnectionHandlerServerInterface;
import Sadna.Server.API.ServerInterface;
import Sadna.Client.Moderator;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

/**
 *
 * @author fistuk
 */
public class ServerRequestHandler implements Runnable {

	private ConnectionHandlerServerInterface _ch;
	private ServerInterface _si;

	public ServerRequestHandler(ConnectionHandlerServerInterface ch, ServerInterface si) {
		_ch = ch;
		_si = si;
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {

		while(true){
			//
			// Read a message sent by client application
			//
			String request = _ch.receiveRequestFromClient();
			if(request != null){
				if(request.equals("end")){
					_ch.sendErrorInServer();
					break;
				}
				System.out.println("-- Message Received -- \n" + request);
				parseAndHandleRequest(request);
			}
			else{
				continue;
			}
		}
		_ch.closeSocket();

	}

	private void parseAndHandleRequest(String request) {
		String[] parsedReq = request.split("\n");
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
			for(int i = 8; i < Integer.parseInt(parsedReq[6]);i = i+2){
				moderators.add(new Moderator(parsedReq[i], "", "", parsedReq[2], null));
			}
			Forum foru = _si.getForum(parsedReq[2]);
			SubForum subF = new SubForum(foru, parsedReq[4]);
			handleAddSubForum(subF, moderators);
			break;
		case "ADDF":
			if(handleInitiateForum(parsedReq[2], parsedReq[4], parsedReq[6]))
				System.out.println("After add forum");
			break;
		case "GETAP":
			handleGetAllPosts(parsedReq[2], parsedReq[4],Integer.parseInt(parsedReq[6]));
			break;
		case "POST":
			ThreadMessage tm = _si.getThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
			Post p = new Post(tm, parsedReq[10], parsedReq[12], parsedReq[8]);
			handlePostComment(p);
			break;
		case "THREAD":
			SubForum sf = _si.getSubForum(parsedReq[2], parsedReq[4]);
			ThreadMessage threadM = new ThreadMessage(sf, parsedReq[8], parsedReq[10], parsedReq[6]);
			handlePublishThread(threadM);
			break;
		default:
			break;
		}

	}



	public boolean handleRegister(String forumName, String userName, String password, 
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

	public boolean handleLogin(String forumName, String userName, String password){
		boolean logedIn = _si.login(forumName, userName, password);
		if(logedIn){
			_ch.sendOK();
		}
		else{
			_ch.sendNotFound();
		}
		return logedIn;
	}

	public boolean handleInitiateForum(String forumName, String adminName, 
			String adminPassword){
		boolean isAdded = false;
		isAdded = _si.initiateForum(adminName, adminPassword, forumName);		
		if(isAdded){
			System.out.println("isAdded");
			_ch.sendOK();
			System.out.println("Sent ok");
		}
		else{
			System.out.println("notAdded");
			_ch.sendErrorInServer();
			System.out.println("Sent ErrorInServer");
		}

		return isAdded;
	}

	public boolean handleAddSubForum(SubForum subForum, List<Moderator> moderators){
		boolean subForumIsAdded = false;
		subForumIsAdded = _si.addSubForum(subForum, moderators);
		if(subForumIsAdded){
			_ch.sendOK();
		}
		else{
			_ch.sendErrorInServer();
		}
		return subForumIsAdded;
	}

	public boolean handlePublishThread(ThreadMessage newThread){
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

	public boolean handlePostComment(Post post){
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

	public void handleGetForumsList(){
		_ch.sendForumsList(_si.getForumsList());
	}

	private void handleGetAllPosts(String forumName, String subForumName, int threadId) {
		_ch.sendAllPosts(_si.getAllPosts(forumName, subForumName, threadId));
		
	}
	
	public void handleGetForum(String forumName){
		_ch.sendForum(_si.getForum(forumName));
	}

	public void handleGetSubForumsList(String forumName){
		_ch.sendSubForumsList(_si.getSubForumsList(forumName));
	}

	public void handleGetSubForum(String forum ,String subForumName){
		_ch.sendSubForum(_si.getSubForum(forum, subForumName));
	}

	public void handleGetThreadsList(String forumName, 
			String subForumName){
		_ch.sendThreadsList(_si.getThreadsList(forumName, subForumName));
	}

	public void handleGetThreadMessage(String forumName ,String subForumName,
			int messageID){
		_ch.sendThreadMeassage(_si.getThreadMessage(forumName, subForumName, messageID));
	}

	/*  For later use..

  	private User handleLogout(String forumName, String userName){ 
		return null;
	}
	 */

}