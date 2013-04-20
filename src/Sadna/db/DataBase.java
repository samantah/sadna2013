/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.db.API.DBInterface;

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

    public static void main(String args[]) {
        DataBase db = new DataBase();
        Forum forum = new Forum("forum1");
        SubForum subForum = new SubForum(forum, "subforum1");
        SubForum subForum2 = new SubForum(forum, "subforum2");
        ThreadMessage threadMessage = new ThreadMessage(subForum, "NA", "hi11", "publisher");
        ThreadMessage threadMessage2 = new ThreadMessage(subForum, "NA", "hi2aaa2", "publisher");
        Post post = new Post(threadMessage, "NA", "hi11post1", "publisher");
        Post post2 = new Post(threadMessage, "NA", "hi11post2", "publisher");
        Post post3 = new Post(threadMessage2, "NA", "hii222", "publisher");
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
        System.out.println(thread.getContent());
        Post post1 = db.getPost("forum1", "subForum1", 0, 1);
        System.out.println(post1.getContent());
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
    }
}
