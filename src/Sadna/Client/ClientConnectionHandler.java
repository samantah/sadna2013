package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Server.ForumNotification;
import Sadna.db.Forum;
import Sadna.db.Message;
import Sadna.db.Policy;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientConnectionHandler implements ClientCommunicationHandlerInterface {

    private Socket clientSocket;
    private PrintWriter stringToServer;
    private BufferedReader stringFromServer;
    private ObjectInputStream objectFromServer;
    private String msgToSend;
    private String receivedMsg;
    private String delimiter = "\0";
    private Pattern emailPattern;
    private Matcher emailMatcher;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public ClientConnectionHandler() {
    }

    // removeModerator()
    public ClientConnectionHandler(String host, int port) {
        try {
            clientSocket = new Socket(host, port);
            stringToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            stringToServer.print("HELLO\n" + delimiter);
            stringToServer.flush();
            InputStream inputStream = clientSocket.getInputStream();
            stringFromServer = new BufferedReader(new InputStreamReader(inputStream));
            objectFromServer = new ObjectInputStream(inputStream);
            objectFromServer.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Got exception in ConnectionHandler constructor");
        }
    }

    public static ClientConnectionHandler create(String host, int port) {
        ClientConnectionHandler cch = new ClientConnectionHandler();
        try {
            cch.clientSocket = new Socket(host, port);
            cch.stringToServer = new PrintWriter(cch.clientSocket.getOutputStream(), true);
            InputStream inputStream = cch.clientSocket.getInputStream();
            cch.stringFromServer = new BufferedReader(new InputStreamReader(inputStream));
            cch.objectFromServer = new ObjectInputStream(cch.clientSocket.getInputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Got exception in ConnectionHandler constructor");
        }
        return cch;
    }

    @Override
    public boolean finishCommunication() {
        try {
            clientSocket.close();
            return true;
        } catch (IOException ex) {
            String message = ex.getMessage();
            System.out.println(message);
        }
        return false;
    }

    @Override
    public Member login(String forumName, String userName, String password) {
        Member loggedInMember = null;
        msgToSend = "LOGIN\n" + "forumName:\n" + forumName + "\n"
                + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            loggedInMember = new Member(userName, password, null, forumName, this);
        }
        if (receivedMsg.contains("201ok")) {
            loggedInMember = new Moderator(userName, password, null, forumName, this);
        }
        if (receivedMsg.contains("202ok")) {
            loggedInMember = new Admin(userName, password, null, forumName, this);
        }
        return loggedInMember;
    }

    @Override
    public SuperAdmin loginAsSuperAdmin(String userName, String password) {
        SuperAdmin loggedInSuperAdmin = null;
        msgToSend = "LOGINS\n" + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            loggedInSuperAdmin = new SuperAdmin(userName, password, null, this);
        }
        return loggedInSuperAdmin;
    }

    @Override
    public Member register(String forumName, String userName, String password,
            String email) {
        Member loggedInMember = null;
        if (passwordValidity(password)) {
            msgToSend = "REGISTER\n" + "forumName:\n" + forumName + "\n" + "userName:\n" + userName + "\n"
                    + "password:\n" + password + "\n" + "email:\n" + email + "\n";
            msgToSend += delimiter;
            stringToServer.print(msgToSend);
            stringToServer.flush();
            try {
                objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
                receivedMsg = (String) objectFromServer.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
            if (receivedMsg.contains("200ok")) {
                loggedInMember = new Member(userName, password, null, forumName, this);
            }
        }
        return loggedInMember;
    }

    private boolean passwordValidity(String password) {
        return (password.length() <= 16 && password.length() >= 8 && range(password));
    }

    private boolean range(String password) {
        boolean charFlag = false;
        boolean numFlag = false;
        char currchar;
        for (int i = 0; i < password.length() && !(numFlag && charFlag); i++) {
            currchar = password.charAt(i);
            if (currchar >= '0' && currchar <= '9') {
                numFlag = true;
            }
            if ((currchar >= 'a' && currchar <= 'z') || (currchar >= 'A' && currchar <= 'Z')) {
                charFlag = true;
            }
        }
        return (charFlag && numFlag);
    }

    public boolean EmailValidator(String email) {
        emailPattern = Pattern.compile(EMAIL_PATTERN);
        emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

    @Override
    public SubForum getSubForum(String forum, String subForumName) {
        SubForum returnedSF = null;
        msgToSend = "GETSF\n" + "forumName:\n" + forum + "\n"
                + "subForumName:\n" + subForumName + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            returnedSF = (SubForum) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return returnedSF;
    }

    @Override
    public List<SubForum> getSubForumsList(String forumName) {
        List<SubForum> returnedList = new ArrayList<SubForum>();
        msgToSend = "GETSFL\n" + "forumName:\n" + forumName + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            returnedList = (List<SubForum>) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return returnedList;
    }

    @Override
    public List<ThreadMessage> getThreadsList(String forumName,
            String subForumName) {
        List<ThreadMessage> returnedList = new ArrayList<ThreadMessage>();
        ThreadMessage tmp = null;
        msgToSend = "GETTL\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            returnedList = (List<ThreadMessage>) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return returnedList;
    }

    @Override
    public ThreadMessage getThreadMessage(String forumName,
            String subForumName, int msgID) {
        ThreadMessage returnedTM = null;
        msgToSend = "GETTM\n" + "forumName:\n" + forumName + "\n"
                + "subForumName:\n" + subForumName + "\n" + "treadMessageID:\n" + msgID + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            returnedTM = (ThreadMessage) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return returnedTM;
    }

    @Override
    public boolean postComment(Post post, String password) {
        boolean isPosted = false;
        if (legalMsg(post)) {

            ThreadMessage tm = post.getThread();
            int tmid = tm.getId();
            SubForum sf = tm.getSubForum();
            String sfname = sf.getSubForumName();
            Forum f = sf.getForum();
            String fname = f.getForumName();
            String posterName = post.getPublisher();
            String title = post.getTitle();
            String content = post.getContent();
            msgToSend = "POST\n" + "forumName:\n" + fname + "\n"
                    + "subForumName:\n" + sfname + "\n" + "ThreadMessage:\n" + tmid + "\n"
                    + "posterName:\n" + posterName + "\n" + "postTitle:\n" + title + "\n"
                    + "postContent:\n" + content + "\n" + "password:\n" + password + "\n";
            msgToSend += delimiter;
            stringToServer.print(msgToSend);
            stringToServer.flush();
            try {
                objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
                receivedMsg = (String) objectFromServer.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
            if (receivedMsg.contains("200ok")) {
                isPosted = true;
            }
        }
        return isPosted;
    }

    @Override
    public boolean publishThread(ThreadMessage newThread, String password) {
        boolean isPublished = false;
        if (legalMsg(newThread)) {
            String newThreadTitle = newThread.getTitle();
            String newThreadContent = newThread.getContent();
            SubForum sf = newThread.getSubForum();
            String sfName = sf.getSubForumName();
            Forum f = sf.getForum();
            String fName = f.getForumName();
            String posterName = newThread.getPublisher();
            msgToSend = "THREAD\n" + "forumName:\n" + fName + "\n"
                    + "subForumName:\n" + sfName + "\n" + "posterName:\n" + posterName + "\n"
                    + "threadTitle:\n" + newThreadTitle + "\n"
                    + "threadContent:\n" + newThreadContent + "\n"
                    + "requsterPassword:\n" + password + "\n";
            msgToSend += delimiter;
            stringToServer.print(msgToSend);
            stringToServer.flush();
            try {
                objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
                receivedMsg = (String) objectFromServer.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
            if (receivedMsg.contains("200ok")) {
                isPublished = true;
            }
        }
        return isPublished;
    }

    @Override
    public User logout(String forumName, String userName) {
        User loggedOutMember = null;
        msgToSend = "LOGOUT\n" + "forumName:\n" + forumName + "\n"
                + "userName:\n" + userName + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            loggedOutMember = new User(this);
        }
        return loggedOutMember;
    }

    @Override
    public List<Forum> getForumsList() {
        List<Forum> returnedList = new ArrayList<Forum>();
        msgToSend = "GETFL\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            returnedList = (List<Forum>) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return returnedList;
    }

    @Override
    public Forum getForum(String forumName) {

        Forum returnedSF = null;
        msgToSend = "GETF\n" + "forumName:\n" + forumName + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            returnedSF = (Forum) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return returnedSF;
    }

    @Override
    public boolean addSubForum(SubForum subForum, List<Member> lm, String userName, String password) {
        boolean added = false;
        Forum f = subForum.getForum();
        String fName = f.getForumName();
        String sfName = subForum.getSubForumName();
        msgToSend = "ADDSF\n" + "forumName:\n" + fName + "\n"
                + "subForumName:\n" + sfName + "\n" + "size:\n" + lm.size() + "\n";
        for (Member m : lm) {
            msgToSend += "member:\n" + m.getUserName() + "\n";
        }
        msgToSend += "requester:\n" + userName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            added = true;
        }
        return added;
    }

    @Override
    public boolean initiateForum(String forumName, String adminName, String AdminPassword, Policy policy, String superAdminName, String superAdminPasswaord) {
        boolean initiated = false;
        msgToSend = "ADDF\n" + "forumName:\n" + forumName + "\n"
                + "adminName:\n" + adminName + "\n" + "adminPassword:\n" + AdminPassword + "\n"
                + "policy:\n"
                + policy.getImidOrArgeNotiPolicy() + "\n"
                + policy.getFriendsNotiPolicy() + "\n"
                + policy.getDeletePolicy() + "\n"
                + policy.getAssignModeratorPolicy() + "\n"
                + policy.getSeniority() + "\n"
                + policy.getMinPublish() + "\n"
                + policy.getCancelModeratorPolicy() + "\n"
                + "superAdminName:\n" + superAdminName + "\n"
                + "superAdminPasswaord:\n" + superAdminPasswaord + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            initiated = true;
        }
        return initiated;
    }

    private boolean legalMsg(Message m) {
        return m.getContent().length() <= 1000;
    }

    @Override
    public List<Post> getAllPosts(ThreadMessage tm) {
        List<Post> allPosts = null;
        SubForum subForum = tm.getSubForum();
        Forum forum = subForum.getForum();
        String forumName = forum.getForumName();
        String subForumName = subForum.getSubForumName();
        int threadID = tm.getId();
        msgToSend = "GETAP\n" + "forumName:\n" + forumName + "\n"
                + "SubForumName:\n" + subForumName + "\n" + "TheadID:\n" + threadID + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            allPosts = (List<Post>) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return allPosts;
    }

    @Override
    public boolean deleteForum(String forumName, String userName, String password) {
        boolean deleted = false;
        msgToSend = "DELF\n" + "forumName:\n" + forumName + "\n" + "userName:\n"
                + userName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            deleted = true;
        }
        return deleted;
    }

    @Override
    public boolean deleteSubForum(String forumName, String subForumName, String userName, String password) {

        boolean deleted = false;
        msgToSend = "DELSF\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n" + "userName:\n"
                + userName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            deleted = true;
        }
        return deleted;
    }

    @Override
    public boolean deleteThreadMessage(ThreadMessage tm, String userName, String password) {
        boolean deleted = false;
        SubForum subForum = tm.getSubForum();
        String subForumName = subForum.getSubForumName();
        Forum forum = subForum.getForum();
        String forumName = forum.getForumName();
        int tmId = tm.getId();
        msgToSend = "DELTHRD\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n"
                + "threadId:\n" + tmId + "\n" + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            deleted = true;
        }
        return deleted;
    }

    @Override
    public boolean deletePost(Post p, String userName, String password) {
        boolean deleted = false;
        int pId = p.getId();
        ThreadMessage tm = p.getThread();
        SubForum subForum = tm.getSubForum();
        String subForumName = subForum.getSubForumName();
        Forum forum = subForum.getForum();
        String forumName = forum.getForumName();
        int tmId = tm.getId();
        msgToSend = "DELPST\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n"
                + "threadId:\n" + tmId + "\n" + "postId:\n" + pId + "\n" + "userName:\n" + userName + "\n"
                + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            deleted = true;
        }
        return deleted;
    }

    @Override
    public boolean editThread(ThreadMessage newTM, String editorName, String editorPassword) {
        boolean edited = false;
        SubForum subForum = newTM.getSubForum();
        String subForumName = subForum.getSubForumName();
        Forum forum = subForum.getForum();
        String forumName = forum.getForumName();
        String posterName = newTM.getPublisher();
        String title = newTM.getTitle();
        String content = newTM.getContent();
        msgToSend = "EDTTHRD\n" + "forumName:\n" + forumName + "\n"
                + "subForumName:\n" + subForumName + "\n" + "posterName:\n" + posterName + "\n" + "threadTitle:\n" + title + "\n"
                + "threadContent:\n" + content + "\n" + "editorName:\n" + editorName + "\n" + "editorPassword:\n" + editorPassword + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            edited = true;
        }
        return edited;
    }

    @Override
    public boolean editPost(Post newP, String editorName, String editorPassword) {
        boolean edited = false;
        ThreadMessage newTM = newP.getThread();
        int newTMid = newTM.getId();
        SubForum subForum = newTM.getSubForum();
        String subForumName = subForum.getSubForumName();
        Forum forum = subForum.getForum();
        String forumName = forum.getForumName();
        String posterName = newP.getPublisher();
        String title = newP.getTitle();
        String content = newP.getContent();
        msgToSend = "EDTPST\n" + "forumName:\n" + forumName + "\n"
                + "subForumName:\n" + subForumName + "\n" + "threadId:\n" + newTMid
                + "\n" + "posterName:\n" + posterName + "\n" + "postTitle:\n" + title + "\n"
                + "postContent:\n" + content + "\n" + "editorName:\n" + editorName + "\n"
                + "editorPassword:\n" + editorPassword + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            edited = true;
        }
        return edited;
    }

    @Override
    public boolean addModerator(String forumName, String subForumName,
            String newModerator, String userName, String password) {
        boolean added = false;
        msgToSend = "ADDMOD\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForumName + "\n"
                + "newModerator:\n" + newModerator + "\n"
                + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg != null && receivedMsg.contains("200ok")) {
            added = true;
        }
        return added;
    }

    @Override
    public List<ForumNotification> getNotification(String forumName, String userName, String password) {
        List<ForumNotification> notifications = null;
        msgToSend = "NOTI\n" + "forumName:\n" + forumName + "\n"
                + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            notifications = (List<ForumNotification>) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return notifications;
    }

    @Override
    public boolean removeModerator(String forumName, String subForum,
            String moderatorName, String userName, String password) {
        boolean remove = false;
        msgToSend = "REMMOD\n" + "forumName:\n" + forumName + "\n" + "subForumName:\n" + subForum + "\n"
                + "moderatorName:\n" + moderatorName + "\n" + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = (String) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        if (receivedMsg.contains("200ok")) {
            remove = true;
        }
        return remove;
    }

    @Override
    public int getNumOfThreadsInForum(String forumName, String userName, String password) {
        int counter = 0;
        msgToSend = "GETCOUNT\n" + "forumName:\n" + forumName + "\n" + "userName:\n" + userName + "\n"
                + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = objectFromServer.readObject() + "";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        counter = Integer.parseInt(receivedMsg);
        return counter;
    }

    @Override
    public int getNumOfUserThreads(String forumName, String userName,
            String requester, String password) {
        int counter = 0;
        msgToSend = "GETNUT\n" + "forumName:\n" + forumName + "\n"
                + "userName:\n" + userName + "\n" + "requester:\n" + requester + "\n"
                + "passwordRequester:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = objectFromServer.readObject() + "";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        counter = Integer.parseInt(receivedMsg);
        return counter;
    }

    @Override
    public int getNumOfForums(String userName, String password) {
        int counter = 0;
        msgToSend = "GETNF\n" + "\n" + "userName:\n" + userName + "\n"
                + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            receivedMsg = objectFromServer.readObject() + "";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        counter = Integer.parseInt(receivedMsg);
        return counter;
    }

    /*
     * MAP(forum name:String, number of members in forum:Integer)
     */
    @Override
    public List<String> getCommonMembers(String superAdminName,
            String password) {
        List<String> map = null;
        msgToSend = "GETCOM\n" + "userName:\n"
                + superAdminName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            map = (List<String>) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return map;
    }

    @Override
    public List<Member> getAllForumMembers(String forum, String userName,
            String password) {
        List<Member> members = null;
        msgToSend = "GETAM\n" + "forum:\n"
                + forum + "\n" + "userName:\n" + userName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            members = (List<Member>) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return members;
    }

    @Override
    public HashMap<String, List<String>> getUsersPostToUser(String forumName,
            String userName, String password) {
        HashMap<String, List<String>> map = null;
        msgToSend = "GETUPU\n" + "forumName:\n" + forumName + "\n" + "userName:\n"
                + userName + "\n" + "password:\n" + password + "\n";
        msgToSend += delimiter;
        stringToServer.print(msgToSend);
        stringToServer.flush();
        try {
            objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
            map = (HashMap<String, List<String>>) objectFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return map;
    }

    @Override
    public void listenToServer() {
        String recivedLine = null;
        try {
            recivedLine = this.stringFromServer.readLine();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println(recivedLine);
    }

    @Override
    public void sendListenerIdentifier() {
        String msg = "LISTENING\0";
        this.stringToServer.print(msg);
        this.stringToServer.flush();
    }
}
