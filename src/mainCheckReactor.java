
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.CharBuffer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chen
 */
public class mainCheckReactor {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Socket clientSocket = new Socket("192.168.0.107", 3333);
        PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
        String forumName = "forum1";
        String userName = "chen";
        String password = "chen1234";
        String email = "email";
        String msgToSend = "REGISTER\n" + "forumName:\n" + forumName + "\n" + "userName:\n" + userName + "\n"
                + "password:\n" + password + "\n" + "email:\n" + email + "\n";
        msgToSend += "\0";
        System.out.println("sending:" + msgToSend);
        pw.print(msgToSend);
        pw.flush();
        ObjectInputStream objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
        System.out.println("sent");
        System.out.println("wait for answer");
        String readObject = (String)objectFromServer.readObject();
        System.out.println(readObject);
    }
}
