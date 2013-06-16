package unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Sadna.Client.ClientConnectionHandler;

public class UnitTestsForServer {
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

}
