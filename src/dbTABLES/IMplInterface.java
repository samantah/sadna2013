package dbTABLES;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface IMplInterface {

	Forumdb getForum(String forumName);

	Subforumdb getSubForum(String forumName, String subForumName);

	Threaddb getThread(String forumName, String subForumName,
			int threadID);

	Postdb getPost(String forumName, String subForumName,
			int threadID, int postID);

	Memberdb getMember(String forumName, String userName);

	Set<Memberdb> getModerators(String forumName,
			String subForumName);

	Set<Subforumdb> getSubForumsList(String forumName);

	Set<Threaddb> getThreadsList(String forumName,
			String subForumName);

	Set<Postdb> getPostList(String forumName,
			String subForumName, int threadID);

	List<Forumdb> getForumsList();

	List<Memberdb> getAllAdmins();

	int getNumberOfSubforums(String forumName);

	int getNumberOfThreadsInSubForum(String forumName,
			String subForumName);

	int getNumberOfThreadsInForum(String forumName);

	boolean addForum(Forumdb forum);

	// ?? FIRST CREATE THE MEMBERS OR SUBFORUM ??
	// THE SUBFORUM CONTAINS THE MEMBERS
	// CHANGED
	// FROM:
	// boolean addSubForum(Subforumdb subForum, List<Memberdb> listOfModerators);
	// TO:
	boolean addSubForum(Subforumdb subForum);

	// creating subforum ?
	// is there need to create listOfModerators or they exist in database ?
	boolean addSubForum(Subforumdb subForum,
			List<Memberdb> listOfModerators);

	boolean addMember(Memberdb member);

	boolean addPost(Postdb post);

	boolean addThread(Threaddb thread);

	boolean deleteForum(Forumdb forum);

	boolean deleteSubForum(Subforumdb subForum);

	boolean deleteMember(Memberdb member);

	// can do subForum.getMemberdbs().remove(moderatorm);
	// and only update the object
	// SEE updateSubForum(Subforumdb subForum)
	boolean deleteModerator(Memberdb moderatorm,
			Subforumdb subForum);

	boolean deleteModerator(Memberdb moderatorm, String subForum);


	boolean deletePost(Postdb post);

	boolean deleteThread(Threaddb thread);

	boolean addModerator(Memberdb moderator, Subforumdb subForum);

	Set<String> getCommonMembers();

	HashMap<String, Set<String>> getUsersPostToUser(
			String forumName);
	
	int getNumberOfForums();
	
	// String forumName - not in use
	// can operate on member directly
	int getNumberOfUserThreads(String forumName, Memberdb member);
	
	List<Memberdb> getAllMembers(String forumName);
	
	boolean setSuperAdmin(Memberdb superAdmin);
	
	Memberdb getSuperAdmin();
	 
	
	
	// instead of void initiateDataBase();
	// create IMpl impl = new IMpl()
	// when you want to use do impl.openSession
	// and after use do impl.closeSession 
	 
    /* 
    ??????????????????????????????
      
    void deleteFolder(String folder);
    
    ??????????????????????????????
    */
	boolean updateSubForum(Subforumdb subForum);

	boolean updateForum(Forumdb forum);
	
	boolean updateMember(Memberdb member);

}