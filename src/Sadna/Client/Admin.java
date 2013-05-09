/*
 * To change this template, choose Tools | Templates

 * and open the template in the editor.
 */


package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.SubForum;
import java.util.ArrayList;
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

	public boolean addSubForum(SubForum subForum, List<Moderator> lm) {
		return conHand.addSubForum(subForum, lm);
	}

	
	public void addSubForum(String newSubForumName, ArrayList<Moderator> arrayList) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	/*
	 *  ?s c m?
	 */
	public void addModerator(String forumName, String subForumName, String newModerator, 
			String userName, String password) {
		try{
			conHand.addModerator(forumName, subForumName, newModerator, userName, password);
		}
		catch(Exception e){
			System.out.println("Admin(addModerator) " + e); 
		}
	}

	/*
	 *   ?s r m?
	 *   User is unique
	 *	 Remove all his subFuroms
	 *	 Remove only when exists more then one
	 *	 Add to clientAPI removeModerator(String userName);
	 *   Add to servertAPI removeModerator
	 */
	public void removeModerator(String moderatorName) {
		try{
			//conHand.removeModerator(moderatorName);
		}
		catch(Exception e){
			System.out.println("Admin(removeModerator) " + e); 
		}
	}
	
	public void deleteSubForum(String forumName, String subForumName, String moderatorName) {
		try{
			conHand.deleteSubForum(forumName, subForumName, moderatorName, moderatorName);
		}
		catch(Exception e){
			System.out.println("Admin(deleteSubForum) " + e); 
		}
	}


	
}
