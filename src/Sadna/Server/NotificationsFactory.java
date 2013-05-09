package Sadna.Server;

import java.util.ArrayList;
import Sadna.Client.Member;
import Sadna.Server.API.ServerInterface;
import Sadna.db.Post;
import Sadna.db.ThreadMessage;

public class NotificationsFactory {

	private ServerInterface _si;

	public NotificationsFactory(ServerInterface si) {
		_si = si;
	}

	public boolean sendNotifications(ThreadMessage tm) {
		String subForumName = tm.getSubForum().getSubForumName();
		String forumName = tm.getSubForum().getForum().getForumName();
		int threadID = tm.getId();
		String txt = "Thread: " + threadID + "in forum: " + forumName + "sub-forum: " + subForumName + "was modified.";
		ArrayList<Post> users = (ArrayList<Post>) _si.getAllPosts(forumName, subForumName, threadID);
		for (Post currPost : users) {
			String memberName = currPost.getPublisher();
			Member currMember = _si.getMember(forumName, memberName);
			if (currMember != null) {
				currMember.addNotification(new ForumNotification(txt));
				_si.addMember(currMember);
			} else {
				return false;
			}
		}
		return true;

	}

	public boolean sendNotifications(Post post) {
		String subForumName = post.getThread().getSubForum().getSubForumName();
		String forumName = post.getThread().getSubForum().getForum().getForumName();
		int threadID = post.getThread().getId();
		String txt = "Thread: " + threadID + "in forum: " + forumName + "sub-forum: " + subForumName + "was modified.";
		ArrayList<Post> users = (ArrayList<Post>) _si.getAllPosts(forumName, subForumName, threadID);
		for (Post currPost : users) {
			String memberName = currPost.getPublisher();
			Member currMember = _si.getMember(forumName, memberName);
			if (currMember != null) {
				currMember.addNotification(new ForumNotification(txt));
				_si.addMember(currMember);
			} else {
				return false;
			}
		}
		return true;

	}
}