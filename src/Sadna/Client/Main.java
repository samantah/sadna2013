package Sadna.Client;

import Sadna.db.*;

import java.util.List;


public class Main {

    public static void main(String argv[]) throws Exception {
        String host = "192.168.1.104";
        int port = 3333;
        ClientConnectionHandler c = new ClientConnectionHandler(host, port);
        //        c.initiateForum("newForum", "chen", "chen1234", "superAdmin", "superAdmin1234");
        //        SuperAdmin sa = new SuperAdmin("superAdmin", "superAdmin1234", "", c);
        //        sa.initiateForum("newForum", "aviel", "aviel1234");
        //        c.initiateForum("newForum", "chen", "chen1234");
        //        User chen = new User(c);
        //        chen = chen.login("newForum", "chen", "chen1234");

//        SubForum sf = new SubForum(new Forum("newForum"), "sf");
//        List<Moderator> lm = new ArrayList<Moderator>();
//        Member m1 = new Member("dotan", "dotan1234", "dotan@gmail.com", "newForum", null);
//        Member m2 = new Member("shai", "shai1234", "shai@gmail.com", "newForum", null);
//        Moderator e1 = new Moderator(m1);
//        Moderator e2 = new Moderator(m2);
//        lm.add(e1);
//        lm.add(e2);
//        System.out.println(((Admin)chen).addSubForum(sf, lm));

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
//        litest(c);
//
//		/*-----REGISTER----*/
//		regitest(c);
//
//		/*-----LOGOUT----*/
//		lotest(c);
//
//		/*-----GET-SUB-FORUM----*/
//		gsftest(c);
//
//		/*-----GET-SUB-FORUM-LIST---*/
//		gsfltest(c);
//
//		/*-----GET-THREAD-LIST---*/
//		gtltest(c);
//
//		/*-----GET-THREAD-MSG---*/
//		gtmtest(c);
//		
//		/*----POST----*/
//		pctest(c);
//		
//		/*----PUBLISH----*/
//		pttest(c);
//		
//		/*----GET-FORUM-LIST---*/
//		gfltest(c);
//		
                /*----GET-FORUM---*/
                gftest(c);
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

//        System.out.println(c.login("forum1", "snirelka", "snir1234"));


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
//		}


        c.finishCommunication();

    }

    private static void gfltest(ClientConnectionHandler c) {
        List<Forum> fl = c.getForumsList();
        if (fl != null) {
            for (Forum f : fl) {
                System.out.println(f.getForumName());
            }
        } else {
            System.out.println("zain");
        }
    }

    private static void pttest(ClientConnectionHandler c) {
        SubForum sf = c.getSubForum("forum1", "subForum1");
        ThreadMessage t = new ThreadMessage(sf, "eminem", "hiiiiiiii", "m1");
        boolean ans = c.publishThread(t, "password1");
        if (ans) {
            System.out.println(t.getContent());
            System.out.println(t.getTitle());
        } else {
            System.out.println("zain");
        }
    }

    private static void pctest(ClientConnectionHandler c) {
        ThreadMessage t = c.getThreadMessage("forum1", "subForum1", 1);
        Post p = new Post(t, "slim shady", "hiii my name is what, my name is who, my name  is-- chicki chiki, slim shady", "m1");
        boolean posted = c.postComment(p, "password1");
        if (posted) {
            System.out.println(p.getTitle());
            System.out.println(p.getContent());
        } else {
            System.out.println("zain");
        }
    }

    private static void gtmtest(ClientConnectionHandler c) {
        ThreadMessage tm = c.getThreadMessage("forum1", "subForum1", 1);
        if (tm != null) {
            System.out.println(tm.getId());
            System.out.println(tm.getTitle());
            System.out.println(tm.getContent());
            System.out.println(tm.getSubForum().getSubForumName());
        } else {
            System.out.println("zain");
        }
    }

    /**
     * @param c
     */
    private static void litest(ClientConnectionHandler c) {
        Member m = c.login("forum1", "m1", "password1");
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
    private static void regitest(ClientConnectionHandler c) {
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
    private static void lotest(ClientConnectionHandler c) {
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
    private static void gsftest(ClientConnectionHandler c) {
        SubForum f = c.getSubForum("forum1", "subForum1");
        if (f != null) {
            System.out.println(f.getForum().getForumName());
            System.out.println(f.getSubForumName());
        } else {
            System.out.println("zain");
        }
    }

    private static void gsfltest(ClientConnectionHandler c) {
        List<SubForum> f = c.getSubForumsList("forum1");
        if (f != null) {
            for (SubForum sf : f) {
                System.out.println(sf.getSubForumName());
            }
        } else {
            System.out.println("zain");
        }
    }

    private static void gtltest(ClientConnectionHandler c) {
        List<ThreadMessage> tl = c.getThreadsList("forum1", "subForum1");
        if (tl != null) {
            for (ThreadMessage t : tl) {
                System.out.println(t.getTitle());
            }
        } else {
            System.out.println("zain");
        }

    }

    private static void gftest(ClientConnectionHandler c) {
        Forum forum = c.getForum("snir");
        if (forum!=null){
            System.out.println(forum.getForumName());
            Policy policy = forum.getPolicy();
            System.out.println(policy.getAssignModeratorPolicy());
            System.out.println(policy.getCancelModeratorPolicy());
            System.out.println(policy.getDeletePolicy());
            System.out.println(policy.getFriendsNotiPolicy());
            System.out.println(policy.getImidOrArgeNotiPolicy());
        }
        else
            System.out.println("zain");
    }
}
