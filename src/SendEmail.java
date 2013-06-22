import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail
{
   public static void main(String [] args) throws AddressException, MessagingException
   {    
	   	
	   	String host = "smtp.gmail.com";
	    String from = "forumlzubizubi";
	    String pass = "zubizubi";
	    Properties props = System.getProperties();
	    props.put("mail.smtp.starttls.enable", "true"); // added this line
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.user", from);
	    props.put("mail.smtp.password", pass);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");

	    String[] to = {"chenl29@gmail.com"}; // added this line

	    Session session = Session.getDefaultInstance(props, null);
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(from));

	    InternetAddress[] toAddress = new InternetAddress[to.length];

	    // To get the array of addresses
	    for( int i=0; i < to.length; i++ ) { // changed from a while loop
	        toAddress[i] = new InternetAddress(to[i]);
	    }
	    System.out.println(Message.RecipientType.TO);

	    for( int i=0; i < toAddress.length; i++) { // changed from a while loop
	        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	    }
	    message.setSubject("sending in a group");
	    message.setText("Welcome to JavaMail");
	    Transport transport = session.getTransport("smtp");
	    transport.connect(host, from, pass);
	    transport.sendMessage(message, message.getAllRecipients());
	    transport.close();  
	    
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

