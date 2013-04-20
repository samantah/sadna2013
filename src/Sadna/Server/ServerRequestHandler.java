/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server;

import Sadna.Server.API.ConnectionHandlerServerInterface;
import Sadna.Server.API.ServerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
/*dear sami,
 * the server need to handle the next case:
 * in case of new thread publish, you need to add to the ThreadList of the current subForum the current thread.
 * the same case, with a new post, and with a  adding new subForum.
 * if you have any question, you are more then welcome to call the IT department,
 * regards,
 * snir elkaras
 *   8888888888888888888888888888888888888888888888888888888888888888888888888888888
 *   8888888888888888888888888888888888888888888888888888888888888888888888888888888
 */
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

		//
		// Read a message sent by client application
		//
		String request = _ch.receiveRequestFromClient();
		if(request != null){
			System.out.println("-- Message Received -- \n" + request);
			parseAndHandleRequest(request);
		}
		else{
			System.out.println("Bad request from client.");
			_ch.sendErrorInServer();
		}
		_ch.closeSocket();

	}

	private void parseAndHandleRequest(String request) {
		String[] parsedReq = request.split("\n| ");
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
			SubForum subF = new SubForum(parsedReq[2], parsedReq[4]);
			handleAddSubForum(subF);
			break;
		case "ADDF":
			handleInitiateForum(parsedReq[2], parsedReq[4], parsedReq[6]);
			break;
		case "POST":
			ThreadMessage tm = _si.getThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
			Post p = new Post(tm, parsedReq[10], parsedReq[12], parsedReq[8]);

			/* Not relevant to add it here since in the db it will
			   be updated! This object tm is irrelevant..
			tm.addPost(p);
			 */

			handlePostComment(p);
			break;
		case "THREAD":
			SubForum sf = _si.getSubForum(parsedReq[2], parsedReq[4]);
			ThreadMessage threadM = new ThreadMessage(sf, parsedReq[8], parsedReq[10], parsedReq[6]);

			/* Not relevant to add it here since in the db it will
			   be updated! This object sf is irrelevant..
			sf.addThreadMessage(threadM);
			 */

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
			_ch.sendOK();
		}
		else{
			_ch.sendErrorInServer();
		}

		return isAdded;
	}

	public boolean handleAddSubForum(SubForum subForum){
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