
package acceptance;

import Driver.ClientBridge;
import Driver.ClientDriver;
import Sadna.Client.*;
import Sadna.db.*;
import Sadna.db.PolicyEnums.enumAssignModerator;
import Sadna.db.PolicyEnums.enumCancelModerator;
import Sadna.db.PolicyEnums.enumDelete;
import Sadna.db.PolicyEnums.enumMessageContent;
import Sadna.db.PolicyEnums.enumModeratorPermissions;
import Sadna.db.PolicyEnums.enumNotiFriends;
import Sadna.db.PolicyEnums.enumNotiImidiOrAgre;
import Sadna.db.PolicyEnums.enumSecurity;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author fistuk
 */
public class AcceptanceTests {

	private static ClientBridge bridge;
	//ALL fields need to be valid
	public static final String SUPER_ADMIN_NAME = "superAdmin";
	public static final String SUPER_ADMIN_PASSWORD = "superAdmin1234";

	public static final String FORUM_NAME = "forum233"; //valid forum
	public static final String ADMIN_NAME = "adminsami";
	public static final String ADMIN_PASSWORD = "adminpass12";

	public static final String SUB_FORUM_NAME = "zubizubi1"; //valid sub forum

	public static final String USER_NAME = "sadnaUser";  //valid username
	public static final String USER1_EMAIL = "sadna@bgu.ac.il";
	public static final String USER2_EMAIL = "sadna@bgu.ac.il";
	public static final String USER3_EMAIL = "sadna@bgu.ac.il";
	public static final String USER4_EMAIL = "sadna@bgu.ac.il";
	public static final String USER5_EMAIL = "sadna@bgu.ac.il";
	public static final String USER6_EMAIL = "sadna@bgu.ac.il";
	public static final String USER7_EMAIL = "sadna@bgu.ac.il";
	public static final String USER_PASSWORD = "abcdefg34";
	public static final String ip = "192.168.1.102";
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

	/* -------------- Tests ---------------- */
	
	@Test
	public void registerInvalidPassTest() {
		Member _member1 = bridge.register(FORUM_NAME, USER_NAME, "short", USER1_EMAIL);
		assertNull(_member1);
		Member _member2 = bridge.register(FORUM_NAME, USER_NAME, "goodButNoNum", USER2_EMAIL);
		assertNull(_member2);
		Member _member3 = bridge.register(FORUM_NAME, USER_NAME, "longVeryVeryVeryLong", USER3_EMAIL);
		assertNull(_member3);
		Member _member4 = bridge.register(FORUM_NAME, USER_NAME, "3456", USER4_EMAIL);
		assertNull(_member4);
		Member _member5 = bridge.register(FORUM_NAME, USER_NAME, "3456565547456", USER5_EMAIL);
		assertNull(_member5);
	}

	@Test
	public void registerValidPassTest() {
		Member _member1 = bridge.register(FORUM_NAME, "aclient333", "1234567k", USER1_EMAIL);
		System.out.println(_member1);
		assertNotNull(_member1);
		bridge.finishCommunication();
	}

	
	@Test
	public void logInTest() throws InterruptedException {
		Member _member1 = bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER2_EMAIL);
		User _user = bridge.login(FORUM_NAME, USER_NAME, "abc-123");
		assertFalse(_user instanceof Member);
		_user = bridge.login(FORUM_NAME, USER_NAME, USER_PASSWORD);
		assertTrue(_user instanceof Member);
		bridge.finishCommunication();

	}

	
	@Test
	public void multipleUsersLoginTest() {
		char a = 'a';
		for (int i = 0; i < 3; i++) {
			Member _member = bridge.register(FORUM_NAME, USER_NAME + a, USER_PASSWORD + a, "email" + "a" + "@.gmail.com");
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

	
	@Test
	public void multipleUsersRegisterTest() {
		char a = 'a';
		for (int i = 0; i < 5; i++) {
			Member _member = bridge.register(FORUM_NAME, USER_NAME + a, USER_PASSWORD + a, "emaillala" + "a" + "@.gmail.com");
			assertNotNull(_member);
			a++;
		}
		bridge.finishCommunication();

	}

	
	@Test
	public void sameUserNameTest() {
		Member _member1 = bridge.register(FORUM_NAME, "oneclient", USER_PASSWORD, USER1_EMAIL);
		assertNotNull(_member1);
		Member _member2 = bridge.register(FORUM_NAME, "oneclient", USER_PASSWORD + "a", USER2_EMAIL + 'a');
		assertNull(_member2);
		bridge.finishCommunication();

	}


	@Test
	public void logOutTest() {
		Member mem = bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER2_EMAIL);
		User _user = mem.logout(FORUM_NAME);
		assertFalse(_user instanceof Member);
		bridge.finishCommunication();
	}


	@Test
	public void getListOfForumsTest(){
		List<Forum> forumsList = bridge.getForumsList();
		assertNotNull(forumsList);
		assertEquals(1, forumsList.size());
	}

	@Test
	public void publishThreadTest(){
		SubForum sb = bridge.getSubForum(FORUM_NAME, SUB_FORUM_NAME);
		Member m = bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER3_EMAIL);
		ThreadMessage tm = new ThreadMessage(sb, "title", "content", m.getUserName());
		boolean publish = bridge.publishThread(tm, "1234");
		assertFalse(publish);
		publish = bridge.publishThread(tm, m.getPassword());
		assertTrue(publish);
	}

	@Test
	//Stress test
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
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.PUBLISHERS, enumDelete.EXTENDED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.NO_RESTRICTION,
				enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.NOT_USED_EMAIL, 0, 0);
		sa.initiateForum(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD, policy, "");
		Forum forum = sa.getForum(FORUM_NAME);
		
		Member m1 = u.register(FORUM_NAME, "laaaaa", "ksjdf66asd", "sdf1@adf.com");
		Member m2 = u.register(FORUM_NAME, "baaaaa", "ksjdf66asd", "sdf2@adf.com");
		Member m3 = u.register(FORUM_NAME, "eaaaaa", "ksjdf66asd", "sdf3@adf.com");
		Admin admin = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		
		SubForum subForum_flowers = new SubForum(forum, "flowers");
		SubForum subForum_fruits = new SubForum(forum, "fruits");
		ArrayList<Member> al = new ArrayList<Member>();
		al.add(m1);
		admin.addSubForum(subForum_fruits, al);
		ArrayList<Member> al2 = new ArrayList<Member>();
		al2.add(m2);
		al2.add(m3);
		admin.addSubForum(subForum_flowers, al2);
		ThreadMessage tm_flowers1 = new ThreadMessage(subForum_flowers, "flowers are the best", "love them!", "laaaaa");
		ThreadMessage tm_flowers2 = new ThreadMessage(subForum_flowers, "my flowers", "at home i have a lot!!", "eaaaaa");
		
		m1.publishThread(tm_flowers1);
		m2.publishThread(tm_flowers2);
		List<ThreadMessage> threadMessages = m1.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		tm_flowers1 = threadMessages.get(0);
		tm_flowers2 = threadMessages.get(1);
		
		Post post = new Post(tm_flowers1, "love the flowers", "put them a lot in the sun!", "laaaaa");
		Post post2 = new Post(tm_flowers1, "look at their flowers", "new york flowers are the most beautifull", "eaaaaa");
		Post post3 = new Post(tm_flowers2, "lovely flowers", "my garden is full, can any one take some?? lol", "baaaaa");
	
		m3.postComment(post);
		m2.postComment(post2);
		m1.postComment(post3);
		System.out.println("Finished initializing test");
	}
}
