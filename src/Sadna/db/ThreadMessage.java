/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.db.Generators.PostIDGenerator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class ThreadMessage extends Message {

    /**
     *
     */
    private static final long serialVersionUID = 2804071144673510942L;
    private SubForum subForum;
    private PostIDGenerator postIDGenerator;

    public ThreadMessage(SubForum subForum, String title, String content, String publisher) {
        super(title, content, publisher);
        this.subForum = subForum;
        this.postIDGenerator = new PostIDGenerator();
        this.id = subForum.getThreadIDGenerator().getID();
    }

    public PostIDGenerator getPostIDGenerator() {
        return postIDGenerator;
    }
    
    public SubForum getSubForum() {
        return subForum;
    }

    public void setSubForum(SubForum subForum) {
        this.subForum = subForum;
    }

}
