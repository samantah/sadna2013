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

	public void deleteSubForum(String forumName, String subForumName, String moderatorName) {
		try{
			conHand.deleteSubForum(forumName, subForumName, moderatorName, moderatorName);
		}
		catch(Exception e){
			System.out.println("Admin(deleteSubForum) " + e); 
		}
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
	public boolean removeModerator(String forumName, String moderatorName, String userName, String password)
	{
		try{
			conHand.removeModerator(forumName, moderatorName, userName, password);
		}
		catch(Exception e){
			System.out.println("Admin(removeModerator) " + e); 
		}
		return true;
	}



	public int getThreadCounter(String forumName, String userName, String password) {
		int numberOfThreads = -1;
		try{
			numberOfThreads = conHand.getThreadCounter(forumName, userName, password);
		}
		catch(Exception e){
			System.out.println("Admin(getThreadCounter) " + e); 
		}
		return numberOfThreads;
	}


	public int getNumOfUserThread(String forumName, String generalUserName, String userName, String password) {
		int numberOfThreads = -1;
		try{
			numberOfThreads = conHand.getThreadCounter(forumName, userName, password);
		}
		catch(Exception e){
			System.out.println("Admin(getNumOfUserThread) " + e); 
		}
		return numberOfThreads;
	}

	
	public int getUsersPostToUser(String forumName, String userName, String password) {
		try{
			conHand.getThreadCounter(forumName, userName, password);
		}
		catch(Exception e){
			System.out.println("Admin(getUsersPostToUser) " + e); 
		}
		return 8;
	}


}
