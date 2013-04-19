/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server;

import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.io.*;
import java.net.*;

/**
 *
 * @author fistuk
 */
public class Server {

    public static void main(String args[]) throws Exception {
        int firsttime = 1;
        String clientSentence;
        String capitalizedSentence = "";
        ServerSocket welcomeSocket = new ServerSocket(3248);
        Socket connectionSocket = welcomeSocket.accept();
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        ObjectOutputStream objout = new ObjectOutputStream(connectionSocket.getOutputStream());
        SubForum sub = new SubForum("forum1", "subForum1");
        ThreadMessage t = new ThreadMessage(sub, "ani t1");
        ThreadMessage t2 = new ThreadMessage(sub, "ani t2");
        sub.addThreadMessage(t);
        sub.addThreadMessage(t2);
        while (true) {

            clientSentence = inFromClient.readLine();
            if (clientSentence == null) {
                continue;
            }
            capitalizedSentence = "200ok\n";
//            if (clientSentence.contains("subForum")){
                System.out.println(sub.getSubForumName());
                objout.writeObject(sub);
//            }
//            outToClient.writeBytes(capitalizedSentence);
            System.out.println("passed");
//            welcomeSocket.close();
//            System.out.println("connection terminated");
        }
    }
}
