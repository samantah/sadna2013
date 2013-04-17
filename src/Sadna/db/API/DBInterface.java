/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db.API;

import Sadna.Client.User;
import Sadna.db.Forum;
import Sadna.db.Message;
import Sadna.db.SubForum;

/**
 *
 * @author fistuk
 */
public interface DBInterface {
    Forum getForum(String forumName);
    
    SubForum getSubForum(String ForumName, String subForumName);
    
    User getUser(String userName);
    
    Message getMessage(String ForumName, String subForumName, int messageID);
    
    void addForum(Forum forum);
    
    void addSubForum(SubForum subForum);
    
    void addUser(User user);
    
    void addMessage(Message message);
}
