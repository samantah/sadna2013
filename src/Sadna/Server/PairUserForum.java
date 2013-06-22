package Sadna.Server;

public class PairUserForum {
	private String userName;
	private String forumName;

	public PairUserForum(String userName, String forumName) {
		super();
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PairUserForum))
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
	
	
	
}
