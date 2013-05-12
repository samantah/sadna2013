package UnitTests;

/**
 *
 */
import Sadna.Client.*;
import Sadna.db.DataBase;
import javax.imageio.spi.RegisterableService;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.ArrayList;
import java.util.List;
import Driver.ClientBridge;
import Driver.ClientDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author snir elkaras
 *
 */
public class connectionHandlerTest {

	
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

	private static ConnectionHandler ch;

	@BeforeClass
	public static void setUpClass() {
		initiateTestPlatform();
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}


	private static void initiateTestPlatform() {
		DataBase db = new DataBase();
		db.initiateDataBase();
		ch = new ConnectionHandler("192.168.1.105", 3333);
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
		ArrayList<Moderator> al = new ArrayList<Moderator>();
		al.add(mod1);
		sa.addSubForum(subForum2, al);
		ArrayList<Moderator> al2 = new ArrayList<Moderator>();
		al2.add(mod2);
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


	@Test
	public void test_logIn() throws InterruptedException {
		User u = new User(ch);
		User _user = u.login(FORUM_NAME, USER_NAME, USER_PASSWORD);
		assertTrue(_user instanceof Member);
		System.out.println("Finished test_logIn");
	}

	@Test
	public void registerTest() {
		User u = new User(ch);
		Member _member1 = u.register(FORUM_NAME, USER_NAME, "short", USER_EMAIL);
		assertNull(_member1);
		Member _member2 = u.register(FORUM_NAME, USER_NAME, "goodButNoNum", USER_EMAIL);
		assertNull(_member2);
		Member _member3 = u.register(FORUM_NAME, USER_NAME, "longVeryVeryVeryLong", USER_EMAIL);
		assertNull(_member3);
		Member _member4 = u.register(FORUM_NAME, USER_NAME, "3456", USER_EMAIL);
		assertNull(_member4);
		Member _member5 = u.register(FORUM_NAME, USER_NAME, "3456565547456", USER_EMAIL);
		assertNull(_member5);
		Member _member6 = u.register(FORUM_NAME, "validUser", "valid1234", USER_EMAIL);
		assertNotNull(_member6);
		System.out.println("Finished registerTest");		
	}

//	@Test
//	public void addSubForumTest() {
//		//User u = new User(ch);
//		Member m = ch.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
//		assertNotNull(m);
//		assertTrue(m instanceof Admin);
//		Admin a = (Admin)m;
//		Forum fake = new Forum(a, "not-exist");
//		SubForum illegal = new SubForum(fake, "firstAttemp");
//		ArrayList<Moderator> mods = new ArrayList<Moderator>();
//		assertFalse(a.addSubForum(illegal, mods));
//		Forum forum = new Forum(FORUM_NAME);
//		SubForum legal = new SubForum(forum, "succeesfullAttemp");
//		assertTrue(a.addSubForum(legal, mods));
//	}

	@Test
	public void getSubForumTest() {
		User u = new User(ch);
		Member a = u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertNotNull(a);
		Admin ad = (Admin) a;
		Forum f = a.getForum(FORUM_NAME);
		SubForum sf2 = new SubForum(f, "FASHION");
		assertTrue(ad.addSubForum(sf2, null));
		SubForum sf3 = ad.getSubForum("notExist", "FASHION");
		assertNull(sf3);
		sf3 = ad.getSubForum(FORUM_NAME, "FAMILY");
		assertNull(sf3);
		sf3 = ad.getSubForum(FORUM_NAME, "FASHION");
		assertNotNull(sf3);
		assertTrue(sf2.getForum().getForumName() == sf3.getForum().getForumName() && 
				sf2.getSubForumName() == sf3.getSubForumName());
	}

//	@Test
//	public void getThreadMessageTest() {
//		List<ThreadMessage> threadsList = ch.getThreadsList(FORUM_NAME, SUB_FORUM_NAME);
//		int msgID = threadsList.get(0).getId();
//		ThreadMessage threadMessage = ch.getThreadMessage(FORUM_NAME, SUB_FORUM_NAME, msgID);
//		assertNotNull(threadMessage);
//		SubForum subForum = threadMessage.getSubForum();
//		assertEquals(subForum.getSubForumName(), SUB_FORUM_NAME);
//	}
//
//	@Test
//	public void postCommentTest() {
//		//User u = new User(ch);
//		List<ThreadMessage> threadsList = ch.getThreadsList(FORUM_NAME, SUB_FORUM_NAME);
//		ThreadMessage thread1 = threadsList.get(0);
//		Member m = ch.register(FORUM_NAME, "sami5432", "password7655", "asa@af.sdf");
//		Post post = new Post(thread1, "new", "post", "chen");
//		boolean postComment = m.postComment(post);
//		assertTrue(postComment);
//		List<Post> allPosts = m.getAllPosts(thread1);
//		int size = allPosts.size();
//		assertEquals(post.getId(), allPosts.get(size - 1).getId());
//	}
//
//
//
//
//	@Test
//	public void getForumTest() {
//		//User u = new User(ch);
//		Forum f = ch.getForum(FORUM_NAME);
//		assertTrue(f.getForumName().equals(FORUM_NAME));
//		Forum doesntExist = ch.getForum("forumThatDoesn'tExisttttttt");
//		assertNull(doesntExist);
//	}
//
//	@Test
//	public void getThreadsListTest() {
//		//User u = new User(ch);
//		SuperAdmin sa = ch.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
//		assertNotNull(sa);
//		assertTrue(sa.initiateForum("forumTest2", "samanta111", "1234567a"));
//		Admin admin = (Admin)ch.login("forumTest2", "samanta111", "1234567a");
//		Forum exists = admin.getForum("forumTest2");
//		Member m = ch.register("forumTest2", "member123", "lasjflkJDF1", "ASFADF@asd.com");
//		List<Moderator> lm = new ArrayList<Moderator>();
//		Moderator mo = new Moderator(m);
//		lm.add(mo);
//		admin.addSubForum(new SubForum(exists, "subForumTest2"), lm);
//		assertNotNull(m.viewThreadMessages("forumTest2", "subForumTest2"));
//		assertEquals(0, m.viewThreadMessages("forumTest2", "subForumTest2").size());
//		SubForum sf = m.getSubForum("forumTest2", "subForumTest2");
//		ThreadMessage t = new ThreadMessage(sf, "title222", "lalala", "samanta111");
//		m.publishThread(t);
//		assertEquals(1, m.viewThreadMessages("forumTest2", "subForumTest2").size());
//	}
//
//
//
//	@Test
//	public void publishThreadTest(){
//		//User u = new User(ch);
//		Member mem = ch.login(FORUM_NAME, USER_NAME, USER_PASSWORD);
//		Forum f = ch.getForum(FORUM_NAME); 
//		List<SubForum> sf = ch.getSubForumsList(FORUM_NAME);
//		ThreadMessage tm = new ThreadMessage(sf.get(0), "test", "test", USER_NAME);
//		assertTrue(ch.publishThread(tm, USER_PASSWORD));
//	}
//
//	@Test
//	public void getSubForumsListTest(){
//		//User u = new User(ch);
//		assertNotNull(ch.getSubForumsList(FORUM_NAME));
//	}
//	@Test
//	public void getForumsListTest(){
//		//User u = new User(ch);
//		assertNotNull(ch.getForumsList());
//	}
//
//	@Test
//	public void loginAsSuperAdminTest(){
//		//User u = new User(ch);
//		assertNull(ch.loginAsSuperAdmin("notAnAdmin", "notAnAdmin123"));
//		assertTrue(ch.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD) instanceof SuperAdmin);
//	}

//	@Test
//	public void initiateForumTest(){
//		//User u = new User(ch);
//		SuperAdmin sa = ch.loginAsSuperAdmin(ADMIN_NAME, ADMIN_PASSWORD);
//		assertTrue(sa.initiateForum("new"+FORUM_NAME, "new"+ADMIN_NAME, ADMIN_PASSWORD));
//	}

//	@Test
//	public void getAllPostsTest(){
//		//User u = new User(ch);
//		List<ThreadMessage> threadsList = ch.getThreadsList(FORUM_NAME, SUB_FORUM_NAME);
//		ThreadMessage thread1 = threadsList.get(0);
//		Member m = ch.register(FORUM_NAME, "eliSDDhana", "ASASD7678", "assd@af.vo");
//		Post post = new Post(thread1, "new", "post", "chen");
//		m.postComment(post);
//		assertNotNull(ch.getAllPosts(thread1));
//	}

//	@Test
//	public void addModeratorTest(){
//		//User u = new User(ch);
//		Member m = ch.register(FORUM_NAME, "willbemode", "willbemode12", "willbe@gmail.com");
//		Admin ad = (Admin) ch.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
//		assertTrue(ad.addModerator(FORUM_NAME, SUB_FORUM_NAME, m.getUserName()));
//	}

//	@Test
//	public void removeModeratorTest(){
//		//User u = new User(ch);
//		Admin ad = (Admin) ch.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
//		assertTrue(ad.removeModerator(FORUM_NAME, SUB_FORUM_NAME, "willbemode"));
//	}

//	@Test
//	public void getAllForumMembersTest(){
//		
//		assertNotNull(ch.getAllForumMembers(FORUM_NAME, ADMIN_NAME, SUPER_ADMIN_PASSWORD));
//	}
//
//	@Test
//	public void getCommonMembersTest(){
//		//User u = new User(ch);
//		SuperAdmin sa = ch.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
//		assertNotNull(sa.getCommonMembers());
//	}

//	@Test
//	public void getForumCounterTest(){
//		//User u = new User(ch);
//		SuperAdmin sa = ch.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
//		assertTrue(sa.getForumCounter()>0);
//	}

//	@Test
//	public void deletePostTest(){
//		//User u = new User(ch);
//		Member mem = ch.login("forumTest2", "samanta111", "1234567a");
//		SubForum sf = mem.getSubForum("forumTest2", "subForumTest2");
//		ThreadMessage t = new ThreadMessage(sf, "title333", "lalala", "samanta111");
//		mem.publishThread(t);
//		Post p = new Post(t, "title11", "contenttttttt", "samanta111");
//		mem.postComment(p);
//		assertTrue(mem.deletePost(p));
//	}
//
//	@Test
//	public void deleteThreadMessageTest(){
//		//User u = new User(ch);
//		ch.login(FORUM_NAME, "samanta11188", "1234567a");
//		Member mem = ch.login(FORUM_NAME, "samanta11188", "1234567a");
//		SubForum sf = ch.getSubForum("forumTest2", "subForumTest2");
//		List<ThreadMessage> ltm = ch.getThreadsList("forumTest2", "subForumTest2");
//		assertNotNull(ltm);
//		assertTrue(ch.deleteThreadMessage(ltm.get(0), "samanta11188", "1234567a"));
//	}

//	@Test
//	public void logoutTest(){
//		//User u = new User(ch);
//		Member mem = ch.login(FORUM_NAME, USER_NAME, USER_PASSWORD);
//		assertTrue(ch.logout(FORUM_NAME, USER_NAME) instanceof User);
//	}
}
