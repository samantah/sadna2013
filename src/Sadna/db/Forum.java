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

    public Forum(Admin admin, String forumName, Policy policy) {
        this.forumName = forumName;
        this.admin = admin;
        this.setPolicy(policy);
    }

    public Forum(String forumName, Policy policy) {
        this.forumName = forumName;
        this.admin = null;
        this.setPolicy(policy);
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
