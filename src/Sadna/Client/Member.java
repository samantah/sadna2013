/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import java.util.List;

import Sadna.db.SubForum;


public class Member extends User {
    protected String userName;
    protected String password;
    protected String email;
    protected ConnectionHandler conHand;
    
    protected Member(String userName, String password, String email, ConnectionHandler ch){
    	this.userName = userName;
    	this.password = password;
    	this.email = email;
    	this.conHand = ch;
    }
    protected boolean login(String userName, String password){
    	return conHand.login(userName, password);
    }
    protected  List<SubForum> viewSubForums(){
    	return conHand.getSubForumsList();
    }
}
