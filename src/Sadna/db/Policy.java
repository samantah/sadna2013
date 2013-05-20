package Sadna.db;

import Sadna.db.PolicyEnums.enumAssignModerator;
import Sadna.db.PolicyEnums.enumCancelModerator;
import Sadna.db.PolicyEnums.enumDelete;
import Sadna.db.PolicyEnums.enumNotiFriends;
import Sadna.db.PolicyEnums.enumNotiImidiOrAgre;

public class Policy {
	private enumNotiImidiOrAgre imidOrArgeNotiPolicy;
	private enumNotiFriends friendsNotiPolicy;
	private	enumDelete deletePolicy;
	private enumAssignModerator assignModeratorPolicy;
	private enumCancelModerator cancelModeratorPolicy;

	public Policy(enumNotiImidiOrAgre imidOrArgeNotiPolicy, enumNotiFriends friendsNotiPolicy,
			enumDelete deletePolicy, enumAssignModerator assignModeratorPolicy, enumCancelModerator cancelModeratorPolicy){
		this.setImidOrArgeNotiPolicy(imidOrArgeNotiPolicy);
		this.setFriendsNotiPolicy(friendsNotiPolicy);
		this.setDeletePolicy(deletePolicy);
		this.setAssignModeratorPolicy(assignModeratorPolicy);
		this.setCancelModeratorPolicy(cancelModeratorPolicy);
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
	
}
