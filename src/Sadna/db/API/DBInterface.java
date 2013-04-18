/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db.API;

import java.util.List;

import Sadna.Client.User;
import Sadna.db.Forum;
import Sadna.db.Message;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

/**
 *
 * @author fistuk
 */
public interface DBInterface {

    Forum getForum(String forumName);

    SubForum getSubForum(String forumName, String subForumName);

    ThreadMessage getThread(String forumName, String subForumName, int messageID);

    User getUser(String userName);
    
    List<SubForum> getSubForumsList();
    
    List<ThreadMessage> getThreadsList(String subForumName);
    
    int getNumberOfSubforums(String forumName);
    
    int getNumberOfThreads(String forumName, String subForumName);

    boolean addForum(Forum forum);

    boolean addSubForum(SubForum subForum);

    boolean addMessage(Message message);

    boolean addUser(User user);
}
