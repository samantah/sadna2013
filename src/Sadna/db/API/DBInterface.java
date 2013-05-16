/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db.API;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Client.Admin;
import Sadna.Client.ClientConnectionHandler;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.Server.API.ConnectionHandlerServerInterface;
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
public interface DBInterface {

    Forum getForum(String forumName);

    SubForum getSubForum(String forumName, String subForumName);

    ThreadMessage getThread(String forumName, String subForumName, int threadID);

    Post getPost(String forumName, String subForumName, int ThreadID, int postID);

    Member getMember(String forumName, String userName);

    List<Member> getModerators(String forumName, String subForumName);

    List<SubForum> getSubForumsList(String forumName);

    List<ThreadMessage> getThreadsList(String forumName, String subForumName);

    List<Post> getPostList(String forumName, String subForumName, int threadID);

    List<Forum> getForumsList();

    List<Admin> getAllAdmins();

    int getNumberOfSubforums(String forumName);

    int getNumberOfThreadsInSubForum(String forumName, String subForumName);

    int getNumberOfThreadsInForum(String forumName);

    boolean addForum(Forum forum);

    boolean addSubForum(SubForum subForum, List<Moderator> listOfModerators);

    boolean addMember(Member member);

    boolean addPost(Post post);

    boolean addThread(ThreadMessage thread);

    boolean deleteForum(Forum forum);

    boolean deleteSubForum(SubForum subForum);

    boolean deleteMember(Member member);

    boolean deleteModerator(Moderator moderatorm, String subForum);

    boolean deletePost(Post post);

    boolean deleteThread(ThreadMessage thread);

    List<Member> getAllMembers(String forumName);

    boolean setSuperAdmin(ClientCommunicationHandlerInterface ch);

    boolean addModerator(Moderator moderator, SubForum subForum);

    SuperAdmin getSuperAdmin();

    int getNumberOfUserThreads(String forumName, Member member); //returns the number of threads that the user write in the specified forum.

    HashMap<String, List<String>> getUsersPostToUser(String forumName); //returns for each member in this forum list that his user name in the first index and in the other indices  the users names of the members that post comments for his threads. 

    int getNumberOfForums(); // super admin method - returns number of forums.

    List<String> getCommonMembers(); // returns list of users names that registered to more than one forum.
    
    void deleteFolder(String folder);
    
    void initiateDataBase();
}
