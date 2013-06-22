import javax.mail.MessagingException;

import Sadna.Server.EmailSender;

public class SendEmail
{
   public static void main(String [] args)
   {    
	   	EmailSender em = new EmailSender();
	   	try {
			em.send("chenl29@gmail.com", "hiii", "WE MADE IT!!");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	
   }
}
