/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.Client.Admin;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class Forum implements Serializable{
    
    private List<SubForum> listOfSubForums;
    private Admin admin;
    private String forumName;


    public Forum(Admin admin,String forumName) {
        this.forumName = forumName;
        this.listOfSubForums = new ArrayList<SubForum>();
        this.admin = admin;
    }

    public boolean addSubForum(SubForum e) {
        return listOfSubForums.add(e);
    }

    public boolean removeSubForum(Object o) {
        return listOfSubForums.remove(o);
    }

    public String getForumName() {
        return forumName;
    }

    public List<SubForum> getListOfSubForums() {
        return listOfSubForums;
    }
    
    
    
}
