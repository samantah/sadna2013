/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Client.Admin;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.db.API.DBInterface;
import Sadna.db.PolicyEnums.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase implements DBInterface {

    private DecimalFormat df = new DecimalFormat("000");
    public String dataBaseFolder = "dataBase";

    public DataBase() {
    }

    @Override
    public Forum getForum(String forumName) {
        FileInputStream inputStream;
        ObjectInputStream obj;
        Forum returnForum = null;
        try {
            inputStream = new FileInputStream(dataBaseFolder + "/" + forumName + ".obj");
            obj = new ObjectInputStream(inputStream);
            returnForum = (Forum) obj.readObject();
            obj.close();
            inputStream.close();
        } catch (FileNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        }
        return returnForum;
    }

    @Override
    public SubForum getSubForum(String forumName, String subForumName) {
        FileInputStream inputStream;
        ObjectInputStream obj;
        SubForum returnSubForum = null;
        try {
            inputStream = new FileInputStream(dataBaseFolder + "/" + forumName
                    + "/" + subForumName + ".obj");
            obj = new ObjectInputStream(inputStream);
            returnSubForum = (SubForum) obj.readObject();
            obj.close();
            inputStream.close();
        } catch (FileNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        }
        return returnSubForum;
    }

    @Override
    public ThreadMessage getThread(String forumName, String subForumName, int threadID) {
        FileInputStream inputStream;
        ObjectInputStream obj;
        ThreadMessage returnThreadMessage = null;
        try {
            inputStream = new FileInputStream(dataBaseFolder + "/" + forumName
                    + "/" + subForumName + "/" + "thread" + df.format(threadID) + ".obj");
            obj = new ObjectInputStream(inputStream);
            returnThreadMessage = (ThreadMessage) obj.readObject();
            obj.close();
            inputStream.close();
        } catch (FileNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        }
        return returnThreadMessage;

    }

    @Override
    public Post getPost(String forumName, String subForumName, int threadID, int postID) {
        FileInputStream inputStream;
        ObjectInputStream obj;
        Post returnPost = null;
        String path = dataBaseFolder + "/" + forumName
                + "/" + subForumName + "/" + "thread" + df.format(threadID)
                + "/" + "message" + df.format(postID) + ".obj";
        try {
            inputStream = new FileInputStream(path);
            obj = new ObjectInputStream(inputStream);
            returnPost = (Post) obj.readObject();
            obj.close();
            inputStream.close();
        } catch (FileNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        }
        return returnPost;
    }

    @Override
    public Member getMember(String forumName, String userName) {
        FileInputStream inputStream;
        ObjectInputStream obj;
        Member returnMemeber = null;
        String path = dataBaseFolder + "/" + forumName
                + "/members/" + userName + ".obj";
        try {
            inputStream = new FileInputStream(path);
            obj = new ObjectInputStream(inputStream);
            returnMemeber = (Member) obj.readObject();
            obj.close();
            inputStream.close();
        } catch (FileNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        }
        return returnMemeber;
    }

    @Override
    public boolean addForum(Forum forum) {
        //get the names and IDs of the post
        String forumStr = forum.getForumName();
        ObjectOutputStream obj;
        FileOutputStream outputstream;
        try {
            String path = dataBaseFolder + "/"; //save the path of the post
            boolean mkdirs = new File(path).mkdirs();
            String pathToFolder = dataBaseFolder + "/" + forumStr + "/";
            new File(pathToFolder).mkdirs();
            String pathToMembers = dataBaseFolder + "/" + forumStr + "/members/";
            new File(pathToMembers).mkdirs();
            outputstream = new FileOutputStream(path + forumStr + ".obj");
            obj = new ObjectOutputStream(outputstream);
            obj.writeObject(forum);//write the object to the file
            obj.close();
            outputstream.close();
        } catch (FileNotFoundException ex) { //cannot open the file
            String message = ex.getMessage();
            System.out.println(message);
            return false;
        } catch (IOException e) { // cannot write into the file
            String message = e.getMessage();
            System.out.println(message);
            return false;
        }

        return true;

    }

    @Override
    public boolean addSubForum(SubForum subForum, List<Member> listOfModerators) {
        //get the names and IDs of the post
        String forum = subForum.getForum().getForumName();
        String subForumStr = subForum.getSubForumName();
        ObjectOutputStream obj;
        FileOutputStream outputstream;
        try {
            String path = dataBaseFolder + "/" + forum + "/"; //save the path of the post
            new File(path).mkdirs();
            String pathToFolder = path + "/" + subForumStr + "/";
            new File(pathToFolder).mkdirs();
            PrintWriter bw = new PrintWriter(new FileWriter(pathToFolder + "moderators.txt"));
            bw.close();
            String pathToMembers = pathToFolder;
            for (Member m : listOfModerators) {
                addModerator(new Moderator(m), subForum);
            }
            outputstream = new FileOutputStream(path + subForumStr + ".obj");
            obj = new ObjectOutputStream(outputstream);
            obj.writeObject(subForum);//write the object to the file
            obj.close();
            outputstream.close();
        } catch (FileNotFoundException ex) { //cannot open the file
            String message = ex.getMessage();
            System.out.println(message);
            return false;
        } catch (IOException e) { // cannot write into the file
            String message = e.getMessage();
            System.out.println(message);
            return false;
        }

        return true;
    }

    @Override
    public boolean addPost(Post post) {
        //get the names and IDs of the post
        String forum = post.getThread().getSubForum().getForum().getForumName();
        String subForum = post.getThread().getSubForum().getSubForumName();
        int threadMessage = post.getThread().getId();
        int postid = post.getId();
        ObjectOutputStream obj;
        FileOutputStream outputstream;

        ThreadMessage thread = post.getThread();
        String pathToThread = this.dataBaseFolder + "/" + forum + "/"
                + subForum + "/";
        try {
            outputstream = new FileOutputStream(pathToThread + "thread"
                    + df.format(threadMessage) + ".obj");
            obj = new ObjectOutputStream(outputstream);
            obj.writeObject(thread);
            outputstream.close();
            obj.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        try {
            String path = dataBaseFolder + "/" + forum
                    + "/" + subForum + "/" + "thread" + df.format(threadMessage) + "/"; //save the path of the post
            boolean mkdirs = new File(path).mkdirs();
            outputstream = new FileOutputStream(path + "/message" + df.format(postid) + ".obj");
            obj = new ObjectOutputStream(outputstream);
            obj.writeObject(post);//write the object to the file
            obj.close();
            outputstream.close();
        } catch (FileNotFoundException ex) { //cannot open the file
            String message = ex.getMessage();
            System.out.println(message);
            return false;
        } catch (IOException e) { // cannot write into the file
            String message = e.getMessage();
            System.out.println(message);
            return false;
        }

        return true;
    }

    @Override
    public boolean addThread(ThreadMessage thread) {
        //get the names and IDs of the thread
        String forum = thread.getSubForum().getForum().getForumName();
        String subForumStr = thread.getSubForum().getSubForumName();
        int threadMessage = thread.getId();
        ObjectOutputStream obj;
        FileOutputStream outputstream;
        SubForum subForumToRewrite = thread.getSubForum();
        String pathToSubForum = this.dataBaseFolder + "/" + forum + "/";
        try {
            outputstream = new FileOutputStream(pathToSubForum + subForumStr + ".obj");
            obj = new ObjectOutputStream(outputstream);
            obj.writeObject(subForumToRewrite);
            outputstream.close();
            obj.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        try {
            String path = dataBaseFolder + "/" + forum
                    + "/" + subForumStr + "/"; //save the path of the post
            boolean mkdirs = new File(path).mkdirs();
            String pathToFolder = path + "thread" + df.format(threadMessage) + "/";
            new File(pathToFolder).mkdirs();
            outputstream = new FileOutputStream(path + "thread"
                    + df.format(threadMessage) + ".obj");
            obj = new ObjectOutputStream(outputstream);
            obj.writeObject(thread);//write the object to the file
            obj.close();
            outputstream.close();
        } catch (FileNotFoundException ex) { //cannot open the file
            String message = ex.getMessage();
            System.out.println(message);
            return false;
        } catch (IOException e) { // cannot write into the file
            String message = e.getMessage();
            System.out.println(message);
            return false;
        }

        return true;

    }

    @Override
    public boolean addMember(Member member) {
        String forumStr = member.getForum();
        String userName = member.getUserName();
        ObjectOutputStream obj;
        FileOutputStream outputstream;
        try {
            String path = dataBaseFolder + "/" + forumStr + "/members/";
            boolean mkdirs = new File(path).mkdirs();
            outputstream = new FileOutputStream(path + userName + ".obj");
            obj = new ObjectOutputStream(outputstream);
            obj.writeObject(member);
            obj.close();
            outputstream.close();

        } catch (FileNotFoundException ex) { //cannot open the file
            String message = ex.getMessage();
            System.out.println(message);
            return false;
        } catch (IOException e) { // cannot write into the file
            String message = e.getMessage();
            System.out.println(message);
            return false;
        }

        return true;
    }

    @Override
    public List<SubForum> getSubForumsList(String forumName) {
        FileInputStream inputStream;
        ObjectInputStream obj;
        ArrayList<SubForum> retList = new ArrayList<SubForum>();
        String pathToFolder = dataBaseFolder + "/" + forumName + "/";
        File folder = new File(pathToFolder);
        File listOfFiles[] = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String nameOfFile = listOfFiles[i].getName();
            if (!nameOfFile.endsWith(".obj")) {
                continue;
            }
            try {
                inputStream = new FileInputStream(pathToFolder + nameOfFile);
                obj = new ObjectInputStream(inputStream);
                SubForum readObject = (SubForum) obj.readObject();
                retList.add(readObject);
                obj.close();
                inputStream.close();

            } catch (FileNotFoundException ex) {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            } catch (IOException | ClassNotFoundException ex) {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            }
        }
        return retList;
    }

    @Override
    public List<ThreadMessage> getThreadsList(String forumName, String subForumName) {
        FileInputStream inputStream;
        ObjectInputStream obj;
        ArrayList<ThreadMessage> retList = new ArrayList<ThreadMessage>();
        String pathToFolder = dataBaseFolder + "/" + forumName + "/"
                + subForumName + "/";
        File folder = new File(pathToFolder);
        File listOfFiles[] = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String nameOfFile = listOfFiles[i].getName();
            if (!nameOfFile.endsWith(".obj")) {
                continue;
            }
            try {
                inputStream = new FileInputStream(pathToFolder + nameOfFile);
                obj = new ObjectInputStream(inputStream);
                ThreadMessage readObject = (ThreadMessage) obj.readObject();
                retList.add(readObject);
                obj.close();
                inputStream.close();

            } catch (FileNotFoundException ex) {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            } catch (IOException | ClassNotFoundException ex) {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            }
        }
        return retList;
    }

    @Override
    public int getNumberOfSubforums(String forumName) {
        String pathToFolder = dataBaseFolder + "/" + forumName + "/";
        File folder = new File(pathToFolder);
        File listOfFiles[] = folder.listFiles();
        int numberOfSubForums = 0;
        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName();
            if (fileName.endsWith(".obj")) {
                numberOfSubForums++;
            }
        }
        return numberOfSubForums;
    }

    @Override
    public int getNumberOfThreadsInSubForum(String forumName, String subForumName) {
        String pathToFolder = dataBaseFolder + "/" + forumName + "/"
                + subForumName + "/";
        File folder = new File(pathToFolder);
        File listOfFiles[] = folder.listFiles();
        int numberOfThreads = 0;
        for (int i = 0; i < listOfFiles.length; i++) {
            String fileName = listOfFiles[i].getName();
            if (fileName.endsWith(".obj")) {
                numberOfThreads++;
            }
        }
        return numberOfThreads;
    }

    @Override
    public List<Member> getAllMembers(String forumName) {
        FileInputStream inputStream;
        ObjectInputStream obj;
        ArrayList<Member> retList = new ArrayList<Member>();
        String pathToFolder = dataBaseFolder + "/" + forumName + "/members/";
        File folder = new File(pathToFolder);
        File listOfFiles[] = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String nameOfFile = listOfFiles[i].getName();
            if (!nameOfFile.endsWith(".obj")) {
                continue;
            }
            try {
                inputStream = new FileInputStream(pathToFolder + nameOfFile);
                obj = new ObjectInputStream(inputStream);
                Member readObject = (Member) obj.readObject();
                retList.add(readObject);
                obj.close();
                inputStream.close();

            } catch (FileNotFoundException ex) {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            } catch (IOException | ClassNotFoundException ex) {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            }
        }
        return retList;
    }

    @Override
    public List<Forum> getForumsList() {
        FileInputStream inputStream;
        ObjectInputStream obj;
        ArrayList<Forum> retList = new ArrayList<Forum>();
        String pathToFolder = dataBaseFolder + "/";
        File folder = new File(pathToFolder);
        File listOfFiles[] = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String nameOfFile = listOfFiles[i].getName();
            if (!nameOfFile.endsWith(".obj") || nameOfFile.contains("Admin")) {
                continue;
            }
            try {
                inputStream = new FileInputStream(pathToFolder + nameOfFile);
                obj = new ObjectInputStream(inputStream);
                Forum readObject = (Forum) obj.readObject();
                retList.add(readObject);
                obj.close();
                inputStream.close();

            } catch (FileNotFoundException ex) {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            } catch (IOException | ClassNotFoundException ex) {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            }
        }
        return retList;
    }

    @Override
    public boolean setSuperAdmin(ClientCommunicationHandlerInterface ch) {
        String userName = "superAdmin";
        String password = "superAdmin1234";
        SuperAdmin sa = new SuperAdmin(userName, password, "AdminMail", ch);
        ObjectOutputStream obj;
        FileOutputStream outputstream;
        try {

            String path = dataBaseFolder + "/";
            new File(path).mkdir();
            outputstream = new FileOutputStream(path + userName + ".obj");
            obj = new ObjectOutputStream(outputstream);
            obj.writeObject(sa);
            obj.close();
            outputstream.close();

        } catch (FileNotFoundException ex) { //cannot open the file
            String message = ex.getMessage();
            System.out.println(message);
            return false;
        } catch (IOException e) { // cannot write into the file
            String message = e.getMessage();
            System.out.println(message);
            return false;
        }

        return true;
    }

    @Override
    public SuperAdmin getSuperAdmin() {
        FileInputStream inputStream;
        ObjectInputStream obj;
        SuperAdmin superAdmin = null;
        try {
            inputStream = new FileInputStream(dataBaseFolder + "/superAdmin.obj");
            obj = new ObjectInputStream(inputStream);
            superAdmin = (SuperAdmin) obj.readObject();
            obj.close();
            inputStream.close();
        } catch (FileNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        }
        return superAdmin;
    }

    @Override
    public List<Member> getModerators(String forumName, String subForumName) {
        FileInputStream inputStream = null;
        ObjectInputStream objin = null;
        ArrayList<Member> retList = new ArrayList<Member>();
        String currMod;
        String path = this.dataBaseFolder + "/" + forumName + "/"
                + subForumName + "/moderators.txt";
        String pathToMembers = this.dataBaseFolder + "/" + forumName
                + "/members/";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
            while ((currMod = br.readLine()) != null) {
                try {
                    inputStream = new FileInputStream(pathToMembers + currMod + ".obj");
                    objin = new ObjectInputStream(inputStream);
                    Member readObject = (Member) objin.readObject();
                    retList.add(readObject);
                } catch (FileNotFoundException ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                } catch (IOException ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                } catch (ClassNotFoundException ex) {
                    String message = ex.getMessage();
                    System.out.println(message);
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        } catch (IOException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retList;
    }

    @Override
    public List<Post> getPostList(String forumName, String subForumName, int threadID) {
        FileInputStream inputStream;
        ObjectInputStream obj;
        ArrayList<Post> retList = new ArrayList<Post>();
        String pathToFolder = dataBaseFolder + "/" + forumName + "/"
                + subForumName + "/thread" + df.format(threadID) + "/";
        File folder = new File(pathToFolder);
        File listOfFiles[] = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String nameOfFile = listOfFiles[i].getName();
            if (!nameOfFile.endsWith(".obj")) {
                continue;
            }
            try {
                inputStream = new FileInputStream(pathToFolder + nameOfFile);
                obj = new ObjectInputStream(inputStream);
                Post readObject = (Post) obj.readObject();
                retList.add(readObject);
                obj.close();
                inputStream.close();

            } catch (FileNotFoundException ex) {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            } catch (IOException | ClassNotFoundException ex) {
                String message = ex.getMessage();
                System.out.println(message);
                return null;
            }
        }
        return retList;
    }

    @Override
    public boolean addModerator(Moderator moderator, SubForum subForum) {
        String forum = moderator.getForum();
        String userName = moderator.getUserName();
        String subForumName = subForum.getSubForumName();
        Member memberToBeModerator = getMember(forum, userName);
        if (memberToBeModerator == null) {
            return false;
        }
        deleteMember(memberToBeModerator);
        addMember(moderator);
        String path = this.dataBaseFolder + "/" + forum
                + "/" + subForumName + "/moderators.txt";
        try {
            PrintWriter bw = new PrintWriter(new FileWriter(path, true));
            bw.println(userName);
            bw.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    @Override
    public boolean deleteForum(Forum forum) {
        //get the names and IDs of the post
        String forumName = forum.getForumName();
        String path = dataBaseFolder + "/";
        String pathToFolder = path + "/" + forumName + "/";
        String pathToForum = path + "/" + forumName + ".obj";
        File fileToDelete = new File(pathToForum);
        fileToDelete.delete();
        deleteFolder(pathToFolder);
        return true;
    }

    @Override
    public boolean deleteSubForum(SubForum subForum) {
        //get the names and IDs of the post
        String forum = subForum.getForum().getForumName();
        String subForumStr = subForum.getSubForumName();
        ObjectOutputStream obj;
        FileOutputStream outputstream;
        String path = dataBaseFolder + "/" + forum + "/";
        String pathToFolder = path + "/" + subForumStr + "/";
        String pathToSubForum = path + "/" + subForumStr + ".obj";
        File fileToDelete = new File(pathToSubForum);
        fileToDelete.delete();
        deleteFolder(pathToFolder);
        return true;
    }

    @Override
    public boolean deleteMember(Member member) {
        String forum = member.getForum();
        String path = this.dataBaseFolder + "/" + forum + "/members/";
        File file = new File(path);
        File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            String currMemberFileName = listFiles[i].getName();
            int fileNameLength = (int) currMemberFileName.length();
            String currMemberUserName = currMemberFileName.substring(0, fileNameLength - 4);
            if (member.getUserName().equals(currMemberUserName)) {
                listFiles[i].delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteModerator(Moderator moderator, String subForum) {
        boolean moderatorExist = false;
        String forum = moderator.getForum();
        String userName = moderator.getUserName();
        String path = this.dataBaseFolder + "/" + forum + "/" + subForum + "/moderators.txt";
        File file = new File(path);
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(file);
            String newPath = path + "tmp";
            File newFile = new File(newPath);
            FileWriter fileWriter = new FileWriter(newPath);
            PrintWriter pw = new PrintWriter(fileWriter);
            while (fileScanner.hasNextLine()) {
                String next = fileScanner.nextLine();
                if (next.equals(userName)) {
                    moderatorExist = true;
                    continue;
                }
                pw.println(next);
            }
            pw.close();
            fileWriter.close();
            fileScanner.close();
            file.delete();
            newFile.renameTo(file);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        if (!moderatorExist) {
            return true;
        }
        ClientCommunicationHandlerInterface conHand = moderator.getConHand();
        String email = moderator.getEmail();
        String password = moderator.getPassword();
        Member memberWasModerator = new Member(userName, password, email, forum, conHand);
        addMember(memberWasModerator);
        return true;
    }

    @Override
    public boolean deletePost(Post post) {
        //get the names and IDs of the post
        String forum = post.getThread().getSubForum().getForum().getForumName();
        String subForum = post.getThread().getSubForum().getSubForumName();
        int threadMessage = post.getThread().getId();
        int postid = post.getId();
        ObjectOutputStream obj;
        FileOutputStream outputstream;
        File fileToDelete;


        String path = dataBaseFolder + "/" + forum
                + "/" + subForum + "/" + "thread" + df.format(threadMessage) + "/"; //save the path of the post
        fileToDelete = new File(path + "/message" + df.format(postid) + ".obj");
        if (fileToDelete.delete()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteThread(ThreadMessage thread) {
        //get the names and IDs of the thread
        String forum = thread.getSubForum().getForum().getForumName();
        String subForumStr = thread.getSubForum().getSubForumName();
        int threadMessage = thread.getId();
        ObjectOutputStream obj;
        FileOutputStream outputstream;
        File fileToDelete;
        SubForum subForumToRewrite = thread.getSubForum();
        String pathToSubForum = this.dataBaseFolder + "/" + forum + "/";
        String path = dataBaseFolder + "/" + forum
                + "/" + subForumStr + "/"; //save the path of the post
        String pathToFolder = path + "thread" + df.format(threadMessage) + "/";
        fileToDelete = new File(path + "thread"
                + df.format(threadMessage) + ".obj");
        fileToDelete.delete();
        deleteFolder(pathToFolder);
        return true;

    }

    @Override
    public void deleteFolder(String path) {
        File f = new File(path);
        File[] listFiles = f.listFiles();
        if (listFiles == null) {
            return;
        }
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isDirectory()) {
                String newPath = listFiles[i] + "/";
                deleteFolder(newPath);
            }
            listFiles[i].delete();
        }
        f.delete();
    }

    public static void main(String args[]) {

        DataBase db = new DataBase();
        db.deleteFolder(db.dataBaseFolder);
        Forum forum = new Forum("forum1", new Policy(enumNotiImidiOrAgre.IMIDIATE, enumNotiFriends.ALLMEMBERS, enumDelete.LIMITED, enumAssignModerator.NO_RESTRICTION, enumCancelModerator.NO_RESTRICTION, 1, 1));
        SubForum subForum = new SubForum(forum, "subForum1");
        SubForum subForum2 = new SubForum(forum, "subForum2");
        ThreadMessage threadMessage = new ThreadMessage(subForum, "NA", "hi11", "publisher");
        ThreadMessage threadMessage2 = new ThreadMessage(subForum, "NA", "hi2aaa2", "publisher");
        Post post = new Post(threadMessage, "NA", "hi11post1", "publisher");
        Post post2 = new Post(threadMessage, "NA", "hi11post2", "publisher");
        Post post3 = new Post(threadMessage2, "NA", "hii222", "publisher");
        db.addForum(forum);
        Member member = new Member("m1", "password1", "a", "forum1", null);
        Member member2 = new Member("m2", "password1", "a", "forum1", null);
        db.addMember(member);
        db.addMember(member2);
        ArrayList<Member> arrayList = new ArrayList<Member>();
        arrayList.add(member);
        arrayList.add(member2);
        db.addSubForum(subForum, arrayList);
        db.addSubForum(subForum2, new ArrayList<Member>());
        db.addThread(threadMessage);
        db.addThread(threadMessage2);
        db.addPost(post);
        db.addPost(post2);
        db.addPost(post3);
        ThreadMessage thread = db.getThread("forum1", "subForum1", 1);
        System.out.println("thread: " + thread.getContent());
        Post post1 = db.getPost("forum1", "subForum1", 0, 0);
        System.out.println("post: " + post1.getContent());
        List<SubForum> subForumsList = db.getSubForumsList("forum1");
        for (SubForum sf : subForumsList) {
            System.out.println(sf.getSubForumName());
        }
        List<ThreadMessage> threadsList = db.getThreadsList("forum1", "subForum1");
        for (ThreadMessage threadMessage1 : threadsList) {
            System.out.println(threadMessage1.getContent());
        }
        System.out.println(db.getNumberOfSubforums("forum1"));
        System.out.println(db.getNumberOfThreadsInSubForum("forum1", "subForum1"));
        Member member3 = new Member("user1", "pass1234", "mail", "forum1", null);
        db.addMember(member3);
        System.out.println(db.getMember("forum1", "user1").getUserName());
        Moderator moderator = new Moderator(member3);
        db.addModerator(moderator, subForum);
        db.deleteModerator(moderator, subForum.getSubForumName());

    }

    @Override
    public List<Admin> getAllAdmins() {
        FileInputStream inputStream;
        ObjectInputStream obj;
        String path = dataBaseFolder;
        File folder = new File(path);
        File[] listFiles = folder.listFiles();
        ArrayList<Admin> adminsList = new ArrayList<>();
        for (int i = 0; i < listFiles.length; i++) {
            String fileName = listFiles[i].getName();
            if (listFiles[i].isDirectory()) {
                String pathToMembers = path + "/" + fileName + "members/";
                File membersFolder = new File(pathToMembers);
                File[] members = membersFolder.listFiles();
                for (int j = 0; j < members.length; j++) {
                    try {
                        String member = members[i].getName();
                        inputStream = new FileInputStream(pathToMembers + member);
                        obj = new ObjectInputStream(inputStream);
                        Member currMember = (Member) obj.readObject();
                        if (currMember instanceof Admin) {
                            adminsList.add((Admin) currMember);
                            break;
                        }
                    } catch (FileNotFoundException ex) {
                        System.out.println(ex.getMessage());
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            }
        }

        return adminsList;
    }

    @Override
    public int getNumberOfThreadsInForum(String forumName) {
        String pathToFolder = dataBaseFolder + "/" + forumName + "/";
        File folder = new File(pathToFolder);
        File listOfFiles[] = folder.listFiles();
        int numberOfThreads = 0;
        for (int i = 0; i < listOfFiles.length; i++) {
            String folderName = listOfFiles[i].getName();
            if (listOfFiles[i].isDirectory() && !folderName.equals("members")) {
                numberOfThreads += getNumberOfThreadsInSubForum(forumName, folderName);
            }
        }
        return numberOfThreads;
    }

    @Override
    public int getNumberOfUserThreads(String forumName, Member member) {
        int counter = 0;
        String userName = member.getUserName();
        List<SubForum> subForumsList = getSubForumsList(forumName);
        for (SubForum subForum : subForumsList) {
            String subForumName = subForum.getSubForumName();
            List<ThreadMessage> threadsList = getThreadsList(forumName, subForumName);
            for (ThreadMessage threadMessage : threadsList) {
                if (threadMessage.getPublisher().equals(userName)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    public int getNumberOfForums() {
        List<Forum> forumsList = getForumsList();
        return forumsList.size();
    }

    @Override
    public List<String> getCommonMembers() {
        List<Forum> forumsList = getForumsList();
        HashMap<Member, Integer> hashMap = new HashMap<>();
        for (Forum forum : forumsList) {
            String forumName = forum.getForumName();
            List<Member> allMembers = getAllMembers(forumName);
            for (Member member : allMembers) {
                if (hashMap.containsKey(member)) {
                    Integer numberOfOccurance = hashMap.get(member);
                    int intValue = numberOfOccurance.intValue();
                    intValue++;
                    hashMap.put(member, new Integer(intValue));
                } else {
                    hashMap.put(member, 1);
                }
            }
        }
        ArrayList<String> listOfCommonMembers = new ArrayList<>();
        for (Map.Entry<Member, Integer> entry : hashMap.entrySet()) {
            Member member = entry.getKey();
            int numberOfOccurances = entry.getValue().intValue();
            if (numberOfOccurances > 1) {
                listOfCommonMembers.add(member.getUserName());
            }
        }
        return listOfCommonMembers;
    }

    @Override
    public HashMap<String, List<String>> getUsersPostToUser(String forumName) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        List<SubForum> subForumsList = getSubForumsList(forumName);
        for (SubForum subForum : subForumsList) {
            String subForumName = subForum.getSubForumName();
            List<ThreadMessage> threadsList = getThreadsList(forumName, subForumName);
            for (ThreadMessage threadMessage : threadsList) {
                int threadID = threadMessage.getId();
                String threadPublisher = threadMessage.getPublisher();
                ArrayList<String> arrayList;
                if (!hashMap.containsKey(threadPublisher)) {
                    arrayList = new ArrayList<>();
                } else {
                    arrayList = (ArrayList<String>) hashMap.get(threadPublisher);
                }
                List<Post> postsList = getPostList(forumName, subForumName, threadID);
                for (Post post : postsList) {
                    String postPublisher = post.getPublisher();
                    if (!arrayList.contains(postPublisher)) {
                        arrayList.add(postPublisher);
                    }
                }
                hashMap.put(threadPublisher, arrayList);
            }
        }
        System.out.println(hashMap);
        return hashMap;
    }

    @Override
    public void initiateDataBase() {
        String path = this.dataBaseFolder + "/";
        File file = new File(path);
        File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            File currFile = listFiles[i];
            String name = currFile.getName();
            if (name.contains("superAdmin")) {
                continue;
            }
            if (currFile.isDirectory()) {
                deleteFolder(path + name);
            } else {
                currFile.delete();
            }
        }

    }
}
