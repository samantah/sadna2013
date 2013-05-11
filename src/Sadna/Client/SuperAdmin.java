/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import Sadna.Client.API.ClientCommunicationHandlerInterface;

/**
 *
 * @author fistuk
 */
public class SuperAdmin extends Admin{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3955980432575126358L;

	public SuperAdmin(String userName, String password, String email, ClientCommunicationHandlerInterface ch) {
		super(userName, password, email,null ,ch);
	}

	public boolean initiateForum(String forumName, String adminName, String adminPassword) {
		return conHand.initiateForum(forumName, adminName, adminPassword, this.userName, this.password);
	}

	public int getForumCounter() {
		int numberOfForums = -1;
		try {
			numberOfForums = conHand.getForumCounter(this.userName, this.password);
		} catch (Exception e) {
			System.out.println("SuperAdmin(getForumCounter) " + e);
		}
		return numberOfForums;
	}
	
    /*
     * MAP(forum name:String, number of members in forum:Integer)
     */
    public List<String> getCommonMembers() {
		List<String> map = null;
        try {
        	map = conHand.getCommonMembers(this.userName, this.password);
        } catch (Exception e) {
            System.out.println("Admin(getNumMembersInEachForum) " + e);
        }
        return map;
    }
    
    public List<Member> getAllForumMembers(String forumName){
    	return conHand.getAllForumMembers(forum, userName, password);
    }



}
