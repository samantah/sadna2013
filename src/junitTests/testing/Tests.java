/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import Driver.ClientBridge;
import Driver.ClientDriver;
import Sadna.Client.*;
import Sadna.db.*;
import Sadna.db.PolicyEnums.enumAssignModerator;
import Sadna.db.PolicyEnums.enumCancelModerator;
import Sadna.db.PolicyEnums.enumDelete;
import Sadna.db.PolicyEnums.enumNotiFriends;
import Sadna.db.PolicyEnums.enumNotiImidiOrAgre;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
	public static final String ip = "132.73.199.251";
	public static final int port = 3333;

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
		User _user = bridge.login(FORUM_NAME, USER_NAME, "abc 123");
		assertFalse(_user instanceof Member);
		_user = bridge.login(FORUM_NAME, USER_NAME, USER_PASSWORD);
		assertTrue(_user instanceof Member);
		bridge.finishCommunication();

	}

	/*
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
		Member mem = bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		User _user = mem.logout(FORUM_NAME);
		assertFalse(_user instanceof Member);
		bridge.finishCommunication();
	}


	@Test
	public void getListOfForums(){
		List<Forum> forumsList = bridge.getForumsList();
		assertNotNull(forumsList);
		assertEquals(1, forumsList.size());
	}

	@Test
	public void PublishThread(){
		SubForum sb = bridge.getSubForum(FORUM_NAME, SUB_FORUM_NAME);
		Member m = bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		ThreadMessage tm = new ThreadMessage(sb, "title", "content", m.getUserName());
		boolean publish = bridge.publishThread(tm, "1234");
		assertFalse(publish);
		publish = bridge.publishThread(tm, m.getPassword());
		assertTrue(publish);
	}

	@Test
	public void manyUsersTest(){
		System.out.println("start");
		ClientConnectionHandler[] array = new ClientConnectionHandler[100];

		long start = System.currentTimeMillis();
		for (int i = 0; i < array.length; i++) {
			array[i] = new ClientConnectionHandler(ip, port);
		}
		System.out.println("connected");
		for (int i = 0; i < array.length; i++) {
			ClientConnectionHandler currUser = array[i];
			currUser.getForumsList();
			currUser.getSubForumsList(FORUM_NAME);
			currUser.getThreadsList(FORUM_NAME, SUB_FORUM_NAME);
		}
		long end = System.currentTimeMillis();
		assertTrue((end-start<1000*5));
	}

	
	private static void initiateTestPlatform() {
		ch = new ClientConnectionHandler(ip, 3333);
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		sa.clearDataBase();
//		if(sa == null) System.out.println("nullllllllll");
//		else System.out.println(sa.getUserName());
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.PUBLISHERS, enumDelete.EXTENDED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.NO_RESTRICTION, 0, 0);
		sa.initiateForum(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD, policy);
		Forum forum = sa.getForum(FORUM_NAME);
		
		Member m1 = u.register(FORUM_NAME, "laaaaa", "ksjdf66asd", "sdf@adf.com");
		Member m2 = u.register(FORUM_NAME, "baaaaa", "ksjdf66asd", "sdf@adf.com");
		Member m3 = u.register(FORUM_NAME, "eaaaaa", "ksjdf66asd", "sdf@adf.com");
//		Member m4 = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		Admin admin = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		
		SubForum subForum = new SubForum(forum, "zubizubi1");
		SubForum subForum2 = new SubForum(forum, "zubizubi2");
		ArrayList<Member> al = new ArrayList<Member>();
		al.add(m1);
		admin.addSubForum(subForum2, al);
		ArrayList<Member> al2 = new ArrayList<Member>();
		al2.add(m2);
		al2.add(m3);
		admin.addSubForum(subForum, al2);
		ThreadMessage threadMessage = new ThreadMessage(subForum, "zzzz", "hi11", "laaaaa");
		ThreadMessage threadMessage2 = new ThreadMessage(subForum, "ccccc", "hi2aaa2", "eaaaaa");
		
		m1.publishThread(threadMessage);
		m2.publishThread(threadMessage2);
		List<ThreadMessage> threadMessages = m1.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		threadMessage = threadMessages.get(0);
		threadMessage2 = threadMessages.get(1);
		
		Post post = new Post(threadMessage, "uuuuuuu", "hi11post1", "laaaaa");
		Post post2 = new Post(threadMessage, "iiiiiiii", "hi11post2", "eaaaaa");
		Post post3 = new Post(threadMessage2, "wwww", "hii222", "baaaaa");
	
		m3.postComment(post);
		m2.postComment(post2);
		m1.postComment(post3);
		System.out.println("Finished initializing test");
	}
}
