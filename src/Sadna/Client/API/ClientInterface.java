/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client.API;

import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.List;

/**
 *
 * @author fistuk
 */
public interface ClientInterface {

    boolean login(String userName, String password);

    void postComment(Post post);

    void publishThread(ThreadMessage newThread);

    boolean register(String userName, String password, String fullName);

    SubForum getSubForum(String subForumName);

    List<SubForum> getSubForumList();
}
