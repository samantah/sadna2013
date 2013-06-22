package Sadna.Server;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	private String emailUsernameCompany;
	private String emailPasswordCompany;

	
	public EmailSender(){
		emailUsernameCompany = "forumlzubizubi";
		emailPasswordCompany = "zubizubi";
	}
	
	public void sendCode(String sendTo, String code) throws MessagingException{
		send(sendTo, "Welcome To ForumZubiZubi", messagecode(code));
	}
	
	private String messagecode(String code) {
		return "Hi,\nYour code is: "+code+".\nTo continue, please enter it to the verification window.\n\n"+ "Thank's,\n\nForumZubiZibi crew (:";
	}

	public void send(String sendTo, String title, String content) throws MessagingException{
		String host = "smtp.gmail.com";
	    String from = emailUsernameCompany;
	    String pass = emailPasswordCompany;
	    Properties props = System.getProperties();
	    props.put("mail.smtp.starttls.enable", "true"); // added this line
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.user", from);
	    props.put("mail.smtp.password", pass);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");

	    String[] to = {sendTo}; // added this line

	    Session session = Session.getDefaultInstance(props, null);
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(from));

	    InternetAddress[] toAddress = new InternetAddress[to.length];

	    // To get the array of addresses
	    for( int i=0; i < to.length; i++ ) { // changed from a while loop
	        toAddress[i] = new InternetAddress(to[i]);
	    }

	    for( int i=0; i < toAddress.length; i++) { // changed from a while loop
	        message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	    }
	    message.setSubject(title);
	    message.setText(content);
	    Transport transport = session.getTransport("smtp");
	    transport.connect(host, from, pass);
	    transport.sendMessage(message, message.getAllRecipients());
	    transport.close();  
	}
}
