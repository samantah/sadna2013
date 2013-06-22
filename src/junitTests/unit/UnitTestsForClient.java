package unit;

import Sadna.Client.*;
import Sadna.Server.ForumNotification;
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

import dbTABLES.Forumdb;
import dbTABLES.Memberdb;
import dbTABLES.Subforumdb;
import dbTABLES.Threaddb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author s. hourmann
 *
 */
public class UnitTestsForClient {


	//ALL fields need to be valid
	public static final String SUPER_ADMIN_NAME = "superAdmin";
	public static final String SUPER_ADMIN_PASSWORD = "superAdmin1234";

	public static final String FORUM_NAME = "forum233"; //valid forum
	public static final String ADMIN_NAME = "adminsami";
	public static final String ADMIN_PASSWORD = "adminpass12";

	public static final String FORUM1_NAME = "forum111"; //valid forum
	public static final String ADMIN1_NAME = "adminben";
	public static final String ADMIN1_PASSWORD = "adminpass15";

	public static final String FORUM2_NAME = "forum444"; //valid forum
	public static final String ADMIN2_NAME = "adminsnir";
	public static final String ADMIN2_PASSWORD = "adminpass13";

	public static final String SUBFORUM_Buy_cars = "cars"; //valid sub forum
	public static final String SUBFORUM_Holidays = "holiday"; //valid sub forum
	public static final String SUBFORUM_Food_and_animals = "food and animals"; //valid sub forum 2


	public static final String USER_NAME = "sadnaUser";  //valid username
	public static final String USER_PASSWORD = "abcdefg34";
	public static final String USER_EMAIL = "sadna@bgu.ac.il";
	public static final String USER_EMAIL1 = "sadna@bgu.ac.il";
	public static final String USER_EMAIL2 = "sadna11@bgu.ac.il";
	public static final String USER_EMAIL3 = "sadna22@bgu.ac.il";
	public static final String USER_EMAIL4 = "sadna333@bgu.ac.il";
	public static final String USER_EMAIL5 = "sadna444@bgu.ac.il";
	public static final String USER_EMAIL6 = "sadna555@bgu.ac.il";
	public static final String USER_EMAIL7 = "sadn444a555@bgu.ac.il";

	public static final String USER2_NAME = "sofyyyy";  //valid username2
	public static final String USER2_PASSWORD = "frjdf77asd";
	public static final String USER2_EMAIL = "sdf2@adf.com";

	public static final String USER3_NAME = "samisaviv";  //valid username3
	public static final String USER3_PASSWORD = "ksjdf88asd";
	public static final String USER3_EMAIL = "sdf3@adf.com";

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


	public static void initiateTestPlatform() {
		ch = new ClientConnectionHandler("192.168.1.104", 3333);
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		sa.clearDataBase();
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.PUBLISHERS, enumDelete.EXTENDED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.NO_RESTRICTION, enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.NOT_USED_EMAIL, 0, 0);
		sa.initiateForum(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD, policy, "fuck shit");
		Forum forum = sa.getForum(FORUM_NAME);

		Member m1 = u.register(FORUM_NAME, "samanta h", "ksjdf66asd", "sdf@adf.com");
		Member m2 = u.register(FORUM_NAME, "snir e", "ksjdf66asd", "sd444f1@adf.com");
		Member m3 = u.register(FORUM_NAME, "chen l", "ksjdf66asd", "sd2222f2@adf.com");
		Admin admin = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);

		SubForum subForum_Buy_cars = new SubForum(forum, SUBFORUM_Buy_cars);
		SubForum subForum_Food_and_animals = new SubForum(forum, SUBFORUM_Food_and_animals);
		ArrayList<Member> moderatorsFor_Food_and_animals = new ArrayList<Member>();
		moderatorsFor_Food_and_animals.add(m1);
		admin.addSubForum(subForum_Food_and_animals, moderatorsFor_Food_and_animals);
		ArrayList<Member> moderatorsFor_Buy_cars = new ArrayList<Member>();
		moderatorsFor_Buy_cars.add(m2);
		moderatorsFor_Buy_cars.add(m3);
		admin.addSubForum(subForum_Buy_cars, moderatorsFor_Buy_cars);
		ThreadMessage tm_where = new ThreadMessage(subForum_Buy_cars, "where can i buy a car?", "hello, i went to a sale in tel aviv and i was wondering if eldan is doing a sale of mazda some time soon.", "samanta h");
		ThreadMessage tm_looking = new ThreadMessage(subForum_Buy_cars, "looking to buy a car", "hi, i want to buy a small car for my girlfriend and also i'm selling my car. it's a 2004 honda civic, well preserved. contact me if interested", "chen l");
		ThreadMessage tm_cats = new ThreadMessage(subForum_Food_and_animals, "cats are the best animals", "i have a great cat and i would love to take her to meet othe cats, anyone interested?", "snir e");

		m1.publishThread(tm_where);
		m2.publishThread(tm_looking);
		List<ThreadMessage> threadMessages = m1.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		if(threadMessages.get(0).getTitle().equals("where can i buy a car?")){
			tm_where = threadMessages.get(0);
			tm_looking = threadMessages.get(1);
		}
		else{
			tm_looking = threadMessages.get(0);
			tm_where = threadMessages.get(1);
		}

		m2.publishThread(tm_cats);
		List<ThreadMessage> tms = m1.viewThreadMessages(FORUM_NAME, SUBFORUM_Food_and_animals);
		tm_cats = tms.get(0);
		
		
		Post post = new Post(tm_where, "you can buy a car in beer sheva", "there is a free sale next month! i'm sure there will be mazda cars as well", "chen l");
		Post post2 = new Post(tm_where, "buy a car online", "why bother going to sales, just click and buy a car from yad2 :)", "snir e");
		Post post3 = new Post(tm_looking, "buy your car", "for how much do you sell it? what is the mileage?", "snir e");
		Post post4 = new Post(tm_cats, "i have a grey cat", "where can we meet? i live in beer sheva, i have two cats!", "samanta h");

		m1.postComment(post4);
		m2.postComment(post3);
		m2.postComment(post2);
		m3.postComment(post);
		
		System.out.println("Finished initializing test");
	}


	@Test
	public void logInTest() throws InterruptedException {
		User u = new User(ch);
		User _user = u.login(FORUM_NAME, "samanta h", "ksjdf66asd");
		assertTrue(_user instanceof Member);
	}

	@Test
	public void logOutTest() throws InterruptedException {
		User u = new User(ch);
		User _user = u.login(FORUM_NAME, "samanta h", "ksjdf66asd");
		assertTrue(_user instanceof Member);
		Member member = (Member) _user;
		User logout = member.logout(FORUM_NAME);
		assertFalse(logout instanceof Member);		
	}

	@Test
	public void postCommentTest() throws InterruptedException {
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		List<ThreadMessage> tmList = member.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		int id = tmList.get(0).getId();
		ThreadMessage tm = member.getThread(FORUM_NAME, SUBFORUM_Buy_cars, id);
		Post p = new Post(tm, "new title", "new content", member.getUserName());
		assertTrue(member.postComment(p));
	}

	@Test
	public void publishThreadTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL1);
		SubForum sf = member.getSubForum(FORUM_NAME, SUBFORUM_Buy_cars);
		ThreadMessage tm = new ThreadMessage(sf, "new title", "new content", member.getUserName());
		assertTrue(member.publishThread(tm));
	}

	@Test
	public void registerTest() {
		User u = new User(ch);
		Member _member1 = u.register(FORUM_NAME, USER_NAME, "short", USER_EMAIL2);
		assertNull(_member1);
		Member _member2 = u.register(FORUM_NAME, USER_NAME, "goodButNoNum", USER_EMAIL3);
		assertNull(_member2);
		Member _member3 = u.register(FORUM_NAME, USER_NAME, "longVeryVeryVeryLong", USER_EMAIL4);
		assertNull(_member3);
		Member _member4 = u.register(FORUM_NAME, USER_NAME, "3456", USER_EMAIL5);
		assertNull(_member4);
		Member _member5 = u.register(FORUM_NAME, USER_NAME, "3456565547456", USER_EMAIL6);
		assertNull(_member5);
		Member _member6 = u.register(FORUM_NAME, "validUser", "valid1234", USER_EMAIL7);
		assertNotNull(_member6);
	}

	@Test
	public void getSubForumTest() {
		User u = new User(ch);
		Member a = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		assertNotNull(a);
		SubForum sub = a.getSubForum(FORUM_NAME, "abc");
		assertNull(sub);
		sub = a.getSubForum(FORUM_NAME, SUBFORUM_Buy_cars);
		assertNotNull(sub);
		assertTrue(sub.getSubForumName().equals(SUBFORUM_Buy_cars));
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
	public void viewSubForumsTest(){
		User u = new User(ch);
		List<SubForum> sfList = u.viewSubForums(FORUM_NAME);
		assertNotNull(sfList);
	}

	@Test
	public void viewThreadMessagesTest() {
		User u = new User(ch);
		List<ThreadMessage> tmList = u.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		assertNotNull(tmList);
		assertTrue(tmList.size()>0);
	}

	@Test
	public void viewForumsTest(){
		User u = new User(ch);
		assertNotNull(u.viewForums());
	}

	@Test
	public void getThreadMessageTest() {
		User u = new User(ch);
		List<ThreadMessage> threadsList = u.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		int msgID = threadsList.get(0).getId();
		ThreadMessage threadMessage = u.getThread(FORUM_NAME, SUBFORUM_Buy_cars, msgID);
		assertNotNull(threadMessage);
		assertTrue(threadMessage.getTitle().equals("where can i buy a car?")|| threadMessage.getTitle().equals("looking to buy a car"));
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
				enumCancelModerator.NO_RESTRICTION,
				enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.NOT_USED_EMAIL, 0, 0);
		assertTrue(sa.initiateForum("new_forum1", "new admin", "newAdmin1234", policy,"*"));
	}

	@Test
	public void getAllPostsTest(){
		User u = new User(ch);
		List<ThreadMessage> threadsList = u.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
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
		assertTrue(a.deleteSubForum(FORUM_NAME, SUBFORUM_Buy_cars));
		assertNull(u.getSubForum(FORUM_NAME, SUBFORUM_Buy_cars));
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
		List<ThreadMessage> threadsList = u.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		ThreadMessage thread1 = threadsList.get(0);
		assertTrue(a.deleteThread(thread1));

		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		SubForum sf = member.getSubForum(FORUM_NAME, SUBFORUM_Buy_cars);
		ThreadMessage tm = new ThreadMessage(sf, "new title", "new content", member.getUserName());
		assertTrue(member.publishThread(tm));
		List<ThreadMessage> threads = member.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		int threadInt = -1;
		for (ThreadMessage threadMessage : threads) {
			if(threadMessage.getTitle().equals("new title")){
				threadInt = threadMessage.getId();
			}
		}
		tm = member.getThread(FORUM_NAME, SUBFORUM_Buy_cars, threadInt);
		assertTrue(member.deleteThread(tm));
	}

	@Test
	public void deletePostTest(){
		User u = new User(ch);
		Admin a = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		List<ThreadMessage> threadsList = u.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		ThreadMessage thread1 = threadsList.get(0);
		List<Post> postList = u.getAllPosts(thread1);
		Post p = postList.get(0);
		assertTrue(a.deletePost(p));

		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		List<ThreadMessage> tmList = member.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		int id = tmList.get(0).getId();
		ThreadMessage tm = member.getThread(FORUM_NAME, SUBFORUM_Buy_cars, id);
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
		assertTrue(ad.addModerator(FORUM_NAME, SUBFORUM_Buy_cars, USER_NAME));
	}

	@Test
	public void editThreadTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		SubForum sf = member.getSubForum(FORUM_NAME, SUBFORUM_Buy_cars);
		ThreadMessage tm = new ThreadMessage(sf, "new title1", "new content1", member.getUserName());
		assertTrue(member.publishThread(tm));
		List<ThreadMessage> threads = member.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		int id = -1;
		for (ThreadMessage threadMessage : threads) {
			if(threadMessage.getContent().equals("new content1")){
				id = threadMessage.getId();
				break;
			}
		}
		tm = member.getThread(FORUM_NAME, SUBFORUM_Buy_cars, id);
		tm.setTitle("edited title");
		assertTrue(member.editThread(tm));
		ThreadMessage tmEdited = member.getThread(FORUM_NAME, SUBFORUM_Buy_cars, id);
		assertTrue(tmEdited.getTitle().equals("edited title"));
	}

	@Test
	public void editPostTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		List<ThreadMessage> tmList = member.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
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
		SubForum sf = member.getSubForum(FORUM_NAME, SUBFORUM_Buy_cars);
		ThreadMessage tm = new ThreadMessage(sf, "new title23", "new content23", member.getUserName());
		assertTrue(member.publishThread(tm));
		List<ThreadMessage> threadsssss = member.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
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
		assertTrue(ad.addModerator(FORUM_NAME, SUBFORUM_Buy_cars, USER_NAME));
		assertTrue(ad.removeModerator(FORUM_NAME, SUBFORUM_Buy_cars, USER_NAME));
		assertFalse(ad.removeModerator(FORUM_NAME, SUBFORUM_Buy_cars, USER_NAME));
	}

	@Test
	public void getNumberOfThreadsForUserInForumTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		SubForum sf = member.getSubForum(FORUM_NAME, SUBFORUM_Buy_cars);
		ThreadMessage tm = new ThreadMessage(sf, "new title", "new content", member.getUserName());
		assertTrue(member.publishThread(tm));
		Admin a = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		int number = a.getNumOfUserThreads(FORUM_NAME, USER_NAME);
		assertEquals(number, 1);
	}

	@Test
	public void getNumOfForumsTest(){
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
				enumCancelModerator.NO_RESTRICTION,
				enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.NOT_USED_EMAIL, 0, 0);
		assertTrue(sa.initiateForum("new_forum1", "new admin", "newAdmin1234", policy, "*"));
		u.register("new_forum1", USER_NAME, USER_PASSWORD, USER_EMAIL);
		u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL1);	
		List<String> common = sa.getCommonMembers();
		assertEquals(common.size(), 1);
		assertTrue(common.get(0).equals(USER_NAME));
	}


	@Test
	public void getAllForumMembersTest(){
		User u = new User(ch);
		Admin ad = (Admin)u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertNotNull(ad);
		assertNotNull(ad.getAllForumMembers());
	}

	@Test
	public void hasNotificationsTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		assertNotNull(member);
		SubForum sf = member.getSubForum(FORUM_NAME, SUBFORUM_Buy_cars);
		assertNotNull(sf);
		ThreadMessage tm = new ThreadMessage(sf, "new title44", "new content44", member.getUserName());
		assertNotNull(tm);
		assertTrue(member.publishThread(tm));
		List<ThreadMessage> threadssss = member.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		assertNotNull(threadssss);
		assertTrue(threadssss.size() > 0);
		for (ThreadMessage threadMessage : threadssss) {
			if(threadMessage.getTitle().equals("new title44")){
				tm = threadMessage;
				break;
			}
		}
		Member member2 = u.register(FORUM_NAME, "chen", "chen1234", "emaaail@mail.com");
		assertNotNull(member2);
		Post post = new Post(tm, "new post555", "new post cont555", member2.getUserName());
		assertTrue(member2.postComment(post));
		boolean has = member.hasNotifications();
		assertTrue(has);
	}

	@Test
	public void	testLoginAsSuperAdmin(){
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		assertNotNull(sa);
		Policy policy = new Policy(enumNotiImidiOrAgre.AGGREGATE,
				enumNotiFriends.PUBLISHERS, enumDelete.LIMITED,
				enumAssignModerator.MIN_PUBLISH,
				enumCancelModerator.NO_RESTRICTION,
				enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.NOT_USED_EMAIL, 0, 0);
		assertTrue(sa.initiateForum("another forum", ADMIN_NAME, ADMIN_PASSWORD, policy, "*"));
		SuperAdmin notSa = u.loginAsSuperAdmin("not_superadmin", "incorrect_pass");
		assertNull(notSa);
	}

	@Test
	public void	getNumOfUserThreadsTest(){
		User u = new User(ch);
		Admin admin = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertNotNull(admin);
		Member mem = u.register(FORUM_NAME, "a user", "asfadsf23", "mailaaa.cc@gmail.com");
		assertNotNull(mem);
		assertEquals(0, admin.getNumOfUserThreads(FORUM_NAME, mem.getUserName()));
		SubForum sf = mem.getSubForum(FORUM_NAME, SUBFORUM_Buy_cars);
		assertNotNull(sf);
		assertTrue(mem.publishThread(new ThreadMessage(sf, "my thread lalalala", "my content babababa", "a user")));
		assertEquals(1, admin.getNumOfUserThreads(FORUM_NAME, mem.getUserName()));
	}

	@Test
	public void getUsersPostToUserTest(){
		User u = new User(ch);
		Admin admin = (Admin) u.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertNotNull(admin);
		Member mem = u.register(FORUM_NAME, "zohar1", "addsfadsf23", "z.cc@gmail.com");
		assertNotNull(mem);
		HashMap<String, List<String>> usersCommentsPerUser = admin.getUsersPostToUser(FORUM_NAME);
		assertNotNull(usersCommentsPerUser);
		List<String> posters = usersCommentsPerUser.get(mem.getUserName());
		assertEquals(0, posters.size());
		SubForum sf = mem.getSubForum(FORUM_NAME, SUBFORUM_Buy_cars);
		assertNotNull(sf);
		assertTrue(mem.publishThread(new ThreadMessage(sf, "zohar1 thread", "zohar1 content", "zohar1")));

		Member mem1 = u.register(FORUM_NAME, "memberrrr1", "SDfsd777", "mjj.cc@gmail.com");
		assertNotNull(mem1);
		List<ThreadMessage> tmessages = mem1.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		assertNotNull(tmessages);
		ThreadMessage zoharTm = null;
		for (ThreadMessage threadMessage : tmessages) {
			if(threadMessage.getTitle().equals("zohar1 thread")){
				zoharTm = threadMessage;
				break;
			}
		}
		assertNotNull(zoharTm);
		assertTrue(mem1.postComment(new Post(zoharTm, "title for a comment on zohar1", "content memberrr1 comment", mem1.getUserName())));
		HashMap<String, List<String>> usersCommentsPerUser1 = admin.getUsersPostToUser(FORUM_NAME);
		assertNotNull(usersCommentsPerUser1);
		List<String> posters1 = usersCommentsPerUser1.get(mem.getUserName());
		assertEquals(1, posters1.size());
		String poster = posters1.get(0);
		assertTrue(poster.equals(mem1.getUserName()));
	}

	@Test
	public void policyCancelModeratorTest(){
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.PUBLISHERS, enumDelete.EXTENDED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.RESTRICTED, enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.NOT_USED_EMAIL, 0, 0);
		assertTrue(sa.initiateForum(FORUM2_NAME, ADMIN_NAME, ADMIN_PASSWORD, policy, "*"));

		Forum forum = sa.getForum(FORUM2_NAME);
		assertNotNull(forum);
		List<Member> members1 = new ArrayList<Member>();
		assertNotNull(members1);
		Member m2 = u.register(FORUM2_NAME, USER2_NAME, USER2_PASSWORD, USER2_EMAIL);
		Member m3 = u.register(FORUM2_NAME, USER3_NAME, USER3_PASSWORD, USER3_EMAIL);
		assertNotNull(m2);
		assertNotNull(m3);
		
		members1.add(m2);
		members1.add(m3);
		SubForum subForum2 = new SubForum(forum, SUBFORUM_Food_and_animals);
		Admin admin = (Admin) u.login(FORUM2_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertNotNull(admin);
		
		assertTrue(admin.addSubForum(subForum2, members1));
		assertTrue(admin.removeModerator(FORUM2_NAME, SUBFORUM_Food_and_animals, m3.getUserName()));
		assertFalse(admin.removeModerator(FORUM2_NAME, SUBFORUM_Food_and_animals, m2.getUserName()));
	}

	@Test
	public void policyDeleteOptionForModeratorTest(){
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.PUBLISHERS, enumDelete.LIMITED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.RESTRICTED, enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.NOT_USED_EMAIL, 0, 0);
		assertTrue(sa.initiateForum(FORUM2_NAME, ADMIN2_NAME, ADMIN2_PASSWORD, policy, "*"));

		Forum forum = sa.getForum(FORUM2_NAME);
		assertNotNull(forum);
		List<Member> members1 = new ArrayList<Member>();
		Member m2 = u.register(FORUM2_NAME, USER2_NAME, USER2_PASSWORD, USER2_EMAIL);
		assertNotNull(m2);
		members1.add(m2);
		SubForum subForum = new SubForum(forum, SUBFORUM_Food_and_animals);
		Admin admin = (Admin) u.login(FORUM2_NAME, ADMIN2_NAME, ADMIN2_PASSWORD);
		assertNotNull(admin);
		assertTrue(admin.addSubForum(subForum, members1));

		Member m3 = u.register(FORUM2_NAME, USER3_NAME, USER3_PASSWORD, USER3_EMAIL);
		assertNotNull(m3);
		ThreadMessage threadMessage = new ThreadMessage(subForum, "title for delete test", "content for delete", USER3_NAME);

		assertTrue(m3.publishThread(threadMessage));
		forum = admin.getForum(FORUM2_NAME);
		assertNotNull(forum);
		Moderator mod = (Moderator) u.login(FORUM2_NAME, USER2_NAME, USER2_PASSWORD);
		assertNotNull(mod);
		List<ThreadMessage> tmessages = m3.viewThreadMessages(FORUM2_NAME, SUBFORUM_Food_and_animals);
		assertNotNull(tmessages);
		ThreadMessage m3tm = null;
		for (ThreadMessage thr : tmessages) {
			if(thr.getTitle().equals("title for delete test")){
				m3tm = thr;
				break;
			}
		}
		assertNotNull(m3tm);
		assertFalse(mod.deleteThread(m3tm));
		assertTrue(m3.deleteThread(m3tm));
	}

	@Test
	public void policyNotiFriendsTest(){
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.PUBLISHERS, enumDelete.EXTENDED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.NO_RESTRICTION, enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.NOT_USED_EMAIL, 0, 0);
		assertTrue(sa.initiateForum(FORUM1_NAME, ADMIN_NAME, ADMIN_PASSWORD, policy, "*"));

		Forum forum = sa.getForum(FORUM1_NAME);
		assertNotNull(forum);
		List<Member> members1 = new ArrayList<Member>();
		assertNotNull(members1);
		Member m2 = u.register(FORUM1_NAME, USER2_NAME, USER2_PASSWORD, USER2_EMAIL);
		assertNotNull(m2);
		members1.add(m2);
		SubForum subForum2 = new SubForum(forum, SUBFORUM_Food_and_animals);
		Admin admin = (Admin) u.login(FORUM1_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertNotNull(admin);
		assertTrue(admin.addSubForum(subForum2, members1));

		Member m3 = u.register(FORUM1_NAME, USER3_NAME, USER3_PASSWORD, USER3_EMAIL);
		Member m1 = u.register(FORUM1_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
		assertNotNull(m3);
		assertNotNull(m1);

		SubForum sf = m1.getSubForum(FORUM1_NAME, SUBFORUM_Food_and_animals);
		assertNotNull(sf);
		ThreadMessage tm = new ThreadMessage(sf, "new ----- title", "new -----aa content", m1.getUserName());
		assertTrue(m1.publishThread(tm));
		List<ForumNotification> notis = m3.getNotificationsFromServer();
		assertNotNull(notis);
		assertEquals(0, notis.size());
	}

	
	@Test
	public void policySecurity_uniqueTest(){
		// email should be unique!!

		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.ALLMEMBERS, enumDelete.EXTENDED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.NO_RESTRICTION, enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.NOT_USED_EMAIL, 0, 0);
		assertTrue(sa.initiateForum(FORUM2_NAME, ADMIN_NAME, ADMIN_PASSWORD, policy, "*"));

		Member mem333 = u.register(FORUM2_NAME, USER2_NAME, USER2_PASSWORD, "first.mail@unique.mail");
		assertNotNull(mem333);
		Member mem444 = u.register(FORUM2_NAME, "other_mem444", "other_pass444", "first.mail@unique.mail");
		assertNull(mem444);
		mem444 = u.register(FORUM2_NAME, "other_mem444", "other_pass444", "second.mail@unique.mail");
		assertNotNull(mem444);
	}

	@Test
	public void policySecurity_sendEmailTest(){
		// email verification

		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.ALLMEMBERS, enumDelete.EXTENDED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.NO_RESTRICTION, enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.VERIFY_EMAIL, 0, 0);
		assertTrue(sa.initiateForum(FORUM2_NAME, ADMIN_NAME, ADMIN_PASSWORD, policy, "*"));

		Member mem333 = u.register(FORUM2_NAME, USER2_NAME, USER2_PASSWORD, "sami.hour@gmail.com");
		assertNull(mem333.getUserName());
		Member mem444 = u.verifyEmail(FORUM2_NAME, "other_mem444", "other_pass444", "12345678999");
		assertNull(mem444);
	}
	
	@Test
	public void policyModeratorPermissionsTest(){
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.PUBLISHERS, enumDelete.LIMITED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.NO_RESTRICTION, enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.LIMITED, 
				enumSecurity.NOT_USED_EMAIL, 0, 0);
		assertTrue(sa.initiateForum(FORUM2_NAME, ADMIN2_NAME, ADMIN2_PASSWORD, policy, "*"));

		Forum forum = sa.getForum(FORUM2_NAME);
		assertNotNull(forum);
		List<Member> members1 = new ArrayList<Member>();
		Member m2 = u.register(FORUM2_NAME, USER2_NAME, USER2_PASSWORD, USER2_EMAIL);
		assertNotNull(m2);
		members1.add(m2);
		SubForum subForum = new SubForum(forum, SUBFORUM_Food_and_animals);
		Admin admin = (Admin) u.login(FORUM2_NAME, ADMIN2_NAME, ADMIN2_PASSWORD);
		assertNotNull(admin);
		assertTrue(admin.addSubForum(subForum, members1));

		Member m3 = u.register(FORUM2_NAME, USER3_NAME, USER3_PASSWORD, USER3_EMAIL);
		assertNotNull(m3);
		ThreadMessage threadMessage = new ThreadMessage(subForum, "title for delete test", "content for delete", USER3_NAME);

		assertTrue(m3.publishThread(threadMessage));
		forum = admin.getForum(FORUM2_NAME);
		assertNotNull(forum);
		Moderator mod = (Moderator)u.login(FORUM2_NAME, USER2_NAME, USER2_PASSWORD);
		List<ThreadMessage> tmessages = m3.viewThreadMessages(FORUM2_NAME, SUBFORUM_Food_and_animals);
		assertNotNull(tmessages);
		ThreadMessage m3tm = null;
		for (ThreadMessage thr : tmessages) {
			if(thr.getTitle().equals("title for delete test")){
				m3tm = thr;
				break;
			}
		}
		assertNotNull(m3tm);
		m3tm.setContent("changed content...");
		assertFalse(mod.editThread(m3tm));
		assertTrue(m3.editThread(m3tm));
	}


	@Test
	public void policyMessageContentTest(){
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		sa.clearDataBase();
		Policy policy = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.PUBLISHERS, enumDelete.EXTENDED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.NO_RESTRICTION, enumMessageContent.FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.NOT_USED_EMAIL, 0, 0);
		assertTrue(sa.initiateForum(FORUM1_NAME, ADMIN_NAME, ADMIN_PASSWORD, policy, "*"));
		Forum forum = sa.getForum(FORUM1_NAME);
		assertNotNull(forum);
		
		Member m1 = u.register(FORUM1_NAME, "sofy c", "ksjdf66asd", "sdf@adf.com");
		Member m2 = u.register(FORUM1_NAME, "ori li", "ksjdf66asd", "sd444f1@adf.com");
		Member m3 = u.register(FORUM1_NAME, "neta lee", "ksjdf66asd", "sd2222f2@adf.com");
		Admin admin = (Admin) u.login(FORUM1_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertNotNull(m1);
		assertNotNull(m2);
		assertNotNull(m3);
		assertNotNull(admin);

		SubForum subForum_Holidays = new SubForum(forum, SUBFORUM_Holidays);
		ArrayList<Member> moderatorsFor_Holidays = new ArrayList<Member>();
		moderatorsFor_Holidays.add(m2);
		moderatorsFor_Holidays.add(m3);
		assertTrue(admin.addSubForum(subForum_Holidays, moderatorsFor_Holidays));
		ThreadMessage tm_french = new ThreadMessage(subForum_Holidays, "best french holiday options...?", "hi all, my boyfriend and i want to travel this summer to france, any ideas?", "sofy c");
		ThreadMessage tm_list = new ThreadMessage(subForum_Holidays, "my holiday options", "here is a small list of my prefered vacations: bali, thailand, hawaii and costa rica!!! right?", "neta lee");

		assertTrue(m1.publishThread(tm_french));
		assertTrue(m3.publishThread(tm_list));
		List<ThreadMessage> threadMessages = m1.viewThreadMessages(FORUM1_NAME, SUBFORUM_Holidays);
		assertNotNull(threadMessages); 
		assertTrue(threadMessages.size() > 1);
		if(threadMessages.get(0).getTitle().equals("best french holiday options...?")){
			tm_french = threadMessages.get(0);
			tm_list = threadMessages.get(1);
		}
		else{
			tm_list = threadMessages.get(0);
			tm_french = threadMessages.get(1);
		}
		
		
		Post post = new Post(tm_french, "your french vacation", "i'm sure you will love paris holiday (without kids) and all the beautiful sites there for adults.. visit it! but i also recommend the south - the riviere..", "neta lee");
		Post post2 = new Post(tm_french, "go to eurodisney in france", "if you are going with kids -> that's a must to visit!! even for adults :)", "ori li");
		Post post3 = new Post(tm_list, "add some sites to your holiday options list", "what about the big cities like new york, Buenos Aires, Barcelona...?", "sofy c");

		assertTrue(m2.postComment(post3));
		assertTrue(m2.postComment(post2));
		assertTrue(m1.postComment(post));
	}
	
	@Test
	public void badWordsTest(){
		User u = new User(ch);
		Member member = u.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL1);
		assertNotNull(member);
		SubForum sf = member.getSubForum(FORUM_NAME, SUBFORUM_Buy_cars);
		assertNotNull(sf);
		ThreadMessage tm_bad_title = new ThreadMessage(sf, "go fuck it", "should not publish this one..", member.getUserName());
		assertFalse(member.publishThread(tm_bad_title));
		ThreadMessage tm_bad_content = new ThreadMessage(sf, "looking to buy a car - shouldn't publish this one", "shit, fuck you man!", member.getUserName());
		assertFalse(member.publishThread(tm_bad_content));
		List<ThreadMessage> tms = member.viewThreadMessages(FORUM_NAME, SUBFORUM_Buy_cars);
		assertNotNull(tms);
		assertTrue(tms.size() > 0);
		ThreadMessage someTm = tms.get(0);
		assertNotNull(someTm);
		Post post_bad_title = new Post(someTm, "fuck this shit", "don't post it!!", member.getUserName());
		assertFalse(member.postComment(post_bad_title));
		Post post_bad_content = new Post(someTm, "looks like normal title", "you shit fuck!", member.getUserName());
		assertFalse(member.postComment(post_bad_content));
	}
	
	@Test
	public void buildPolicyForums(){
		User u = new User(ch);
		SuperAdmin sa = u.loginAsSuperAdmin(SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		sa.clearDataBase();
		
		Policy policy1 = new Policy(enumNotiImidiOrAgre.IMIDIATE,
				enumNotiFriends.ALLMEMBERS, enumDelete.LIMITED,
				enumAssignModerator.NO_RESTRICTION,
				enumCancelModerator.NO_RESTRICTION, enumMessageContent.FILTERED,
				enumModeratorPermissions.LIMITED, 
				enumSecurity.VERIFY_EMAIL, 0, 0);
		assertTrue(sa.initiateForum("Left forum policies", "leftadmin", "left1234", policy1, ""));
		
		Policy policy2 = new Policy(enumNotiImidiOrAgre.AGGREGATE,
				enumNotiFriends.PUBLISHERS, enumDelete.EXTENDED,
				enumAssignModerator.MIN_PUBLISH,
				enumCancelModerator.RESTRICTED, enumMessageContent.NOT_FILTERED,
				enumModeratorPermissions.EXTENDED, 
				enumSecurity.NOT_USED_EMAIL, 0, 1);
		assertTrue(sa.initiateForum("Right forum policies", "rightadmin", "right1234", policy2, ""));
	}
	
}
