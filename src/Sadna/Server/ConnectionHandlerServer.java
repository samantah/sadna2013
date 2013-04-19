package Sadna.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import Sadna.Server.API.ConnectionHandlerServerInterface;

public class ConnectionHandlerServer implements ConnectionHandlerServerInterface {
	private Socket serverSocket;
	private PrintWriter stringToClient;
	private BufferedReader stringFromClient;
	private ObjectOutputStream objectToClient;
	private ObjectInputStream objectFromClient;
	private String msgToSend;
	private String reciviedMsg;

	public ConnectionHandlerServer(String host, int port){
		try{
			serverSocket = new Socket(host, port);
			stringToClient = new PrintWriter(serverSocket.getOutputStream(), true);
			stringFromClient = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			objectToClient = new ObjectOutputStream(serverSocket.getOutputStream()); 
			objectFromClient = new ObjectInputStream(serverSocket.getInputStream());
		}catch(Exception e){}
	}

	@Override
	public void sendOK() {
		stringToClient.println("200ok\n");
	}
}
