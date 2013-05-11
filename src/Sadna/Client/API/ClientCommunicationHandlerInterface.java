/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client.API;


import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.Client.User;
import Sadna.Server.ForumNotification;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author fistuk
 */
public interface ClientCommunicationHandlerInterface {
    //tested 
    Member login(String forumName, String userName, String password);
    //tested
    User logout(String forumName, String userName);
    //tested
    boolean postComment(Post post);
    //tested
    boolean publishThread(ThreadMessage newThread);
    //tested
    Member register(String forumName, String userName, String password, String email);
    //tested
    SubForum getSubForum(String forum, String subForumName);
    //tested
    Forum getForum(String forumName);
    //tested
    List<SubForum> getSubForumsList(String forumName);
    //tested
    List<ThreadMessage> getThreadsList(String forumName, String subForumName);
    //tested
    List<Forum> getForumsList();
    //tested
    ThreadMessage getThreadMessage(String forumName, String subForumName, int messageID);
    //tested
    boolean addSubForum(SubForum subForum, List<Moderator> lm);
    //tested
    boolean initiateForum(String forumName, String adminName, String adminPassword, String superAdminName, String superAdminPasswaord);
    //tested
    List<Post> getAllPosts(ThreadMessage tm);
    //sami
    boolean deleteForum(String forumName, String userName, String password);
    //sami
    boolean deleteSubForum(String forumName, String subForumName, String userName, String password);
    //sami
    boolean deleteThreadMessage(ThreadMessage tm, String userName, String password);
    //sami
    boolean deletePost(Post p, String userName, String password);
    //tested
    boolean addModerator(String forumName, String subForumName, String newModerator, String userName, String password);
    //not supported
    boolean editThread(ThreadMessage tm, String newText, String userName, String password);
    //not supported
    boolean editPost(Post p, String newText, String userName, String password);
    
    List<ForumNotification> getNotification(String forumName, String userName, String password);
    //not need to be tested
    public boolean finishCommunication();
    //tested
    boolean removeModerator(String forumName, String subForum, String moderatorName, String userName, String password);

    int getThreadCounter(String forumName, String userName, String password);
    
    public int getNumOfUserThreads(String forumName, String generalUserName, String userName, String password);
    //not to test right now
    public HashMap<String ,List<String>> getUsersPostToUser(String forumName, String userName, String password);
    //tested
    public int getForumCounter(String userName, String password);
    //tested
    public List<String> getCommonMembers(String userName, String password);
    //tested
    public SuperAdmin loginAsSuperAdmin(String userName, String password);
    //tested
	public List<Member> getAllForumMembers(String forum, String userName,
			String password);

}
