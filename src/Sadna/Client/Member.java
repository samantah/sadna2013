/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import java.util.List;

import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

public class Member extends User {

    protected String userName;
    protected String password;
    protected String email;
    protected String forum;

    public Member(String userName, String password, String email, String forum, ClientCommunicationHandlerInterface ch) {
        super(ch);
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.forum = forum;
    }

    public boolean postComment(Post post) {
        return conHand.postComment(post);
    }

    public boolean publishThread(ThreadMessage newThread) {
        return conHand.publishThread(newThread);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getForum() {
        return forum;
    }
}
