package Sadna.Server;

import java.util.List;

import Sadna.Client.Admin;
import Sadna.Client.ConnectionHandler;
import Sadna.Client.Member;
import Sadna.Server.API.ConnectionHandlerServerInterface;
import Sadna.Server.API.ServerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import Sadna.db.API.DBInterface;

public class ServerImpl implements ServerInterface {

	private DBInterface _db;

	public ServerImpl(DBInterface db){
		_db = db;
	}

	@Override
	public boolean register(String forumName, String userName, String password, String email) {
		boolean isAdded = false;
		if(forumExists(forumName)){
			if(isUserNameUnique(forumName, userName)){
				isAdded = _db.addMember(new Member(userName, password, email, forumName, null));
			}
		}
		return isAdded;
	}

	private boolean isUserNameUnique(String forumName, String userName) {
		boolean isUnique = true;
		List<Member> members = _db.getAllMembers(forumName);
		for(Member m: members){
			if(m.getUserName().equals(userName)){
				isUnique = false;
				break;
			}
		}
		return isUnique;
	}

	@Override
	public boolean login(String forumName, String userName, String password) {
		boolean succeeded = false;
		if(forumExists(forumName)){
			Member loginner = _db.getMember(forumName, userName);
			if(loginner!=null && loginner.getPassword().equals(password)){
				succeeded = true;
			}
		}
		return succeeded;
	}

	/*	For later use..

    @Override
	public boolean logout(String forumName, String userName) {
		boolean succeeded = false;
		Member loginner = _db.getMember(forumName, userName);
		if(loginner!=null){
			succeeded = true;
		}
		return succeeded;
	}
	 */

	@Override
	public boolean initiateForum(String adminUserName, String adminPassword,
			String forumName) {
		boolean isAdded = false;
		Admin admin = null;
		if(isForumNameUnique(forumName)){
			Forum forumToAdd = new Forum(forumName);
			admin = new Admin(adminUserName, adminPassword, "", null);
			admin.setForum(forumName);
			forumToAdd.setAdmin(admin);
			boolean addedAdmin = _db.addMember(admin);
			boolean addedForum = _db.addForum(forumToAdd);
			isAdded = addedAdmin && addedForum;
		}
		return isAdded;
	}	

	private boolean isForumNameUnique(String forumName) {
		boolean isUnique = true;
		List<Forum> forums = _db.getForumsList();
		for(Forum f: forums){
			if(f.getForumName().equals(forumName)){
				isUnique = false;
				break;
			}
		}
		return isUnique;
	}

	@Override
	public boolean addSubForum(SubForum subForum) {
		boolean isAdded = false;
		if(isSubForumNameUnique(subForum.getForum().getForumName(), 
				subForum.getSubForumName())){
			isAdded = _db.addSubForum(subForum);
		}
		return isAdded;
	}

	private boolean isSubForumNameUnique(String forum, String subForumName) {
		boolean isUnique = true;
		List<SubForum> subForums = _db.getSubForumsList(forum);
		for(SubForum s: subForums){
			if(s.getSubForumName().equals(subForumName)){
				isUnique = false;
				break;
			}
		}
		return isUnique;
	}

	@Override
	public boolean publishThread(ThreadMessage thread) {
		boolean succeeded = false;
		succeeded = _db.addThread(thread);
		return succeeded;
	}

	@Override
	public boolean postComment(Post comment) {
		boolean posted = false;
		posted = _db.addPost(comment);
		return posted;
	}

	@Override
	public List<Forum> getForumsList() {
		return _db.getForumsList();
	}

	@Override
	public Forum getForum(String forumName) {
		Forum resForum = null;
		if(forumExists(forumName)){
			resForum = _db.getForum(forumName);
		}
		return resForum;
	}

	@Override
	public List<SubForum> getSubForumsList(String forumName) {
		List<SubForum> subforums = null;
		if(forumExists(forumName)){
			subforums = _db.getSubForumsList(forumName);
		}
		return subforums;
	}

	@Override
	public SubForum getSubForum(String forumName, String subForumName) {
		SubForum subForum = null;
		if(subForumExists(forumName,subForumName)){
			subForum = _db.getSubForum(forumName, subForumName);
		}
		return subForum;
	}

	@Override
	public List<ThreadMessage> getThreadsList(String forumName,
			String subForumName) {
		List<ThreadMessage> threads = null;
		if(subForumExists(forumName,subForumName)){
			threads = _db.getThreadsList(forumName, subForumName);
		}
		return threads;
	}

	@Override
	public ThreadMessage getThreadMessage(String forumName,
			String subForumName, int messageId) {
		ThreadMessage thread = null;
		if(threadExists(forumName,subForumName, messageId)){
			thread = _db.getThread(forumName, subForumName, messageId);
		}
		return thread;
	}


}