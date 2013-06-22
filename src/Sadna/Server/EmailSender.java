package Sadna.Server;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	private String emailUsernameCompany;
	private String emailPasswordCompany;
	private String emailToUsername;
	private String messageSubject;
	private String messageText;
	
	public EmailSender(String usernameCompany, String passwordCompany, String to, String subject, String text){
		emailUsernameCompany = usernameCompany;
		emailPasswordCompany = passwordCompany;
		emailToUsername = to;
		messageSubject = subject;
		messageText = text;
	}
	
	public EmailSender(String to, String subject, String text){
		emailUsernameCompany = "forumlzubizubi";
		emailPasswordCompany = "zubizubi";
		emailToUsername = to;
		messageSubject = subject;
		messageText = text;
	}
	
	public void send() throws AddressException, MessagingException{
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

	    String[] to = {emailToUsername}; // added this line

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
	    message.setSubject(messageSubject);
	    message.setText(messageText);
	    Transport transport = session.getTransport("smtp");
	    transport.connect(host, from, pass);
	    transport.sendMessage(message, message.getAllRecipients());
	    transport.close();  
	}
}
