/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import java.io.Serializable;

/**
 *
 * @author fistuk
 */
public class Post extends Message implements Serializable {

    private ThreadMessage thread;

    public Post(ThreadMessage thread, String message) {
        super(message);
        this.thread = thread;
    }

    public ThreadMessage getThread() {
        return thread;
    }

    public void setThread(ThreadMessage thread) {
        this.thread = thread;
    }
}
