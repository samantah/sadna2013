package dbTABLES;

// Generated May 29, 2013 2:33:53 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Server.ForumNotification;

/**
 * Memberdb generated by hbm2java
 */
public class Memberdb implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4019578929884886541L;
	private int idmember;
	private Forumdb forumdb;
	private String userName;
	private String password;
	private String email;
	private String roll;
	private String dateJoin;
	private String notification = "";
	private Set<Forumdb> forumdbs = new HashSet<Forumdb>(0);
	private Set<Postdb> postdbs = new HashSet<Postdb>(0);
	private Set<Threaddb> threaddbs = new HashSet<Threaddb>(0);
	private Set<Subforumdb> subforumdbs = new HashSet<Subforumdb>(0);

	public Memberdb() {
	}

	public Memberdb(int idmember) {
		this.idmember = idmember;
	}
	
	public Memberdb(Forumdb forumdb, String userName,
			String password, String email, String roll, String dateJoin,
			String notification){
		this.forumdb = forumdb;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.roll = roll;
		this.dateJoin = dateJoin;
		this.notification = notification;
		this.forumdbs.add(this.forumdb);
	}

	public Member convertToMember(){
		return new Member(userName, password, email, forumdb.getForumName(), null);
	}
	
	public Moderator convertToModerator(){
		return new Moderator(userName, password, email, forumdb.getForumName(), null);
	}

	public int getIdmember() {
		return this.idmember;
	}

	public void setIdmember(int idmember) {
		this.idmember = idmember;
	}

	public Forumdb getForumdb() {
		return this.forumdb;
	}

	public void setForumdb(Forumdb forumdb) {
		this.forumdb = forumdb;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoll() {
		return this.roll;
	}

	public void setRoll(String roll) {
		this.roll = roll;
	}

	public String getDateJoin() {
		return this.dateJoin;
	}

	public void setDateJoin(String dateJoin) {
		this.dateJoin = dateJoin;
	}

	public String getNotification() {
		return this.notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public Set<Forumdb> getForumdbs() {
		return this.forumdbs;
	}

	public void setForumdbs(Set<Forumdb> forumdbs) {
		this.forumdbs = forumdbs;
	}

	public Set<Postdb> getPostdbs() {
		return this.postdbs;
	}

	public void setPostdbs(Set<Postdb> postdbs) {
		this.postdbs = postdbs;
	}

	public Set<Threaddb> getThreaddbs() {
		return this.threaddbs;
	}

	public void setThreaddbs(Set<Threaddb> threaddbs) {
		this.threaddbs = threaddbs;
	}

	public Set<Subforumdb> getSubforumdbs() {
		return this.subforumdbs;
	}

	public void setSubforumdbs(Set<Subforumdb> subforumdbs) {
		this.subforumdbs = subforumdbs;
	}
	
	public void addNotification(ForumNotification forumNotification) {
		StringBuilder s = new StringBuilder();
		s.append(forumNotification.getText());
		s.append("\0");
		s.append(forumNotification.getNotificationTime());
		s.append("\0");
		this.notification = this.notification + s.toString();	
	}

}
