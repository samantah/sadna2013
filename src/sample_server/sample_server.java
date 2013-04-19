package sample_server;

import Sadna.db.SubForum;
import java.io.*;
import java.net.*;
import java.security.*;

/**
 * Title: Sample Server Description: This utility will accept input from a
 * socket, posting back to the socket before closing the link. It is intended as
 * a template for coders to base servers on. Please report bugs to brad at
 * kieser.net Copyright: Copyright (c) 2002 Company: Kieser.net
 *
 * @author B. Kieser
 * @version 1.0
 */
public class sample_server {

    private static int port = 3248, maxConnections = 0;
    // Listen for incoming connections and handle them

    public static void main(String[] args) {
        int i = 0;

        try {
            ServerSocket listener = new ServerSocket(port);
            Socket server;

            while ((i++ < maxConnections) || (maxConnections == 0)) {
                doComms connection;
                System.out.println("waiting to client");
                server = listener.accept();
                System.out.println("client accepted");
                doComms conn_c = new doComms(server);
                Thread t = new Thread(conn_c);
                t.start();
            }
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
    }
}

class doComms implements Runnable {

    private Socket server;
    private String line, input;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectOutputStream objout;
    private ObjectInputStream objin;

    doComms(Socket server) throws IOException {
        this.server = server;
        this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        this.out = new PrintWriter(server.getOutputStream(), true);
        this.objout = new ObjectOutputStream(server.getOutputStream());
        this.objin = new ObjectInputStream(server.getInputStream());
    }

    public void run() {
        SubForum sub = new SubForum("forum1", "subForum1");

        input = "";

        try {
            // Get input from the client


            while (true) {
                String readLine = in.readLine();
                if (readLine == null) {
                    break;
                }
            }
            objout.writeObject(sub);
        } catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
    }
}
