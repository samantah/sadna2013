
import java.util.Properties;
import java.io.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chen
 */
public class mainCheckReactor {

	public static void main(String[] args) {

		// Recipient's email ID needs to be mentioned.
		String to = "chenl29@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "sniral@walla.co.il";

		// Assuming you are sending email from localhost
		String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText("This is actual message");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}


//        ObjectOutputStream out1;
//        ObjectOutputStream out2;
//        ObjectInputStream in;
//        FileOutputStream fileOutputStream = new FileOutputStream("path.txt");
//        out1 = new ObjectOutputStream(fileOutputStream);
//        out2 = new ObjectOutputStream(fileOutputStream);
//        in = new ObjectInputStream(new FileInputStream("path.txt"));
//        String s = "abc";
//        out1.writeObject(s);
//        Object readObject = in.readObject();
//        System.out.println(readObject);
//        out2.writeObject(s);
//        readObject = in.readObject();
//        System.out.println(readObject);
//        mail
//        
//
//        //        Socket clientSocket = new Socket("192.168.0.107", 3333);
////        PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
////        String forumName = "forum1";
////        String userName = "chen";
////        String password = "chen1234";
////        String email = "email";
////        String msgToSend = "REGISTER\n" + "forumName:\n" + forumName + "\n" + "userName:\n" + userName + "\n"
////                + "password:\n" + password + "\n" + "email:\n" + email + "\n";
////        msgToSend += "\0";
////        System.out.println("sending:" + msgToSend);
////        pw.print(msgToSend);
////        pw.flush();
////        ObjectInputStream objectFromServer = new ObjectInputStream(clientSocket.getInputStream());
////        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
////        System.out.println("sent");
////        System.out.println("wait for answer");
////        String readObject = (String)objectFromServer.readObject();
////        System.out.println(readObject);

