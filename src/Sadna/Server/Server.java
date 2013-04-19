/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Server;

import Sadna.Client.Admin;
import Sadna.db.Forum;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

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
		PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);
		ObjectOutputStream objout = new ObjectOutputStream(connectionSocket.getOutputStream());
		SubForum sub = new SubForum("forum1", "subForum1");
		ThreadMessage t = new ThreadMessage(sub, "ani t1");
		ThreadMessage t2 = new ThreadMessage(sub, "ani t2");
		sub.addThreadMessage(t);
		sub.addThreadMessage(t2);
		Admin a = new Admin("userName", "password", "email", "forum", null);
		Forum f = new Forum(a, "myForum");
		for(int i =0; i<3; i++){
			sub = new SubForum(f, "subForumName"+i);
			f.addSubForum(sub);
		}
		List<ThreadMessage> tl = new ArrayList<ThreadMessage>();
		for(int i =0; i<3; i++){
			ThreadMessage t1 = new ThreadMessage(sub, ""+i+i+i+i+i+i+i);
			tl.add(t1);
		}

		while (true) {

			clientSentence = inFromClient.readLine();
			if (clientSentence == null) {
				continue;
			}
			
			capitalizedSentence = "200ok\n";
			if (clientSentence.contains("POST")){
				System.out.println("here");
				outToClient.println("200ok");
			}
		}
	}
}
