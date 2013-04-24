package junitTests.testingClient;

import Sadna.Client.*;

public class ClientTest1 extends ClientTestStartUp {

	//ALL fields need to be valid
	 public static final String FORUM_NAME = "sadnaForum"; //valid forum
	 public static final String USER_NAME  = "sadnaUser";  //valid username
	 public static final String USER_EMAIL  = "sadna@bgu.ac.il";
	 public static final String USER_PASSWORD  = "abcdefg34";
	 public static final String ADMIN_NAME  = "admin1";
	 public static final String ADMIN_PASSWORD  = "admin1";
	 
	 
	 
	/*
	  אבטחה:
	  סיסמא של חבר חייבת להכיל לפחות תו נומרי אחד ותו אלפביתי אחד. כמו כן גודלה
      יהיה בין 8 ל 16 תוים. 
	 */
 
	 
	public void test_RegisterInvalidPass() {
		Member _member1 =  this.bridge.register(FORUM_NAME, USER_NAME, "short", USER_EMAIL);
		assertNull(_member1);
		Member _member2 =  this.bridge.register(FORUM_NAME, USER_NAME, "goodButNoNum", USER_EMAIL);
		assertNull(_member2);
		Member _member3 =  this.bridge.register(FORUM_NAME, USER_NAME, "longVeryVeryVeryLong", USER_EMAIL);
		assertNull(_member3);
		Member _member4 =  this.bridge.register(FORUM_NAME, USER_NAME, "3456", USER_EMAIL);
		assertNull(_member4);
		Member _member5 =  this.bridge.register(FORUM_NAME, USER_NAME, "3456565547456", USER_EMAIL);
		assertNull(_member5);

	}

	
	
	
	

	public void test_RegisterValidPass() {
		Member _member1 =  this.bridge.register(FORUM_NAME, USER_NAME, "1234567k", USER_EMAIL);
		assertNotNull(_member1);
		Member _member2 =  this.bridge.register(FORUM_NAME, USER_NAME, "123456789123456k", USER_EMAIL);
		assertNotNull(_member2);
		Member _member3 =  this.bridge.register(FORUM_NAME, USER_NAME, "abcdefghijklmno4", USER_EMAIL);
		assertNotNull(_member3);
		Member _member4 =  this.bridge.register(FORUM_NAME, USER_NAME, "a3b4c5d6", USER_EMAIL);
		assertNotNull(_member4);

	}


	/*
	ניהול פורום:
		1. לפורום יש מנהל יחיד, שהוא משתמש חבר.
	 */
	public void test_oneAdmin() {
		boolean isAdmin =  this.bridge.initiateForum(FORUM_NAME, ADMIN_NAME, ADMIN_PASSWORD);
		assertTrue(isAdmin);
		boolean isAdmin2 =  this.bridge.initiateForum(FORUM_NAME, "admin2", "validPass2");
		assertFalse(isAdmin2);

	}

	
	
	
	
	
	/*
	 * check logIn
	 */

	public void test_logIn() {
		Member _member1 =  this.bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD,USER_EMAIL);		
		assertNotNull(_member1);
		User _user = this.bridge.login(FORUM_NAME, USER_NAME, USER_PASSWORD);
		boolean isConnect = this.bridge.isConnect(_user);
		assertEquals(true, isConnect);
		
	}
	

	
	

	/*
	 6.  הפורום תומך בריבוי משתמשים בו זמנית Login .
	 */

	public void test_multipleUsersLogin() {
		char a = 'a';
		for (int i = 0; i < 10; i++) {
			Member _member =  this.bridge.login(FORUM_NAME, USER_NAME + a, USER_PASSWORD + a);
			assertNotNull(_member);
			a++;
		}
	}

 
	/*
	 6.  הפורום תומך בריבוי משתמשים בו זמנית Register .
	 */
	
	public void test_multipleUsersRegister() {
		char a = 'a';
		for (int i = 0; i < 10; i++) {
			Member _member =  this.bridge.register(FORUM_NAME, USER_NAME + a, USER_PASSWORD + a, a + USER_EMAIL);
			assertNotNull(_member);
			a++;
		}
	}
	
	
	
	
	/*
	 לכל משתמש יש שם המזהה אותו באופן יחיד בפורום.
	 */
	public void test_sameUserName() {
		Member _member1 =  this.bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD,USER_EMAIL);		
		assertNotNull(_member1);
		Member _member2 =  this.bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD + 'a',USER_EMAIL + 'a');		
		assertNotNull(_member2);
	}
	
	
	/*
	 * check logout
	 */
	public void test_logOut() {
		Member _member1 =  this.bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD,USER_EMAIL);		
		assertNotNull(_member1);
		User _user = this.bridge.logout(FORUM_NAME, USER_NAME);
		boolean isConnect = this.bridge.isConnect(_user);
		assertEquals(false, isConnect); 
		
	}

















	/*	
	public void test_addSubForum() {
		boolean subForum1 = this.bridge.addSubForum(new SubForum("sadna", "sadna"));
		assertTrue(subForum1);
		boolean subForum2 = this.bridge.addSubForum(new SubForum("sadna", "sadna"));
		assertFalse(subForum2);


	}
	 */	

	/*
	public void test_postComment() {
		boolean post1 = this.bridge.postComment(new Post(null, "", "", ""));
		assertFalse(post1);
	  	boolean post2 = this.bridge.postComment(new Post(null, "", "", ""));
		assertFalse(post2);
		assertFalse("Different shows must have different ids !", post1 != post2);

	}
	 */



}
