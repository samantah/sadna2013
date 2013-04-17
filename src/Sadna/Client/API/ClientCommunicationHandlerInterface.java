/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client.API;

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

    boolean login(String userName, String password);

    boolean postComment(Post post, User user);

    boolean publishThread(ThreadMessage newThread, User user);

    boolean register(String userName, String password, String fullName);

    SubForum getSubForum(String subForumName);

    List<SubForum> getSubForumsList();
    
    List<ThreadMessage> getThreadsList();
    
    ThreadMessage getThreadMessage(String threadMessage);
}
