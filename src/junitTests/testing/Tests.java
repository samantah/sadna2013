/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import Sadna.Client.ClientConnectionHandler;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.Client.User;
import Sadna.db.DataBase;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import static UnitTests.connectionHandlerTest.SUPER_ADMIN_NAME;
import static UnitTests.connectionHandlerTest.SUPER_ADMIN_PASSWORD;
import java.util.ArrayList;
import Driver.ClientBridge;
import Driver.ClientDriver;
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
    public static final String SUPER_ADMIN_NAME = "superAdmin";
	public static final String SUPER_ADMIN_PASSWORD = "superAdmin1234";
	
	public static final String FORUM_NAME = "forum233"; //valid forum
	public static final String ADMIN_NAME = "adminsami";
	public static final String ADMIN_PASSWORD = "adminpass12";
	
	public static final String SUB_FORUM_NAME = "zubizubi1"; //valid sub forum
	
	public static final String USER_NAME = "sadnaUser";  //valid username
	public static final String USER_EMAIL = "sadna@bgu.ac.il";
	public static final String USER_PASSWORD = "abcdefg34";
	
    private static ClientConnectionHandler ch;

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
        bridge = ClientDriver.getBridge();
        initiateTestPlatform();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_RegisterInvalidPass() {
        Member _member1 = bridge.register(FORUM_NAME, USER_NAME, "short", USER_EMAIL);
        assertNull(_member1);
        Member _member2 = bridge.register(FORUM_NAME, USER_NAME, "goodButNoNum", USER_EMAIL);
        assertNull(_member2);
        Member _member3 = bridge.register(FORUM_NAME, USER_NAME, "longVeryVeryVeryLong", USER_EMAIL);
        assertNull(_member3);
        Member _member4 = bridge.register(FORUM_NAME, USER_NAME, "3456", USER_EMAIL);
        assertNull(_member4);
        Member _member5 = bridge.register(FORUM_NAME, USER_NAME, "3456565547456", USER_EMAIL);
        assertNull(_member5);
    }

    @Test
    public void test_RegisterValidPass() {
        Member _member1 = bridge.register(FORUM_NAME, "aclient333", "1234567k", USER_EMAIL);
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
        Member _member1 = bridge.register(FORUM_NAME, "oneclient", USER_PASSWORD, USER_EMAIL);
        assertNotNull(_member1);
        Member _member2 = bridge.register(FORUM_NAME, "oneclient", USER_PASSWORD + "a", USER_EMAIL + 'a');
        assertNull(_member2);
        bridge.finishCommunication();

    }

    /*
     * check logout
     */
    @Test
    public void test_logOut() {
    	Member mem = bridge.login(FORUM_NAME, USER_NAME, USER_PASSWORD);
        User _user = mem.logout(FORUM_NAME);
        assertFalse(_user instanceof Member);
        bridge.finishCommunication();
    }

    private static void initiateTestPlatform() {
        DataBase db = new DataBase();
        db.initiateDataBase();
        ch = new ClientConnectionHandler("172.16.106.179", 3333);
        User u = new User(ch);
        SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
//		if(sa == null) System.out.println("nullllllllll");
//		else System.out.println(sa.getUserName());
        sa.initiateForum(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
        Forum forum = sa.getForum(FORUM_NAME);

        Member m1 = u.register(FORUM_NAME, "laaaaa", "ksjdf66asd", "sdf@adf.com");
        Member m2 = u.register(FORUM_NAME, "baaaaa", "ksjdf66asd", "sdf@adf.com");
        Member m3 = u.register(FORUM_NAME, "eaaaaa", "ksjdf66asd", "sdf@adf.com");
        Member m4 = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);

        SubForum subForum = new SubForum(forum, "zubizubi1");
        SubForum subForum2 = new SubForum(forum, "zubizubi2");
        Moderator mod1 = new Moderator(m1);
        Moderator mod2 = new Moderator(m2);
        Moderator mod3 = new Moderator(m3);
        ArrayList<Moderator> al = new ArrayList<Moderator>();
        al.add(mod1);
        sa.addSubForum(subForum2, al);
        ArrayList<Moderator> al2 = new ArrayList<Moderator>();
        al2.add(mod2);
        al2.add(mod3);
        sa.addSubForum(subForum, al2);
        ThreadMessage threadMessage = new ThreadMessage(subForum, "zzzz", "hi11", "laaaaa");
        ThreadMessage threadMessage2 = new ThreadMessage(subForum, "ccccc", "hi2aaa2", "eaaaaa");

        m1.publishThread(threadMessage);
        m2.publishThread(threadMessage2);

        Post post = new Post(threadMessage, "uuuuuuu", "hi11post1", "laaaaa");
        Post post2 = new Post(threadMessage, "iiiiiiii", "hi11post2", "eaaaaa");
        Post post3 = new Post(threadMessage2, "wwww", "hii222", "baaaaa");

        m3.postComment(post);
        m2.postComment(post2);
        m1.postComment(post3);
        System.out.println("Finished initializing test");
    }
}
