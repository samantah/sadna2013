package Sadna.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.ArrayList;
import java.util.List;
public class Main {
	public static void main(String argv[]) throws Exception{
		String host = "192.168.0.105";
		int port = 3248;
		ConnectionHandler c = new ConnectionHandler(host, port);
		SuperAdmin sa = new SuperAdmin("mega-admin", "megaadmin12", "mega@gmail.com", c);
		sa.initiateForum("forum1", "snirElkaras", "snir1234");
		
		/*----LOGIN-----*/
		//litest(c);

		/*-----REGISTER----*/
		//regitest(c);

		/*-----LOGOUT----*/
		//lotest(c);

		/*-----GET-SUB-FORUM----*/
		//gsftest(c);

		/*-----GET-SUB-FORUM-LIST---*/
		//gsfltest(c);

		/*-----GET-THREAD-LIST---*/
		//gtltest(c);

		/*-----GET-THREAD-MSG---*/
		//gtmtest(c);
		
		/*----POST----*/
		//pctest(c);
		
		/*----PUBLISH----*/
		//pttest(c);
		
		/*----GET-FORUM-LIST---*/
		gfltest(c);
		
		c.finishCommunication();

	}



	private static void gfltest(ConnectionHandler c) {
		List<Forum> fl = c.getForumsList();
		if(fl!=null){
			for(Forum f: fl){ 
				System.out.println(f.getForumName());
			}
		}
		else
			System.out.println("zain");
	}



	private static void pttest(ConnectionHandler c) {
		Admin m = new Admin("userName", "password", "email", null);
		Forum f = new Forum(m, "forumName");
		SubForum sf = new SubForum(f, "subForumName");
		ThreadMessage t = new ThreadMessage(sf, "eminem", "hiiiiiiii", "userName");
		boolean ans = c.publishThread(t);
		if(ans){
			System.out.println(t.getContent());
			System.out.println(t.getTitle());
		}
		else
			System.out.println("zain");
	}



	private static void pctest(ConnectionHandler c) {
		Admin m = new Admin("userName", "password", "email", null);
		Forum f = new Forum(m, "forumName");
		SubForum sf = new SubForum(f, "subForumName");
		ThreadMessage t = new ThreadMessage(sf, "eminem", "hiiiiiiiiiiiii", "userName");
		Post p = new Post(t ,"slim shady" , "hiii my name is what, my name is who, my name  is-- chicki chiki, slim shady", "userName"); 
		boolean posted = c.postComment(p);
		System.out.println(posted);
		if(posted){
			System.out.println(p.getTitle());
		}
		else
			System.out.println("zain");		
	}



	private static void gtmtest(ConnectionHandler c) {
		ThreadMessage tm = c.getThreadMessage("forumName", "subForumName", 50);
		if(tm!=null){
			System.out.println(tm.getId());
			System.out.println(tm.getTitle());
			System.out.println(tm.getSubForum().getSubForumName());
		}
		else
			System.out.println("zain");
	}



	/**
	 * @param c
	 */
	private static void litest(ConnectionHandler c) {
		Member m = c.login("forumName", "userName", "password");
		if(m!=null){
			System.out.println(m.userName);
			System.out.println(m.password);
			System.out.println(m.forum);
		}
		else
			System.out.println("zain");
	}

	/**
	 * @param c
	 */
	private static void regitest(ConnectionHandler c) {
		Member m = c.register("forumName", "userName", "snirfh1234", "email");
		if(m!=null){
			System.out.println(m.userName);
			System.out.println(m.password);
			System.out.println(m.forum);
			System.out.println(m.email);
		}
		else
			System.out.println("zain");
	}

	/**
	 * @param c
	 */
	private static void lotest(ConnectionHandler c) {
		User u = c.logout("forumName", "userName");
		if(u!=null){
			System.out.println("yesh");
		}
		else
			System.out.println("zain");
	}

	/**
	 * @param c
	 */
	private static void gsftest(ConnectionHandler c) {
		SubForum f = c.getSubForum("forum", "subForumName");
		if(f!=null){
			System.out.println(f.getForum().getForumName());
			System.out.println(f.getSubForumName());
			for(ThreadMessage t: f.getListOfThreadMessages()){ 
				System.out.println(t.getTitle());
			}
		}
		else
			System.out.println("zain");
	}

	private static void gsfltest(ConnectionHandler c) {
		List<SubForum> f = c.getSubForumsList("forumName");
		if(f!=null){
			for(SubForum sf: f){ 
				System.out.println(sf.getSubForumName());
			}
		}
		else
			System.out.println("zain");
	}

	private static void gtltest(ConnectionHandler c) {
		List<ThreadMessage> tl = c.getThreadsList("forumName", "subForumName");
		if(tl!=null){
			for(ThreadMessage t: tl){ 
				System.out.println(t.getTitle());
			}
		}
		else
			System.out.println("zain");

	}
}
