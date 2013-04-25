package junitTests.testingServer;

import Sadna.Client.*;
import Sadna.db.Forum;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;


public class ServerTest1 extends junitTests.testingServer.ServerTestStartUp {

	//ALL fields need to be valid
	 public static final String FORUM_NAME = "forum1"; //valid forum
	 public static final String USER_NAME  = "sadnaUser";  //valid username
	 public static final String USER_EMAIL  = "sadna@bgu.ac.il";
	 public static final String USER_PASSWORD  = "abcdefg34";
	 public static final String ADMIN_NAME  = "admin1";
	 public static final String ADMIN_PASSWORD  = "admin1";
	 public static final String SUB_FORUM_NAME = "sadnaSubForum"; //valid forum
	 
	 
	 
	 
	 /*
	  * 3. שירותי ניהול פורומים:
1. בדיקה שתוכן הודעה-ראשונה  תואמות את נושא תת-הפורום.
	  */
	 
		public void test_matchContent() {
			//the initiate of data can be done here or in realBridge class
			Forum _forum = new Forum(FORUM_NAME);
			SubForum _subForum = new SubForum(_forum, SUB_FORUM_NAME);
			bridge.addSubForum(_subForum);
			bridge.publishThread(new ThreadMessage(_subForum, "1", "2", "3"));
			assertEquals(_subForum.getSubForumName(), "1");
		}
	  

	 
	 /*
	  * גדול תוכן ההודעה מוגבל עד 1000 תווים (למניעת העמסה).
	  */
	 
		
		public void test_messageSize() {
			//the initiate of data can be done here or in realBridge class
			Forum _forum = new Forum(FORUM_NAME);
			SubForum _subForum = new SubForum(_forum, SUB_FORUM_NAME);
			bridge.addSubForum(_subForum);
			String content1 = "12345678"; 
			boolean isTooBig1 = bridge.publishThread(new ThreadMessage(_subForum, "1", content1, "3"));
			assertFalse(isTooBig1);
			
			String content2 = "12345678";
			for (int i = 0; i < 1020; i++) {
				 content2 += "1";
			}
			
			boolean isTooBig2 = bridge.publishThread(new ThreadMessage(_subForum, "1", content2, "3"));
			assertTrue(isTooBig2);
			
			
		}
		
		/*
		 * check registration
		 */
		public void test_registration() {
			//the initiate of data can be done here or in realBridge class
//			Member _member1 = bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD, USER_EMAIL);
//			assertNotNull(_member1);
//			Member _member2 = bridge.login(FORUM_NAME, USER_EMAIL, USER_PASSWORD);
//			assertNotNull(_member2);
			
		}




/*
 * תת-פורום:
אחד, ואולי יותר. (moderator) 2. לתת-פורום יש לפחות מנחה
3. מנחה של תת-פורום חייב להיות חבר של תת-הפורום.
4. למנחה יש לפחות תת-פורום אחד שאותו הוא מנחה.




/*
 * הפורום תומך בריבוי משתמשים בו זמנית.
 */
		public void test_multipleUsersLogin() {
			char a = 'a';
			for (int i = 0; i < 10; i++) {
//				Member _member =  this.bridge.login(FORUM_NAME, USER_NAME + a, USER_PASSWORD + a);
//				assertNotNull(_member);
//				a++;
			}
		}

		
		/*
		 6.  הפורום תומך בריבוי משתמשים בו זמנית Register .
		 */
		
		public void test_multipleUsersRegister() {
			char a = 'a';
			for (int i = 0; i < 10; i++) {
//				Member _member =  this.bridge.register(FORUM_NAME, USER_NAME + a, USER_PASSWORD + a, a + USER_EMAIL);
//				assertNotNull(_member);
//				a++;
			}
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
	 לכל משתמש יש שם המזהה אותו באופן יחיד בפורום.
	 */
	public void test_sameUserName() {
//		Member _member1 =  this.bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD,USER_EMAIL);		
//		assertNotNull(_member1);
//		Member _member2 =  this.bridge.register(FORUM_NAME, USER_NAME, USER_PASSWORD + 'a',USER_EMAIL + 'a');		
//		assertNotNull(_member2);
	}
	
	



	
	
	
	
	
	


}
