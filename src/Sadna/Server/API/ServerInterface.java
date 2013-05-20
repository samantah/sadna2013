/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server.API;

import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Server.ForumNotification;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author fistuk
 */
public interface ServerInterface {
	
    boolean register(String forumName, String userName, String password, String email);

    boolean login(String forumName, String userName, String password);

    boolean deleteForum(String forumName, String userName, String password);

    boolean addSubForum(SubForum subForum, List<Member> moderators, String username, String password);

    boolean deleteSubForum(SubForum subForum, String userName, String password);

    boolean publishThread(ThreadMessage thread, String username, String password);

    boolean deleteThread(ThreadMessage thread, String userName, String password);
    
    boolean deleteModerator(Moderator moderator, String subForumName, String modName, String userName, String password);

    boolean postComment(Post comment, String username, String password);

    boolean deleteComment(Post comment, String userName, String password);

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

    boolean addModerator(Moderator moderator, SubForum subForum, String userName, String password);

    boolean setSuperAdmin(ClientCommunicationHandlerInterface ch);

    SuperAdmin getSuperAdmin();
    
    boolean addMember(Member member);
 
    boolean logout(String forumName, String userName);
   
    public List<ForumNotification> getNotifications(String forumName, String userName, String password);

	int getNumberOfThreadsInForum(String forumName, String userName, String password);

	int getNumberOfUserThreads(String forumName, String username, String requesterUsername, String requesterPassword);

	HashMap<String, List<String>> getUsersPostToUser(String forumName, String userName, String password);

	int getForumCounter(String userName, String password);

	List<String> getCommonMembers(String userName, String password);

	List<Member> getAllForumMembers(String forumName, String userName, String password);

	boolean loginAsSuperAdmin(String userName, String password);    
	
	//mod or admin or publisher (String userName, String password)
	boolean editThread(ThreadMessage tm, String userName, String password);

	//mod or admin or publisher (String userName, String password)
	boolean editPost(Post p, String userName, String password);

	boolean initiateForum(String adminName, String adminPassword,
			String forumName, String ioap, String nfp, String dp, String amp, String s,
			String mp, String cmp, String superAdminUserName,
			String superAdminPassword);

}
