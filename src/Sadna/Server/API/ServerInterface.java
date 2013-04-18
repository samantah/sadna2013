/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server.API;

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

    boolean register(String userName, String password, String fullName);

    boolean login(String userName, String password);

    boolean publishThread(ThreadMessage thread, User user);

    SubForum getSubForum(String subForumName);

    List<SubForum> getSubForumsList();

    List<ThreadMessage> getThreadsList();

    ThreadMessage getThreadMessage(String threadMessage);

    Forum initiateForum(String adminUserName, String adminPassword);

    boolean addSubForum(SubForum subForum, List<Moderator> subForumModerators);

    boolean postComment(ThreadMessage commentTo, Post comment);
}
