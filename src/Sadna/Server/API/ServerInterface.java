/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server.API;

import java.util.List;

import Sadna.Server.Users.Member;
import Sadna.Server.Users.Moderator;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

/**
 *
 * @author fistuk
 */
public interface ServerInterface {

    boolean register(String forumName, String userName, String password, String email);

    boolean login(String forumName, String userName, String password);
 
    boolean initiateForum(String adminUserName, String adminPassword, String forumName);
    
    boolean addSubForum(SubForum subForum, List<Moderator> moderators);

    boolean publishThread(ThreadMessage thread);
    
    boolean postComment(Post comment);

    List<Forum> getForumsList();

    Forum getForum(String forumName);

    List<SubForum> getSubForumsList(String forumName);

    SubForum getSubForum(String forumName, String subForumName);

    List<ThreadMessage> getThreadsList(String forumName, String subForumName);

    ThreadMessage getThreadMessage(String forumName, String subForumName, int messageId);
    
    List<Member> getModerators(String forumName, String subForumName);

    List<Post> getAllPosts(String forumName, String subForumName, int threadId);

	boolean memberExistsInForum(String forumName, String posterName);
    

    /* For later use..  
     * 
     * boolean logout(String forumName, String userName);
     */

}
