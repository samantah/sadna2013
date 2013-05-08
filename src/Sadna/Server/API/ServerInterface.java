/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server.API;

import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.List;

/**
 *
 * @author fistuk
 */
public interface ServerInterface {

    boolean register(String forumName, String userName, String password, String email);

    boolean login(String forumName, String userName, String password);

    boolean initiateForum(String adminUserName, String adminPassword, String forumName);

    boolean deleteForum(String forumName);
    
    boolean addSubForum(SubForum subForum, List<Moderator> moderators);

    boolean deleteSubForum(SubForum subForum);

    boolean publishThread(ThreadMessage thread);

    boolean deleteThread(ThreadMessage thread);

    boolean postComment(Post comment);

    boolean deleteComment(Post comment);

    List<Forum> getForumsList();

    Forum getForum(String forumName);

    List<SubForum> getSubForumsList(String forumName);

    SubForum getSubForum(String forumName, String subForumName);

    List<ThreadMessage> getThreadsList(String forumName, String subForumName);

    ThreadMessage getThreadMessage(String forumName, String subForumName, int messageId);

    List<Member> getModerators(String forumName, String subForumName);

    List<Post> getAllPosts(String forumName, String subForumName, int threadId);
    
    Post getPost(String forumName, String subForumName, int threadId, int postId);

    boolean memberExistsInForum(String forumName, String posterName);
    
    Member getMember(String forumName, String userName);
    
    boolean addNotification(int threadId);
    
    boolean addModerator(Moderator moderator, SubForum subForum);
    
    boolean setSuperAdmin();
    
    SuperAdmin getSuperAdmin();
    
    /* For later use..  
     * 
     * boolean logout(String forumName, String userName);
     */
}
