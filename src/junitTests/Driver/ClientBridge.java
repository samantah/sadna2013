package Driver;

import Sadna.Client.Member;
import Sadna.Client.SuperAdmin;
import Sadna.Client.User;
import Sadna.Server.ForumNotification;
import Sadna.db.Forum;
import Sadna.db.Policy;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

import java.util.HashMap;
import java.util.List;

public interface ClientBridge {
    //tested 

    Member login(String forumName, String userName, String password);
    //tested

    User logout(String forumName, String userName);
    //tested

    boolean postComment(Post post, String password);
    //tested

    boolean publishThread(ThreadMessage newThread, String password);
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

    boolean addSubForum(SubForum subForum, List<Member> lm, String userName, String password);
    //tested

    boolean initiateForum(String forumName, String adminName, String adminPassword, Policy policy, String superAdminName, String superAdminPasswaord);
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

    boolean editThread(ThreadMessage newTM, String userName, String password);
    //not supported

    boolean editPost(Post newP, String userName, String password);

    List<ForumNotification> getNotification(String forumName, String userName, String password);
    //not need to be tested

    public boolean finishCommunication();
    //tested

    boolean removeModerator(String forumName, String subForum, String moderatorName, String userName, String password);

    int getNumOfThreadsInForum(String forumName, String userName, String password);

    public int getNumOfUserThreads(String forumName, String userName, String requesterUserName, String requesterpassword);
    //not to test right now

    public HashMap<String, List<String>> getUsersPostToUser(String forumName, String userName, String password);
    //tested

    public int getNumOfForums(String userName, String password);
    //tested

    public List<String> getCommonMembers(String userName, String password);
    //tested

    public SuperAdmin loginAsSuperAdmin(String userName, String password);
    //tested

    public List<Member> getAllForumMembers(String forum, String userName,
            String password);

    public boolean listenToServer();

    public boolean sendListenerIdentifier();

    public boolean hasNotifications(String forum, String userName, String password);
}
