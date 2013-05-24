/*
 * To change this template, choose Tools | Templates

 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.SubForum;

import java.util.HashMap;
import java.util.List;

public class Admin extends Moderator {

    /**
     *
     */
    private static final long serialVersionUID = -420707384012581575L;

    public Admin(String userName, String password, String email, String forumName,
            ClientCommunicationHandlerInterface ch) {
        super(userName, password, email, forumName, ch);
    }

    public boolean setForum(String forumName) {
        if (forum == null) {
            forum = forumName;
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean addSubForum(SubForum subForum, List<Member> m) {
        return conHand.addSubForum(subForum, m, this.userName, this.password);
    }

    public synchronized void deleteSubForum(String forumName, String subForumName) {
        try {
            conHand.deleteSubForum(forumName, subForumName, this.userName, this.password);
        } catch (Exception e) {
            System.out.println("Admin(deleteSubForum) " + e);
        }
    }

    /*
     *  ?s c m?
     */
    public synchronized boolean addModerator(String forumName, String subForumName, String newModerator) {
        try {
            return conHand.addModerator(forumName, subForumName, newModerator, this.userName, this.password);
        } catch (Exception e) {
            System.out.println("Admin(addModerator) " + e);
        }
        return false;
    }

    /*
     *   ?s r m?
     *   User is unique
     *	 Remove all his subFuroms
     *	 Remove only when exists more then one
     *	 Add to clientAPI removeModerator(String userName);
     *   Add to servertAPI removeModerator
     */
    public synchronized boolean removeModerator(String forumName,String subForumName, String moderatorName) {
        try {
            return conHand.removeModerator(forumName,subForumName, moderatorName, this.userName, this.password);
        } catch (Exception e) {
            System.out.println("Admin(removeModerator) " + e);
        }
        return false;
    }

    public synchronized int getThreadCounter(String forumName) {
        int numberOfThreads = -1;
        try {
            numberOfThreads = conHand.getNumOfThreadsInForum(forumName, this.userName, this.password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Admin(getThreadCounter) " + e);
        }
        return numberOfThreads;
    }

    public synchronized int getNumOfUserThreads(String forumName, String generalUserName) {
        int numberOfThreads = -1;
        try {
            numberOfThreads = conHand.getNumOfUserThreads(forumName, generalUserName, this.userName, this.password);
        } catch (Exception e) {
            System.out.println("Admin(getNumOfUserThreads) " + e);
        }
        return numberOfThreads;
    }
   
    /*
     * MAP(username:String, users that post:Vector<String>)
     */
    public synchronized HashMap<String, List<String>> getUsersPostToUser(String forumName) {
    	HashMap<String, List<String>> map = null;
        try {
        	map = conHand.getUsersPostToUser(forumName, this.userName, this.password);
        } catch (Exception e) {
            System.out.println("Admin(getUsersPostToUser) " + e);
        }
        return map;
    }
    
    public synchronized List<Member> getAllForumMembers(){
		return conHand.getAllForumMembers(this.forum, this.userName, this.password);
    }
    
   
}
