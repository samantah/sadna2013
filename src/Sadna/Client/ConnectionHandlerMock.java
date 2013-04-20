/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class ConnectionHandlerMock implements ClientCommunicationHandlerInterface {

    @Override
    public Member login(String forumName, String userName, String password) {
        return new Member("user", "pass12345", "mail", "forum1", null);
    }

    @Override
    public User logout(String forumName, String userName) {
        return new User(null);
    }

    @Override
    public boolean postComment(Post post) {
        return true;
    }

    @Override
    public boolean publishThread(ThreadMessage newThread) {
        return true;
    }

    @Override
    public Member register(String forumName, String userName, String password, String email) {
        return new Member("user", "pass12345", "mail", "forum1", null);
    }

    @Override
    public SubForum getSubForum(String forum, String subForumName) {
        SubForum subForum = new SubForum(new Forum("forum1"), "subForum1");
        return subForum;
    }

    @Override
    public List<SubForum> getSubForumsList(String forumName) {
        SubForum l = new SubForum(new Forum("forum1"), "subForum1");
        List<SubForum> arrayList = new ArrayList<>();
        arrayList.add(l);
        return arrayList;
    }

    @Override
    public List<ThreadMessage> getThreadsList(String forumName, String subForumName) {
        List<ThreadMessage> retList = new ArrayList<ThreadMessage>();
        SubForum l ;
        for (int i = 0; i < 12; i++) {
            l = new SubForum(new Forum("forum1"), "subForum1");
            ThreadMessage threadMessage = new ThreadMessage(l, "Title!!!" + i, "nothing", "chen");
            retList.add(threadMessage);
        }
        return retList;
    }

    @Override
    public List<Forum> getForumsList() {
        Forum l = new Forum(null, "forum1");
        List<Forum> arrayList = new ArrayList<>();
        arrayList.add(l);
        return arrayList;
    }

    @Override
    public ThreadMessage getThreadMessage(String forumName, String subForumName, int messageID) {
        SubForum subForum = new SubForum(new Forum(forumName), subForumName);
        ThreadMessage threadMessage = new ThreadMessage(subForum,
                "title", "content", "chen");
          return threadMessage;
    }

    @Override
    public Forum getForum(String forumName) {
        Forum forum = new Forum(null, forumName);
        SubForum subForum = new SubForum(forum, "sub1");
        return forum;
    }

    @Override
    public boolean addSubForum(SubForum subForum, List<Moderator> l) {
        return true;
    }

    @Override
    public boolean initiateForum(String forumName, String adminName, String adminPassword) {
        return true;
    }

    @Override
    public List<Post> getAllPosts(ThreadMessage tm) {
        ArrayList<Post> arrayList = new ArrayList<Post>();
        for (int i = 0; i < 44; i++) {
            arrayList.add(new Post(tm, "title " + i, "content" , "chen"));            
        }
        return arrayList;
    }
}
