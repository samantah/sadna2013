package Sadna.Server;

import Sadna.Client.Member;
import Sadna.Server.API.ServerInterface;
import Sadna.db.Forum;
import Sadna.db.PolicyEnums.enumNotiFriends;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFactory {

	private ServerInterface _si;

	public NotificationsFactory(ServerInterface si) {
		_si = si;
	}
    //delete thread notification
	public boolean sendNotifications(ThreadMessage tm){
		String subForumName = tm.getSubForum().getSubForumName();
		Forum forum = tm.getSubForum().getForum();
		int threadID = tm.getId();
		String forumName = forum.getForumName();
		String txt = "Thread: " + threadID + " in forum: " + forumName + " sub-forum: " + subForumName + " was deleted.";
		ArrayList<Member> members;
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.ALLMEMBERS){
			members = (ArrayList<Member>) _si.getAllForumMembers(forumName, null, null);
			for (Member currMember : members) {
				currMember.addNotification(new ForumNotification(txt));
			}
		}
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.PUBLISHERS){
			members = (ArrayList<Member>) getForumMembersWhoPublishInThisSubForum(forumName, subForumName);
			for (Member currMember : members) {
				currMember.addNotification(new ForumNotification(txt));
			}
		}
		return true;
	}
    //delete post / post comment notification
	public boolean sendNotifications(Post post) {
		ThreadMessage tm= post.getThread();
		String subForumName = tm.getSubForum().getSubForumName();
		Forum forum = tm.getSubForum().getForum();
		String forumName = forum.getForumName();
		int threadID = tm.getId();
		String txt = "Thread: " + threadID + " in forum: " + forumName + " sub-forum: " + subForumName + " was modified.";
		ArrayList<Member> members;
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.ALLMEMBERS){
			members = (ArrayList<Member>) _si.getAllForumMembers(forumName, null, null);
			for (Member currMember : members) {
                if(!(post.getPublisher().equals(currMember.getUserName()))){
				currMember.addNotification(new ForumNotification(txt));
                }
			}
		}
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.PUBLISHERS){
			members = (ArrayList<Member>) getForumMembersWhoPublishInThisSubForum(forumName, subForumName);
			for (Member currMember : members) {
                if(!(post.getPublisher().equals(currMember.getUserName()))){
                    currMember.addNotification(new ForumNotification(txt));
                }
			}
		}
		return true;
	}

	private ArrayList<Member> getForumMembersWhoPublishInThisSubForum(
			String forumName, String subForumName) {
		ArrayList<Member> members = new ArrayList<Member>();
		List<ThreadMessage> threadsList = _si.getThreadsList(forumName, subForumName);
		for (ThreadMessage threadMessage : threadsList) {
			String memberAsString = threadMessage.getPublisher();
			Member member = _si.getMember(forumName, memberAsString);
			members.add(member);
		}
		return members;
	}

    //delete sub forum notification
	public boolean sendNotifications(SubForum sf) {
		String txt = "Sub-forum: " + sf.getSubForumName() + " deleted.";
		Forum forum = sf.getForum();
		String forumName = forum.getForumName();
		String subForumName = sf.getSubForumName();
		ArrayList<Member> members;
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.ALLMEMBERS){
			members = (ArrayList<Member>) _si.getAllForumMembers(forumName, null, null);
			for (Member currMember : members) {
				currMember.addNotification(new ForumNotification(txt));
			}
		}
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.PUBLISHERS){
			members = (ArrayList<Member>) getForumMembersWhoPublishInThisSubForum(forumName, subForumName);
			for (Member currMember : members) {
				currMember.addNotification(new ForumNotification(txt));
			}
		}
		return true;
	}
    //publish thread notification
    public boolean sendNotifications(SubForum sf, ThreadMessage tm) {
        Forum forum = sf.getForum();
        String forumName = forum.getForumName();
        String subForumName = sf.getSubForumName();
        String publisherName = tm.getPublisher();
        ArrayList<Member> members;
        String txt = "Thread: " + tm.getId() + " in forum: " + forumName + " sub-forum: " + subForumName + " was added.";
        if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.ALLMEMBERS){
            members = (ArrayList<Member>) _si.getAllForumMembers(forumName, null, null);
            for (Member currMember : members) {
                if(!publisherName.equals(currMember.getUserName())){
                currMember.addNotification(new ForumNotification(txt));
                }
            }
        }
        if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.PUBLISHERS){
            members = (ArrayList<Member>) getForumMembersWhoPublishInThisSubForum(forumName, subForumName);
            for (Member currMember : members) {
                if(!publisherName.equals(currMember.getUserName())){
                    currMember.addNotification(new ForumNotification(txt));
                }
            }
        }
        return true;
    }
}