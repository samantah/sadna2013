package junitTests.driverServer;

import java.util.List;


import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

public interface ServerBridge {

	
	///////////////  ConnectionHandlerServerInterface  //////////////////
	/////////////////////////////////////////////////////////////////////
	public void sendOK();

	public void sendNotFound();

	public void sendErrorInServer();

	public void sendSubForumsList(List<SubForum> subForumsList);

	public void sendSubForum(SubForum subForum);

	public void sendThreadsList(List<ThreadMessage> threadsList);

	public void sendThreadMeassage(ThreadMessage threadMessage);

	public void sendForumsList(List<Forum> forumsList);

	public void sendForum(Forum forum);

	public String receiveRequestFromClient();
	
	public void closeSocket();
	
	
	///////////////  ServerInterface  //////////////////
	/////////////////////////////////////////////////////////////////////
	
    boolean register(String forumName, String userName, String password, String email);

    boolean login(String forumName, String userName, String password);
 
    boolean initiateForum(String adminUserName, String adminPassword, String forumName);

    boolean addSubForum(SubForum subForum);

    boolean publishThread(ThreadMessage thread);
    
    boolean postComment(Post comment);

    List<Forum> getForumsList();

    Forum getForum(String forumName);

    List<SubForum> getSubForumsList(String forumName);

    SubForum getSubForum(String forumName, String subForumName);

    List<ThreadMessage> getThreadsList(String forumName, String subForumName);

    ThreadMessage getThreadMessage(String forumName, String subForumName, int messageId);

    /* For later use..  
     * 
     * boolean logout(String forumName, String userName);
     */
	
	
	

	
}
