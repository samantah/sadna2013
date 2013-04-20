/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import java.util.List;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.SubForum;

public class Admin extends Member {

    /**
	 * 
	 */
	private static final long serialVersionUID = -420707384012581575L;

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
	
	public boolean addSubForum(SubForum subForum, List<Moderator> lm){
		return conHand.addSubForum(subForum, lm);
	}
}
