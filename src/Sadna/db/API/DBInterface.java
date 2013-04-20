/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db.API;

import java.util.List;

import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

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
    
    List<Moderator> getModerators(String forumName, String subForumName);

    List<SubForum> getSubForumsList(String forumName);

    List<ThreadMessage> getThreadsList(String forumName, String subForumName);
    
    List<Post> getPostList(String forumName, String subForumName, int threadID);
    
    List<Forum> getForumsList();

    int getNumberOfSubforums(String forumName);

    int getNumberOfThreads(String forumName, String subForumName);

    boolean addForum(Forum forum);

    boolean addSubForum(SubForum subForum);

    boolean addMember(Member member);

    boolean addPost(Post post);

    boolean addThread(ThreadMessage thread);
    
    List<Member> getAllMembers(String forumName);
    
    boolean setSuperAdmin();
    
    SuperAdmin getSuperAdmin();
    

}
