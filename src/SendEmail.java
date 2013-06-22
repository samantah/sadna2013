import javax.mail.MessagingException;

import Sadna.Server.EmailSender;

public class SendEmail
{
   public static void main(String [] args)
   {    
	   	EmailSender em = new EmailSender("sami.hour@gmail.com", "hiii", "WE MADE IT!!");
	   	try {
			em.send();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	
   }
}
