package Driver;

import Sadna.Client.Member;
import Sadna.Client.SuperAdmin;
import Sadna.Client.User;
import Sadna.Server.ForumNotification;
import Sadna.db.Forum;
import Sadna.db.Policy;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

import java.util.HashMap;
import java.util.List;

public class ClientProxyBridge implements ClientBridge {

    private ClientBridge real;

    public ClientProxyBridge() {
        this.real = null;
    }

    public void setRealBridge(ClientBridge real) {
        this.real = real;
    }


	@Override
	public Forum getForum(String forumName) {
		if (this.real != null)
			return this.real.getForum(forumName);
		return null;
	}

	@Override
	public List<Forum> getForumsList() {
		if (this.real != null)
		return this.real.getForumsList();
		return null;
	}

	@Override
	public SubForum getSubForum(String forum, String subForumName) {
		if (this.real != null)
		return this.real.getSubForum(forum, subForumName);
		return null;
	}

	@Override
	public List<SubForum> getSubForumsList(String forumName) {
		if (this.real != null)
		return this.real.getSubForumsList(forumName);
		return null;
	}

	@Override
	public ThreadMessage getThreadMessage(String forumName,
			String subForumName, int messageID) {
		if (this.real != null)
		return this.real.getThreadMessage(forumName, subForumName, messageID);
		return null;
	}

	@Override
	public List<ThreadMessage> getThreadsList(String forumName,
			String subForumName) {
		if (this.real != null)
		return this.real.getThreadsList(forumName, subForumName);
		return null;
	}


	@Override
	public Member login(String forumName, String userName, String password) {
		if (this.real != null)
		return this.real.login(forumName, userName, password);
		return null;
	}

	@Override
	public User logout(String forumName, String userName) {
		if (this.real != null)
		return this.real.logout(forumName, userName);
		return null;
	}

	@Override
	public boolean postComment(Post post, String password) {
		if (this.real != null)
		return this.real.postComment(post, password);
		return false;
	}

	@Override
	public boolean publishThread(ThreadMessage newThread, String password) {
		if (this.real != null)
		return this.real.publishThread(newThread, password);
		return false;
	}

	@Override
	public Member register(String forumName, String userName, String password,
			String email) {
		if (this.real != null)
		return this.real.register(forumName, userName, password, email);
		return null;
	}

	@Override
	public List<Post> getAllPosts(ThreadMessage tm) {
		if (this.real != null)
		return this.real.getAllPosts(tm);
		return null;
	}

	@Override
	public boolean finishCommunication() {
		if (this.real != null)
		return this.real.finishCommunication();
		return false;
	}

	@Override
	public boolean addSubForum(SubForum subForum, List<Member> lm,
			String userName, String password) {
		if (this.real != null)
		return this.real.addSubForum(subForum, lm, userName, password);
		return false;
	}

	@Override
	public boolean initiateForum(String forumName, String adminName,
			String adminPassword, Policy policy, String superAdminName,
			String superAdminPasswaord) {
		if (this.real != null)
		return this.real.initiateForum(forumName, adminName,
				adminPassword, policy, superAdminName, superAdminPasswaord);
		return false;
	}

	@Override
	public boolean deleteForum(String forumName, String userName,
			String password) {
		if (this.real != null)
		return this.real.deleteForum(forumName, userName, password);
		return false;
	}

	@Override
	public boolean deleteSubForum(String forumName, String subForumName,
			String userName, String password) {
		if (this.real != null)
		return this.real.deleteSubForum(forumName, subForumName,
				userName, password);
		return false;
	}

	@Override
	public boolean deleteThreadMessage(ThreadMessage tm, String userName,
			String password) {
		if (this.real != null)
		return this.real.deleteThreadMessage(tm, userName, password);
		return false;
	}

	@Override
	public boolean deletePost(Post p, String userName, String password) {
		if (this.real != null)
		return this.real.deletePost(p, userName, password);
		return false;
	}

	@Override
	public boolean addModerator(String forumName, String subForumName,
			String newModerator, String userName, String password) {
		if (this.real != null)
		return this.real.addModerator(forumName, subForumName,
				newModerator, userName, password);
		return false;
	}

	@Override
	public boolean editThread(ThreadMessage newTM, String userName,
			String password) {
		if (this.real != null)
		return this.real.editThread(newTM, userName, password);
		return false;
	}

	@Override
	public boolean editPost(Post newP, String userName, String password) {
		if (this.real != null)
		return this.real.editPost(newP, userName, password);
		return false;
	}

	@Override
	public List<ForumNotification> getNotification(String forumName,
			String userName, String password) {
		if (this.real != null)
		return this.real.getNotification(forumName, userName, password);
		return null;
	}

	@Override
	public boolean removeModerator(String forumName, String subForum,
			String moderatorName, String userName, String password) {
		if (this.real != null)
		return this.real.removeModerator(forumName, subForum, 
				moderatorName, userName, password);
		return false;
	}

	@Override
	public int getNumOfThreadsInForum(String forumName, String userName,
			String password) {
		if (this.real != null)
		return this.real.getNumOfThreadsInForum(forumName, userName, password);
		return 0;
	}

	@Override
	public int getNumOfUserThreads(String forumName, String userName,
			String requesterUserName, String requesterpassword) {
		if (this.real != null)
		return this.real.getNumOfUserThreads(forumName, userName, 
				requesterUserName, requesterpassword);
		return 0;
	}

	@Override
	public HashMap<String, List<String>> getUsersPostToUser(String forumName,
			String userName, String password) {
		if (this.real != null)
		return this.real.getUsersPostToUser(forumName, userName, password);
		return null;
	}

	@Override
	public int getNumOfForums(String userName, String password) {
		if (this.real != null)
		return this.real.getNumOfForums(userName, password);
		return 0;
	}

	@Override
	public List<String> getCommonMembers(String userName, String password) {
		if (this.real != null)
		return this.real.getCommonMembers(userName, password);
		return null;
	}

	@Override
	public SuperAdmin loginAsSuperAdmin(String userName, String password) {
		if (this.real != null)
		return this.real.loginAsSuperAdmin(userName, password);
		return null;
	}

	@Override
	public List<Member> getAllForumMembers(String forum, String userName,
			String password) {
		if (this.real != null)
		return this.real.getAllForumMembers(forum, userName, password);
		return null;
	}

	@Override
	public boolean listenToServer() {
		if (this.real != null)
		return this.real.listenToServer();
		return false;
	}

	@Override
	public boolean sendListenerIdentifier() {
		if (this.real != null)
		return this.real.sendListenerIdentifier();
		return false;
	}

	@Override
	public boolean hasNotifications(String forum, String userName,
			String password) {
		if (this.real != null)
		return this.real.hasNotifications(forum, userName, password);
		return false;
	}
}

