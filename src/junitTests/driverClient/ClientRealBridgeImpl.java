package junitTests.driverClient;

import java.util.List;

import Sadna.Client.ConnectionHandler;
import Sadna.Client.Member;
import Sadna.Client.User;
import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

public class ClientRealBridgeImpl implements ClientBridge {
	ClientCommunicationHandlerInterface clientHandler = new ConnectionHandler("sadna", 3000);
	//ClientCommunicationHandlerInterface clientHandler = new ConnectionHandlerMock();
	
	
	
	public boolean addSubForum(SubForum subForum) {
		return clientHandler.addSubForum(subForum, null);
	}

	public Forum getForum(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Forum> getForumsList() {
		// TODO Auto-generated method stub
		return null;
	}

	public SubForum getSubForum(String forum, String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SubForum> getSubForumsList(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	public ThreadMessage getThreadMessage(String forumName,
			String subForumName, int messageID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ThreadMessage> getThreadsList(String forumName,
			String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean initiateForum(String forumName, String adminName,
			String adminPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	public Member login(String forumName, String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public User logout(String forumName, String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean postComment(Post post) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean publishThread(ThreadMessage newThread) {
		// TODO Auto-generated method stub
		return false;
	}

	public Member register(String forumName, String userName, String password,
			String email) {
		// TODO Auto-generated method stub
		return null;
	}

	//
	public boolean isConnect(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	//
}
