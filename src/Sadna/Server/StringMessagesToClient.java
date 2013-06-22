/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server;

/**
 *
 * @author fistuk
 */
public class StringMessagesToClient {
	public Object sendOK() {
		return "200ok";
	}

	public Object sendNotFound() {
		return "404 Not Found";
	}

	public Object sendErrorInServer() {
		return "500 Internal Server Error";
	}

	public Object sendErrorNoAuthorized() {
		return "401 Unauthorized";
	}

	public Object sendThreadCounter(int threadCounter) {
		return String.valueOf(threadCounter);	
	}

	public Object sendIsTheOnlyModeratorInTheSubForum() {
		return "action not allowed";
	}

	public Object sendUserThreadsCounter(int numberOfUserThreads) {
		return String.valueOf(numberOfUserThreads);
	}

	public Object sendNumberOfForums(int forumCounter) {
		return String.valueOf(forumCounter);
	}

	public Object sendModeratorOK() {
		return "201ok";
	}

	public Object sendAdminOK() {
		return "202ok";
	}

	public Object sendSuperAdminOK() {
		return "200ok";
	}

	public Object deleteForum(boolean deleteForum) {
		if(deleteForum)
			return "200ok";
		else
			return "500 Internal Server Error";
	}

	public String verification() {
		return "verification is required";
	}
}
