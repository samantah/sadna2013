package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MainClientSami {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
            Socket s = new Socket("192.168.1.101", 3248);
            PrintWriter p = new PrintWriter(s.getOutputStream(), true);
            p.println("REGISTER");
            p.close();
            s.close();
//		ConnectionHandler ch = new ConnectionHandler("192.168.1.101", 3248);
//		Member mem = ch.register("forum1", "sami", "pp", "a@c.v");
//		if (mem != null){
//			System.out.println(mem.userName);
//		}
//		else{
//			System.out.println(">> Got null from server");
//		}
//                ch.finishCommunication();
	}

}
