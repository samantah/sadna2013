/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;

public class Admin extends Member {

    public Admin(String userName, String password, String email,
            ClientCommunicationHandlerInterface ch) {
        super(userName, password, email, null, ch);
    }
    
	public boolean setForum(String forumName) {
		if(forum == null){
			forum = forumName;
			return true;
		}
		else{
			return false;
		}
	}
}
