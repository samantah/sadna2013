/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.Client.Moderator;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class SubForum {
    private Forum forum;
    private List<ThreadMessage> listOfThreadMessages;
    private List<Moderator> listOfModerators;

    public SubForum(Forum forum, List<ThreadMessage> listOfThreadMessages, List<Moderator> listOfModerators) {
        this.forum = forum;
        this.listOfThreadMessages = listOfThreadMessages;
        this.listOfModerators = listOfModerators;
    }
}
