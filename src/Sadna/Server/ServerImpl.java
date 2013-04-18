package Sadna.Server;

import java.util.List;

import Sadna.Client.ConnectionHandler;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.User;
import Sadna.Server.API.ServerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import Sadna.db.API.DBInterface;

public class ServerImpl implements ServerInterface {
	
	protected DBInterface _db;
	
	public ServerImpl(DBInterface db) {
		_db = db;
	}


	@Override
	public boolean register(String userName, String password, String email,
			ConnectionHandler ch) {
		if(isUnique(userName)){
			_db.addUser(new Member(userName,password,email,ch));
		}
		return false;
	}
	
	private boolean isUnique(String userName) {
		boolean isUnique = false;
		List<Member> members = _db.getAllMembers(forumName)
		while ()
		return false;
	}


	@Override
	public boolean login(String userName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean publishThread(ThreadMessage thread, User user) {
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
	public List<ThreadMessage> getThreadsList(String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThreadMessage getThreadMessage(String threadMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Forum initiateForum(String adminUserName, String adminPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addSubForum(SubForum subForum, List<Moderator> subForumModerators) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean postComment(ThreadMessage commentTo, Post comment) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
