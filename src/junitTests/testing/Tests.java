/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package junitTests.testing;

import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.User;
import Sadna.db.DataBase;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.ArrayList;
import java.util.List;
import junitTests.Driver.ClientBridge;
import junitTests.Driver.ClientDriver;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author fistuk
 */
public class Tests {

    private static ClientBridge bridge;
    //ALL fields need to be valid
    public static final String FORUM_NAME = "forum1"; //valid forum
    public static final String USER_NAME = "sadnaUser";  //valid username
    public static final String USER_EMAIL = "sadna@bgu.ac.il";
    public static final String USER_PASSWORD = "abcdefg34";
    public static final String ADMIN_NAME = "admin1";
    public static final String ADMIN_PASSWORD = "password1234";

    @BeforeClass
    public static void setUpClass() {
        initiateTestPlatform();
        bridge = ClientDriver.getBridge();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        initiateTestPlatform();
        bridge = ClientDriver.getBridge();
    }

    @After
    public void tearDown() {
    }

//    @Test
//    public void test_RegisterInvalidPass() {
//        Member _member1 = bridge.register(FORUM_NAME, USER_NAME, "short", USER_EMAIL);
//        assertNull(_member1);
//        Member _member2 = bridge.register(FORUM_NAME, USER_NAME, "goodButNoNum", USER_EMAIL);
//        assertNull(_member2);
//        Member _member3 = bridge.register(FORUM_NAME, USER_NAME, "longVeryVeryVeryLong", USER_EMAIL);
//        assertNull(_member3);
//        Member _member4 = bridge.register(FORUM_NAME, USER_NAME, "3456", USER_EMAIL);
//        assertNull(_member4);
//        Member _member5 = bridge.register(FORUM_NAME, USER_NAME, "3456565547456", USER_EMAIL);
//        assertNull(_member5);
//    }
    @Test
    public void test_RegisterValidPass() {
        Member _member1 = bridge.register(FORUM_NAME, USER_NAME, "1234567k", USER_EMAIL);
        System.out.println(_member1);
        assertNotNull(_member1);
        bridge.finishCommunication();
    }

    /*
     * check logIn
     */
    @Test
    public void test_logIn() throws InterruptedException {
        Member _member1 = bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
        User _user = bridge.login(FORUM_NAME, USER_NAME, USER_PASSWORD);
        assertTrue(_user instanceof Member);
        bridge.finishCommunication();

    }

    /*
     6.  äôåøåí úåîê áøéáåé îùúîùéí áå æîðéú Login .
     */
    @Test
    public void test_multipleUsersLogin() {
        char a = 'a';
        for (int i = 0; i < 3; i++) {
            Member _member = bridge.register(FORUM_NAME, USER_NAME + a, USER_PASSWORD + a, "email");
            a++;
        }
        a = 'a';
        for (int i = 0; i < 3; i++) {
            Member _member = bridge.login(FORUM_NAME, USER_NAME + a, USER_PASSWORD + a);
            assertNotNull(_member);
            a++;
        }
        bridge.finishCommunication();

    }

    /*
     6.  äôåøåí úåîê áøéáåé îùúîùéí áå æîðéú Register .
     */
    @Test
    public void test_multipleUsersRegister() {
        char a = 'a';
        for (int i = 0; i < 5; i++) {
            Member _member = bridge.register(FORUM_NAME, USER_NAME + a, USER_PASSWORD + a, a + USER_EMAIL);
            assertNotNull(_member);
            a++;
        }
        bridge.finishCommunication();

    }

    /*
     ìëì îùúîù éù ùí äîæää àåúå áàåôï éçéã áôåøåí.
     */
    @Test
    public void test_sameUserName() {
        Member _member1 = bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
        assertNotNull(_member1);
        Member _member2 = bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD + 'a', USER_EMAIL + 'a');
        assertNull(_member2);
        bridge.finishCommunication();

    }

    /*
     * check logout
     */
    @Test
    public void test_logOut() {
        User _user = bridge.logout(FORUM_NAME, USER_NAME);
        assertFalse(_user instanceof Member);
        bridge.finishCommunication();
    }

    private static void initiateTestPlatform() {
        DataBase db = new DataBase();
        db.deleteAll("DataBase/");
        Forum forum = new Forum("forum1");
        SubForum subForum = new SubForum(forum, "subForum1");
        SubForum subForum2 = new SubForum(forum, "subForum2");
        ThreadMessage threadMessage = new ThreadMessage(subForum, "NA", "hi11", "publisher");
        ThreadMessage threadMessage2 = new ThreadMessage(subForum, "NA", "hi2aaa2", "publisher");
        Post post = new Post(threadMessage, "NA", "hi11post1", "publisher");
        Post post2 = new Post(threadMessage, "NA", "hi11post2", "publisher");
        Post post3 = new Post(threadMessage2, "NA", "hii222", "publisher");
        db.addForum(forum);
        ArrayList<Moderator> arrayList = new ArrayList<Moderator>();
        arrayList.add(new Moderator("userNameMod", null, null, null, null));
        arrayList.add(new Moderator("userNameMod2", null, null, null, null));

        db.addSubForum(subForum, arrayList);
        db.addSubForum(subForum2, new ArrayList<Moderator>());
        db.addThread(threadMessage);
        db.addThread(threadMessage2);
        db.addPost(post);
        db.addPost(post2);
        db.addPost(post3);
        ThreadMessage thread = db.getThread("forum1", "subForum1", 1);
        System.out.println("thread: " + thread.getContent());
        Post post1 = db.getPost("forum1", "subForum1", 0, 0);
        System.out.println("post: " + post1.getContent());
        List<SubForum> subForumsList = db.getSubForumsList("forum1");
        for (SubForum sf : subForumsList) {
            System.out.println(sf.getSubForumName());
        }
        List<ThreadMessage> threadsList = db.getThreadsList("forum1", "subForum1");
        for (ThreadMessage threadMessage1 : threadsList) {
            System.out.println(threadMessage1.getContent());
        }
        System.out.println(db.getNumberOfSubforums("forum1"));
        System.out.println(db.getNumberOfThreadsInSubForum("forum1", "subForum1"));
        Member member = new Member("user1", "pass1234", "mail", "forum1", null);
        db.addMember(member);
        System.out.println(db.getMember("forum1", "user1").getUserName());
    }
}
