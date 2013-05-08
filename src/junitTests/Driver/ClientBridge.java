package junitTests.Driver;

import java.util.List;

import Sadna.Client.Member;
import Sadna.Client.User;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

public interface ClientBridge {
	
    Member login(String forumName, String userName, String password);
    
    User logout(String forumName, String userName);

    boolean postComment(Post post);

    boolean publishThread(ThreadMessage newThread);

    Member register(String forumName, String userName, String password, String email);

    SubForum getSubForum(String forum ,String subForumName);
    
    Forum getForum(String forumName);

    List<SubForum> getSubForumsList(String forumName);
    
    List<ThreadMessage> getThreadsList(String forumName ,String subForumName);
    
    List<Forum> getForumsList();
    
    ThreadMessage getThreadMessage(String forumName ,String subForumName, int messageID);
    
    boolean addSubForum(SubForum subForum);
    
    boolean initiateForum(String forumName, String adminName, String adminPassword);

    List<Post> getAllPosts(ThreadMessage tm);
    
    public boolean finishCommunication();

	
}