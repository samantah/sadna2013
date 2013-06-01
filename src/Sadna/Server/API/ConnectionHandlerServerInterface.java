package Sadna.Server.API;

import Sadna.Client.Member;
import Sadna.Server.ForumNotification;
import java.util.HashMap;
import java.util.List;

import dbTABLES.*;


public interface ConnectionHandlerServerInterface{

	public void sendOK();

	public void sendNotFound();

	public void sendErrorInServer();

	public void sendSubForumsList(List<Subforumdb> subForumsList);

	public void sendSubForum(Subforumdb subForum);

	public void sendThreadsList(List<Threaddb> threadsList);

	public void sendThreadMeassage(Threaddb threadMessage);

	public void sendForumsList(List<Forumdb> forumsList);

	public void sendForum(Forumdb forum);

	public String receiveRequestFromClient();

	public void closeSocket();

	public void sendAllPosts(List<Postdb> allPosts);

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

	public void sendAllMembers(List<Memberdb> allMembers);

	public void sendSuperAdminOK();
	
	public void deleteForum(boolean deleteForum);

}

