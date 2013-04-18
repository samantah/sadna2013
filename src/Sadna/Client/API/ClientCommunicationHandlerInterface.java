/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client.API;

import Sadna.Client.Member;
import Sadna.Client.User;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.List;

/**
 *
 * @author fistuk
 */
public interface ClientCommunicationHandlerInterface {

    boolean login(String forumName, String userName, String password);

    boolean postComment(Post post, Member member);

    boolean publishThread(ThreadMessage newThread, Member member);

    boolean register(String forumName, String userName, String password, String email);

    SubForum getSubForum(String forum ,String subForumName);

    List<SubForum> getSubForumsList(String forumName);
    
    List<ThreadMessage> getThreadsList(String forumName ,String subForumName);
    
    ThreadMessage getThreadMessage(String forumName ,String subForumName, String threadMessage);
}
