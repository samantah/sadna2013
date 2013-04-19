package Sadna.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import Sadna.Client.ConnectionHandler;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.User;
import Sadna.Server.API.ConnectionHandlerServerInterface;
import Sadna.Server.API.ServerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import Sadna.db.API.DBInterface;

public class ServerImpl implements ServerInterface {

	private DBInterface _db;
	private ConnectionHandlerServerInterface _ch;

	public ServerImpl(DBInterface db, ConnectionHandlerServerInterface ch){
		_db = db;
		_ch = ch;
	}

	@Override
	public boolean register(String forumName, String userName, String password, String email,
			ConnectionHandler ch) {
		boolean isAdded = false;
		if(isUnique(forumName, userName)){
			isAdded = _db.addMember(new Member(userName, password, email, forumName, ch));
			if(isAdded)
				_ch.sendOK();
		}
		return isAdded;
	}

	private boolean isUnique(String forumName, String userName) {
		boolean isUnique = true;
		List<Member> members = _db.getAllMembers(forumName);
		for(Member m: members){
			if(m.getUserName()==userName){
				isUnique = false;
			}
		}
		return isUnique;
	}


	@Override
	public boolean login(String forumName, String userName, String password) {
		boolean succeeded = false;
		Member loginner = _db.getMember(forumName, userName);
		if(loginner!=null && loginner.getPassword()==password){
			succeeded = true;
			_ch.sendOK();
		}
		return succeeded;
	}


	@Override
	public boolean publishThread(ThreadMessage thread) {
		boolean succeeded = false;
		succeeded = _db.addThread(thread);
		if(succeeded)
			_ch.sendOK();
		return succeeded;
	}


	@Override
	public SubForum getSubForum(String forumName, String subForumName) {
		
		return null;
	}


	@Override
	public List<SubForum> getSubForumsList(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<ThreadMessage> getThreadsList(String forumName,
			String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ThreadMessage getThreadMessage(String forumName,
			String subForumName, String threadMessage) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Forum initiateForum(String adminUserName, String adminPassword,
			String forumName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean addSubForum(SubForum subForum) {
		boolean succeeded = false;
		succeeded = _db.addSubForum(subForum);
		if(succeeded)
			_ch.sendOK();
		return succeeded;
	}


	@Override
	public boolean postComment(Post comment) {
		boolean posted = false;
		posted = _db.addPost(comment);
		if(posted)
			_ch.sendOK();
		return posted;
	}

}