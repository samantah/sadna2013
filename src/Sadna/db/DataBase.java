/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.SuperAdmin;
import Sadna.db.API.DBInterface;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class DataBase implements DBInterface {

    private DecimalFormat df = new DecimalFormat("000");
    private String dataBaseFolder = "dataBase";

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
    public boolean addSubForum(SubForum subForum, List<Moderator> listOfModerators) {
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
            String pathToMembers = pathToFolder;
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathToMembers
                    + "moderators.txt"));
            for (Moderator m : listOfModerators) {
                bw.write(m.getUserName() + "\n");
            }
            bw.close();

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
    public int getNumberOfThreads(String forumName, String subForumName) {
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
            if (!nameOfFile.endsWith(".obj")) {
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
    public boolean setSuperAdmin() {
        String userName = "superAdmin";
        String password = "superAdmin1234";
        SuperAdmin sa = new SuperAdmin(userName, password, "AdminMail", null);
        ObjectOutputStream obj;
        FileOutputStream outputstream;
        try {
            String path = dataBaseFolder;
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

//    @Override
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
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((currMod = br.readLine()) != null) {
                inputStream = new FileInputStream(pathToMembers + currMod + ".obj");
                objin = new ObjectInputStream(inputStream);
                Member readObject = (Member) objin.readObject();
                retList.add(readObject);
            }
            if (objin != null && inputStream != null) {
                objin.close();
                inputStream.close();
            }

        } catch (FileNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            String message = ex.getMessage();
            System.out.println(message);
            return null;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteModerator(Moderator moderator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    private void deleteFolder(String path) {
        File f = new File(path);
        File[] listFiles = f.listFiles();
        System.out.println(listFiles.length);
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
        Forum forum = new Forum("forum1");
        SubForum subForum = new SubForum(forum, "subForum1");
        SubForum subForum2 = new SubForum(forum, "subForum2");
        ThreadMessage threadMessage = new ThreadMessage(subForum, "NA", "hi11", "publisher");
        ThreadMessage threadMessage2 = new ThreadMessage(subForum, "NA", "hi2aaa2", "publisher");
        Post post = new Post(threadMessage, "NA", "hi11post1", "publisher");
        Post post2 = new Post(threadMessage, "NA", "hi11post2", "publisher");
        Post post3 = new Post(threadMessage2, "NA", "hii222", "publisher");
        db.addForum(forum);
        ArrayList<Moderator> arrayList = new ArrayList<Moderator>();
        arrayList.add(new Moderator("userNameMod", null, null, null, null));
        arrayList.add(new Moderator("userNameMod2", null, null, null, null));

        db.addSubForum(subForum, arrayList);
        db.addSubForum(subForum2, new ArrayList<Moderator>());
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
        System.out.println(db.getNumberOfThreads("forum1", "subForum1"));
        Member member = new Member("user1", "pass1234", "mail", "forum1", null);
        db.addMember(member);
        System.out.println(db.getMember("forum1", "user1").getUserName());
//        db.deletePost(post2);

    }
}
