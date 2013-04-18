/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.Client.User;
import Sadna.db.API.DBInterface;


public class DataBase implements DBInterface {
    
    @Override
    public Forum getForum(String forumName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SubForum getSubForum(String forumName, String subForumName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThreadMessage getThread(String forumName, String subForumName, int messageID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User getUser(String userName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addForum(Forum forum) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addSubForum(SubForum subForum) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addUser(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
