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

    private List<Post> listOfPosts;
    private SubForum subForum;
    private PostIDGenerator postIDGenerator;


    public ThreadMessage(SubForum subForum, String message) {
        super(message);
        listOfPosts = new ArrayList<Post>();
        this.subForum = subForum;
        this.postIDGenerator = new PostIDGenerator();

    }

    public SubForum getSubForum() {
        return subForum;
    }

    public void setSubForum(SubForum subForum) {
        this.subForum = subForum;
    }

    public boolean addPost(Post e) {
        e.setId(postIDGenerator.getID());
        return listOfPosts.add(e);
    }

    public boolean removePost(Post e) {
        return listOfPosts.remove(e);
    }
}
