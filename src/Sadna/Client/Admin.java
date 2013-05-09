/*
 * To change this template, choose Tools | Templates

 * and open the template in the editor.
 */

// try 1
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

    public boolean addSubForum(SubForum subForum, List<Moderator> lm) { // chen added this function
        return conHand.addSubForum(subForum, lm);
    }
    
    
    
    public void addModerator(String userName, String password, String email,
            String forum, ClientCommunicationHandlerInterface ch) {
        throw new UnsupportedOperationException("Admin(addModerator) Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
