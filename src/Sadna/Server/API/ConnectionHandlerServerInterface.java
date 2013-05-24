package Sadna.Server.API;

import Sadna.Client.Member;
import Sadna.Server.ForumNotification;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

import java.util.HashMap;
import java.util.List;


public interface ConnectionHandlerServerInterface{

	public void sendOK();

	public void sendNotFound();

	public void sendErrorInServer();

	public void sendSubForumsList(List<SubForum> subForumsList);

	public void sendSubForum(SubForum subForum);

	public void sendThreadsList(List<ThreadMessage> threadsList);

	public void sendThreadMeassage(ThreadMessage threadMessage);

	public void sendForumsList(List<Forum> forumsList);

	public void sendForum(Forum forum);

	public String receiveRequestFromClient();

	public void closeSocket();

	public void sendAllPosts(List<Post> allPosts);

	public void sendErrorNoAuthorized();

	public void sendNotifications(List<ForumNotification> notifications);

	public void sendThreadCounter(int threadCounter);

	public void sendUserThreadsCounter(int numberOfUserThreads);

	public void sendUsersPostToUser(HashMap<String,List<String>> hashMap);

	public void sendIsTheOnlyModeratorInTheSubForum();

	public void sendNumberOfForums(int forumCounter);

	public void sendCommonMembers(List<String> commonMembers);

	public void sendModeratorOK();

	public void sendAdminOK();

	public void sendAllMembers(List<Member> allMembers);

	public void sendSuperAdminOK();
	
	public void deleteForum(boolean deleteForum);

}

