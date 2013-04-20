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
		String host = "192.168.1.101";
		int port = 3248;
		ConnectionHandler c = new ConnectionHandler(host, port);
		SuperAdmin sa = new SuperAdmin("mega-admin", "megaadmin12", "mega@gmail.com", c);
		User guest = new User(c);
		Member member = guest.login("forum1", "snirelkaras1","snirfh1234");
		System.out.println(member.getUserName());
		Forum f =member.getForum("forum1");
		SubForum sf = member.getSubForum(f.getForumName(), "subForum1");
		ThreadMessage tm = new ThreadMessage(sf, "kaveret", "Is somebody knows where can i find the lyrics of GULIATH?", "snirelkaras1");
		if(member.publishThread(tm)){
			System.out.println("success");
		}
		Post p = new Post(tm, "", "that's really importent to me!!!", member.getUserName());
		if(member.postComment(p)){
			System.out.println("success");;
		}

		
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
		//gfltest(c);
		
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
		Member m = c.login("forum1", "userName", "password");
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
		Member m = c.register("forum1", "snirElkaras1", "snirfh1234", "email");
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
