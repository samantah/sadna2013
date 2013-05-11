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
import junitTests.Driver.ClientBridge;
import junitTests.Driver.ClientDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author snir elkaras
 *
 */
public class connectionHandlerTest {

    //ALL fields need to be valid
    public static final String FORUM_NAME = "forum1"; //valid forum
    public static final String SUB_FORUM_NAME = "subForum1"; //valid sub forum
    public static final String USER_NAME = "sadnaUser";  //valid username
    public static final String USER_EMAIL = "sadna@bgu.ac.il";
    public static final String USER_PASSWORD = "abcdefg34";
    public static final String ADMIN_NAME = "admin1";
    public static final String ADMIN_PASSWORD = "password1234";
    private static ConnectionHandler ch;

    @BeforeClass
    public static void setUpClass() {
        initiateTestPlatform();
        ch = new ConnectionHandler("192.168.1.105", 3333);
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

    @Test
    public void test_logIn() throws InterruptedException {
        Member _member1 = ch.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
        User _user = ch.login(FORUM_NAME, USER_NAME, USER_PASSWORD);
        assertTrue(_user instanceof Member);

    }

    @Test
    public void registerTest() {
        Member _member1 = ch.register(FORUM_NAME, USER_NAME, "short", USER_EMAIL);
        assertNull(_member1);
        Member _member2 = ch.register(FORUM_NAME, USER_NAME, "goodButNoNum", USER_EMAIL);
        assertNull(_member2);
        Member _member3 = ch.register(FORUM_NAME, USER_NAME, "longVeryVeryVeryLong", USER_EMAIL);
        assertNull(_member3);
        Member _member4 = ch.register(FORUM_NAME, USER_NAME, "3456", USER_EMAIL);
        assertNull(_member4);
        Member _member5 = ch.register(FORUM_NAME, USER_NAME, "3456565547456", USER_EMAIL);
        assertNull(_member5);
        Member _member6 = ch.register(FORUM_NAME, "validUser", "valid1234", USER_EMAIL);
        assertNotNull(_member6);
    }

    @Test
    public void addSubForumTest() {
        Forum fake = new Forum(null, "non-exist");
        SubForum illegal = new SubForum(fake, "firstAttemp");
        ArrayList<Moderator> mods = new ArrayList<Moderator>();
        assertFalse(ch.addSubForum(illegal, mods));
        Forum forum = new Forum(FORUM_NAME);
        SubForum legal = new SubForum(forum, "succeesfullAttemp");
        assertTrue(ch.addSubForum(legal, mods));
    }

    @Test
    public void getSubForumTest() {
        SubForum sf2 = new SubForum(new Forum(FORUM_NAME), "FASHION");
        ch.addSubForum(sf2, null);
        SubForum sf3 = ch.getSubForum("notExist", "FASHION");
        assertNull(sf3);
        sf3 = ch.getSubForum(FORUM_NAME, "FAMILY");
        assertNull(sf3);
        sf3 = ch.getSubForum(FORUM_NAME, "FASHION");
        assertNotNull(sf3);
        boolean isEqual = sf2.getForum() == sf3.getForum() && sf2.getSubForumName() == sf3.getSubForumName();
        assertTrue(isEqual);
    }

    @Test
    public void getThreadMessageTest() {
        List<ThreadMessage> threadsList = ch.getThreadsList(FORUM_NAME, SUB_FORUM_NAME);
        int msgID = threadsList.get(0).getId();
        ThreadMessage threadMessage = ch.getThreadMessage(FORUM_NAME, SUB_FORUM_NAME, msgID);
        assertNotNull(threadMessage);
        SubForum subForum = threadMessage.getSubForum();
        assertEquals(subForum.getSubForumName(), SUB_FORUM_NAME);
    }

    @Test
    public void postCommentTest() {
        List<ThreadMessage> threadsList = ch.getThreadsList(FORUM_NAME, SUB_FORUM_NAME);
        ThreadMessage thread1 = threadsList.get(0);
        Post post = new Post(thread1, "new", "post", "chen");
        boolean postComment = ch.postComment(post);
        assertTrue(postComment);
        List<Post> allPosts = ch.getAllPosts(thread1);
        int size = allPosts.size();
        assertEquals(post.getId(), allPosts.get(size - 1).getId());
    }

    @Test
    private static void initiateTestPlatform() {
        DataBase db = new DataBase();
       // db.deleteAll("DataBase/");
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
    
    @Test
    private static void getForumTest() {
    	assertTrue(ch.initiateForum("forumTest1", "samanta", "1234567a"));
    	Member m = ch.register("forumTest1", "sam11111", "lasjflkJDF1", "ASFADF@asd.com");
    	Forum exists = ch.getForum("forumTest1");
    	assertNotNull(exists);
    	assertEquals("forumTest1", exists.getForumName());
     	Forum doesntExist = ch.getForum("forumThatDoesn'tExisttttttt");
    	assertNull(doesntExist);
    }
    
    @Test
    private static void getThreadsListTest() {
    	assertTrue(ch.initiateForum("forumTest2", "samanta111", "1234567a"));
    	Forum exists = ch.getForum("forumTest2");
    	Member m = ch.register("forumTest2", "member123", "lasjflkJDF1", "ASFADF@asd.com");
    	List<Moderator> lm = new ArrayList<Moderator>();
    	Moderator mo = new Moderator(m);
    	lm.add(mo);
    	ch.addSubForum(new SubForum(exists, "subForumTest2"), lm);
    	assertNotNull(ch.getThreadsList("forumTest2", "subForumTest2"));
    	assertEquals(0, ch.getThreadsList("forumTest2", "subForumTest2").size());
    	SubForum sf = ch.getSubForum("forumTest2", "subForumTest2");
    	ThreadMessage t = new ThreadMessage(sf, "title222", "lalala", "samanta111");
    	ch.publishThread(t);
    	assertEquals(1, ch.getThreadsList("forumTest2", "subForumTest2").size());
    }
}
