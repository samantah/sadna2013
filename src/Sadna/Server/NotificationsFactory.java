package Sadna.Server;

import Sadna.Server.API.ServerInterface;
import Sadna.db.PolicyEnums.enumNotiFriends;
import java.util.ArrayList;
import java.util.List;

import dbTABLES.*;

public class NotificationsFactory {

	private ServerInterface _si;

	public NotificationsFactory(ServerInterface si) {
		_si = si;
	}
    //delete thread notification
	public boolean sendNotifications(Threaddb tm){
		String subForumName = tm.getSubforumdb().getSubForumName();
		Forumdb forum = tm.getSubforumdb().getForumdb();
		int threadID = tm.getIdthread();
		String forumName = forum.getForumName();
		String txt = "Thread: " + threadID + " in forum: " + forumName + " sub-forum: " + subForumName + " was deleted.";
		ArrayList<Memberdb> members;
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.ALLMEMBERS){
			members = (ArrayList<Memberdb>) _si.getAllForumMembers(forumName, null, null);
			for (Memberdb currMember : members) {
				currMember.addNotification(new ForumNotification(txt));
                _si.addMember(currMember);
			}
		}
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.PUBLISHERS){
			members = (ArrayList<Memberdb>) getForumMembersWhoPublishInThisSubForum(forumName, subForumName);
			for (Memberdb currMember : members) {
				currMember.addNotification(new ForumNotification(txt));
                _si.addMember(currMember);

            }
		}
		return true;
	}
    //delete post / post comment notification
	public boolean sendNotifications(Postdb post) {
		Threaddb tm= post.getThread();
		String subForumName = tm.getSubforumdb().getSubForumName();
		Forumdb forum = tm.getSubforumdb().getForumdb();
		String forumName = forum.getForumName();
		int threadID = tm.getIdthread();
		String txt = "Thread: " + threadID + " in forum: " + forumName + " sub-forum: " + subForumName + " was modified.";
		ArrayList<Memberdb> members;
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.ALLMEMBERS){
			members = (ArrayList<Memberdb>) _si.getAllForumMembers(forumName, null, null);
			for (Memberdb currMember : members) {
                if(!(post.getPublisher().equals(currMember.getUserName()))){
				    currMember.addNotification(new ForumNotification(txt));
                    _si.addMember(currMember);

                }
			}
		}
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.PUBLISHERS){
			members = (ArrayList<Memberdb>) getForumMembersWhoPublishInThisSubForum(forumName, subForumName);
			for (Memberdb currMember : members) {
                if(!(post.getPublisher().equals(currMember.getUserName()))){
                    currMember.addNotification(new ForumNotification(txt));
                    _si.addMember(currMember);

                }
			}
		}
		return true;
	}

	private ArrayList<Memberdb> getForumMembersWhoPublishInThisSubForum(
			String forumName, String subForumName) {
		ArrayList<Memberdb> members = new ArrayList<Memberdb>();
		List<Threaddb> threadsList = _si.getThreadsList(forumName, subForumName);
		for (Threaddb threadMessage : threadsList) {
			String memberAsString = threadMessage.getMemberdb().getUserName();
			Memberdb member = _si.getMember(forumName, memberAsString);
			members.add(member);
		}
		return members;
	}

    //delete sub forum notification
	public boolean sendNotifications(Subforumdb sf) {
		String txt = "Sub-forum: " + sf.getSubForumName() + " deleted.";
		Forumdb forum = sf.getForumdb();
		String forumName = forum.getForumName();
		String subForumName = sf.getSubForumName();
		ArrayList<Memberdb> members;
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.ALLMEMBERS){
			members = (ArrayList<Memberdb>) _si.getAllForumMembers(forumName, null, null);
			for (Memberdb currMember : members) {
				currMember.addNotification(new ForumNotification(txt));
                _si.addMember(currMember);

            }
		}
		if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.PUBLISHERS){
			members = (ArrayList<Memberdb>) getForumMembersWhoPublishInThisSubForum(forumName, subForumName);
			for (Memberdb currMember : members) {
				currMember.addNotification(new ForumNotification(txt));
                _si.addMember(currMember);

            }
		}
		return true;
	}
    //publish thread notification
    public boolean sendNotifications(Subforumdb sf, Threaddb tm) {
        Forumdb forum = sf.getForumdb();
        String forumName = forum.getForumName();
        String subForumName = sf.getSubForumName();
        String publisherName = tm.getMemberdb().getUserName();
        ArrayList<Memberdb> members;
        String txt = "Thread: " + tm.getIdthread() + " in forum: " + forumName + " sub-forum: " + subForumName + " was added.";
        if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.ALLMEMBERS){
            members = (ArrayList<Memberdb>) _si.getAllForumMembers(forumName, null, null);
            for (Memberdb currMember : members) {
                if(!publisherName.equals(currMember.getUserName())){
                     currMember.addNotification(new ForumNotification(txt));
                     _si.addMember(currMember);

                }
            }
        }
        if(forum.getPolicy().getFriendsNotiPolicy()==enumNotiFriends.PUBLISHERS){
            members = (ArrayList<Memberdb>) getForumMembersWhoPublishInThisSubForum(forumName, subForumName);
            for (Memberdb currMember : members) {
                if(!publisherName.equals(currMember.getUserName())){
                    currMember.addNotification(new ForumNotification(txt));
                    _si.addMember(currMember);

                }
            }
        }
        return true;
    }
}