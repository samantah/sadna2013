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
        return new SubForum("forum1", "subForum1");
    }

    @Override
    public List<SubForum> getSubForumsList(String forumName) {
        SubForum l = new SubForum("forum1", "subForum1");
        List<SubForum> arrayList = new ArrayList<>();
        arrayList.add(l);
        return arrayList;
    }

    @Override
    public List<ThreadMessage> getThreadsList(String forumName, String subForumName) {
        List<ThreadMessage> retList = new ArrayList<ThreadMessage>();
        for (int i = 0; i < 44; i++) {
            ThreadMessage threadMessage = new ThreadMessage("forum1",
                    "sub1", "Title!!!" + i, "nothing", "chen");
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
        ThreadMessage threadMessage = new ThreadMessage(forumName, subForumName,
                "title", "content", "chen");
        for (int i = 0; i < 10; i++) {
            Post p = new Post(threadMessage, "postTitle " + i, "postContent ", "snir");
            threadMessage.addPost(p);
        }
        return threadMessage;
    }

    @Override
    public Forum getForum(String forumName) {
        Forum forum = new Forum(null, forumName);
        SubForum subForum = new SubForum(forum, "sub1");
        forum.addSubForum(subForum);
        return forum;
    }

    @Override
    public boolean addSubForum(SubForum subForum) {
        return true;
    }

    @Override
    public boolean initiateForum(String forumName, String adminName, String adminPassword) {
        return true;
    }
}
