package junitTests.Driver;

import java.util.List;

import Sadna.Client.Member;
import Sadna.Client.User;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

public class ClientProxyBridge implements ClientBridge {

    private ClientBridge real;

    public ClientProxyBridge() {
        this.real = null;
    }

    public void setRealBridge(ClientBridge real) {
        this.real = real;
    }

///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    public boolean addSubForum(SubForum subForum, String user, String pass) {
        if (this.real != null) {
            return this.real.addSubForum(subForum, user, pass);
        }
        return false;
    }

    public Forum getForum(String forumName) {
        if (this.real != null) {
            return this.real.getForum(forumName);
        }
        return null;
    }

    public List<Forum> getForumsList() {
        if (this.real != null) {
            return this.real.getForumsList();
        }
        return null;
    }

    public SubForum getSubForum(String forum, String subForumName) {
        if (this.real != null) {
            return this.real.getSubForum(forum, subForumName);
        }
        return null;
    }

    public List<SubForum> getSubForumsList(String forumName) {
        if (this.real != null) {
            return this.real.getSubForumsList(forumName);
        }
        return null;
    }

    public ThreadMessage getThreadMessage(String forumName,
            String subForumName, int messageID) {
        if (this.real != null) {
            return this.real.getThreadMessage(forumName,
                    subForumName, messageID);
        }
        return null;
    }

    public List<ThreadMessage> getThreadsList(String forumName,
            String subForumName) {
        if (this.real != null) {
            return this.real.getThreadsList(forumName,
                    subForumName);
        }
        return null;
    }

    public boolean initiateForum(String forumName, String adminName,
            String adminPassword, String superAdminName,
            String superAdminPasswaord) {
        if (this.real != null) {
            return this.real.initiateForum(forumName, adminName,
                    adminPassword, superAdminName, superAdminPasswaord);
        }
        return false;
    }

    public Member login(String forumName, String userName, String password) {
        if (this.real != null) {
            return this.real.login(forumName, userName, password);
        }
        return null;
    }

    public User logout(String forumName, String userName) {
        if (this.real != null) {
            return this.real.logout(forumName, userName);
        }
        return null;
    }

    public boolean postComment(Post post, String password) {
        if (this.real != null) {
            return this.real.postComment(post, password);
        }
        return false;
    }

    public boolean publishThread(ThreadMessage newThread, String password) {
        if (this.real != null) {
            return this.real.publishThread(newThread, password);
        }
        return false;
    }

    public Member register(String forumName, String userName, String password,
            String email) {
        if (this.real != null) {
            return this.real.register(forumName, userName, password,
                    email);
        }
        return null;
    }

    @Override
    public List<Post> getAllPosts(ThreadMessage tm) {
        if (this.real != null) {
            return this.real.getAllPosts(tm);
        }
        return null;
    }

    @Override
    public boolean finishCommunication() {
        if (this.real != null) {
            return this.real.finishCommunication();
        }
        return false;
    }
}
