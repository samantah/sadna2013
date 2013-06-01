package junitTests.UnitTests;

/**
 *
 */
import Sadna.Client.*;
import Sadna.Server.ForumNotification;
import Sadna.db.*;
import Sadna.db.PolicyEnums.enumAssignModerator;
import Sadna.db.PolicyEnums.enumCancelModerator;
import Sadna.db.PolicyEnums.enumDelete;
import Sadna.db.PolicyEnums.enumNotiFriends;
import Sadna.db.PolicyEnums.enumNotiImidiOrAgre;

import org.junit.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

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

	private static ClientConnectionHandler ch;

	@BeforeClass
	public static void setUpClass() {
		initiateTestPlatform();
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
		initiateTestPlatform();
	}

	@After
	public void tearDown() {
	}


	private static void initiateTestPlatform() {
		ch = new ClientConnectionHandler("192.168.1.109", 3333);
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


	@Test
	public void logInTest() throws InterruptedException {
		User u = new User(ch);
		User _user = u.login(FORUM_NAME, "laaaaa", "ksjdf66asd");
		assertTrue(_user instanceof Member);
	}
	
	@Test
	public void logOutTest() throws InterruptedException {
		User u = new User(ch);
		User _user = u.login(FORUM_NAME, "laaaaa", "ksjdf66asd");
		assertTrue(_user instanceof Member);
		Member member = (Member) _user;
		User logout = member.logout(FORUM_NAME);
		assertFalse(logout instanceof Member);		
	}
	
	@Test
	public void postCommentTest() throws InterruptedException {
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		List<ThreadMessage> tmList = member.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		int id = tmList.get(0).getId();
		ThreadMessage tm = member.getThread(FORUM_NAME, SUB_FORUM_NAME, id);
		Post p = new Post(tm, "new title", "new content", member.getUserName());
		assertTrue(member.postComment(p));
	}
	
	@Test
	public void publishThreadTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		SubForum sf = member.getSubForum(FORUM_NAME, SUB_FORUM_NAME);
		ThreadMessage tm = new ThreadMessage(sf, "new title", "new content", member.getUserName());
		assertTrue(member.publishThread(tm));
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
	}
	
	@Test
	public void getSubForumTest() {
		User u = new User(ch);
		Member a = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		assertNotNull(a);
		SubForum sub = a.getSubForum(FORUM_NAME, "abc");
		assertNull(sub);
		sub = a.getSubForum(FORUM_NAME, SUB_FORUM_NAME);
		assertNotNull(sub);
		assertTrue(sub.getSubForumName().equals(SUB_FORUM_NAME));
	}
	
	@Test
	public void getForumTest() {
		User u = new User(ch);
		Forum doesntExist = u.getForum("forumThatDoesntExisttttttt");
		assertNull(doesntExist);
		Forum f = u.getForum(FORUM_NAME);
		assertTrue(f.getForumName().equals(FORUM_NAME));
	}
	
	@Test
	public void getSubForumsListTest(){
		User u = new User(ch);
		List<SubForum> sfList = u.viewSubForums(FORUM_NAME);
		assertNotNull(sfList);
	}
	
	@Test
	public void getThreadsListTest() {
		User u = new User(ch);
		List<ThreadMessage> tmList = u.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		assertNotNull(tmList);
		assertTrue(tmList.size()>0);
	}
	
	@Test
	public void getForumsListTest(){
		User u = new User(ch);
		assertNotNull(u.viewForums());
	}
	
	@Test
	public void getThreadMessageTest() {
		User u = new User(ch);
		List<ThreadMessage> threadsList = u.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		int msgID = threadsList.get(0).getId();
		ThreadMessage threadMessage = u.getThread(FORUM_NAME, SUB_FORUM_NAME, msgID);
		assertNotNull(threadMessage);
		assertTrue(threadMessage.getTitle().equals("zzzz")|| threadMessage.getTitle().equals("ccccc"));
	}

	@Test
	public void addSubForumTest() {
		User u = new User(ch);
		Member m = u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertNotNull(m);
		assertTrue(m instanceof Admin);
		Admin a = (Admin)m;
		Forum fake = new Forum("stamForum", null);
		SubForum illegal = new SubForum(fake, "firstAttemp");
		ArrayList<Member> mods = new ArrayList<Member>();
		assertFalse(a.addSubForum(illegal, mods));
		Forum f = a.getForum(FORUM_NAME);
		SubForum legal = new SubForum(f, "succeesfullAttemp");
		assertTrue(a.addSubForum(legal, mods));
	}
	
	@Test
	public void initiateForumTest(){
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.PUBLISHERS, enumDelete.EXTENDED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.NO_RESTRICTION, 0, 0);
		assertTrue(sa.initiateForum("new_forum1", "new admin", "newAdmin1234", policy));
	}
	
	@Test
	public void getAllPostsTest(){
		User u = new User(ch);
		List<ThreadMessage> threadsList = u.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		ThreadMessage thread1 = threadsList.get(0);
		List<Post> postList = u.getAllPosts(thread1);
		assertNotNull(postList);
		assertTrue(postList.size()>0);
	}
	
	@Test
	public void deleteForumTest(){
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		assertTrue(sa.deleteForum(FORUM_NAME));
		assertNull(u.getForum(FORUM_NAME));
	}
	
	@Test
	public void deleteSubForumTest(){
		User u = new User(ch);
		Admin a = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertTrue(a.deleteSubForum(FORUM_NAME, SUB_FORUM_NAME));
		assertNull(u.getSubForum(FORUM_NAME, SUB_FORUM_NAME));
	}
	

	@Test
	public void loginAsSuperAdminTest(){
		User u = new User(ch);
		assertNull(u.loginAsSuperAdmin("notAnAdmin", "notAnAdmin123"));
		assertTrue(u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD) instanceof SuperAdmin);
	}

	@Test
	public void deleteThreadMessageTest(){
		User u = new User(ch);
		Admin a = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		List<ThreadMessage> threadsList = u.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		ThreadMessage thread1 = threadsList.get(0);
		assertTrue(a.deleteThread(thread1));

		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		SubForum sf = member.getSubForum(FORUM_NAME, SUB_FORUM_NAME);
		ThreadMessage tm = new ThreadMessage(sf, "new title", "new content", member.getUserName());
		assertTrue(member.publishThread(tm));
		List<ThreadMessage> threads = member.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		int threadInt = -1;
		for (ThreadMessage threadMessage : threads) {
			if(threadMessage.getTitle().equals("new title")){
				threadInt = threadMessage.getId();
			}
		}
		tm = member.getThread(FORUM_NAME, SUB_FORUM_NAME, threadInt);
		assertTrue(member.deleteThread(tm));
	}
	
	@Test
	public void deletePostTest(){
		User u = new User(ch);
		Admin a = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		List<ThreadMessage> threadsList = u.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		ThreadMessage thread1 = threadsList.get(0);
		List<Post> postList = u.getAllPosts(thread1);
		Post p = postList.get(0);
		assertTrue(a.deletePost(p));

		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		List<ThreadMessage> tmList = member.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		int id = tmList.get(0).getId();
		ThreadMessage tm = member.getThread(FORUM_NAME, SUB_FORUM_NAME, id);
		Post p1 = new Post(tm, "new title", "new content", member.getUserName());
		assertTrue(member.postComment(p1));
		List<Post> posts = member.getAllPosts(tm);
		Post posttodelete = null;
		for (Post post : posts) {
			if(post.getContent().equals("new content")){
				posttodelete = post;
			}
		}
		assertTrue(member.deletePost(posttodelete));
	}
	
	@Test
	public void addModeratorTest(){
		User u = new User(ch);
		u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		Admin ad = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertTrue(ad.addModerator(FORUM_NAME, SUB_FORUM_NAME, USER_NAME));
	}
	
	@Test
	public void editThreadTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		SubForum sf = member.getSubForum(FORUM_NAME, SUB_FORUM_NAME);
		ThreadMessage tm = new ThreadMessage(sf, "new title1", "new content1", member.getUserName());
		assertTrue(member.publishThread(tm));
		List<ThreadMessage> threads = member.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		int id = -1;
		for (ThreadMessage threadMessage : threads) {
			if(threadMessage.getContent().equals("new content1")){
				id = threadMessage.getId();
				break;
			}
		}
		tm = member.getThread(FORUM_NAME, SUB_FORUM_NAME, id);
		tm.setTitle("edited title");
		assertTrue(member.editThread(tm));
		ThreadMessage tmEdited = member.getThread(FORUM_NAME, SUB_FORUM_NAME, id);
		assertTrue(tmEdited.getTitle().equals("edited title"));
	}
	
	@Test
	public void editPostTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		List<ThreadMessage> tmList = member.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		ThreadMessage tm = tmList.get(0);
		Post p = new Post(tm, "new title", "new content", member.getUserName());
		assertTrue(member.postComment(p));
		List<Post> posts = member.getAllPosts(tm);
		for (Post post : posts) {
			if(post.getContent().equals("new content")){
				p = post;
				break;
			}
		}
		p.setTitle("edited title");
		assertTrue(member.editPost(p));
		List<Post> postsList = member.getAllPosts(tm);
		Iterator<Post> iter = postsList.iterator();
		Post curr = null;
		while (iter.hasNext()){
			curr = iter.next();
			if (curr.getId() == p.getId())
				break;
		}
		if (curr!=null)
			assertTrue(curr.getTitle().equals("edited title"));

	}
	
	@Test
	public void getNotificationsTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		SubForum sf = member.getSubForum(FORUM_NAME, SUB_FORUM_NAME);
		ThreadMessage tm = new ThreadMessage(sf, "new title23", "new content23", member.getUserName());
		assertTrue(member.publishThread(tm));
		List<ThreadMessage> threadsssss = member.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		for (ThreadMessage threadMessage : threadsssss) {
			if(threadMessage.getContent().equals("new content23")){
				tm = threadMessage;
				break;
			}
		}
		Member member2 = u.register(FORUM_NAME, "chen", "chen1234", "email@mail.com");
		Post post = new Post(tm, "new post", "new post cont", member2.getUserName());
		assertTrue(member2.postComment(post));
		List<ForumNotification> notifs = member.getNotificationsFromServer();	
		assertNotNull(notifs);
		assertTrue(notifs.size()>0);
	}
	
	@Test
	public void removeModeratorTest(){
		User u = new User(ch);
		u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		Admin ad = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertTrue(ad.addModerator(FORUM_NAME, SUB_FORUM_NAME, USER_NAME));
		assertTrue(ad.removeModerator(FORUM_NAME, SUB_FORUM_NAME, USER_NAME));
		assertFalse(ad.removeModerator(FORUM_NAME, SUB_FORUM_NAME, USER_NAME));
	}
	
	@Test
	public void getNumberOfThreadsForUserInForumTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		SubForum sf = member.getSubForum(FORUM_NAME, SUB_FORUM_NAME);
		ThreadMessage tm = new ThreadMessage(sf, "new title", "new content", member.getUserName());
		assertTrue(member.publishThread(tm));
		Admin a = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		int number = a.getNumOfUserThreads(FORUM_NAME, USER_NAME);
		assertEquals(number, 1);
	}
	
	@Test
	public void getNumOfForum(){
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		assertEquals(sa.getForumCounter(), 1);
	}
	
	@Test
	public void getCommonMembersTest(){
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.PUBLISHERS, enumDelete.EXTENDED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.NO_RESTRICTION, 0, 0);
		assertTrue(sa.initiateForum("new_forum1", "new admin", "newAdmin1234", policy));
		u.register("new_forum1", USER_NAME, USER_PASSWORD, USER_EMAIL);
		u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);	
		List<String> common = sa.getCommonMembers();
		assertEquals(common.size(), 1);
		assertTrue(common.get(0).equals(USER_NAME));
	}


	@Test
	public void getAllForumMembersTest(){
		User u = new User(ch);
		Admin ad = (Admin)u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertNotNull(ad.getAllForumMembers());
	}
	
	@Test
	public void hasNotificationsTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		SubForum sf = member.getSubForum(FORUM_NAME, SUB_FORUM_NAME);
		ThreadMessage tm = new ThreadMessage(sf, "new title44", "new content44", member.getUserName());
		assertTrue(member.publishThread(tm));
		List<ThreadMessage> threadssss = member.viewThreadMessages(FORUM_NAME, SUB_FORUM_NAME);
		for (ThreadMessage threadMessage : threadssss) {
			if(threadMessage.getTitle().equals("new title44")){
				tm = threadMessage;
				break;
			}
		}
		Member member2 = u.register(FORUM_NAME, "chen", "chen1234", "email@mail.com");
		Post post = new Post(tm, "new post555", "new post cont555", member2.getUserName());
		assertTrue(member2.postComment(post));
		boolean has = member.hasNotifications();
		assertTrue(has);
	}

}
