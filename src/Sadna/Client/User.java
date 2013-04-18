/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import java.io.Serializable;
import java.util.List;

import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

/**
 *
 * @author fistuk
 */
public abstract class User implements Serializable{
	protected ConnectionHandler conHand;
	
	public User(ConnectionHandler ch){
		this.conHand = ch;
	}
	
    protected Member login(String forum, String userName, String password){
    	return conHand.login(forum,userName, password);
    }
    
    protected List<SubForum> viewSubForums(String forum){
    	return conHand.getSubForumsList(forum);
    }
    protected List<ThreadMessage> viewThreadMessages(String forum ,String subForumName){
    	return conHand.getThreadsList(forum ,subForumName);
    }
    
    protected Member register(String forumName, String userName, String password, String email){
    	return conHand.register(forumName, userName, password, email);
    }
}
