package Sadna.Client;

import java.util.List;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

public class ConnectionHandler implements ClientCommunicationHandlerInterface{

	@Override
	public boolean login(String userName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean postComment(Post post, User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean publishThread(ThreadMessage newThread, User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register(String userName, String password, String fullName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SubForum getSubForum(String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubForum> getSubForumsList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ThreadMessage> getThreadsList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThreadMessage getThreadMessage(String threadMessage) {
		// TODO Auto-generated method stub
		return null;
	}

}
