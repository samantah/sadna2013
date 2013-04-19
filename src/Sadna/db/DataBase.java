/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.Client.Admin;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.User;
import Sadna.db.API.DBInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBase implements DBInterface {

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
                    + "/" + subForumName + "/" + "thread" + threadID + ".obj");
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
    public Post getPost(String forumName, String subForumName, int ThreadID, int postID) {
        FileInputStream inputStream;
        ObjectInputStream obj;
        Post returnPost = null;
        try {
            inputStream = new FileInputStream(dataBaseFolder + "/" + forumName
                    + "/" + subForumName + "/" + "thread" + ThreadID
                    + "/" + "message" + postID + ".obj");
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
    public User getUser(String forumName, String userName) {
        FileInputStream inputStream;
        ObjectInputStream obj;
        User returnUser = null;
        try {
            inputStream = new FileInputStream(dataBaseFolder + "/" + forumName
                    + "/" + userName + ".obj");
            obj = new ObjectInputStream(inputStream);
            returnUser = (User) obj.readObject();
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
        return returnUser;
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
    public boolean addSubForum(SubForum subForum) {
        //get the names and IDs of the post
        String forum = subForum.getForum().getForumName();
        String subForumStr = subForum.getSubForumName();
        ObjectOutputStream obj;
        FileOutputStream outputstream;
        try {
            String path = dataBaseFolder + "/" + forum + "/"; //save the path of the post
            boolean mkdirs = new File(path).mkdirs();
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
        try {
            String path = dataBaseFolder + "/" + forum
                    + "/" + subForum + "/" + "thread" + threadMessage + "/"; //save the path of the post
            boolean mkdirs = new File(path).mkdirs();
            outputstream = new FileOutputStream(path + "/message" + postid + ".obj");
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
        String subForum = thread.getSubForum().getSubForumName();
        int threadMessage = thread.getId();
        ObjectOutputStream obj;
        FileOutputStream outputstream;
        try {
            String path = dataBaseFolder + "/" + forum
                    + "/" + subForum + "/"; //save the path of the post
            boolean mkdirs = new File(path).mkdirs();
            outputstream = new FileOutputStream(path + "thread"
                    + threadMessage + ".obj");
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
            String path = dataBaseFolder + "/" + forumStr + "/";
            outputstream = new FileOutputStream(path + userName + ".obj");
            obj = new ObjectOutputStream(outputstream);

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

    public static void main(String args[]) {
        DataBase db = new DataBase();
        Forum forum = new Forum(new Admin(null, null, null, null, null), "forum1");
        SubForum subForum = new SubForum(forum, "subforum1");
        SubForum subForum2 = new SubForum(forum, "subforum2");
        ThreadMessage threadMessage = new ThreadMessage(subForum, "hi11");
        ThreadMessage threadMessage2 = new ThreadMessage(subForum, "hi2aaa2");
        Post post = new Post(threadMessage, "hi11post1");
        Post post2 = new Post(threadMessage, "hi11post2");
        Post post3 = new Post(threadMessage2, "hii222");
        forum.addSubForum(subForum);
        subForum.addThreadMessage(threadMessage);
        subForum.addThreadMessage(threadMessage2);
        subForum.addModerator(new Moderator(null, null, null, null, null));
        threadMessage.addPost(post);
        threadMessage.addPost(post2);
        db.addForum(forum);
        db.addSubForum(subForum);
        db.addSubForum(subForum2);
        db.addThread(threadMessage);
        db.addThread(threadMessage2);
        db.addPost(post);
        db.addPost(post2);
        db.addPost(post3);
        ThreadMessage thread = db.getThread("forum1", "subForum1", 1);
        System.out.println(thread.getMessage());
        Post post1 = db.getPost("forum1", "subForum1", 0, 1);
        System.out.println(post1.getMessage());
        List<SubForum> subForumsList = db.getSubForumsList("forum1");
        for (SubForum sf : subForumsList) {
            System.out.println(sf.getSubForumName());
        }
        List<ThreadMessage> threadsList = db.getThreadsList("forum1", "subForum1");
        for (ThreadMessage threadMessage1 : threadsList) {
            System.out.println(threadMessage1.getMessage());
        }
        System.out.println(db.getNumberOfSubforums("forum1"));
        System.out.println(db.getNumberOfThreads("forum1", "subForum1"));
    }
}
