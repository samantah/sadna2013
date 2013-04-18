/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

/**
 *
 * @author fistuk
 */
public class Post extends Message{
    private ThreadMessage thread;

    public Post(ThreadMessage thread, String message) {
        super(message);
        this.thread = thread;
    }
}
