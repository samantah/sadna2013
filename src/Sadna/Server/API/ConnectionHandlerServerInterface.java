package Sadna.Server.API;

import java.util.List;

import Sadna.db.Forum;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;


public interface ConnectionHandlerServerInterface extends Runnable{

	public void sendOK();

	public void sendNotFound();

	public void sendErrorInServer();

	public void sendSubForumsList(List<SubForum> subForumsList);

	public void sendSubForum(SubForum subForum);

	public void sendThreadsList(List<ThreadMessage> threadsList);

	public void sendThreadMeassage(ThreadMessage threadMessage);

	public void sendForumsList(List<Forum> forumsList);

	public void sendForum(Forum forum);
}

