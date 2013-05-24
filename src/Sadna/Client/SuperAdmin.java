/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.Policy;

import java.util.List;

/**
 *
 * @author fistuk
 */
public class SuperAdmin extends Admin {

    /**
     *
     */
    private static final long serialVersionUID = 3955980432575126358L;

    public SuperAdmin(String userName, String password, String email, ClientCommunicationHandlerInterface ch) {
        super(userName, password, email, null, ch);
    }

    public boolean initiateForum(String forumName, String adminName, String adminPassword, Policy policy) {
        return conHand.initiateForum(forumName, adminName, adminPassword, policy, this.userName, this.password);
    }

	public int getForumCounter() {
		int numberOfForums = -1;
		try {
			numberOfForums = conHand.getNumOfForums(this.userName, this.password);
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

    public List<Member> getAllForumMembers(String forumName) {
        return conHand.getAllForumMembers(forum, userName, password);
    }
    
    public boolean deleteForum(String forumName){
        return conHand.deleteForum(forumName, this.userName, this.password);
    }
}
