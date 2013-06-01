/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.db.Generators.ThreadIDGenerator;

import java.io.Serializable;

/**
 *
 * @author fistuk
 */
public class SubForum implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4538107590374025375L;
    private Forum forum;
    private String subForumName;

    public SubForum(Forum forum, String subForumName) {
        this.subForumName = subForumName;
        this.forum = forum;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public String getSubForumName() {
        return subForumName;
    }

    @Override
    public String toString() {
        return this.subForumName;
    }
    
    
}
