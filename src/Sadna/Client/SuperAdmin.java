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

	public synchronized boolean initiateForum(String forumName, String adminName, String adminPassword, Policy policy) {
		return conHand.initiateForum(forumName, adminName, adminPassword, policy, this.userName, this.password);
	}

	public synchronized int getForumCounter() {
		return conHand.getNumOfForums(this.userName, this.password);
	}

	/*
	 * MAP(forum name:String, number of members in forum:Integer)
	 */
	public synchronized List<String> getCommonMembers() {
			return conHand.getCommonMembers(this.userName, this.password);
	}

	public synchronized List<Member> getAllForumMembers(String forumName) {
		return conHand.getAllForumMembers(forum, userName, password);
	}

	public synchronized boolean deleteForum(String forumName) {
		return conHand.deleteForum(forumName, this.userName, this.password);
	}

	public synchronized boolean clearDataBase(){
		return conHand.clearDataBase(this.userName, this.password);
	}
}
