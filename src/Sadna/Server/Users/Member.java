/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server.Users;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.db.Post;
import Sadna.db.ThreadMessage;

public class Member extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 383909427775825138L;
	protected String userName;
	protected String password;
	protected String email;
	protected String forum;

	public Member(String userName, String password, String email, String forum, ClientCommunicationHandlerInterface ch) {
		super(ch);
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.forum = forum;
	}


	public boolean postComment(Post post) {
		return conHand.postComment(post);
	}

	public boolean publishThread(ThreadMessage newThread) {
		return conHand.publishThread(newThread);
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getForum() {
		return forum;
	}
        
        public User logout(String forum, String userName){
            return this.conHand.logout(forum, userName);
        }

}
