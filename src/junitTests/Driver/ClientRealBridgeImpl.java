package junitTests.Driver;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Client.ClientConnectionHandler;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.User;
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

    public boolean addSubForum(SubForum subForum, String user, String pass) {
        return _clientHandler.addSubForum(subForum, new ArrayList<Member>(), user, pass);
    }

    public Forum getForum(String forumName) {
        return _clientHandler.getForum(forumName);
    }

    public List<Forum> getForumsList() {
        return _clientHandler.getForumsList();
    }

    public SubForum getSubForum(String forum, String subForumName) {
        return _clientHandler.getSubForum(forum, subForumName);
    }

    public List<SubForum> getSubForumsList(String forumName) {
        return _clientHandler.getSubForumsList(forumName);
    }

    public ThreadMessage getThreadMessage(String forumName,
            String subForumName, int messageID) {
        return _clientHandler.getThreadMessage(forumName, subForumName, messageID);
    }

    public List<ThreadMessage> getThreadsList(String forumName,
            String subForumName) {
        return _clientHandler.getThreadsList(forumName, subForumName);
    }

    public boolean initiateForum(String forumName, String adminName, String adminPassword, String superAdminName, String superAdminPasswaord) {
        Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
                enumNotiFriends.ALLMEMBERS, enumDelete.LIMITED, 
                enumAssignModerator.NO_RESTRICTION, enumCancelModerator.NO_RESTRICTION, 0, 0);
        return _clientHandler.initiateForum(forumName, adminName, adminPassword, policy,"superAdmin", "superAdmin1234");
    }

    public Member login(String forumName, String userName, String password) {
        return _clientHandler.login(forumName, userName, password);
    }

    public User logout(String forumName, String userName) {
        return _clientHandler.logout(forumName, userName);
    }

    public boolean postComment(Post post, String password) {
        return _clientHandler.postComment(post, password);
    }

    public boolean publishThread(ThreadMessage newThread, String password) {
        return _clientHandler.publishThread(newThread, password);
    }

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
}
