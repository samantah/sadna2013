/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server.API;

import Sadna.db.Message;
import Sadna.db.Forum;
import Sadna.Client.Moderator;
import Sadna.Server.User;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.List;

/**
 *
 * @author fistuk
 */
public interface ServerInterface {
    boolean register(String userName, String password, String fullName);
    
    boolean login(String userName, String password);
    
    void publishThread(SubForum subForumToPublishIn, ThreadMessage thread, User user);
    
    SubForum getSubForum(String subForumName);
    
    List<SubForum> getSubForumList();
    
    Forum initiateForum(String adminUserName, String adminPassword);
    
    void AddSubForum(Forum forum, SubForum subForum, List<Moderator> subForumModerators);
    
    void publishComment(Message commentTo, Post comment);
    
    ThreadMessage getThread(String threadName);
    
    void deleteThread(ThreadMessage thread);
    
    void deletePost(Post post); 
}
