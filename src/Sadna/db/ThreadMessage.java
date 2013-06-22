/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.db.Generators.PostIDGenerator;

/**
 *
 * @author fistuk
 */
public class ThreadMessage extends Message implements Comparable<ThreadMessage>{

    /**
     *
     */
    private static final long serialVersionUID = 2804071144673510942L;
    private SubForum subForum;

    public ThreadMessage(SubForum subForum, String title, String content, String publisher) {
        super(title, content, publisher);
        this.subForum = subForum;
    }
    
    public ThreadMessage(int id, SubForum subForum, String title, String content, String publisher) {
        super(title, content, publisher);
        this.subForum = subForum;
        this.id = id;
    }

    
    public SubForum getSubForum() {
        return subForum;
    }

    public void setSubForum(SubForum subForum) {
        this.subForum = subForum;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

    @Override
    public int compareTo(ThreadMessage t) {
        return this.id-t.id;
    }
    
    

}
