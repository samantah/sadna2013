/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.Client.Moderator;
import Sadna.db.Generators.ThreadIDGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class SubForum implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4538107590374025375L;
	private Forum forum;
    private List<ThreadMessage> listOfThreadMessages;
    private List<Moderator> listOfModerators;
    private String subForumName;
    private ThreadIDGenerator threadIDGenerator;

    public SubForum(Forum forum, String subForumName) {
        this.subForumName = subForumName;
        this.listOfThreadMessages = new ArrayList<ThreadMessage>();
        this.listOfModerators = new ArrayList<Moderator>();
        this.forum = forum;
        this.threadIDGenerator = new ThreadIDGenerator();
    }

    public SubForum(String forumStr, String subForumName) {
        this.subForumName = subForumName;
        this.listOfThreadMessages = new ArrayList<ThreadMessage>();
        this.listOfModerators = new ArrayList<Moderator>();
        this.forum = new Forum(null, forumStr);
        this.threadIDGenerator = new ThreadIDGenerator();
    }

    public ThreadIDGenerator getThreadIDGenerator() {
        return threadIDGenerator;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public boolean addModerator(Moderator e) {
        return listOfModerators.add(e);
    }

    public boolean removeModerator(Moderator e) {
        return listOfModerators.remove(e);
    }

    public boolean addThreadMessage(ThreadMessage e) {
        e.setId(threadIDGenerator.getID());
        return listOfThreadMessages.add(e);
    }

    public boolean removeThreadMessage(ThreadMessage e) {
        return listOfThreadMessages.remove(e);
    }

    public String getSubForumName() {
        return subForumName;
    }

    public List<ThreadMessage> getListOfThreadMessages() {
        return listOfThreadMessages;
    }

    public List<Moderator> getListOfModerators() {
        return listOfModerators;
    }
    
    
}
