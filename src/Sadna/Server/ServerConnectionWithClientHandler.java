package Sadna.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import Sadna.Server.API.ConnectionHandlerServerInterface;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

public class ServerConnectionWithClientHandler implements ConnectionHandlerServerInterface {

    private Socket serverSocket;
    private PrintWriter stringToClient;
    private ObjectOutputStream objectOutputToClient;
    private BufferedReader stringFromClient;
    private ObjectInputStream objectInputFromClient;

    public ServerConnectionWithClientHandler(Socket socket) {
        serverSocket = socket;
        try {
            stringToClient = new PrintWriter(serverSocket.getOutputStream(), true);
            stringFromClient = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            objectOutputToClient = new ObjectOutputStream(serverSocket.getOutputStream());
//			objectInputFromClient = new ObjectInputStream(serverSocket.getInputStream());
//			System.out.println("*555555*****");

        } catch (Exception e) {
            System.out.println(">> Got Exception in ServerConnectionWithClientHandler - constructor");
        }
    }

    @Override
    public void sendOK() {
        stringToClient.println("200ok");
    }

    @Override
    public void sendNotFound() {
        stringToClient.println("404 Not Found");
    }

    @Override
    public void sendErrorInServer() {
        stringToClient.println("500 Internal Server Error");
    }

    @Override
    public void sendSubForumsList(List<SubForum> subForumsList) {
        try {
            objectOutputToClient.writeObject(subForumsList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(">> Got Exception in ServerConnectionWithClientHandler - sendSubForumsList");
        }
    }

    @Override
    public void sendSubForum(SubForum subForum) {
        try {
            objectOutputToClient.writeObject(subForum);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(">> Got Exception in ServerConnectionWithClientHandler - sendSubForum");
        }
    }

    @Override
    public void sendThreadsList(List<ThreadMessage> threadsList) {
        try {
            objectOutputToClient.writeObject(threadsList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(">> Got Exception in ServerConnectionWithClientHandler - sendThreadsList");
        }
    }

    @Override
    public void sendThreadMeassage(ThreadMessage threadMessage) {
        try {
            objectOutputToClient.writeObject(threadMessage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(">> Got Exception in ServerConnectionWithClientHandler - sendThreadMeassage");
        }
    }

    @Override
    public void sendForumsList(List<Forum> forumsList) {
        try {
            objectOutputToClient.writeObject(forumsList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(">> Got Exception in ServerConnectionWithClientHandler - sendForumsList");

        }
    }

    @Override
    public void sendForum(Forum forum) {
        try {
            objectOutputToClient.writeObject(forum);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(">> Got Exception in ServerConnectionWithClientHandler - sendForum");

        }
    }

    @Override
    public void sendAllPosts(List<Post> allPosts) {
        try {
            objectOutputToClient.writeObject(allPosts);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(">> Got Exception in ServerConnectionWithClientHandler - sendAllPosts");

        }
    }

    @Override
    public String receiveRequestFromClient() {
        String request = "";
        String tmpline = "";
        try {

//			System.out.println("Reading line from client in - ServerConnectionWithClientHandler");
            tmpline = stringFromClient.readLine();
            if (tmpline == null) {
                return null;
            }
            while ((tmpline != null) && (!tmpline.equals("\0"))) {
                request += tmpline + "\n";
                tmpline = stringFromClient.readLine();

//				if(request.equals("\0"))
//					break;
            }
//			System.out.println("----- request from client : " + request);
//			System.out.println("Done reading line from client");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(">> Got Exception in ServerConnectionWithClientHandler - receiveRequestFromClient");
            return "end";
        }
        return request;

    }

    public void closeSocket() {
        try {
            objectOutputToClient.close();
            stringToClient.close();
            stringFromClient.close();
            serverSocket.close();
            System.out.println("Closed socket..");
        } catch (Exception e) {
            System.out.println(">> Couldn't close socket in server side..");
        }
    }

    @Override
    public void sendErrorNoAuthorized() {
        stringToClient.println("401 Unauthorized");
    }

    @Override
    public void sendNotifications(Vector<ForumNotification> notifications) {
		try {
            objectOutputToClient.writeObject(notifications);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(">> Got Exception in ServerConnectionWithClientHandler - sendNotifications");

        }

    }

}
