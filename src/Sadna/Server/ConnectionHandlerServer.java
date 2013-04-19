package Sadna.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import Sadna.Server.API.ConnectionHandlerServerInterface;
import Sadna.db.Forum;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

public class ConnectionHandlerServer implements ConnectionHandlerServerInterface {
	private Socket serverSocket;
	private PrintWriter stringToClient;
	private ObjectOutputStream objectToClient;
	public ConnectionHandlerServer(String host, int port){
		try{
			serverSocket = new Socket(host, port);
			stringToClient = new PrintWriter(serverSocket.getOutputStream(), true);
			new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			objectToClient = new ObjectOutputStream(serverSocket.getOutputStream()); 
			new ObjectInputStream(serverSocket.getInputStream());
		}catch(Exception e){}
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
			objectToClient.writeObject(subForumsList);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void sendSubForum(SubForum subForum) {
		try {
			objectToClient.writeObject(subForum);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void sendThreadsList(List<ThreadMessage> threadsList) {
		try {
			objectToClient.writeObject(threadsList);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

	@Override
	public void sendThreadMeassage(ThreadMessage threadMessage) {
		try {
			objectToClient.writeObject(threadMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

	@Override
	public void sendForumsList(List<Forum> forumsList) {
		try {
			objectToClient.writeObject(forumsList);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

	@Override
	public void sendForum(Forum forum) {
		try {
			objectToClient.writeObject(forum);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	

}
