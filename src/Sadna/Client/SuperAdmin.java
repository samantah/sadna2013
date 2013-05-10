/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

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
		return conHand.initiateForum(forumName, adminName, adminPassword);
	}

	public int getForumCounter(String forumName, String userName, String password) {
		int numberOfForums = -1;
		try {
			numberOfForums = conHand.getForumCounter(forumName, userName, password);
		} catch (Exception e) {
			System.out.println("SuperAdmin(getForumCounter) " + e);
		}
		return numberOfForums;
	}



}
