package driverServer;

import java.net.Socket;
import java.util.List;

import Sadna.Server.MainServer;
import Sadna.Server.ServerConnectionWithClientHandler;
import Sadna.Server.ServerToDataBaseHandler;
import Sadna.Server.API.ConnectionHandlerServerInterface;
import Sadna.Server.API.ServerInterface;
import Sadna.db.DataBase;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import Sadna.db.API.DBInterface;

public class RealBridge implements Bridge {

	Socket _socket = new Socket();
	DBInterface _dataBase = new DataBase();
	MainServer _mainServer = new MainServer();
	ConnectionHandlerServerInterface _serverHandler = new ServerConnectionWithClientHandler(_socket);
	ServerInterface _server = new ServerToDataBaseHandler(new DataBase());
	
	
	///////////////  ServerInterface  //////////////////
	/////////////////////////////////////////////////////////////////////
	
	public boolean addSubForum(SubForum subForum) {
		// TODO Auto-generated method stub
		return false;
	}
	public Forum getForum(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Forum> getForumsList() {
		// TODO Auto-generated method stub
		return null;
	}
	public SubForum getSubForum(String forumName, String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<SubForum> getSubForumsList(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}
	public ThreadMessage getThreadMessage(String forumName,
			String subForumName, int messageId) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<ThreadMessage> getThreadsList(String forumName,
			String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean initiateForum(String adminUserName, String adminPassword,
			String forumName) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean login(String forumName, String userName, String password) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean postComment(Post comment) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean publishThread(ThreadMessage thread) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean register(String forumName, String userName, String password,
			String email) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	///////////////  ConnectionHandlerServerInterface  //////////////////
	/////////////////////////////////////////////////////////////////////
	
	public void closeSocket() {
		// TODO Auto-generated method stub
		
	}
	public String receiveRequestFromClient() {
		// TODO Auto-generated method stub
		return null;
	}
	public void sendErrorInServer() {
		// TODO Auto-generated method stub
		
	}
	public void sendForum(Forum forum) {
		// TODO Auto-generated method stub
		
	}
	public void sendForumsList(List<Forum> forumsList) {
		// TODO Auto-generated method stub
		
	}
	public void sendNotFound() {
		// TODO Auto-generated method stub
		
	}
	public void sendOK() {
		// TODO Auto-generated method stub
		
	}
	public void sendSubForum(SubForum subForum) {
		// TODO Auto-generated method stub
		
	}
	public void sendSubForumsList(List<SubForum> subForumsList) {
		// TODO Auto-generated method stub
		
	}
	public void sendThreadMeassage(ThreadMessage threadMessage) {
		// TODO Auto-generated method stub
		
	}
	public void sendThreadsList(List<ThreadMessage> threadsList) {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
	


	


}
