package Sadna.Server;

import Sadna.Client.Admin;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.Server.API.ServerInterface;
import Sadna.db.API.DBInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.List;


public class ServerToDataBaseHandler implements ServerInterface {

    private DBInterface _db;

    public ServerToDataBaseHandler(DBInterface db) {
        _db = db;
    }

    @Override
    public boolean register(String forumName, String userName, String password, String email) {
        boolean isAdded = false;
        if (forumExists(forumName)) {
            System.out.println("is unique forum");
            if (isUserNameUnique(forumName, userName)) {
                System.out.println("is unique username");
                isAdded = _db.addMember(new Member(userName, password, email, forumName, null));
            }
        }
        return isAdded;
    }

    private boolean isUserNameUnique(String forumName, String userName) {
        boolean isUnique = true;
        List<Member> members = _db.getAllMembers(forumName);
        for (Member m : members) {
            if (m.getUserName().equals(userName)) {
                isUnique = false;
                break;
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
        List<Member> members = _db.getAllMembers(forumName);
        for (Member m : members) {
            if (m.getUserName().equals(userName)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    @Override
    public boolean login(String forumName, String userName, String password) {
        boolean succeeded = false;
        if (forumExists(forumName)) {
            Member loginner = _db.getMember(forumName, userName);
            if (loginner != null && loginner.getPassword().equals(password)) {
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
    @Override
    public boolean initiateForum(String adminUserName, String adminPassword,
            String forumName) {
        boolean isAdded = false;
        Admin admin = null;
        if (isForumNameUnique(forumName)) {
            System.out.println("is unique forum");
            Forum forumToAdd = new Forum(forumName);
            admin = new Admin(adminUserName, adminPassword, "", null);
            admin.setForum(forumName);
            forumToAdd.setAdmin(admin);
            boolean addedForum = _db.addForum(forumToAdd);
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

    private boolean isForumNameUnique(String forumName) {
        boolean isUnique = true;
        List<Forum> forums = _db.getForumsList();
        for (Forum f : forums) {
            if (f.getForumName().equals(forumName)) {
                isUnique = false;
                break;
            }
        }
        return isUnique;
    }

    @Override
    public boolean addSubForum(SubForum subForum, List<Moderator> moderators) {
        boolean isAdded = false;
        if (subForum.getForum() != null) {
            if (isSubForumNameUnique(subForum.getForum().getForumName(),
                    subForum.getSubForumName())) {
                System.out.println("is unique subforum");
                isAdded = _db.addSubForum(subForum, moderators);
            }
        }
        return isAdded;
    }

    private boolean isSubForumNameUnique(String forum, String subForumName) {
        boolean isUnique = true;
        List<SubForum> subForums = _db.getSubForumsList(forum);
        for (SubForum s : subForums) {
            if (s.getSubForumName().equals(subForumName)) {
                isUnique = false;
                break;
            }
        }
        return isUnique;
    }

    @Override
    public boolean publishThread(ThreadMessage thread) {
        boolean succeeded = false;
        succeeded = _db.addThread(thread);
        return succeeded;
    }

    @Override
    public boolean postComment(Post comment) {
        boolean posted = false;
        posted = _db.addPost(comment);
        return posted;
    }

    @Override
    public boolean deleteComment(Post comment) {
        boolean deleted = _db.deletePost(comment);
        return deleted;
    }

    @Override
    public List<Forum> getForumsList() {
        return _db.getForumsList();
    }

    @Override
    public Forum getForum(String forumName) {
        Forum resForum = null;
        if (forumExists(forumName)) {
            resForum = _db.getForum(forumName);
        }
        return resForum;
    }

    @Override
    public List<SubForum> getSubForumsList(String forumName) {
        List<SubForum> subforums = null;
        if (forumExists(forumName)) {
            subforums = _db.getSubForumsList(forumName);
        }
        return subforums;
    }

    @Override
    public SubForum getSubForum(String forumName, String subForumName) {
        SubForum subForum = null;
        if (subForumExists(forumName, subForumName)) {
            subForum = _db.getSubForum(forumName, subForumName);
        }
        return subForum;
    }

    @Override
    public List<ThreadMessage> getThreadsList(String forumName,
            String subForumName) {
        List<ThreadMessage> threads = null;
        if (subForumExists(forumName, subForumName)) {
            threads = _db.getThreadsList(forumName, subForumName);
        }
        return threads;
    }

    @Override
    public ThreadMessage getThreadMessage(String forumName,
            String subForumName, int messageId) {
        ThreadMessage thread = null;
        if (threadExists(forumName, subForumName, messageId)) {
            thread = _db.getThread(forumName, subForumName, messageId);
        }
        return thread;
    }

    private boolean forumExists(String forumName) {
        List<Forum> forums = _db.getForumsList();
        for (Forum forum : forums) {
            if (forum.getForumName().equals(forumName)) {
                return true;
            }
        }
        return false;
    }

    private boolean subForumExists(String forumName, String subForumName) {
        if (forumExists(forumName)) {
            List<SubForum> subForums = _db.getSubForumsList(forumName);
            for (SubForum s : subForums) {
                if (s.getSubForumName().equals(subForumName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean threadExists(String forumName, String subForumName,
            int messageId) {
        if (subForumExists(forumName, subForumName)) {
            List<ThreadMessage> threads = _db.getThreadsList(forumName, subForumName);
            for (ThreadMessage t : threads) {
                if (t.getId() == messageId) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Member> getModerators(String forumName, String subForumName) {
        if (subForumExists(forumName, subForumName)) {
//            return null; // change to the line in the bottom
            return _db.getModerators(forumName, subForumName);
        }
        return null;
    }

    @Override
    public List<Post> getAllPosts(String forumName, String subForumName,
            int threadId) {
        if (subForumExists(forumName, subForumName)) {
            return _db.getPostList(forumName, subForumName, threadId);
        }
        return null;
    }

    @Override
    public boolean deleteSubForum(SubForum subForum) {
        return _db.deleteSubForum(subForum);
    }

    @Override
    public boolean deleteThread(ThreadMessage thread) {
        return _db.deleteThread(thread);
    }

    @Override
    public boolean deleteForum(String forumName) {
        Forum f = new Forum(forumName);
        boolean deleteForum = _db.deleteForum(f);
        return deleteForum;
    }

    @Override
    public Post getPost(String forumName, String subForumName, int threadId, int postId) {
        return _db.getPost(forumName, subForumName, threadId, postId);
    }

    @Override
    public Member getMember(String forumName, String userName) {
        return _db.getMember(forumName, userName);
    }

    @Override
    public boolean addModerator(Moderator moderator, SubForum subForum) {
        return _db.addModerator(moderator, subForum);
    }

    @Override
    public boolean setSuperAdmin() {
        return _db.setSuperAdmin();
    }

    @Override
    public SuperAdmin getSuperAdmin() {
        return _db.getSuperAdmin();
    }

}