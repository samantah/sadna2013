/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import java.io.Serializable;
import java.util.List;

import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

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

    public User(ClientCommunicationHandlerInterface ch) {
        this.conHand = ch;
    }

    public Member login(String forum, String userName, String password) {
        return conHand.login(forum, userName, password);
    }
    
    public List<SubForum> viewSubForums(String forum) {
        return conHand.getSubForumsList(forum);
    }

    public List<ThreadMessage> viewThreadMessages(String forum, String subForumName) {
        return conHand.getThreadsList(forum, subForumName);
    }

    public Member register(String forumName, String userName, String password, String email) {
        return conHand.register(forumName, userName, password, email);
    }
    
    public Forum getForum(String forumName){
        return conHand.getForum(forumName);
    }
    
    public SubForum getSubForum(String forumName, String subForumName){
        return conHand.getSubForum(forumName, subForumName);
    }
    
    public ThreadMessage getThread(String forumName, String subForumname, int messageID){
        return conHand.getThreadMessage(forumName, subForumname, messageID);
    }

    public List<Forum> viewForums() {
        return conHand.getForumsList();
    }
    
    public List<Post> getAllPosts(ThreadMessage tm){
    	return conHand.getAllPosts(tm);
    }
    
}
