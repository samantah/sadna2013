/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.Forum;

/**
 *
 * @author fistuk
 */
public class SuperAdmin extends Admin{

    public SuperAdmin(String userName, String password, String email, ClientCommunicationHandlerInterface ch) {
        super(userName, password, email, ch);
    }

	public boolean initiateForum(String forumName, String adminName, String adminPassword) {
		return conHand.initiateForum(forumName, adminName, adminPassword);
	}
    
}
