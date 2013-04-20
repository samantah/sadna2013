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

    /**
     *
     */
    private static final long serialVersionUID = -3796791526192451832L;
    private ThreadMessage thread;

    public Post(ThreadMessage thread, String title, String content, String publisher) {
        super(title, content, publisher);
        this.thread = thread;
        this.id = thread.getPostIDGenerator().getID();
    }

    public ThreadMessage getThread() {
        return thread;
    }

    public void setThread(ThreadMessage thread) {
        this.thread = thread;
    }
}
