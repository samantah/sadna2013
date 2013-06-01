package dbTABLES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javassist.compiler.ast.Member;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;



public class IMpl implements IMplInterface {

	/**
	 * @param args
	 */
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	private Session session = null;



	public IMpl(){
		config();
	}
	

	private void config(){

		try
		{
			Configuration cfg = new Configuration().addResource("dbTABLES/Forumdb.hbm.xml").configure();
			cfg.addResource("dbTABLES/Subforumdb.hbm.xml");
			cfg.addResource("dbTABLES/Memberdb.hbm.xml");
			cfg.addResource("dbTABLES/Postdb.hbm.xml");
			cfg.addResource("dbTABLES/Threaddb.hbm.xml");
			serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
			sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		}
		catch (Throwable ex)
		{
			System.err.println("DBmySqlImpl(config) " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}


	private void openSession(){	
		try{	
			session = sessionFactory.openSession();
			//	Transaction tx = session.beginTransaction();
		}
		catch(Throwable ex)
		{
			System.err.println("DBmySqlImpl(openSession) " + ex);
		}
	}


	private void closeSession(){	
		try{
			session.close();
		}
		catch(Throwable ex)
		{
			System.err.println("DBmySqlImpl(closeSession) " + ex);
		}
	}




	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getForum(java.lang.String)
	 */
	@Override
	public Forumdb getForum(String forumName) {
		Forumdb forum = (Forumdb) session.createCriteria(Forumdb.class).
				add(Restrictions.eq("forumName", forumName)).uniqueResult();	
		//not good
		//Forumdb forum2 = (Forumdb)session.get(Forumdb.class, forumName);
		return forum;
	}

	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getSubForum(java.lang.String, java.lang.String)
	 */
	@Override
	public Subforumdb getSubForum(String forumName, String subForumName) {

		Subforumdb subForum = (Subforumdb) session.createCriteria(Subforumdb.class).
				createAlias("forumdb", "alias1").
				add(Restrictions.eq("subForumName", subForumName)).
				add(Restrictions.eq("alias1.forumName", forumName)).uniqueResult();
		return subForum;
	}


	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getThread(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Threaddb getThread(String forumName, String subForumName,
			int threadID) {

		Threaddb thread = (Threaddb) session.createCriteria(Threaddb.class).
				createAlias("subforumdb", "alias1").
				createAlias("subforumdb.forumdb", "alias2").
				add(Restrictions.eq("alias1.subForumName", subForumName)).
				add(Restrictions.eq("alias2.forumName", forumName)).
				add(Restrictions.eq("idthread", threadID)).
				uniqueResult();

		/*	
		Threaddb thread = (Threaddb) session.createCriteria(Threaddb.class).
				createAlias("subforumdb", "alias1").
				createAlias("subforumdb.forumdb", "alias2").
				add(Restrictions.eq("alias1.subForumName", subForumName)).
				add(Restrictions.eq("alias2.forumName", forumName)).
				list().get(threadID);
		 */

		/*
		Subforumdb subForum = getSubForum(forumName, subForumName);
		if (subForum==null){
			return null;
		}
		Set<Threaddb> threadSet =  subForum.getThreaddbs();
		Threaddb thread = new ArrayList<Threaddb>(threadSet).get(threadID-1);
		 */

		return thread;
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getPost(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public Postdb getPost(String forumName, String subForumName, int threadID,
			int postID) {

		Postdb post = (Postdb) session.createCriteria(Postdb.class).
				createAlias("threaddb", "alias").
				createAlias("threaddb.subforumdb", "alias1").
				createAlias("threaddb.subforumdb.forumdb", "alias2").
				add(Restrictions.eq("alias.idthread", threadID)).
				add(Restrictions.eq("alias1.subForumName", subForumName)).
				add(Restrictions.eq("alias2.forumName", forumName)).
				add(Restrictions.eq("idpost", postID)).
				uniqueResult();

		/*
		Postdb post2 = (Postdb) session.createCriteria(Postdb.class).
				createAlias("threaddb", "alias").
				createAlias("threaddb.subforumdb", "alias1").
				createAlias("threaddb.subforumdb.forumdb", "alias2").
				add(Restrictions.eq("alias.idthread", threadID)).
				add(Restrictions.eq("alias1.subForumName", subForumName)).
				add(Restrictions.eq("alias2.forumName", forumName)).
				list().get(postID);
		 */



		return post;
	}




	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getMember(java.lang.String, java.lang.String)
	 */
	@Override
	public Memberdb getMember(String forumName, String userName) {


		Memberdb member = (Memberdb) session.createCriteria(Memberdb.class).
				createAlias("forumdb", "alias1").
				add(Restrictions.eq("userName", userName)).
				add(Restrictions.eq("alias1.forumName", forumName)).uniqueResult();

		return member;
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getModerators(java.lang.String, java.lang.String)
	 */
	@Override
	public Set<Memberdb> getModerators(String forumName, String subForumName) {
		Subforumdb subForum = this.getSubForum(forumName, subForumName);
		if (subForum==null){
			return null;
		}
		Set<Memberdb> membersSet = null;
		membersSet = subForum.getMemberdbs();
		return membersSet;
	}


	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getSubForumsList(java.lang.String)
	 */
	@Override
	public Set<Subforumdb> getSubForumsList(String forumName) {
		Forumdb forum = this.getForum(forumName);
		if (forum==null){
			return null;
		}
		Set<Subforumdb> subForumsSet = null;
		subForumsSet = forum.getSubforumdbs();
		return subForumsSet;
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getThreadsList(java.lang.String, java.lang.String)
	 */
	@Override
	public Set<Threaddb> getThreadsList(String forumName, String subForumName) {
		Subforumdb subForum = this.getSubForum(forumName, subForumName);
		if (subForum==null){
			return null;
		}
		Set<Threaddb> threadsSet = null;
		threadsSet = subForum.getThreaddbs();
		return threadsSet;
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getPostList(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Set<Postdb> getPostList(String forumName, String subForumName,
			int threadID) {
		Threaddb thread = this.getThread(forumName, subForumName, threadID);
		if (thread==null){
			return null;
		}
		Set<Postdb> postsSet = null;
		postsSet = thread.getPostdbs();
		return postsSet;
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getForumsList()
	 */
	@Override
	public List<Forumdb> getForumsList() {
		List<Forumdb>  forumList =  session.createCriteria(Forumdb.class).list();
		return forumList;
	}





	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getAllAdmins()
	 */
	@Override
	public List<Memberdb> getAllAdmins() {

		List<Memberdb> adminList = null;	
		adminList =	 session.createCriteria(Forumdb.class)
				.setProjection(Projections.property("memberdb")).list();
		return adminList;
	}


	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getNumberOfSubforums(java.lang.String)
	 */
	@Override
	public int getNumberOfSubforums(String forumName) {
		//option1:
		Set<Subforumdb> subForumSET = getSubForumsList(forumName);
		if (subForumSET==null){
			return 0;
		}
		return subForumSET.size();

		//option2:
		/*
		return 	 session.createCriteria(Subforumdb.class).
				createAlias("forumdb", "alias1").
				add(Restrictions.eq("alias1.forumName", forumName)).list().size();
		 */		
	}


	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getNumberOfThreadsInSubForum(java.lang.String, java.lang.String)
	 */
	@Override
	public int getNumberOfThreadsInSubForum(String forumName,
			String subForumName) {
		Set<Threaddb> threadSET = this.getThreadsList(forumName, subForumName);
		if (threadSET==null){
			return 0;
		}
		return threadSET.size();
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getNumberOfThreadsInForum(java.lang.String)
	 */
	@Override
	public int getNumberOfThreadsInForum(String forumName) {
		int p = session.createCriteria(Threaddb.class).
				createAlias("subforumdb", "alias1").
				createAlias("subforumdb.forumdb", "alias2").
				add(Restrictions.eq("alias2.forumName", forumName)).
				list().size();
		return p;
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#addForum(dbTABLES.Forumdb)
	 */
	@Override
	public boolean addForum(Forumdb forum) {
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.save(forum);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(addForum) " + e);
			bool = false;
		}
		return bool;
	}


	// ?? FIRST CREATE THE MEMBERS OR SUBFORUM ??
	// THE SUBFORUM CONTAINS THE MEMBERS
	// CHANGED
	// FROM:
	// boolean addSubForum(Subforumdb subForum, List<Memberdb> listOfModerators);
	// TO:
	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#addSubForum(dbTABLES.Subforumdb)
	 */
	@Override
	public boolean addSubForum(Subforumdb subForum) {
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.save(subForum);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(addSubForum) " + e);
			bool = false;
		}
		return bool;
	}


	// creating subforum ?
	// is there need to create listOfModerators or they exist in database ?
	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#addSubForum(dbTABLES.Subforumdb, java.util.List)
	 */
	@Override
	public boolean addSubForum(Subforumdb subForum, List<Memberdb> listOfModerators){
		subForum.getMemberdbs().addAll(listOfModerators);
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.save(subForum);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(addSubForum) " + e);
			bool = false;
		}
		return bool;
	}


	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#addMember(dbTABLES.Memberdb)
	 */
	@Override
	public boolean addMember(Memberdb member){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.save(member);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(addMember) " + e);
			bool = false;
		}
		return bool;
	}


	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#addPost(dbTABLES.Postdb)
	 */
	@Override
	public boolean addPost(Postdb post){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.save(post);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(addPost) " + e);
			bool = false;
		}
		return bool;
	}

	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#addThread(dbTABLES.Threaddb)
	 */
	@Override
	public boolean addThread(Threaddb thread){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.save(thread);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(addThread) " + e);
			bool = false;
		}
		return bool;
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#deleteForum(dbTABLES.Forumdb)
	 */
	@Override
	public boolean deleteForum(Forumdb forum){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.delete(forum);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(deleteForum) " + e);
			bool = false;
		}
		return bool;
	}


	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#deleteSubForum(dbTABLES.Subforumdb)
	 */
	@Override
	public boolean deleteSubForum(Subforumdb subForum){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.delete(subForum);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(deleteSubForum) " + e);
			bool = false;
		}
		return bool;
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#deleteMember(dbTABLES.Memberdb)
	 */
	@Override
	public boolean deleteMember(Memberdb member){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.delete(member);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(deleteMember) " + e);
			bool = false;
		}
		return bool;
	}


	// can do subForum.getMemberdbs().remove(moderatorm);
	// and only update the object
	// SEE updateSubForum(Subforumdb subForum)
	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#deleteModerator(dbTABLES.Memberdb, dbTABLES.Subforumdb)
	 */
	@Override
	public boolean deleteModerator(Memberdb moderatorm, Subforumdb subForum){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			subForum.getMemberdbs().remove(moderatorm);
			session.saveOrUpdate(subForum);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(deleteModerator) " + e);
			bool = false;
		}
		return bool;
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#deleteModerator(dbTABLES.Memberdb, java.lang.String)
	 */
	@Override
	public boolean deleteModerator(Memberdb moderatorm, String subForum){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();

			//option1:
			Forumdb forum = moderatorm.getForumdb();
			Subforumdb _subforum = this.getSubForum(forum.getForumName(), subForum);

			//option2:
			/*
			Subforumdb _subforum = null;
			Set<Subforumdb> subforumSET  = moderatorm.getSubforumdbs();
			for (Subforumdb sub : subforumSET) {
				if(sub.getSubForumName().equals(subForum)){
					_subforum =  sub;
					break;
				}
			}
			 */

			_subforum.getMemberdbs().remove(moderatorm);
			session.saveOrUpdate(_subforum);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(deleteModerator) " + e);
			bool = false;
		}
		return bool;
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#updateSubForum(dbTABLES.Subforumdb)
	 */
	@Override
	public boolean updateSubForum(Subforumdb subForum){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(subForum);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(updateSubForum) " + e);
			bool = false;
		}
		return bool;
	}


	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#deletePost(dbTABLES.Postdb)
	 */
	@Override
	public boolean deletePost(Postdb post){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.delete(post);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(deletePost) " + e);
			bool = false;
		}
		return bool;
	}


	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#deleteThread(dbTABLES.Threaddb)
	 */
	@Override
	public boolean deleteThread(Threaddb thread){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			session.delete(thread);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(deleteThread) " + e);
			bool = false;
		}
		return bool;
	}


	// could simply return Set<Memberdb> 
	public List<Memberdb> getAllMembers(String forumName){
		Set<Memberdb> memberSET =  this.getForum(forumName).getMemberdbs();
		return new ArrayList<Memberdb>(memberSET);
	}


	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#addModerator(dbTABLES.Memberdb, dbTABLES.Subforumdb)
	 */
	@Override
	public boolean addModerator(Memberdb moderator, Subforumdb subForum){
		boolean bool = true;
		try{
			Transaction tx = session.beginTransaction();
			subForum.getMemberdbs().add(moderator);
			session.saveOrUpdate(subForum);
			tx.commit();
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(addModerator) " + e);
			bool = false;
		}
		return bool;
	}


	// can user member.getThreaddbs().size() whthout calling this method
	// but this lazy method saves time
	// @param  forumName - not in use
	public int getNumberOfUserThreads(String forumName, Memberdb member){ //returns the number of threads that the user write in the specified forum.
		return member.getThreaddbs().size();
	}


	public int getNumberOfForums(){  // super admin method - returns number of forums.
		return this.getForumsList().size();
	}


	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getCommonMembers()
	 */
	@Override
	public Set<String> getCommonMembers(){ // returns list of users names that registered to more than one forum.
		ArrayList<String> stringARRAY = null;
		final Set<String> setToReturn = new HashSet();
		final Set<String> set1 = new HashSet();
		try{
			stringARRAY =  (ArrayList<String>) session.createCriteria(Memberdb.class).
					setProjection(Projections.property("userName")).list();
			for (String myString : stringARRAY)
			{
				if (!set1.add(myString))
				{
					setToReturn.add(myString);
				}
			}
		}
		catch(Exception e){
			System.out.println("DBmySqlImpl(getCommonMembers) " + e);
		}
		return setToReturn;
	}



	/* (non-Javadoc)
	 * @see dbTABLES.IMplInterface#getUsersPostToUser(java.lang.String)
	 */
	@Override
	public HashMap<String, Set<String>> getUsersPostToUser(String forumName){ //returns for each member in this forum list that his user name in the first index and in the other indices  the users names of the members that post comments for his threads. 

		HashMap<String, Set<String>> hash = new HashMap<String, Set<String>>();
		Set<String> stringSET = new HashSet<String>();
		String memberName = null; 

		List<Memberdb> MemberLIST= this.getAllMembers(forumName);
		for (Memberdb member : MemberLIST) {
			memberName = member.getUserName();
			for (Threaddb thread : member.getThreaddbs()) {
				for (Postdb post : thread.getPostdbs()) {	
					stringSET.add(post.getMemberdb().getUserName());
				}
			}
			hash.put(memberName, stringSET);
			stringSET = new HashSet<String>();
			memberName = null; 
		}
		return hash;
	}


	@Override
	public boolean setSuperAdmin(Memberdb superAdmin) {
		return this.addMember(superAdmin);
	}

	@Override
	public Memberdb getSuperAdmin() {
		Memberdb member = (Memberdb) session.createCriteria(Memberdb.class).
				add(Restrictions.eq("roll", "SuperAdmin")).
				uniqueResult();
		return member;
	}




	public static void main(String[] args) {

		IMpl impl =  new IMpl();
		impl.openSession();


















		//example1:
		/*
		Forumdb forum = impl.getForum("forum1");
		System.out.println(forum);
	    System.out.println("out " + forum.getIdforum());
		 */


		//example2:
		/*
		Subforumdb subForum = impl.getSubForum("forum1", "subforum1");
		System.out.println(subForum.getSubForumName());
		 */ 


		//example3:
		/*
		Threaddb thread = impl.getThread("forum1", "subforum1", 1);
		System.out.println(thread);
		System.out.println(thread.getContent());
		 */



		//example4
		/*
		Postdb post = impl.getPost("forum1", "subforum1", 1, 1);
		System.out.println(post);
		//System.out.println(post.getContent());
		//System.out.println(post.getPublisher());
		 */





		//example5
		/*
		Memberdb member = impl.getMember("forum1", "yossi");
		System.out.println(member);
		System.out.println(member.getEmail());
		 */


		//example6
		/*
		Set<Memberdb> members = impl.getModerators("forum1", "subforum1");
		System.out.println(members);
		for (Memberdb member : members) {
				System.out.println(member.getEmail());
		}
		 */





		//example7
		/*
		Set<Subforumdb> subForums = impl.getSubForumsList("forum1");
		System.out.println(subForums);
		for (Subforumdb subForum : subForums) {
				System.out.println(subForum.getSubForumName());
		}
		 */



		//example8
		/*
		Set<Threaddb> threads = impl.getThreadsList("forum1", "subforum1");
		System.out.println(threads);
		for (Threaddb thread : threads) {
				System.out.println(thread.getContent());
		}
		 */



		//example9
		/*
		Set<Postdb> posts = impl.getPostList("forum1", "subforum1", 1);
		System.out.println(posts);
		for (Postdb post : posts) {
				System.out.println(post.getContent());
		}
		 */


		//example10
		/*
		List<Forumdb> forumList = impl.getForumsList();
		System.out.println(forumList);
		for (Forumdb forum : forumList) {
				System.out.println(forum.getForumName());
		}
		 */


		//example11
		/*
		List<Memberdb> adminList = impl.getAllAdmins();
		System.out.println(adminList);
		for (Memberdb admin : adminList) {
			System.out.println(admin);
		}
		 */



		//example12
		//System.out.println("out " + impl.getNumberOfSubforums("forum14"));




		//example13
		//System.out.println(impl.getNumberOfThreadsInSubForum("forum1", "subforum1"));



		//example14
		/*
		System.out.println(impl.getNumberOfThreadsInForum("forum1"));
		 */


		//example15
		/*
		Forumdb forum = new Forumdb();
		forum.setForumName("forum1");
		//forum.setIdforum(6); -- not needed if we have auto increment 
		forum.setMemberdb(impl.getMember("forum1", "user1"));
		System.out.println(impl.addForum(forum));
		 */


		//example16
		/*
		Subforumdb subForum = new Subforumdb();
		subForum.setForumdb(impl.getForum("forum1"));
		subForum.setSubForumName("subforum2");
		System.out.println(impl.addSubForum(subForum));
		 */




		//example17.1
		/*
		Subforumdb subForum = new Subforumdb();
		subForum.setForumdb(impl.getForum("forum1"));
		subForum.setSubForumName("subforum3");
		System.out.println(impl.addSubForum(subForum));
		System.out.println(impl.addSubForum(impl.getSubForum("forum1", "subforum3"), 
				impl.getAllAdmins()));
		 */


		//example17.2
		/*
		Subforumdb subForum = new Subforumdb();
		subForum.setForumdb(impl.getForum("forum1"));
		subForum.setSubForumName("subforum4");
		System.out.println(impl.addSubForum(subForum));
		System.out.println(impl.addSubForum(subForum, 
				impl.getAllAdmins()));
		 */



		//example17.3
		/*
		Subforumdb subForum = new Subforumdb();
		subForum.setForumdb(impl.getForum("forum1"));
		subForum.setSubForumName("subforum6");
		System.out.println(impl.addSubForum(subForum));
		subForum.getMemberdbs().addAll(impl.getModerators("forum1", "subforum3"));
		System.out.println(impl.addSubForum(subForum));
		 */

		//example17.4
		/*
		Subforumdb subForum = new Subforumdb();
		subForum.setForumdb(impl.getForum("forum1"));
		subForum.setSubForumName("subforum6");
		subForum.getMemberdbs().addAll(impl.getModerators("forum1", "subforum3"));
		System.out.println(impl.addSubForum(subForum));
		 */

		//example17.5
		/*
		Subforumdb subForum = new Subforumdb();
		subForum.setForumdb(impl.getForum("forum1"));
		subForum.setSubForumName("subforum7");
		System.out.println(impl.addSubForum(subForum, impl.getAllAdmins()));
		 */

		//example18
		/*
		Memberdb member = new Memberdb();
		member.setEmail("emailuser2");
		member.setForumdb(impl.getForum("forum1"));
		member.setUserName("user2");
		System.out.println(impl.addMember(member));
		 */


		//example19.1
		/*
		Postdb post = new Postdb();
		post.setMemberdb(impl.getMember("forum1", "user1"));
		post.setThreaddb(impl.getThread("forum1", "subforum1", 1));
		post.setContent("post18");
		System.out.println(impl.addPost(post));
		 */


		//example19.2
		/*
		// "forum1111" - update the post without member(publisher)
		Postdb post = new Postdb();
		post.setMemberdb(impl.getMember("forum1111", "user1"));
		post.setThreaddb(impl.getThread("forum1", "subforum1", 1));
		post.setContent("post18");
		System.out.println(impl.addPost(post));
		 */



		//example20
		/*
		// "forum1111" - update the post without member(publisher)
		Threaddb thread = new Threaddb();
		thread.setMemberdb(impl.getMember("forum1", "user1"));
		thread.setSubforumdb(impl.getSubForum("forum1", "subforum1"));
		thread.setContent("threadcontent20");
		System.out.println(impl.addThread(thread));
		 */






		//example21:
		// need to add cascade to forien key to all the tables in mysql
		/*
		Forumdb forum = impl.getForum("forum1");
		System.out.println(impl.deleteForum(forum));
		 */


		//example22:
		// need to add cascade to forien key to all the tables in mysql
		/*
		Subforumdb subForum = impl.getSubForum("forum1", "subforum1");
		System.out.println(impl.deleteSubForum(subForum));
		 */


		//example23:
		/*
		// need to add cascade to forien key to all the tables in mysql
		// all posts and threads of this member deleted 
		Memberdb member = impl.getMember("forum1", "user1");
		System.out.println(impl.deleteMember(member));
		 */


		//example24:
		/*
		Memberdb moderatorm = impl.getMember("forum1", "user1");
		Subforumdb subforum = impl.getSubForum("forum1", "subforum1");
		System.out.println(impl.deleteModerator(moderatorm, subforum));
		 */


		//example25:
		/*
		Memberdb moderatorm = impl.getMember("forum1", "user1");
		System.out.println(impl.deleteModerator(moderatorm, "subforum1"));
		 */

		//example27:
		/*
		Postdb post = impl.getPost("forum1", "subforum1", 1, 1);
		System.out.println(impl.deletePost(post));
		 */

		//example28:
		/*
		System.out.println(impl.getAllMembers("forum1").size());
		 */


		//example29:
		/*
		Memberdb moderatorm = impl.getMember("forum1", "user1");
		Subforumdb subforum = impl.getSubForum("forum2", "subforum2"); 
		System.out.println(impl.addModerator(moderatorm, subforum));
		 */



		//example30:
		/*
		Memberdb member = impl.getMember("forum1", "user1");
		System.out.println(impl.getNumberOfUserThreads("forum1_NOT_IN_USE", member));
		 */


		//example31:
		/*
		System.out.println(impl.getCommonMembers());
		 */



		//example32:
		/*
		Memberdb superAdmin = new Memberdb();
		superAdmin.setEmail("emailuser3");
		superAdmin.setUserName("user3");
		superAdmin.setRoll("SuperAdmin");
		System.out.println(impl.setSuperAdmin(superAdmin));
		 */

		//example33:
		//System.out.println(impl.getSuperAdmin().getEmail());





		impl.closeSession();


	}





}





















/*
 * 		IMpl impl =  new IMpl();
		impl.openSession();

		//this.openSession();
		Forumdb forum = (Forumdb)impl.session.get(Forumdb.class, 1);
		System.out.println("inside " + forum.getForumName());
		//this.closeSession();
		//impl.closeSession();




		//impl.openSession();
		Transaction tx = impl.session.beginTransaction();
		Subforumdb subForum = new Subforumdb(5, forum, "shlomi");

		impl.session.save(subForum);
		tx.commit();
		impl.closeSession();
 */

/*
 * 
 * 		
		Forumdb forum = (Forumdb)impl.session.get(Forumdb.class, 1);
		System.out.println("inside " + forum.getForumName());

		//impl.openSession();
		Transaction tx = impl.session.beginTransaction();
		Subforumdb subForum = new Subforumdb(forum, "subforum2", null, null);

		//Subforumdb subForum2 = new Subforumdb();
		//subForum2.setForumdb(forum);
		//subForum2.setSubForumName("subforum7");

		impl.session.save(subForum);
		tx.commit();
		impl.closeSession();

 */