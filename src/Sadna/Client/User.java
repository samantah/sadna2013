/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6569160581577696172L;
    protected ClientCommunicationHandlerInterface conHand;

    public User(){
    	
    }
    
    public User(ClientCommunicationHandlerInterface ch) {
        this.conHand = ch;
    }

    public synchronized Member login(String forum, String userName, String password) {
        return conHand.login(forum, userName, password);
    }
    
    public synchronized SuperAdmin loginAsSuperAdmin(String userName, String password) {
        return conHand.loginAsSuperAdmin(userName, password);
    }

    public synchronized List<SubForum> viewSubForums(String forum) {
        return conHand.getSubForumsList(forum);
    }

    public synchronized List<ThreadMessage> viewThreadMessages(String forum, String subForumName) {
        return conHand.getThreadsList(forum, subForumName);
    }

    public synchronized Member register(String forumName, String userName, String password, String email) {
        return conHand.register(forumName, userName, password, email);
    }
    
    public synchronized Member verifyEmail(String forumName, String userName, String randomCode) {
        return conHand.verifyEmail(forumName, userName, randomCode);
    }

    public synchronized Forum getForum(String forumName) {
        return conHand.getForum(forumName);
    }

    public synchronized SubForum getSubForum(String forumName, String subForumName) {
        return conHand.getSubForum(forumName, subForumName);
    }

    public synchronized ThreadMessage getThread(String forumName, String subForumname, int messageID) {
        return conHand.getThreadMessage(forumName, subForumname, messageID);
    }

    public synchronized List<Forum> viewForums() {
        return conHand.getForumsList();
    }

    public synchronized List<Post> getAllPosts(ThreadMessage tm) {
        return conHand.getAllPosts(tm);
    }

    public ClientCommunicationHandlerInterface getConHand() {
        return conHand;
    }
}
