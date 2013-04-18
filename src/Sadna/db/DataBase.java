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
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase implements DBInterface {

    String dataBaseFolder = "dataBase";

    public DataBase() {
    }

    @Override
    public Forum getForum(String forumName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SubForum getSubForum(String forumName, String subForumName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ThreadMessage getThread(String forumName, String subForumName, int messageID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User getUser(String userName) {
        throw new UnsupportedOperationException("Not supported yet.");
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
            outputstream = new FileOutputStream(path + "thread" + 
                                                threadMessage + ".obj");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String args[]) {
        DataBase db = new DataBase();
        Forum forum = new Forum(new Admin(null, null, null, null, null), "forum1");
        SubForum subForum = new SubForum(forum, "subforum1");
        ThreadMessage threadMessage = new ThreadMessage(subForum, "hi11");
        ThreadMessage threadMessage2 = new ThreadMessage(subForum, "hi22");
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
        db.addThread(threadMessage);
        db.addThread(threadMessage2);
        db.addPost(post);
        db.addPost(post2);
        db.addPost(post3);
    }

    @Override
    public List<SubForum> getSubForumsList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ThreadMessage> getThreadsList(String subForumName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNumberOfSubforums(String forumName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNumberOfThreads(String forumName, String subForumName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
