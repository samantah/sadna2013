/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Server.ForumNotification;
import Sadna.db.Post;
import Sadna.db.ThreadMessage;

import java.util.ArrayList;
import java.util.List;
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
    protected List<ForumNotification> notifications;

    public Member(String userName, String password, String email, String forum, ClientCommunicationHandlerInterface ch) {
        super(ch);
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.forum = forum;
        this.notifications = new ArrayList<ForumNotification>();
    }

    public List<ForumNotification> getNotifications() {
        return notifications;
    }
    //Server Use Only
    public void clearNotifications() {
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
    
    public User logout(String forum) {
        return this.conHand.logout(forum, this.userName);
    }
    //Server Use Only 
    public void addNotification(ForumNotification forumNotification) {
        this.notifications.add(forumNotification);
    }

    public boolean deleteThread(ThreadMessage tm) {
        return this.conHand.deleteThreadMessage(tm, this.userName, this.password);
    }

    public boolean deletePost(Post p) {
        return this.conHand.deletePost(p, this.userName, this.password);
    }
    
    public boolean editPost(Post p, String newText){
    	return conHand.editPost(p, newText, this.userName, this.password);
    }
    
    public boolean editThread(ThreadMessage tm, String newText){
    	return conHand.editThread(tm, newText, this.userName, this.password);
    }
}
