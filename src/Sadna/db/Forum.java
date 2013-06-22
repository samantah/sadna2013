/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.Client.Admin;

import java.io.Serializable;

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
    private Policy policy;
    private String forbiddenWords = "";

    public Forum(Admin admin, String forumName, Policy policy, String forbidden) {
        this.forumName = forumName;
        this.admin = admin;
        this.forbiddenWords = forbidden;
        this.setPolicy(policy);
    }

    public Forum(String forumName, Policy policy, String forbidden) {
        this.forumName = forumName;
        this.admin = null;
        this.forbiddenWords = forbidden;
        this.setPolicy(policy);
    }

    public String getForbiddenWords() {
        return forbiddenWords;
    }

    public void setForbiddenWords(String forbiddenWords) {
        this.forbiddenWords = forbiddenWords;
    }

    
    public Admin getAdmin() {
        return admin;
    }

    public boolean setAdmin(Admin admin) {
        if (admin != null) {
            this.admin = admin;
            return true;
        } else {
            return false;
        }
    }

    public String getForumName() {
        return forumName;
    }

    @Override
    public String toString() {
        return forumName;
    }

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public Policy getPolicy() {
		return policy;
	}
}
