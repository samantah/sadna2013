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
import java.util.Objects;

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
    protected long dateOfJoining;

    public Member(String userName, String password, String email, String forum, ClientCommunicationHandlerInterface ch) {
        super(ch);
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.forum = forum;
        this.notifications = new ArrayList<ForumNotification>();
    }

    public long getDateOfJoining(){
    	return dateOfJoining;
    }
    
    public void setDateOfJoining(long time){
    	dateOfJoining = time;
    }
    
    public synchronized List<ForumNotification> getNotificationsFromServer(){
        return this.conHand.getNotification(this.forum, this.userName, this.password);
    }
    public List<ForumNotification> getNotifications() {
        return notifications;
    }
    //Server Use Only
    public void clearNotifications() {
        this.notifications.clear();
    }
    
    public synchronized boolean postComment(Post post) {
        return conHand.postComment(post, this.password);
    }

    public synchronized boolean publishThread(ThreadMessage newThread) {
        return conHand.publishThread(newThread, this.password);
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
    
    public synchronized User logout(String forum) {
        return this.conHand.logout(forum, this.userName);
    }
    //Server Use Only 
    public void addNotification(ForumNotification forumNotification) {
        this.notifications.add(forumNotification);
    }

    public synchronized boolean deleteThread(ThreadMessage tm) {
        return this.conHand.deleteThreadMessage(tm, this.userName, this.password);
    }

    public synchronized boolean deletePost(Post p) {
        return this.conHand.deletePost(p, this.userName, this.password);
    }
    
    public synchronized boolean editPost(Post newP){
    	return conHand.editPost(newP, this.userName, this.password);
    }
    
    public synchronized boolean editThread(ThreadMessage newTM){
    	return conHand.editThread(newTM, this.userName, this.password);
    }
    @Override
    public String toString(){
    	return this.userName;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
    
    public synchronized boolean hasNotifications(){
        return this.conHand.hasNotifications(this.forum, this.userName, this.password);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Member other = (Member) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
