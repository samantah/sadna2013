package Sadna.Server.Protocol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Sadna.Client.Admin;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.Server.ForumNotification;
import Sadna.Server.NotificationsFactory;
import Sadna.Server.StringMessagesToClient;
import Sadna.Server.API.ServerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import Sadna.db.PolicyEnums.enumAssignModerator;
import Sadna.db.PolicyEnums.enumCancelModerator;
import Sadna.db.PolicyEnums.enumDelete;
import Sadna.Server.Tokenizer.StringMessage;
import java.util.Date;

/**
 * a simple implementation of the server protocol interface
 */
public class RequestHandlerProtocol implements AsyncServerProtocol<StringMessage> {

    private boolean _shouldClose = false;
    private boolean _connectionTerminated = false;
    private ServerInterface _si;
    private StringMessagesToClient _msgToClient;
    private NotificationsFactory _notificationsFactory;

    public RequestHandlerProtocol(ServerInterface _si) {
        this._si = _si;
        this._msgToClient = new StringMessagesToClient();
        this._notificationsFactory = new NotificationsFactory(this._si);
    }

    /**
     * processes a message<BR>
     * this simple interface prints the message to the screen, then composes a
     * simple reply and sends it back to the client
     *
     * @param msg the message to process
     * @return the reply that should be sent to the client, or null if no reply
     * needed
     */
    @Override
    public Object processMessage(StringMessage msg) {
        if (this._connectionTerminated) {
            return null;
        }
        if (this.isEnd(msg)) {
            this._shouldClose = true;
            return new StringMessage("Ok, bye bye");
        }
        String request = msg.getMessage();
        System.out.println("-- Message Received -- \n" + request);
        return parseAndHandleRequest(request);
    }

    /**
     * detetmine whether the given message is the termination message
     *
     * @param msg the message to examine
     * @return false - this simple protocol doesn't allow termination...
     */
    @Override
    public boolean isEnd(StringMessage msg) {
        return msg.equals("end");
    }

    /**
     * Is the protocol in a closing state?. When a protocol is in a closing
     * state, it's handler should write out all pending data, and close the
     * connection.
     *
     * @return true if the protocol is in closing state.
     */
    @Override
    public boolean shouldClose() {
        return this._shouldClose;
    }

    /**
     * Indicate to the protocol that the client disconnected.
     */
    @Override
    public void connectionTerminated() {
        this._connectionTerminated = true;
    }

    private Object parseAndHandleRequest(String request) {
        String[] parsedReq = request.split("\n");
        SubForum sf;
        Forum fr;
        ThreadMessage tm;
        Post p;
        System.out.println("asd");
        System.out.println(parsedReq[0]);
        switch (parsedReq[0]) {
            case "HELLO":
                return "HELLO";
            case "LOGIN":
                return handleLogin(parsedReq[2], parsedReq[4], parsedReq[6]);
            case "REGISTER":
                return handleRegister(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8]);
            case "GETSF":
                return handleGetSubForum(parsedReq[2], parsedReq[4]);
            case "GETSFL":
                return handleGetSubForumsList(parsedReq[2]);
            case "GETTL":
                return handleGetThreadsList(parsedReq[2], parsedReq[4]);
            case "GETTM":
                return handleGetThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
            case "GETFL":
                return handleGetForumsList();
            case "GETF":
                return handleGetForum(parsedReq[2]);
            case "ADDSF":
                List<Moderator> moderators = new ArrayList<Moderator>();
                int i;
                for (i = 8; i < Integer.parseInt(parsedReq[6]); i = i + 2) {
                    System.out.println("inside");
                    moderators.add(new Moderator(parsedReq[i], "", "", parsedReq[2], null));
                }
                Forum foru = _si.getForum(parsedReq[2]);
                SubForum subF = new SubForum(foru, parsedReq[4]);
                return handleAddSubForum(subF, moderators, parsedReq[i], parsedReq[i + 2]);
            case "ADDF":
                return handleInitiateForum(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[9],
                        parsedReq[10], parsedReq[11], parsedReq[12], parsedReq[13], parsedReq[14], parsedReq[16], parsedReq[18]);
            case "GETAP":
                return handleGetAllPosts(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
            case "POST":
                tm = _si.getThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
                p = new Post(tm, parsedReq[10], parsedReq[12], parsedReq[8]);
                return handlePostComment(p, parsedReq[8], parsedReq[14]);
            case "THREAD":
                return handlePublishThread(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10], parsedReq[12]);
            case "DELPST":
                p = _si.getPost(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]), Integer.parseInt(parsedReq[8]));
                return handleDeletePost(p, parsedReq[10], parsedReq[12]);
            case "DELTHRD":
                tm = _si.getThreadMessage(parsedReq[2], parsedReq[4], Integer.parseInt(parsedReq[6]));
                return handleDeleteThread(tm, parsedReq[8], parsedReq[10]);
            case "DELSF":
                sf = _si.getSubForum(parsedReq[2], parsedReq[4]);
                return handleDeleteSubForum(sf, parsedReq[6], parsedReq[8]);
            case "DELF":
                fr = _si.getForum(parsedReq[2]);
                return handleDeleteForum(fr, parsedReq[4], parsedReq[6]);
            case "ADDMOD":
                sf = _si.getSubForum(parsedReq[2], parsedReq[4]);
                return handleAddModerator(sf, parsedReq[6], parsedReq[8], parsedReq[10]);
            case "NOTI":
                String forumName = parsedReq[2];
                String userName = parsedReq[4];
                String password = parsedReq[6];
                Member m = _si.getMember(forumName, userName);
                return handleNotification(m, password);
            case "REMMOD":
                return handleRemoveModerator(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10]);
            case "GETCOUNT":
                return handleThreadCounter(parsedReq[2], parsedReq[4], parsedReq[6]);
            case "GETNUT":
                return handleGetNumberOfUserThreads(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8]);
            case "GETUPU":
                return handleGetUsersPostToUser(parsedReq[2], parsedReq[4], parsedReq[6]);
            case "GETNF":
                return handleForumCounter(parsedReq[2], parsedReq[4]);
            case "GETCOM":
                return handleCommonMembers(parsedReq[2], parsedReq[4]);
            case "LOGOUT":
                return handleLogout(parsedReq[2], parsedReq[4]);
            case "GETAM":
                return handleGetAllForumMembers(parsedReq[2], parsedReq[4], parsedReq[6]);
            case "LOGINS":
                return handleLoginAsSuperAdmin(parsedReq[2], parsedReq[4]);
            case "EDTTHRD":
            //return handleEditThread(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10], parsedReq[12], parsedReq[14]);
            case "EDTPST":
            //return handleEditPost(parsedReq[2], parsedReq[4], parsedReq[6], parsedReq[8], parsedReq[10], parsedReq[12], parsedReq[14], parsedReq[16]);
            default:
                return null;
        }

    }

    private Object handleLogout(String forumName, String userName) {
        if (_si.logout(forumName, userName)) {
            return _msgToClient.sendOK();
        } else {
            return _msgToClient.sendErrorInServer();
        }
    }

    private Object handleLoginAsSuperAdmin(String userName, String password) {
        boolean logedIn = _si.loginAsSuperAdmin(userName, password);
        if (logedIn) {
            Member member = _si.getSuperAdmin();
            if (member != null && member instanceof SuperAdmin) {
                return _msgToClient.sendSuperAdminOK();
            }
        } else {
            return _msgToClient.sendNotFound();
        }
        return null;
    }

    private Object handleGetAllForumMembers(String forumName, String userName,
            String password) {
        Member admin = _si.getMember(forumName, userName);
        if (admin != null && !admin.getPassword().equals(password)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        if (!(admin instanceof Admin)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        return _si.getAllForumMembers(forumName, userName, password);
    }

    private Object handleCommonMembers(String adminName, String password) {
        SuperAdmin superAdmin = _si.getSuperAdmin();
        if (!superAdmin.getPassword().equals(password)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        return _si.getCommonMembers(adminName, password);
    }

    private Object handleForumCounter(String superAdminName, String password) {
        SuperAdmin superAdmin = _si.getSuperAdmin();
        if (!superAdmin.getPassword().equals(password)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        return _si.getForumCounter(superAdminName, password);
    }

    private Object handleGetUsersPostToUser(String forumName, String adminName, String password) {
        Member admin = _si.getMember(forumName, adminName);
        if (!admin.getPassword().equals(password)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        if (!(admin instanceof Admin)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        return _si.getUsersPostToUser(forumName, adminName, password);
    }

    private Object handleGetNumberOfUserThreads(String forumName, String userName, String adminName, String password) {
        Member admin = _si.getMember(forumName, adminName);
        if (!admin.getPassword().equals(password)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        if (!(admin instanceof Admin)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        return _si.getNumberOfUserThreads(forumName, userName, adminName, password);
    }

    private Object handleThreadCounter(String forumName, String userName, String password) {
        Member admin = _si.getMember(forumName, userName);
        if (!admin.getPassword().equals(password)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        if (!(admin instanceof Admin)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        return _si.getNumberOfThreadsInForum(forumName, userName, password);
    }

    private Object handleRemoveModerator(String forumName, String subForumName, 
            String moderatorName, String userName, String password) {
        Member admin = _si.getMember(forumName, userName);
        Forum forum = _si.getForum(forumName);
        if (!admin.getPassword().equals(password)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        if (!(admin instanceof Admin)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        List<Member> mList = _si.getModerators(forumName, subForumName);
        if (mList.size() <= 1
                && forum.getPolicy().getCancelModeratorPolicy()==enumCancelModerator.RESTRICTED) {
            return _msgToClient.sendIsTheOnlyModeratorInTheSubForum();
        }
        Moderator m = null;
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getUserName().equals(moderatorName)) {
                m = (Moderator) mList.get(i);
                break;
            }

        }
        if (_si.deleteModerator(m, subForumName, moderatorName, userName, password)) {
            return _msgToClient.sendOK();
        } else {
            return _msgToClient.sendErrorInServer();
        }
    }

    private Object handleNotification(Member m, String password) {
        if (m.getPassword().equals(password)) {
            String forumName = m.getForum();
            String userName = m.getUserName();
            List<ForumNotification> notifications = _si.getNotifications(forumName, userName, password);
            List<ForumNotification> res = new ArrayList<ForumNotification>();
            for (Iterator<ForumNotification> iterator = notifications.iterator(); iterator.hasNext();) {
                ForumNotification forumNotification = (ForumNotification) iterator.next();
                res.add(forumNotification);
            }
            m.clearNotifications();
            _si.addMember(m);
            return res;
        } else {
            return _msgToClient.sendErrorNoAuthorized();
        }
    }

    public Object handleRegister(String forumName, String userName, String password,
            String email) {
        boolean isAdded = _si.register(forumName, userName, password, email);
        if (isAdded) {
            return _msgToClient.sendOK();
        } else {
            return _msgToClient.sendErrorInServer();
        }
    }

    public Object handleLogin(String forumName, String userName, String password) {
        boolean logedIn = _si.login(forumName, userName, password);
        if (logedIn) {
            Member member = _si.getMember(forumName, userName);
            if (member instanceof Admin) {
                return _msgToClient.sendAdminOK();
            } else if (member instanceof Moderator) {
                return _msgToClient.sendModeratorOK();
            } else if (member instanceof Member) {
                return _msgToClient.sendOK();
            }
        } else {
            return _msgToClient.sendNotFound();
        }
        return null;
    }

    public Object handleInitiateForum(String forumName, String adminName,
            String adminPassword, String imidiOrAgrePolicy, String notiFriendsPolicy,
            String deletePolicy, String assignModerPolicy, String seniority, String minPublish,
            String cancelModerPolicy, String superAdminUserName, String superAdminPassword) {
        boolean isAdded = false;

        isAdded = _si.initiateForum(adminName, adminPassword, forumName, imidiOrAgrePolicy, notiFriendsPolicy,
                deletePolicy, assignModerPolicy, seniority, minPublish, cancelModerPolicy, superAdminUserName,
                superAdminPassword);
        if (isAdded) {
            //System.out.println("isAdded");
            return _msgToClient.sendOK();
            //System.out.println("Sent ok");
        } else {
            //System.out.println("notAdded");
            return _msgToClient.sendErrorNoAuthorized();
            //System.out.println("Sent ErrorInServer");
        }
    }

    public Object handleAddSubForum(SubForum subForum, List<Moderator> moderators, String username, String password) {
        boolean subForumIsAdded = false;
        subForumIsAdded = _si.addSubForum(subForum, moderators, username, password);
        if (subForumIsAdded) {
            return _msgToClient.sendOK();
        } else {
            return _msgToClient.sendErrorInServer();
        }
    }

    public Object handlePublishThread(String forumName, String subForumName,
            String posterName, String threadTitle, String threadContent, String password) {
        boolean succeeded = false;
        SubForum sf = _si.getSubForum(forumName, subForumName);
        if (sf != null && _si.memberExistsInForum(forumName, posterName)) {
            ThreadMessage threadM = new ThreadMessage(sf, threadTitle, threadContent, posterName);
            succeeded = _si.publishThread(threadM, posterName, password);
        }
        if (succeeded) {
            return _msgToClient.sendOK();
        } else {
            return _msgToClient.sendErrorInServer();
        }
    }

    public Object handleDeletePost(Post p, String requester, String password) {
        String publisher = p.getPublisher();
        Forum forum = p.getThread().getSubForum().getForum();
        String forumName = forum.getForumName();
        Member member = _si.getMember(forumName, publisher);
        if (member != null) {
            String publisherName = member.getUserName();
            String publisherPassword = member.getPassword();
            if (requester.equals(publisherName) && password.equals(publisherPassword)) {
                return deletePostAndSendOk(p, requester, password);
            }
        }
        if (forum.getPolicy().getDeletePolicy() == enumDelete.EXTENDED) {
            SubForum subForum = p.getThread().getSubForum();
            String subForumName = subForum.getSubForumName();
            List<Member> moderators = _si.getModerators(forumName, subForumName);
            for (Iterator<Member> it = moderators.iterator(); it.hasNext();) {
                Member currModerator = it.next();
                String moderatorName = currModerator.getUserName();
                String moderatorPassword = currModerator.getPassword();
                if (requester.equals(moderatorName) && password.equals(moderatorPassword)) {
                    return deletePostAndSendOk(p, moderatorName, moderatorPassword);
                }
            }
        }
        Admin admin = _si.getForum(forumName).getAdmin();
        if (admin != null) {
            String adminName = admin.getUserName();
            String adminPassword = admin.getPassword();
            if (requester.equals(adminName) && password.equals(adminPassword)) {
                return deletePostAndSendOk(p, adminName, adminPassword);
            }
        }
        return _msgToClient.sendErrorNoAuthorized();
    }

    public Object handlePostComment(Post post, String userName, String password) {
        boolean succeeded = false;
        Object result = null;
        succeeded = _si.postComment(post, userName, password);
        if (succeeded) {
            result = _msgToClient.sendOK();
        } else {
            result = _msgToClient.sendErrorInServer();
        }
        _notificationsFactory.sendNotifications(post);
        return result;
    }

    public Object handleGetForumsList() {
        return _si.getForumsList();
    }

    private Object handleGetAllPosts(String forumName, String subForumName, int threadId) {
        return _si.getAllPosts(forumName, subForumName, threadId);

    }

    public Object handleGetForum(String forumName) {
        return _si.getForum(forumName);
    }

    public Object handleGetSubForumsList(String forumName) {
        return _si.getSubForumsList(forumName);
    }

    public Object handleGetSubForum(String forum, String subForumName) {
        return _si.getSubForum(forum, subForumName);
    }

    public Object handleGetThreadsList(String forumName,
            String subForumName) {
        return _si.getThreadsList(forumName, subForumName);
    }

    public Object handleGetThreadMessage(String forumName, String subForumName,
            int messageID) {
        return _si.getThreadMessage(forumName, subForumName, messageID);
    }

    public Object handleDeleteThread(ThreadMessage tm, String requester, String password) {
        String publisher = tm.getPublisher();
        Forum forum = tm.getSubForum().getForum();
        String forumName = forum.getForumName();
        Member member = _si.getMember(forumName, publisher);
        if (member != null) {
            String publisherName = member.getUserName();
            String publisherPassword = member.getPassword();
            if (requester.equals(publisherName) && password.equals(publisherPassword)) {
                return deleteThreadAndSendOk(tm, requester, password);

            }
        }
        if (forum.getPolicy().getDeletePolicy() == enumDelete.EXTENDED) {
            SubForum subForum = tm.getSubForum();
            String subForumName = subForum.getSubForumName();
            List<Member> moderators = _si.getModerators(forumName, subForumName);
            for (Iterator<Member> it = moderators.iterator(); it.hasNext();) {
                Member currModerator = it.next();
                String moderatorName = currModerator.getUserName();
                String moderatorPassword = currModerator.getPassword();
                if (requester.equals(moderatorName) && password.equals(moderatorPassword)) {
                    return deleteThreadAndSendOk(tm, moderatorName, moderatorPassword);
                }
            }
        }
        Admin admin = _si.getForum(forumName).getAdmin();
        if (admin != null) {
            String adminName = admin.getUserName();
            String adminPassword = admin.getPassword();
            if (requester.equals(adminName) && password.equals(adminPassword)) {
                return deleteThreadAndSendOk(tm, adminName, adminPassword);
            }
        }
        return _msgToClient.sendErrorNoAuthorized();
    }

    private Object deletePostAndSendOk(Post p, String userName, String password) {
        _notificationsFactory.sendNotifications(p);
        _si.deleteComment(p, userName, password);
        return _msgToClient.sendOK();
    }

    private Object deleteThreadAndSendOk(ThreadMessage tm, String userName, String password) {
        _notificationsFactory.sendNotifications(tm);
        _si.deleteThread(tm, userName, password);
        return _msgToClient.sendOK();
    }

    private Object handleDeleteSubForum(SubForum sf, String requester, String password) {
        Admin admin = sf.getForum().getAdmin();
        boolean equals = admin.getUserName().equals(requester) && admin.getPassword().equals(password);
        if (equals) {
            _notificationsFactory.sendNotifications(sf);
            _si.deleteSubForum(sf, requester, password);
            return _msgToClient.sendOK();
        }
        return _msgToClient.sendErrorNoAuthorized();
    }

    private Object handleAddModerator(SubForum sf, String moderatorToAdd,
            String userName, String password) {
        String forumName = sf.getForum().getForumName();
        Forum forum = _si.getForum(forumName);
        Admin admin = forum.getAdmin();
        if (admin == null || !admin.getUserName().equals(userName)
                || !admin.getPassword().equals(password)) {
            return _msgToClient.sendErrorNoAuthorized();
        }
        Member member = _si.getMember(forumName, moderatorToAdd);
        if (member == null) {
            return _msgToClient.sendNotFound();
        }
        
        if (forum.getPolicy().getAssignModeratorPolicy()==enumAssignModerator.MIN_PUBLISH){
            int numberOfUserThreads = _si.getNumberOfUserThreads(forumName, userName, null, null);
            int minPublish = forum.getPolicy().getMinPublish();
            if (numberOfUserThreads<minPublish){
                return _msgToClient.sendErrorNoAuthorized();//consider change to someting more inducative
            }
        }
        if (forum.getPolicy().getAssignModeratorPolicy()==enumAssignModerator.SENIORITY){
            long dateOfJoining = member.getDateOfJoining();
            int seniorityAsDays = calcNumOfDaysSinceJoining(dateOfJoining);
            int seniority = forum.getPolicy().getSeniority();
            if (seniorityAsDays<seniority){
                return _msgToClient.sendErrorNoAuthorized();//consider change to someting more inducative
            }
        }
        
        List<Member> moderators = _si.getModerators(forumName, sf.getSubForumName());
        List<String> moderetorsAsString = new ArrayList<String>();
        for (Iterator<Member> it = moderators.iterator(); it.hasNext();) {
            Member currMember = it.next();
            moderetorsAsString.add(currMember.getUserName());
        }
        if (moderetorsAsString.contains(moderatorToAdd)) {
            return _msgToClient.sendOK();
        }
        Moderator moderator = new Moderator(member);
        boolean addModerator = _si.addModerator(moderator, sf, moderator.getUserName(), moderator.getPassword());
        if (addModerator) {
            return _msgToClient.sendOK();
        }
        return null;
    }

    private Object handleDeleteForum(Forum fr, String userName, String password) {
        SuperAdmin superAdmin = _si.getSuperAdmin();
        if (superAdmin == null || !superAdmin.getPassword().equals(password)) {
            return _msgToClient.sendErrorNoAuthorized();
        } else {
            return _msgToClient.deleteForum(_si.deleteForum(fr.getForumName(), userName, password));
        }
    }

    private int calcNumOfDaysSinceJoining(long dateOfJoining) {
        return (int)((dateOfJoining - new Date().getTime())/(1000*60*60*24));
    }
}
