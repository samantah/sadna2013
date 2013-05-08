/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.Server.Users.Admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class Forum implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7660231455619569811L;
    private Admin admin;
    private String forumName;

    public Forum(Admin admin, String forumName) {
        this.forumName = forumName;
        this.admin = admin;
    }

    public Forum(String forumName) {
        this.forumName = forumName;
        this.admin = null;
    }

    public Admin getAdmin() {
        return admin;
    }

    public boolean setAdmin(Admin admin) {
        if (admin == null) {
            this.admin = admin;
            return true;
        } else {
            return false;
        }
    }

    public String getForumName() {
        return forumName;
    }
}
