package Sadna.Client;

import java.util.List;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

public class ConnectionHandler implements ClientCommunicationHandlerInterface{

	@Override
	public boolean login(String forumName, String userName, String password) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean register(String forumName, String userName, String password,
			String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SubForum getSubForum(String forum, String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubForum> getSubForumsList(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ThreadMessage> getThreadsList(String forumName,
			String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThreadMessage getThreadMessage(String forumName,
			String subForumName, String threadMessage) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public boolean postComment(Post post, Member member) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean publishThread(ThreadMessage newThread, Member member) {
		// TODO Auto-generated method stub
		return false;
	}
	
}