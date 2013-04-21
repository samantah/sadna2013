package driverServer;


import java.util.List;


import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

public class ProxyBridge implements Bridge {
	private Bridge real;
	
	
	public ProxyBridge() {
		this.real=null;
	}

	public void setRealBridge(Bridge real) {
		this.real = real;
	}

	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	
	
	public boolean addSubForum(SubForum subForum) {
		if(this.real!=null) 
			return this.real.addSubForum(subForum);
		return false;
	}

	
	public void closeSocket() {
		if(this.real!=null) 
			this.real.closeSocket();
	} 

	
	public Forum getForum(String forumName) {
		if(this.real!=null)
			return this.real.getForum(forumName);
		return null;
	}

	
	public List<Forum> getForumsList() {
		if(this.real!=null) 
			return this.real.getForumsList();
		return null;
	}

	
	public SubForum getSubForum(String forumName, String subForumName) {
		if(this.real!=null) 
			return this.real.getSubForum(forumName, subForumName);
		return null;
	}

	public List<SubForum> getSubForumsList(String forumName) {
		if(this.real!=null) 
			return this.real.getSubForumsList(forumName);
		return null;
	}

	
	public ThreadMessage getThreadMessage(String forumName,
			String subForumName, int messageId) {
		if(this.real!=null) 
			return this.real.getThreadMessage(forumName,
					 subForumName, messageId);
		return null;
	}

	
	public List<ThreadMessage> getThreadsList(String forumName,
			String subForumName) {
		if(this.real!=null) 
			return this.real.getThreadsList( forumName,
					 subForumName);
		return null;
	}

	
	public boolean initiateForum(String adminUserName, String adminPassword,
			String forumName) {
		if(this.real!=null) 
			return this.real.initiateForum(adminUserName, adminPassword,
					 forumName);
		return false;
	}

	
	public boolean login(String forumName, String userName, String password) {
		if(this.real!=null) 
			return this.real.login(forumName, userName, password);
		return false;
	}

	
	public boolean postComment(Post comment) {
		if(this.real!=null) 
			return this.real.postComment(comment);
		return false;
	}

	
	public boolean publishThread(ThreadMessage thread) {
		if(this.real!=null) 
			return this.real.publishThread(thread);
		return false;
	}



	public boolean register(String forumName, String userName, String password,
			String email) {
		if(this.real!=null) 
			return this.real.register(forumName, userName, password,
					 email);
		return false;
	}

	
	public String receiveRequestFromClient() {
		if(this.real!=null) return this.real.receiveRequestFromClient();
		return null;
	}
	
	public void sendErrorInServer() {
		if(this.real!=null) 
			 this.real.sendErrorInServer();
		
	}

	public void sendForum(Forum forum) {
		if(this.real!=null) 
			 this.real.sendForum(forum);
		
	}

	public void sendForumsList(List<Forum> forumsList) {
		if(this.real!=null) 
			 this.real.sendForumsList(forumsList);
		
	}

	public void sendNotFound() {
		if(this.real!=null) 
			 this.real.sendNotFound();
		
	}

	public void sendOK() {
		if(this.real!=null) 
			 this.real.sendOK();
		
	}

	public void sendSubForum(SubForum subForum) {
		if(this.real!=null) 
			 this.real.sendSubForum(subForum);
		
	}

	public void sendSubForumsList(List<SubForum> subForumsList) {
		if(this.real!=null) 
			 this.real.sendSubForumsList(subForumsList);
		
	}

	public void sendThreadMeassage(ThreadMessage threadMessage) {
		if(this.real!=null) 
			 this.real.sendThreadMeassage(threadMessage);
		
	}

	public void sendThreadsList(List<ThreadMessage> threadsList) {
		if(this.real!=null) 
			 this.real.sendThreadsList(threadsList);
		
	}
	
///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	
	


}
