package Sadna.db;

import Sadna.db.PolicyEnums.*;

import java.io.Serializable;

public class Policy implements Serializable{
	private enumNotiImidiOrAgre imidOrArgeNotiPolicy;
	private enumNotiFriends friendsNotiPolicy;
	private	enumDelete deletePolicy;
	private enumAssignModerator assignModeratorPolicy;
	private enumCancelModerator cancelModeratorPolicy;
	private enumMessageContent messageContentPolicy;	
	private enumModeratorPermissions moderatorPermissionsPolicy;
	private enumSecurity emailPolicy;
	private int seniority;
	private int minPublish;


	public Policy(enumNotiImidiOrAgre imidOrArgeNotiPolicy, enumNotiFriends friendsNotiPolicy,
			enumDelete deletePolicy, enumAssignModerator assignModeratorPolicy, enumCancelModerator cancelModeratorPolicy,
			enumMessageContent messageContentPolicy, enumModeratorPermissions moderatorPermissionsPolicy,
			enumSecurity emailPolicy, int seniority, int minPublish){
		this.setImidOrArgeNotiPolicy(imidOrArgeNotiPolicy);
		this.setFriendsNotiPolicy(friendsNotiPolicy);
		this.setDeletePolicy(deletePolicy);
		this.setAssignModeratorPolicy(assignModeratorPolicy);
		this.setCancelModeratorPolicy(cancelModeratorPolicy);
		this.setMessageContentPolicy(messageContentPolicy);
		this.setModeratorPermissionsPolicy(moderatorPermissionsPolicy);
		this.setEmailPolicy(emailPolicy);
		this.setSeniority(seniority);
		this.setMinPublish(minPublish);
	}

	public void setCancelModeratorPolicy(enumCancelModerator cancelModeratorPolicy) {
		this.cancelModeratorPolicy = cancelModeratorPolicy;
	}

	public enumCancelModerator getCancelModeratorPolicy() {
		return cancelModeratorPolicy;
	}

	public void setAssignModeratorPolicy(enumAssignModerator assignModeratorPolicy) {
		this.assignModeratorPolicy = assignModeratorPolicy;
	}

	public enumAssignModerator getAssignModeratorPolicy() {
		return assignModeratorPolicy;
	}

	public void setDeletePolicy(enumDelete deletePolicy) {
		this.deletePolicy = deletePolicy;
	}

	public enumDelete getDeletePolicy() {
		return deletePolicy;
	}

	public void setFriendsNotiPolicy(enumNotiFriends friendsNotiPolicy) {
		this.friendsNotiPolicy = friendsNotiPolicy;
	}

	public enumNotiFriends getFriendsNotiPolicy() {
		return friendsNotiPolicy;
	}

	public void setImidOrArgeNotiPolicy(enumNotiImidiOrAgre imidOrArgeNotiPolicy) {
		this.imidOrArgeNotiPolicy = imidOrArgeNotiPolicy;
	}

	public enumNotiImidiOrAgre getImidOrArgeNotiPolicy() {
		return imidOrArgeNotiPolicy;
	}

	public void setMinPublish(int minPublish) {
		this.minPublish = minPublish;
	}

	public int getMinPublish() {
		return minPublish;
	}

	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}

	public int getSeniority() {
		return seniority;
	}

	public enumMessageContent getMessageContentPolicy() {
		return messageContentPolicy;
	}

	public void setMessageContentPolicy(enumMessageContent messageContentPolicy) {
		this.messageContentPolicy = messageContentPolicy;
	}

	public enumModeratorPermissions getModeratorPermissionsPolicy() {
		return moderatorPermissionsPolicy;
	}

	public void setModeratorPermissionsPolicy(enumModeratorPermissions moderatorPermissionsPolicy) {
		this.moderatorPermissionsPolicy = moderatorPermissionsPolicy;
	}

	public enumSecurity getEmailPolicy() {
		return emailPolicy;
	}

	public void setEmailPolicy(enumSecurity emailPolicy) {
		this.emailPolicy = emailPolicy;
	}

    @Override
    public String toString() {
        return "Policy\n" + "imidOrArgeNotiPolicy=" + imidOrArgeNotiPolicy + "\n friendsNotiPolicy=" + friendsNotiPolicy + "\n deletePolicy=" + deletePolicy + "\n" + " assignModeratorPolicy=" + assignModeratorPolicy + "\n cancelModeratorPolicy=" + cancelModeratorPolicy + "\n messageContentPolicy=" + messageContentPolicy + "\n moderatorPermissionsPolicy=" + moderatorPermissionsPolicy + "\n emailPolicy=" + emailPolicy + "\n seniority=" + seniority + "\n minPublish=" + minPublish;
    }
        
        
     
}
