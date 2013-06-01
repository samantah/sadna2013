package Sadna.Server;


import Sadna.Server.API.ServerInterface;
import Sadna.db.PolicyEnums.enumAssignModerator;
import Sadna.db.PolicyEnums.enumCancelModerator;
import Sadna.db.PolicyEnums.enumDelete;
import Sadna.db.PolicyEnums.enumNotiFriends;
import Sadna.db.PolicyEnums.enumNotiImidiOrAgre;
import  dbTABLES.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ServerToDataBaseHandler implements ServerInterface {

    private IMplInterface _db;

    public ServerToDataBaseHandler(IMplInterface db) {
        _db = db;
    }
    

    @Override
    public boolean register(String forumName, String userName, String password, String email) {
        boolean isAdded = false;
        if (forumExists(forumName)) {
            //System.out.println("is unique forum");
            if (isUserNameUnique(forumName, userName)) {
                //System.out.println("is unique username");
            	Forumdb forum = this._db.getForum(forumName);
                Date now = new Date();
                long nowAsLong = now.getTime();
                String nowAsString = String.valueOf(nowAsLong);
                Memberdb toAdd = new Memberdb(forum, userName, password, email, "Member", nowAsString, "");
                isAdded = _db.addMember(toAdd);
            }
        }
        return isAdded;
    }

    private boolean isUserNameUnique(String forumName, String userName) {
        boolean isUnique = true;
        List<Memberdb> members = _db.getAllMembers(forumName);
        if (members != null) {
            for (Memberdb m : members) {
                if (m.getUserName().equals(userName)) {
                    isUnique = false;
                    break;
                }
            }
        }
        return isUnique;
    }

    @Override
    public boolean memberExistsInForum(String forumName, String posterName) {
        return userNameExist(forumName, posterName);
    }

    private boolean userNameExist(String forumName, String userName) {
        boolean exist = false;
        List<Memberdb> members = _db.getAllMembers(forumName);
        if (members != null) {
            for (Memberdb m : members) {
                if (m.getUserName().equals(userName)) {
                    exist = true;
                    break;
                }
            }
        }
        return exist;
    }

    @Override
    public boolean login(String forumName, String userName, String password) {
        boolean succeeded = false;
        if (forumExists(forumName)) {
            Memberdb loginner = _db.getMember(forumName, userName);
            if (loginner != null && Encryptor.checkPassword(password, loginner.getPassword())) {
                succeeded = true;
            }
        }
        return succeeded;
    }

    /*	For later use..

     @Override
     public boolean logout(String forumName, String userName) {
     boolean succeeded = false;
     Member loginner = _db.getMember(forumName, userName);
     if(loginner!=null){
     succeeded = true;
     }
     return succeeded;
     }
     */
    private boolean isSuperAdmin(String superAdminUserName,
            String superAdminPassword) {
        Memberdb sa = _db.getSuperAdmin();
        return (sa.getUserName().equals(superAdminUserName)
                && Encryptor.checkPassword(superAdminPassword,sa.getPassword()));
    }

    private boolean isForumNameUnique(String forumName) {
        boolean isUnique = true;
        List<Forumdb> forums = _db.getForumsList();
        for (Forumdb f : forums) {
            if (f.getForumName().equals(forumName)) {
                isUnique = false;
                break;
            }
        }
        return isUnique;
    }

    @Override
    public boolean addSubForum(Subforumdb subForum, List<Memberdb> members, String userName, String password) {
        boolean isAdded = false;
        Memberdb member = _db.getMember(subForum.getForumdb().getForumName(), userName);
        if (subForum.getForumdb() != null && Encryptor.checkPassword(password, member.getPassword())) {
            if (isSubForumNameUnique(subForum.getForumdb().getForumName(),
                    subForum.getSubForumName())) {
                isAdded = _db.addSubForum(subForum, members);
            }
        }
        return isAdded;
    }

    private boolean isSubForumNameUnique(String forum, String subForumName) {
        boolean isUnique = true;
        Set<Subforumdb> subForums = _db.getSubForumsList(forum);
        for (Subforumdb s : subForums) {
            if (s.getSubForumName().equals(subForumName)) {
                isUnique = false;
                break;
            }
        }
        return isUnique;
    }

    @Override
    public boolean publishThread(Threaddb thread, String username, String password) {
        boolean succeeded = false;
        succeeded = _db.addThread(thread);
        return succeeded;
    }

    @Override
    public boolean postComment(Postdb comment, String username, String password) {
        boolean posted = false;
        posted = _db.addPost(comment);
        return posted;
    }

    @Override
    public boolean deleteComment(Postdb comment, String userName, String password) {
        boolean deleted = _db.deletePost(comment);
        return deleted;
    }

    @Override
    public List<Forumdb> getForumsList() {
        return _db.getForumsList();
    }

    @Override
    public Forumdb getForum(String forumName) {
        Forumdb resForum = null;
        if (forumExists(forumName)) {
            resForum = _db.getForum(forumName);
        }
        return resForum;
    }

    @Override
    public List<Subforumdb> getSubForumsList(String forumName) {
        Set<Subforumdb> subforums = null;
        if (forumExists(forumName)) {
            subforums = _db.getSubForumsList(forumName);
        }
        ArrayList<Subforumdb> ans = new ArrayList<>(subforums);
        return ans;
    }

    @Override
    public Subforumdb getSubForum(String forumName, String subForumName) {
        Subforumdb subForum = null;
        if (subForumExists(forumName, subForumName)) {
            subForum = _db.getSubForum(forumName, subForumName);
        }
        return subForum;
    }

    @Override
    public List<Threaddb> getThreadsList(String forumName,
            String subForumName) {
        Set<Threaddb> threads = null;
        if (subForumExists(forumName, subForumName)) {
            threads = _db.getThreadsList(forumName, subForumName);
        }
        ArrayList<Threaddb> ans = new ArrayList<>(threads);
        return ans;
    }

    @Override
    public Threaddb getThreadMessage(String forumName,
            String subForumName, int messageId) {
        Threaddb thread = null;
        if (threadExists(forumName, subForumName, messageId)) {
            thread = _db.getThread(forumName, subForumName, messageId);
        }
        return thread;
    }

    private boolean forumExists(String forumName) {
        List<Forumdb> forums = _db.getForumsList();
        for (Forumdb forum : forums) {
            if (forum.getForumName().equals(forumName)) {
                return true;
            }
        }
        return false;
    }

    private boolean subForumExists(String forumName, String subForumName) {
        if (forumExists(forumName)) {
            Set<Subforumdb> subForums = _db.getSubForumsList(forumName);
            if (subForums != null) {
                for (Subforumdb s : subForums) {
                    if (s.getSubForumName().equals(subForumName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean threadExists(String forumName, String subForumName,
            int messageId) {
        if (subForumExists(forumName, subForumName)) {
            Set<Threaddb> threads = _db.getThreadsList(forumName, subForumName);
            for (Threaddb t : threads) {
                if (t.getIdthread() == messageId) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Memberdb> getModerators(String forumName, String subForumName) {
        if (subForumExists(forumName, subForumName)) {
            ArrayList<Memberdb> ans = new ArrayList<>(_db.getModerators(forumName, subForumName));
            return ans;
        }
        return null;
    }

    @Override
    public List<Postdb> getAllPosts(String forumName, String subForumName,
            int threadId) {
        if (subForumExists(forumName, subForumName)) {
            ArrayList<Postdb> ans = new ArrayList<>(_db.getPostList(forumName, subForumName, threadId));

            return ans;
        }
        return null;
    }

    @Override
    public boolean deleteSubForum(Subforumdb subForum, String userName, String password) {
        return _db.deleteSubForum(subForum);
    }

    @Override
    public boolean deleteThread(Threaddb thread, String userName, String password) {
        return _db.deleteThread(thread);
    }

    @Override
    public boolean deleteForum(String forumName, String userName, String password) {
        Forumdb f = _db.getForum(forumName);
        return _db.deleteForum(f);
    }

    @Override
    public Postdb getPost(String forumName, String subForumName, int threadId, int postId) {
        return _db.getPost(forumName, subForumName, threadId, postId);
    }

    @Override
    public Memberdb getMember(String forumName, String userName) {
        return _db.getMember(forumName, userName);
    }

    @Override
    public boolean addModerator(Memberdb moderator, Subforumdb subForum, String userName, String password) {
        return _db.addModerator(moderator, subForum);
    }

    @Override
    public List<ForumNotification> getNotifications(String forumName, String userName, String password) {
        Memberdb m = _db.getMember(forumName, userName);
        List<ForumNotification> notifications = parseNotifications(m.getNotification());
        return notifications;
    }


	@Override
    public boolean setSuperAdmin() {
		Memberdb superAdmin = new Memberdb(null, "superAdmin", Encryptor.encrypt("superAdmin1234"), "", "SuperAdmin", null, "");
        return _db.setSuperAdmin(superAdmin);
    }

    @Override
    public Memberdb getSuperAdmin() {
        return _db.getSuperAdmin();
    }

    @Override
    public boolean addMember(Memberdb member) {
        return _db.addMember(member);
    }

    @Override
    public boolean deleteModerator(Memberdb moderator, String subForumName, String modName, String userName, String password) {
        return _db.deleteModerator(moderator, subForumName);
    }

    @Override
    public int getNumberOfThreadsInForum(String forumName, String userName, String password) {
        return _db.getNumberOfThreadsInForum(forumName);
    }

    @Override
    public int getNumberOfUserThreads(String forumName, String username, String requesterUsername, String requesterPassword) {
        return _db.getNumberOfUserThreads(forumName, _db.getMember(forumName, username));
    }

    @Override
    public HashMap<String, Set<String>> getUsersPostToUser(String forumName, String userName, String password) {
        return _db.getUsersPostToUser(forumName);
    }

    @Override
    public int getForumCounter(String userName, String password) {
        return _db.getNumberOfForums();
    }

    @Override
    public List<String> getCommonMembers(String userName, String password) {
        ArrayList<String> ans = new ArrayList<>(_db.getCommonMembers());
    	return ans;
    }

    @Override
    public List<Memberdb> getAllForumMembers(String forumName, String userName, String password) {
        return _db.getAllMembers(forumName);
    }

    @Override
    public boolean loginAsSuperAdmin(String userName, String password) {
        boolean succeeded = false;
        Memberdb superAdmin = _db.getSuperAdmin();
        if (Encryptor.checkPassword(password, superAdmin.getPassword()) && isSuperAdmin(userName, password)) {
            succeeded = true;
        }
        return succeeded;
    }


    @Override
    public boolean logout(String forumName, String userName) {
    	return true;
    }

    @Override
    public boolean editThread(Threaddb tm) {
        return this._db.updateThread(tm);
    }

    @Override
    public boolean editPost(Postdb p) {
    	return this._db.updatePost(p);
        
    }

    @Override
    public boolean initiateForum(String adminName, String adminPassword,
            String forumName, String ioap, String nfp, String dp, String amp,
            String s, String mp, String cmp, String superAdminUserName,
            String superAdminPassword) {
        boolean isAdded = false;
        Memberdb admin = null;
        if (isForumNameUnique(forumName)
                && isSuperAdmin(superAdminUserName, superAdminPassword)) {
            //System.out.println("is unique forum");
            enumNotiImidiOrAgre imidOrArgeNotiPolicy = null;
            enumNotiFriends friendsNotiPolicy = null;
            enumDelete deletePolicy = null;
            enumAssignModerator assignModeratorPolicy = null;
            enumCancelModerator cancelModeratorPolicy = null;
            int seniority = Integer.parseInt(s);
            int minPublish = Integer.parseInt(mp);
            switch (ioap) {
                case ("IMIDIATE"):
                    imidOrArgeNotiPolicy = enumNotiImidiOrAgre.IMIDIATE;
                    break;
                case ("AGGREGATE"):
                    imidOrArgeNotiPolicy = enumNotiImidiOrAgre.AGGREGATE;
                    break;
            }
            switch (nfp) {
                case ("ALLMEMBERS"):
                    friendsNotiPolicy = enumNotiFriends.ALLMEMBERS;
                    break;
                case ("PUBLISHERS"):
                    friendsNotiPolicy = enumNotiFriends.PUBLISHERS;
                    break;
            }

            switch (dp) {
                case ("LIMITED"):
                    deletePolicy = enumDelete.LIMITED;
                    break;
                case ("EXTENDED"):
                    deletePolicy = enumDelete.EXTENDED;
                    break;
            }
            switch (amp) {
                case ("NO_RESTRICTION"):
                    assignModeratorPolicy = enumAssignModerator.NO_RESTRICTION;
                    break;
                case ("MIN_PUBLISH"):
                    assignModeratorPolicy = enumAssignModerator.MIN_PUBLISH;
                    break;
                case ("SENIORITY"):
                    assignModeratorPolicy = enumAssignModerator.SENIORITY;
                    break;
            }
            switch (cmp) {
                case ("NO_RESTRICTION"):
                    cancelModeratorPolicy = enumCancelModerator.NO_RESTRICTION;
                    break;
                case ("RESTRICTED"):
                    cancelModeratorPolicy = enumCancelModerator.RESTRICTED;
                    break;
            }
          
            
            Forumdb forumToAdd = new Forumdb(forumName, imidOrArgeNotiPolicy.name(), friendsNotiPolicy.name(), deletePolicy.name(), assignModeratorPolicy.name(), cancelModeratorPolicy.name(), new Integer(seniority), new Integer(minPublish));
            this._db.addForum(forumToAdd);
            register(forumName, adminName, Encryptor.encrypt(adminPassword), "");
            admin = this._db.getMember(forumName, adminName);
            admin.setRoll("Admin");
            this._db.updateMember(admin);
            forumToAdd.setMemberdb(admin);
            boolean addedForum = this._db.updateForum(forumToAdd);
            if (addedForum) {
                System.out.println("After adding forum in database");
            }
            boolean addedAdmin = _db.addMember(admin);
            if (addedAdmin) {
                System.out.println("After adding admin in database");
            }

            isAdded = (addedAdmin && addedForum);
        }
        return isAdded;
    }
	private List<ForumNotification> parseNotifications(String notification) {
		List<ForumNotification> notifications = new ArrayList<ForumNotification>();
		Scanner scanner = new Scanner(notification);
		scanner.useDelimiter("\0");
		String txt;
		String date;
		while (scanner.hasNext()) {
			txt = scanner.next();
			date = scanner.next();
			notifications.add(new ForumNotification(txt, date));
		}
		scanner.close();
		return notifications;
	}

	@Override
	public boolean updateSubForum(Subforumdb subForum) {
		return this._db.updateSubForum(subForum);
	}

	@Override
	public boolean updateForum(Forumdb forum) {
		return this._db.updateForum(forum);
	}

	@Override
	public boolean updateMember(Memberdb member) {
		return this._db.updateMember(member);
	}


	@Override
	public boolean clearDB() {
		return this._db.clearDB();
	}


	@Override
	public boolean openSession() {
		 _db.openSession();
		 return true;
	}


	@Override
	public boolean closeSession() {
		_db.closeSession();
		return true;
	}
}