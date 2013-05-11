package Sadna.Client;




import Sadna.Server.ForumNotification;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.List;

import com.sun.nio.sctp.Notification;
public class Main {

    public static void main(String argv[]) throws Exception {
        String host = "192.168.1.105";
        int port = 3333;
        ConnectionHandler c = new ConnectionHandler(host, port);
//        c.initiateForum("newForum", "chen", "chen1234");
        //List<Forum> forumsList = c.getForumsList();
//		SuperAdmin sa = new SuperAdmin("mega-admin", "megaadmin12", "mega@gmail.com", c);
//		User guest = new User(c);
//		//Member erez = guest.register("forum1", "erezLefel1", "erez12345", "email");
//		Member erez = guest.login("forum1", "erezLefel1", "erez12345");
//		if(erez!=null){
//			System.out.println("yesh");
//		}
//		List<SubForum> sfl = erez.viewSubForums("forum1");
//		SubForum sb = erez.getSubForum("forum1", sfl.get(0).getSubForumName());
//		if(sb!=null){
//			System.out.println("yeshysh");
//		}
//		List<ThreadMessage> tml = erez.viewThreadMessages("forum1", sb.getSubForumName());
//		if(tml!=null){
//			System.out.println(tml.size());
//		}
//		ThreadMessage proxy = tml.get(0);
//		ThreadMessage tm = erez.getThread("forum1", proxy.getSubForum().getSubForumName(), proxy.getId());
//		if(tm!=null){
//			System.out.println(tm.getTitle());
//			System.out.println(tm.getContent());
//		}
//		Post p = new Post(tm, "", "that's really importent to me!!!", erez.getUserName());
//		if(erez.postComment(p)) System.out.println("posted");
//		p = new Post(tm, "can't see", "what is really importent?", erez.getUserName());
//		if(erez.postComment(p)) System.out.println("posted");
//		p = new Post(tm, "ma kara?", "hakol tov?", erez.getUserName());
//		if(erez.postComment(p)) System.out.println("posted");
//			
//		//erez.viewThreadMessages("forum1", sb)
////		Member member = guest.login("forum1", "snirelkaras1","snirfh1234");
////		System.out.println(member.getUserName());
////		Forum f =member.getForum("forum1");
////		SubForum sf = member.getSubForum(f.getForumName(), "subForum1");
////		ThreadMessage tm = new ThreadMessage(sf, "kaveret", "Is somebody knows where can i find the lyrics of GULIATH?", "snirelkaras1");
////		if(member.publishThread(tm)){
////			System.out.println("success");
////		}
////		Post p = new Post(tm, "", "that's really importent to me!!!", member.getUserName());
////		if(member.postComment(p)){
////			System.out.println("success");;
////		}
//
//		
//		/*----LOGIN-----*/
//		//litest(c);
//
//		/*-----REGISTER----*/
//		//regitest(c);
//
//		/*-----LOGOUT----*/
//		//lotest(c);
//
//		/*-----GET-SUB-FORUM----*/
//		//gsftest(c);
//
//		/*-----GET-SUB-FORUM-LIST---*/
//		//gsfltest(c);
//
//		/*-----GET-THREAD-LIST---*/
//		//gtltest(c);
//
//		/*-----GET-THREAD-MSG---*/
//		//gtmtest(c);
//		
//		/*----POST----*/
//		//pctest(c);
//		
//		/*----PUBLISH----*/
//		//pttest(c);
//		
//		/*----GET-FORUM-LIST---*/
//		//gfltest(c);
//		
      //  Sadna.Client.Member u = c.register("forum1", "qwertyui", "1234qwer","dfsdf");
//        c.initiateForum("myForum", "snirsnir", "snir1234");
//        Forum forum = new Forum("myForum");
//        SubForum subForum = new SubForum(forum, "subForum1");
//        c.addSubForum(subForum, new ArrayList<Moderator>());
//        System.out.println(c.register("myForum", "moshe", "moshe1234", "email").getUserName());
//        System.out.println(c.addModerator("myForum", "subForum1", "moshe", "snirsnir", "snir1234"));
//        ThreadMessage thread = new ThreadMessage(subForum, "", "", "publisher");
//        thread.setId(0);
//        boolean al_hazain = c.deleteThreadMessage(thread, "hhhhhhhhhh" , "1234qwer");
//        System.out.println(al_hazain);
        	
        System.out.println(c.login("forum1", "snirelka", "snir1234"));
        
        
//        Forum forum = new Forum("forum1");
//        SubForum subForum = new SubForum(forum, "subForum1");
//        ThreadMessage newThread = new ThreadMessage(subForum, "title123", "content", "snirelka");
//        System.out.println(c.publishThread(newThread));
        
//        c.login("forum1", "snirelkar", "snir1234");
//        //SubForum sf = c.getSubForum("forum1", "subForum1");
//        List<ThreadMessage> l =  c.getThreadsList("forum1", "subForum1");
//        System.out.println(l);
//        ThreadMessage t = l.get(0);
//        Post p = new Post(t, "title", "content", "snirelkar");
//        c.postComment(p);
//        List<ForumNotification> x = c.getNotification("forum1","snirelka" ,"snir1234");
//        for (ForumNotification forumNotification : x) {
//        	System.out.println(forumNotification.getText());
		}
        
        
        c.finishCommunication();

    }

    private static void gfltest(ConnectionHandler c) {
        List<Forum> fl = c.getForumsList();
        if (fl != null) {
            for (Forum f : fl) {
                System.out.println(f.getForumName());
            }
        } else {
            System.out.println("zain");
        }
    }

    private static void pttest(ConnectionHandler c) {
        Admin m = new Admin("userName", "password", "email",null ,null);
        Forum f = new Forum(m, "forumName");
        m.setForum(f.getForumName());
        SubForum sf = new SubForum(f, "subForumName");
        ThreadMessage t = new ThreadMessage(sf, "eminem", "hiiiiiiii", "userName");
        boolean ans = c.publishThread(t);
        if (ans) {
            System.out.println(t.getContent());
            System.out.println(t.getTitle());
        } else {
            System.out.println("zain");
        }
    }

    private static void pctest(ConnectionHandler c) {
        Admin m = new Admin("userName", "password", "email", null, null);
        Forum f = new Forum(m, "forumName");
        m.setForum(f.getForumName());
        SubForum sf = new SubForum(f, "subForumName");
        ThreadMessage t = new ThreadMessage(sf, "eminem", "hiiiiiiiiiiiii", "userName");
        Post p = new Post(t, "slim shady", "hiii my name is what, my name is who, my name  is-- chicki chiki, slim shady", "userName");
        boolean posted = c.postComment(p);
        System.out.println(posted);
        if (posted) {
            System.out.println(p.getTitle());
        } else {
            System.out.println("zain");
        }
    }

    private static void gtmtest(ConnectionHandler c) {
        ThreadMessage tm = c.getThreadMessage("forumName", "subForumName", 50);
        if (tm != null) {
            System.out.println(tm.getId());
            System.out.println(tm.getTitle());
            System.out.println(tm.getSubForum().getSubForumName());
        } else {
            System.out.println("zain");
        }
    }

    /**
     * @param c
     */
    private static void litest(ConnectionHandler c) {
        Sadna.Client.Member m = c.login("forum1", "userName", "password");
        if (m != null) {
            System.out.println(m.userName);
            System.out.println(m.password);
            System.out.println(m.forum);
        } else {
            System.out.println("zain");
        }
    }

    /**
     * @param c
     */
    private static void regitest(ConnectionHandler c) {
        Sadna.Client.Member m = c.register("forum1", "snirElkaras1", "snirfh1234", "email");
        if (m != null) {
            System.out.println(m.userName);
            System.out.println(m.password);
            System.out.println(m.forum);
            System.out.println(m.email);
        } else {
            System.out.println("zain");
        }
    }

    /**
     * @param c
     */
    private static void lotest(ConnectionHandler c) {
        Sadna.Client.User u = c.logout("forumName", "userName");
        if (u != null) {
            System.out.println("yesh");
        } else {
            System.out.println("zain");
        }
    }

    /**
     * @param c
     */
    private static void gsftest(ConnectionHandler c) {
        SubForum f = c.getSubForum("forum", "subForumName");
        if (f != null) {
            System.out.println(f.getForum().getForumName());
            System.out.println(f.getSubForumName());
        } else {
            System.out.println("zain");
        }
    }

    private static void gsfltest(ConnectionHandler c) {
        List<SubForum> f = c.getSubForumsList("forumName");
        if (f != null) {
            for (SubForum sf : f) {
                System.out.println(sf.getSubForumName());
            }
        } else {
            System.out.println("zain");
        }
    }

    private static void gtltest(ConnectionHandler c) {
        List<ThreadMessage> tl = c.getThreadsList("forumName", "subForumName");
        if (tl != null) {
            for (ThreadMessage t : tl) {
                System.out.println(t.getTitle());
            }
        } else {
            System.out.println("zain");
        }

    }
}
