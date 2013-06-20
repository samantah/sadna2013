package unit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import Sadna.Server.ServerToDataBaseHandler;
import Sadna.Server.API.ServerInterface;
import Sadna.db.PolicyEnums.enumAssignModerator;
import Sadna.db.PolicyEnums.enumCancelModerator;
import Sadna.db.PolicyEnums.enumDelete;
import Sadna.db.PolicyEnums.enumNotiFriends;
import Sadna.db.PolicyEnums.enumNotiImidiOrAgre;
import dbTABLES.Forumdb;
import dbTABLES.IMpl;
import dbTABLES.IMplInterface;
import dbTABLES.Memberdb;
import dbTABLES.Postdb;
import dbTABLES.Subforumdb;
import dbTABLES.Threaddb;

public class UnitTestsForServer {

	public static final String SUPER_ADMIN_NAME = "superAdmin";
	public static final String SUPER_ADMIN_PASSWORD = "superAdmin1234";

	public static final String FORUM_NAME = "first_forum"; //valid forum
	public static final String ADMIN_NAME = "adminben1";
	public static final String ADMIN_PASSWORD = "admin12345";
	
	public static final String SUBFORUM1_NAME = "first subforum"; //valid sub forum 1
	public static final String SUBFORUM2_NAME = "second subforum"; //valid sub forum 2
	
	public static final String USER1_NAME = "laaaaa";  //valid username1
	public static final String USER1_PASSWORD = "eejdf66asd";
	public static final String USER1_EMAIL = "sdf1@adf.com";
	
	public static final String USER2_NAME = "baaaaa";  //valid username2
	public static final String USER2_PASSWORD = "frjdf77asd";
	public static final String USER2_EMAIL = "sdf2@adf.com";
	
	public static final String USER3_NAME = "eaaaaa";  //valid username3
	public static final String USER3_PASSWORD = "ksjdf88asd";
	public static final String USER3_EMAIL = "sdf3@adf.com";

	public static ServerInterface si;
    private static IMplInterface databaseImpl;


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
		databaseImpl = new IMpl();
		si = new ServerToDataBaseHandler(databaseImpl);
		si.clearDB();

		si.initiateForum(ADMIN_NAME, ADMIN_PASSWORD, FORUM_NAME, enumNotiImidiOrAgre.IMIDIATE.toString(), 
				enumNotiFriends.PUBLISHERS.toString(), enumDelete.EXTENDED.toString(), 
				enumAssignModerator.NO_RESTRICTION.toString(), "0", "0", enumCancelModerator.NO_RESTRICTION.toString(), 
				SUPER_ADMIN_NAME, SUPER_ADMIN_PASSWORD);
		Forumdb fdb = si.getForum(FORUM_NAME);
		
		si.register(FORUM_NAME, USER1_NAME, USER1_PASSWORD, USER1_EMAIL);
		si.register(FORUM_NAME, USER2_NAME, USER2_PASSWORD, USER2_EMAIL);
		si.register(FORUM_NAME, USER3_NAME, USER3_PASSWORD, USER3_EMAIL);
		si.login(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		
		Set<Memberdb> members1 = new HashSet<Memberdb>();
		Memberdb m1 = si.getMember(FORUM_NAME, USER1_NAME);
		members1.add(m1);
		Memberdb m2 = si.getMember(FORUM_NAME, USER2_NAME);
		members1.add(m1);
		Memberdb m3 = si.getMember(FORUM_NAME, USER3_NAME);
		members1.add(m1);
		Subforumdb subForum2 = new Subforumdb(fdb, SUBFORUM2_NAME,  members1, new HashSet<Threaddb>());
		si.addSubForum(subForum2, new ArrayList<Memberdb>(members1), ADMIN_NAME, ADMIN_PASSWORD);
		Set<Memberdb> members2 = new HashSet<Memberdb>();
		members2.add(m2);
		members2.add(m3);
		Subforumdb subForum1 = new Subforumdb(fdb, SUBFORUM1_NAME,  members2, new HashSet<Threaddb>());
		si.addSubForum(subForum1, new ArrayList<Memberdb>(members2), ADMIN_NAME, ADMIN_PASSWORD);

		Set<Postdb> posts1 = new HashSet<Postdb>(); 
		Set<Postdb> posts2 = new HashSet<Postdb>(); 
		Threaddb threadmsg1 = new Threaddb(subForum2, m2, "hi11", "hello world..", posts1);
		Threaddb threadmsg2 = new Threaddb(subForum2, m3, "hi22", "good night", posts2);
			
		si.publishThread(threadmsg1, USER2_NAME, USER1_PASSWORD);
		si.publishThread(threadmsg2, USER3_NAME, USER2_PASSWORD);
		List<Threaddb> threadMessages = si.getThreadsList(FORUM_NAME, SUBFORUM1_NAME);
		Threaddb threadMessage1 = threadMessages.get(0);
		Threaddb threadMessage2 = threadMessages.get(1);
		
		Postdb post1 = new Postdb(threadMessage1, m1, "post1", "content1");
		Postdb post2 = new Postdb(threadMessage1, m2, "post2", "content2");
		Postdb post3 = new Postdb(threadMessage2, m3, "post3", "content3");
	
		si.postComment(post1, USER1_NAME, USER1_PASSWORD);
		si.postComment(post2, USER2_NAME, USER2_PASSWORD);
		si.postComment(post3, USER3_NAME, USER3_PASSWORD);
		System.out.println("Finished initializing test");
	}

}
