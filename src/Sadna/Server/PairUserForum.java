package Sadna.Server;

import java.util.Objects;

public class PairUserForum {
	private String userName;
	private String forumName;

	public PairUserForum(String userName, String forumName) {
		this.userName = userName;
		this.forumName = forumName;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}
	
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((forumName == null) ? 0 : forumName.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PairUserForum other = (PairUserForum) obj;
		if (forumName == null) {
			if (other.forumName != null)
				return false;
		} else if (!forumName.equals(other.forumName))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PairUserForum [userName=" + userName + ", forumName="
				+ forumName + "]";
	}
    
	
	
	
}
