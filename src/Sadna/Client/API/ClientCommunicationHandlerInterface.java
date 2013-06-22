
package Sadna.Client.API;

import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.Client.User;
import Sadna.Server.ForumNotification;
import Sadna.db.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import dbTABLES.Memberdb;

/**
 *
 * @author fistuk
 */
public interface ClientCommunicationHandlerInterface extends Serializable {

    Member login(String forumName, String userName, String password);

    User logout(String forumName, String userName);

    boolean postComment(Post post, String password);

    boolean publishThread(ThreadMessage newThread, String password);

    Member register(String forumName, String userName, String password, String email);

    SubForum getSubForum(String forum, String subForumName);

    Forum getForum(String forumName);

    List<SubForum> getSubForumsList(String forumName);

    List<ThreadMessage> getThreadsList(String forumName, String subForumName);

    List<Forum> getForumsList();

    ThreadMessage getThreadMessage(String forumName, String subForumName, int messageID);

    boolean addSubForum(SubForum subForum, List<Member> lm, String userName, String password);

    boolean initiateForum(String forumName, String adminName, String adminPassword, Policy policy, String forbiddenWords, String superAdminName, String superAdminPasswaord);

    List<Post> getAllPosts(ThreadMessage tm);

    boolean deleteForum(String forumName, String userName, String password);

    boolean deleteSubForum(String forumName, String subForumName, String userName, String password);

    boolean deleteThreadMessage(ThreadMessage tm, String userName, String password);

    boolean deletePost(Post p, String userName, String password);

    boolean addModerator(String forumName, String subForumName, String newModerator, String userName, String password);

    boolean editThread(ThreadMessage newTM, String userName, String password);

    boolean editPost(Post newP, String userName, String password);

    List<ForumNotification> getNotification(String forumName, String userName, String password);

    public boolean finishCommunication();

    boolean removeModerator(String forumName, String subForum, String moderatorName, String userName, String password);

    int getNumOfThreadsInForum(String forumName, String userName, String password);

    public int getNumOfUserThreads(String forumName, String userName, String requesterUserName, String requesterpassword);

    public HashMap<String, List<String>> getUsersPostToUser(String forumName, String userName, String password);

    public int getNumOfForums(String userName, String password);

    public List<String> getCommonMembers(String userName, String password);
    
    public List<Moderator> getAllModeratorsInSubforum(String forumName, String subForumName, String adminUserName, String adminPassword);


    public SuperAdmin loginAsSuperAdmin(String userName, String password);

    public List<Member> getAllForumMembers(String forum, String userName,
            String password);

    public boolean listenToServer();

    public boolean sendListenerIdentifier();

    public boolean hasNotifications(String forum, String userName, String password);

	public boolean clearDataBase(String superAdminName, String superAdminPassword);
	
	public Member verifyEmail(String forumName, String username, String randomCode);

}
