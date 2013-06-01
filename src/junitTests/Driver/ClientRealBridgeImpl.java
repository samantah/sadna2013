package Driver;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Client.ClientConnectionHandler;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.Client.User;
import Sadna.Server.ForumNotification;
import Sadna.db.Forum;
import Sadna.db.Policy;
import Sadna.db.PolicyEnums.enumAssignModerator;
import Sadna.db.PolicyEnums.enumCancelModerator;
import Sadna.db.PolicyEnums.enumDelete;
import Sadna.db.PolicyEnums.enumNotiFriends;
import Sadna.db.PolicyEnums.enumNotiImidiOrAgre;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientRealBridgeImpl implements ClientBridge {

	private String _host;
	private int _port;
	private ClientCommunicationHandlerInterface _clientHandler;

	public ClientRealBridgeImpl(String host, int port) {
		_host = host;
		_port = port;
		_clientHandler = new ClientConnectionHandler(_host, _port);
	}


	@Override
	public Forum getForum(String forumName) {
		return _clientHandler.getForum(forumName);
	}

	@Override
	public List<Forum> getForumsList() {
		return _clientHandler.getForumsList();
	}

	@Override
	public SubForum getSubForum(String forum, String subForumName) {
		return _clientHandler.getSubForum(forum, subForumName);
	}

	@Override
	public List<SubForum> getSubForumsList(String forumName) {
		return _clientHandler.getSubForumsList(forumName);
	}

	@Override
	public ThreadMessage getThreadMessage(String forumName,
			String subForumName, int messageID) {
		return _clientHandler.getThreadMessage(forumName, subForumName, messageID);
	}

	@Override
	public List<ThreadMessage> getThreadsList(String forumName,
			String subForumName) {
		return _clientHandler.getThreadsList(forumName, subForumName);
	}


	@Override
	public Member login(String forumName, String userName, String password) {
		return _clientHandler.login(forumName, userName, password);
	}

	@Override
	public User logout(String forumName, String userName) {
		return _clientHandler.logout(forumName, userName);
	}

	@Override
	public boolean postComment(Post post, String password) {
		return _clientHandler.postComment(post, password);
	}

	@Override
	public boolean publishThread(ThreadMessage newThread, String password) {
		return _clientHandler.publishThread(newThread, password);
	}

	@Override
	public Member register(String forumName, String userName, String password,
			String email) {
		return _clientHandler.register(forumName, userName, password, email);
	}

	@Override
	public List<Post> getAllPosts(ThreadMessage tm) {
		return _clientHandler.getAllPosts(tm);
	}

	@Override
	public boolean finishCommunication() {
		return _clientHandler.finishCommunication();
	}

	@Override
	public boolean addSubForum(SubForum subForum, List<Member> lm,
			String userName, String password) {
		return _clientHandler.addSubForum(subForum, lm, userName, password);
	}

	@Override
	public boolean initiateForum(String forumName, String adminName,
			String adminPassword, Policy policy, String superAdminName,
			String superAdminPasswaord) {
		return _clientHandler.initiateForum(forumName, adminName,
				adminPassword, policy, superAdminName, superAdminPasswaord);
	}

	@Override
	public boolean deleteForum(String forumName, String userName,
			String password) {
		return _clientHandler.deleteForum(forumName, userName, password);
	}

	@Override
	public boolean deleteSubForum(String forumName, String subForumName,
			String userName, String password) {
		return _clientHandler.deleteSubForum(forumName, subForumName,
				userName, password);
	}

	@Override
	public boolean deleteThreadMessage(ThreadMessage tm, String userName,
			String password) {
		return _clientHandler.deleteThreadMessage(tm, userName, password);
	}

	@Override
	public boolean deletePost(Post p, String userName, String password) {
		return _clientHandler.deletePost(p, userName, password);
	}

	@Override
	public boolean addModerator(String forumName, String subForumName,
			String newModerator, String userName, String password) {
		return _clientHandler.addModerator(forumName, subForumName,
				newModerator, userName, password);
	}

	@Override
	public boolean editThread(ThreadMessage newTM, String userName,
			String password) {
		return _clientHandler.editThread(newTM, userName, password);
	}

	@Override
	public boolean editPost(Post newP, String userName, String password) {
		return _clientHandler.editPost(newP, userName, password);
	}

	@Override
	public List<ForumNotification> getNotification(String forumName,
			String userName, String password) {
		return _clientHandler.getNotification(forumName, userName, password);
	}

	@Override
	public boolean removeModerator(String forumName, String subForum,
			String moderatorName, String userName, String password) {
		return _clientHandler.removeModerator(forumName, subForum, 
				moderatorName, userName, password);
	}

	@Override
	public int getNumOfThreadsInForum(String forumName, String userName,
			String password) {
		return _clientHandler.getNumOfThreadsInForum(forumName, userName, password);
	}

	@Override
	public int getNumOfUserThreads(String forumName, String userName,
			String requesterUserName, String requesterpassword) {
		return _clientHandler.getNumOfUserThreads(forumName, userName, 
				requesterUserName, requesterpassword);
	}

	@Override
	public HashMap<String, List<String>> getUsersPostToUser(String forumName,
			String userName, String password) {
		return _clientHandler.getUsersPostToUser(forumName, userName, password);
	}

	@Override
	public int getNumOfForums(String userName, String password) {
		return _clientHandler.getNumOfForums(userName, password);
	}

	@Override
	public List<String> getCommonMembers(String userName, String password) {
		return _clientHandler.getCommonMembers(userName, password);
	}

	@Override
	public SuperAdmin loginAsSuperAdmin(String userName, String password) {
		return _clientHandler.loginAsSuperAdmin(userName, password);
	}

	@Override
	public List<Member> getAllForumMembers(String forum, String userName,
			String password) {
		return _clientHandler.getAllForumMembers(forum, userName, password);
	}

	@Override
	public boolean listenToServer() {
		return _clientHandler.listenToServer();
	}

	@Override
	public boolean sendListenerIdentifier() {
		return _clientHandler.sendListenerIdentifier();
	}

	@Override
	public boolean hasNotifications(String forum, String userName,
			String password) {
		return _clientHandler.hasNotifications(forum, userName, password);
	}
}
