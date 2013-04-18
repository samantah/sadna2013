/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import java.util.List;

/**
 *
 * @author fistuk
 */
public class ThreadMessage extends Message {

    private List<Post> listOfPosts;
    private SubForum subForum;

    public ThreadMessage(List<Post> listOfPosts, SubForum subForum, String message) {
        super(message);
        this.listOfPosts = listOfPosts;
        this.subForum = subForum;
    }
}
