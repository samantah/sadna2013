/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client.API;


import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.User;
import Sadna.Server.ForumNotification;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author fistuk
 */
public interface ClientCommunicationHandlerInterface {
	
	// removeModerator()
     
    Member login(String forumName, String userName, String password);

    User logout(String forumName, String userName);

    boolean postComment(Post post);

    boolean publishThread(ThreadMessage newThread);

    Member register(String forumName, String userName, String password, String email);

    SubForum getSubForum(String forum, String subForumName);

    Forum getForum(String forumName);

    List<SubForum> getSubForumsList(String forumName);

    List<ThreadMessage> getThreadsList(String forumName, String subForumName);

    List<Forum> getForumsList();

    ThreadMessage getThreadMessage(String forumName, String subForumName, int messageID);

    boolean addSubForum(SubForum subForum, List<Moderator> lm);

    boolean initiateForum(String forumName, String adminName, String adminPassword);

    List<Post> getAllPosts(ThreadMessage tm);

    boolean deleteForum(String forumName, String userName, String password);

    boolean deleteSubForum(String forumName, String subForumName, String userName, String password);

    boolean deleteThreadMessage(ThreadMessage tm, String userName, String password);

    boolean deletePost(Post p, String userName, String password);

    boolean addModerator(String forumName, String subForumName, String newModerator, String userName, String password);

    boolean editThread(ThreadMessage tm, String newText, String userName, String password);

    boolean editPost(Post p, String newText, String userName, String password);
    
    Vector<ForumNotification> getNotification(String forumName, String userName, String password);

    public boolean finishCommunication();
}
