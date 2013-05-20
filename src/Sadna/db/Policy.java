package Sadna.db;

import Sadna.db.PolicyEnums.enumAssignModerator;
import Sadna.db.PolicyEnums.enumCancelModerator;
import Sadna.db.PolicyEnums.enumDelete;
import Sadna.db.PolicyEnums.enumNotiFriends;
import Sadna.db.PolicyEnums.enumNotiImidiOrAgre;
import java.io.Serializable;

public class Policy implements Serializable{
	private enumNotiImidiOrAgre imidOrArgeNotiPolicy;
	private enumNotiFriends friendsNotiPolicy;
	private	enumDelete deletePolicy;
	private enumAssignModerator assignModeratorPolicy;
	private enumCancelModerator cancelModeratorPolicy;
	private int seniority;
	private int minPublish;


	public Policy(enumNotiImidiOrAgre imidOrArgeNotiPolicy, enumNotiFriends friendsNotiPolicy,
			enumDelete deletePolicy, enumAssignModerator assignModeratorPolicy, enumCancelModerator cancelModeratorPolicy,
			int seniority, int minPublish){
		this.setImidOrArgeNotiPolicy(imidOrArgeNotiPolicy);
		this.setFriendsNotiPolicy(friendsNotiPolicy);
		this.setDeletePolicy(deletePolicy);
		this.setAssignModeratorPolicy(assignModeratorPolicy);
		this.setCancelModeratorPolicy(cancelModeratorPolicy);
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
     
}
