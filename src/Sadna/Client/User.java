/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import java.io.Serializable;
import java.util.List;

import Sadna.db.Forum;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

/**
 *
 * @author fistuk
 */
public class User implements Serializable {

    protected ConnectionHandler conHand;

    public User(ConnectionHandler ch) {
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
    

    public List<Forum> viewForums() {
        return conHand.getForumsList();
    }
}
