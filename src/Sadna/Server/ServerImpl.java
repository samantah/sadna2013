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
	public boolean register(String forumName, String userName, String password, String email,
			ConnectionHandler ch) {
		boolean isAdded = false;
		if(isUnique(userName)){
			_db.addMember(new Member(userName, password, email, forumName, ch));
			isAdded = true;
		}
		return isAdded;
	}

	private boolean isUnique(String userName) {
		boolean isUnique = true;
		List<Member> members = _db.getAllMembers();
		for(Member m: members){
			if(m.getUserName()==userName){
				isUnique = false;
			}
		}
		return isUnique;
	}


	@Override
	public boolean login(String forumName, String userName, String password) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean publishThread(ThreadMessage thread, User user) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public SubForum getSubForum(String forumName, String subForumName) {
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
	public Forum initiateForum(String adminUserName, String adminPassword,
			String forumName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean addSubForum(SubForum subForum,
			List<Moderator> subForumModerators) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean postComment(ThreadMessage commentTo, Post comment) {
		// TODO Auto-generated method stub
		return false;
	}

}