/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server.API;


import Sadna.Server.ForumNotification;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


import dbTABLES.*;
/**
 *
 * @author fistuk
 */
public interface ServerInterface {
	
    boolean register(String forumName, String userName, String password, String email);

    boolean login(String forumName, String userName, String password);

    boolean deleteForum(String forumName, String userName, String password);

    boolean addSubForum(Subforumdb subForum, List<Memberdb> moderators, String username, String password);

    boolean deleteSubForum(Subforumdb subForum, String userName, String password);

    boolean publishThread(Threaddb thread, String username, String password);

    boolean deleteThread(Threaddb thread, String userName, String password);
    
    boolean deleteModerator(Memberdb moderator, String subForumName, String modName, String userName, String password);

    boolean postComment(Postdb comment, String username, String password);

    boolean deleteComment(Postdb comment, String userName, String password);

    List<Forumdb> getForumsList();

    Forumdb getForum(String forumName);

    List<Subforumdb> getSubForumsList(String forumName);

    Subforumdb getSubForum(String forumName, String subForumName);

    List<Threaddb> getThreadsList(String forumName, String subForumName);

    Threaddb getThreadMessage(String forumName, String subForumName, int messageId);

    List<Memberdb> getModerators(String forumName, String subForumName);

    List<Postdb> getAllPosts(String forumName, String subForumName, int threadId);

    Postdb getPost(String forumName, String subForumName, int threadId, int postId);

    boolean memberExistsInForum(String forumName, String posterName);

    Memberdb getMember(String forumName, String userName);

    boolean addModerator(Memberdb moderator, Subforumdb subForum, String userName, String password);


    Memberdb getSuperAdmin();
    
    boolean addMember(Memberdb member);
 
    boolean logout(String forumName, String userName);
   
    public List<ForumNotification> getNotifications(String forumName, String userName, String password);

	int getNumberOfThreadsInForum(String forumName, String userName, String password);

	int getNumberOfUserThreads(String forumName, String username, String requesterUsername, String requesterPassword);

	HashMap<String, Set<String>> getUsersPostToUser(String forumName, String userName, String password);

	int getForumCounter(String userName, String password);

	List<String> getCommonMembers(String userName, String password);

	List<Memberdb> getAllForumMembers(String forumName, String userName, String password);

	boolean loginAsSuperAdmin(String userName, String password);    
	
	//mod or admin or publisher (String userName, String password)
	boolean editThread(Threaddb tm);

	//mod or admin or publisher (String userName, String password)
	boolean editPost(Postdb p);

	boolean initiateForum(String adminName, String adminPassword,
			String forumName, String ioap, String nfp, String dp, String amp,
			String s, String mp, String cmp, String forbiddenWords, String superAdminUserName,
			String superAdminPassword);
	
	boolean updateSubForum(Subforumdb subForum);

	boolean updateForum(Forumdb forum);
	
	boolean updateMember(Memberdb member);

	boolean setSuperAdmin();

	boolean clearDB();
	
	boolean openSession();
	
	boolean closeSession();

	int calcNumOfDaysSinceJoining(long dateOfJoining);
	
	
	
}

