package Sadna.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Sadna.Server.API.ConnectionHandlerServerInterface;
import Sadna.Server.API.ServerInterface;
import Sadna.db.DataBase;

public class MainServer {
	
	private ServerInterface si = new ServerImpl(new DataBase());
	private ConnectionHandlerServerInterface ch;
	private ServerHandler server;
	
	private ServerSocket serverSocket;
	private int portNumber = 3248;
	
	public MainServer() {
        try {
        	serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void handleConnection() {
        System.out.println("Waiting for client request...");

        //
        // The server do a loop here to accept all connection initiated by the
        // client application.
        //
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ch = new ConnectionHandlerServer(socket);
                new ServerHandler(ch, si);
            } catch (IOException e) {
         //     e.printStackTrace();
                System.out.println("Connection stopped...");
            }
        }
    }

    public static void main(String[] args) {
    	MainServer main = new MainServer();
        main.handleConnection();
    }
 
}
