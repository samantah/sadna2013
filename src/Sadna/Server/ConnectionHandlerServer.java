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
	private ObjectOutputStream objectOutputToClient;
	private BufferedReader stringFromClient;
	private ObjectInputStream objectInputFromClient;

	public ConnectionHandlerServer(Socket socket){
		try{
			serverSocket = socket;
			stringToClient = new PrintWriter(serverSocket.getOutputStream(), true);
			stringFromClient = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			objectOutputToClient = new ObjectOutputStream(serverSocket.getOutputStream()); 
			objectInputFromClient = new ObjectInputStream(serverSocket.getInputStream());
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
			objectOutputToClient.writeObject(subForumsList);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void sendSubForum(SubForum subForum) {
		try {
			objectOutputToClient.writeObject(subForum);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void sendThreadsList(List<ThreadMessage> threadsList) {
		try {
			objectOutputToClient.writeObject(threadsList);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

	@Override
	public void sendThreadMeassage(ThreadMessage threadMessage) {
		try {
			objectOutputToClient.writeObject(threadMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

	@Override
	public void sendForumsList(List<Forum> forumsList) {
		try {
			objectOutputToClient.writeObject(forumsList);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}

	@Override
	public void sendForum(Forum forum) {
		try {
			objectOutputToClient.writeObject(forum);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public String receiveRequestFromClient() {
		String request = null;
		try {
			request = stringFromClient.readLine();
		} catch (IOException e) {}
		return request;

	}


	public void closeSocket() {
		try{
			objectInputFromClient.close();
			objectOutputToClient.close();
			stringToClient.close();
			stringFromClient.close();
			serverSocket.close();
			System.out.println("Closed socket..");
		}catch (Exception e) {
			System.out.println("Couldn't close socket..");		
		}
	}
	
}
