package dbTABLES;

// Generated May 29, 2013 2:33:53 AM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import Sadna.db.Forum;

/**
 * Forumdb generated by hbm2java
 */
public class Forumdb implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1162767034713145063L;
	private Integer idforum;
	private Memberdb memberdb;
	private String forumName;
	private String enumNotiImidiOrAgre;
	private String enumNotiFriends;
	private String enumDelete;
	private String enumAssignModerator;
	private String enumCancelModerator;
	private Integer seniority;
	private Integer minPublish;
	private String forbiddenWords;
	private Set<Memberdb> memberdbs = new HashSet<Memberdb>(0);
	private Set<Subforumdb> subforumdbs = new HashSet<Subforumdb>(0);

	public Forumdb() {
	}
	
	public Forumdb(String forumName,
			String enumNotiImidiOrAgre, String enumNotiFriends,
			String enumDelete, String enumAssignModerator,
			String enumCancelModerator, Integer seniority, Integer minPublish, String forbiddenWords){
		this.forumName = forumName;
		this.enumNotiImidiOrAgre = enumNotiImidiOrAgre;
		this.enumNotiFriends = enumNotiFriends;
		this.enumDelete = enumDelete;
		this.enumAssignModerator = enumAssignModerator;
		this.enumCancelModerator = enumCancelModerator;
		this.seniority = seniority;
		this.minPublish = minPublish;
		this.forbiddenWords = forbiddenWords;
	}

	public Forumdb(Memberdb memberdb, String forumName,
			String enumNotiImidiOrAgre, String enumNotiFriends,
			String enumDelete, String enumAssignModerator,
			String enumCancelModerator, Integer seniority, Integer minPublish,
			Set<Memberdb> memberdbs, Set<Subforumdb> subforumdbs) {
		this.memberdb = memberdb;
		this.forumName = forumName;
		this.enumNotiImidiOrAgre = enumNotiImidiOrAgre;
		this.enumNotiFriends = enumNotiFriends;
		this.enumDelete = enumDelete;
		this.enumAssignModerator = enumAssignModerator;
		this.enumCancelModerator = enumCancelModerator;
		this.seniority = seniority;
		this.minPublish = minPublish;
		this.memberdbs = memberdbs;
		this.subforumdbs = subforumdbs;
	}

	public Forum convertToForum(){
		return new Forum(forumName, null);
	}
	
	public Integer getIdforum() {
		return this.idforum;
	}

	public void setIdforum(Integer idforum) {
		this.idforum = idforum;
	}

	public Memberdb getMemberdb() {
		return this.memberdb;
	}

	public void setMemberdb(Memberdb memberdb) {
		this.memberdb = memberdb;
	}

	public String getForumName() {
		return this.forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public String getEnumNotiImidiOrAgre() {
		return this.enumNotiImidiOrAgre;
	}

	public void setEnumNotiImidiOrAgre(String enumNotiImidiOrAgre) {
		this.enumNotiImidiOrAgre = enumNotiImidiOrAgre;
	}

	public String getEnumNotiFriends() {
		return this.enumNotiFriends;
	}

	public void setEnumNotiFriends(String enumNotiFriends) {
		this.enumNotiFriends = enumNotiFriends;
	}

	public String getEnumDelete() {
		return this.enumDelete;
	}

	public void setEnumDelete(String enumDelete) {
		this.enumDelete = enumDelete;
	}

	public String getEnumAssignModerator() {
		return this.enumAssignModerator;
	}

	public void setEnumAssignModerator(String enumAssignModerator) {
		this.enumAssignModerator = enumAssignModerator;
	}

	public String getEnumCancelModerator() {
		return this.enumCancelModerator;
	}

	public void setEnumCancelModerator(String enumCancelModerator) {
		this.enumCancelModerator = enumCancelModerator;
	}

	public Integer getSeniority() {
		return this.seniority;
	}

	public void setSeniority(Integer seniority) {
		this.seniority = seniority;
	}

	public Integer getMinPublish() {
		return this.minPublish;
	}

	public void setMinPublish(Integer minPublish) {
		this.minPublish = minPublish;
	}

	public Set<Memberdb> getMemberdbs() {
		return this.memberdbs;
	}

	public void setMemberdbs(Set<Memberdb> memberdbs) {
		this.memberdbs = memberdbs;
	}

	public Set<Subforumdb> getSubforumdbs() {
		return this.subforumdbs;
	}

	public void setSubforumdbs(Set<Subforumdb> subforumdbs) {
		this.subforumdbs = subforumdbs;
	}

	public String getForbiddenWords() {
		return forbiddenWords;
	}

	public void setForbiddenWords(String forbiddenWords) {
		this.forbiddenWords = forbiddenWords;
	}
	
	public List<String> forbiddenWordsStringToList(){
		List retVal = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(this.forbiddenWords);
		while (st.hasMoreTokens()){
			String s = st.nextToken();
			retVal.add(s);
		}
		return retVal;
	}

}
