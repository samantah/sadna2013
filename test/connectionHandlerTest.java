/**
 * 
 */



import Sadna.Client.ConnectionHandler;
import Sadna.Client.Member;
import Sadna.Client.SuperAdmin;
import Sadna.Client.User;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Sadna.db.Forum;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

/**
 * @author snir elkaras
 *
 */
public class connectionHandlerTest {
	String host = "192.168.0.105";
	int port = 3248;
	ConnectionHandler ch;
	User guest;
	SuperAdmin sp;
	Member m;
	Forum forum1;
	SubForum sf;
	ThreadMessage tm;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ch = new ConnectionHandler(host, port);
		guest = new User(ch);
		sp = new SuperAdmin("superAdmin", "password1234", "email@gmail.com", ch);
		sp.initiateForum("forum1", "snirElkaras", "snirElkaras25");
		ch.register("forum1", "chenli", "chen1234", "chenli@gmail.com");
		forum1 = ch.getForum("forum1");
		sf = new SubForum(forum1, "CARS");
		Member m = new Member("avielh", "dolev1234", "avi@nana.com", "forum1", ch);
		ch.register("forum1", "avielh", "dolev1234", "avi@nana.com");
		
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		ch.finishCommunication();
		ch = null;
		guest = null;
		sp = null;
		m = null;
		sf = null;
		tm = null;
	}
	@Test
	public void loginTest(){
		m = ch.login("forum1", "chenli", "chen1234");
		assertNotNull(m);
		boolean isEqual = (m.getForum()=="forum1" && m.getPassword()=="chen1234" && m.getUserName()=="chenli");
		assertEquals(isEqual, true);
		m = ch.login("wrong", "chenli", "chen1234");
		assertEquals(m, null);
		m = ch.login("forum1", "wrong", "chen1234");
		assertEquals(m, null);
		m = ch.login("forum1", "chenli", "wrong");
		assertEquals(m, null);
	}
	@Test
	public void registerTest(){
		m = ch.login("forum1", "samanta", "sami1234");
		assertNull(m);
		m = ch.register("forum1", "samanta", "ts12", "sami@gmail.com");//too short
		assertNull(m);
		m = ch.register("forum1", "samanta", "1toooooooooooolong", "sami@gmail.com");//too long
		assertNull(m);
		m = ch.register("forum1", "samanta", "samantahor", "sami@gmail.com");//no numeric chars
		assertNull(m);
		m = ch.register("forum1", "samanta", "123456789", "sami@gmail.com");//no alphabetical chars
		assertNull(m);
		m = ch.register("forum1", "samanta", "samnata1234", "sami@gmail.com");//perfect
		assertNotNull(m);
		boolean isEqual = (m.getForum()=="forum1" && m.getPassword()=="samanta" && m.getUserName()=="sami1234");
		assertEquals(isEqual, true);
	}
	@Test
	public void addSubForumTest(){
		Forum fake = new Forum("illegal"); 
		SubForum illegal = new SubForum(fake, "firstAttemp");
		assertFalse(ch.addSubForum(illegal, null));
		SubForum legal = new SubForum(forum1, "succeesfullAttemp");
		assertTrue(ch.addSubForum(legal, null));
	}
	public void getSubForumTest(){
		SubForum sf2 = new SubForum(forum1, "FASHION");
		ch.addSubForum(sf2, null);
		SubForum sf3 = ch.getSubForum("notExist", "FASHION");
		assertNull(sf3);
		sf3 = ch.getSubForum("forum1", "FAMILY");
		assertNull(sf3);
		sf3 = ch.getSubForum("forum1", "FASHION");
		assertNotNull(sf3);
		boolean isEqual = sf2.getForum()==sf3.getForum() && sf2.getSubForumName()==sf3.getSubForumName();
		assertTrue(isEqual);
	}
/*	public void getThreadMessageTest(){
		tm = new ThreadMessage(sf, "Audi", "how much a new Audi A4 cost?", "avielh");
		ch.publishThread(tm);
		ch.getThreadMessage("forum1", "Audi", );
	}*///problem with the thread id
	
}
