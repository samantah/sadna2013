/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server.API;

import Sadna.Client.ConnectionHandler;
import Sadna.Client.Moderator;
import Sadna.Client.User;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.List;

/**
 *
 * @author fistuk
 */
public interface ServerInterface {

    boolean register(String forumName, String userName, String password, String email, ConnectionHandler ch);

    boolean login(String forumName, String userName, String password);

    boolean publishThread(ThreadMessage thread);

    SubForum getSubForum(String forumName, String subForumName);

    List<SubForum> getSubForumsList(String forumName);

    List<ThreadMessage> getThreadsList(String forumName, String subForumName);

    ThreadMessage getThreadMessage(String forumName, String subForumName, String threadMessage);

    Forum initiateForum(String adminUserName, String adminPassword, String forumName);

    boolean addSubForum(SubForum subForum);

    boolean postComment(Post comment);
}
