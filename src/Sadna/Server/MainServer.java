package Sadna.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Sadna.Server.API.ConnectionHandlerServerInterface;
import Sadna.Server.API.ServerInterface;
import Sadna.db.DataBase;

public class MainServer {
	
	private ServerInterface si = new ServerToDataBaseHandler(new DataBase());
	private ConnectionHandlerServerInterface ch;
	
	private ServerSocket serverSocket;
	private int portNumber = 3248;
	

	public MainServer() {
        try {
        	serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(">> Got IOException in MainServer - serverSocket couldn't be initialized");
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
                ch = new ServerConnectionWithClientHandler(socket);
                new ServerRequestHandler(ch, si);
            } catch (IOException e) {
         //     e.printStackTrace();
                System.out.println("Connection stopped in server (because client stopped)...");
            }
        }
    }

    public static void main(String[] args) {
    	MainServer main = new MainServer();
        main.handleConnection();
    }
 
}
