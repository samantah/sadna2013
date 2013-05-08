/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Server.ForumNotification;
import Sadna.db.Post;
import Sadna.db.ThreadMessage;
import java.util.Vector;

public class Member extends User {

    /**
     *
     */
    private static final long serialVersionUID = 383909427775825138L;
    protected String userName;
    protected String password;
    protected String email;
    protected String forum;
    protected Vector<ForumNotification> notifications;

    public Member(String userName, String password, String email, String forum, ClientCommunicationHandlerInterface ch) {
        super(ch);
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.forum = forum;
        this.notifications = new Vector<ForumNotification>();
    }

    public Vector<ForumNotification> getNotifications() {
        return notifications;
    }
    
    public void clearNotifications(){
        this.notifications.clear();
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

    public User logout(String forum, String userName) {
        return this.conHand.logout(forum, userName);
    }

    public void addNotification(ForumNotification forumNotification) {
        this.notifications.add(forumNotification);
    }
}
