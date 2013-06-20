package Sadna.Server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

	private static final String EMAIL_PATTERN =	"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	
	public static boolean isValidEmailAddress(String email) {
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static void main(String[] args) {
		  
		System.out.println(isValidEmailAddress("sami.hourgmail.com"));
		System.out.println(isValidEmailAddress("samihour@gmailcom.js"));
		System.out.println(isValidEmailAddress("sami234hour@gmail.com"));
		System.out.println(isValidEmailAddress("444_44g@m.ailcom"));
		System.out.println(isValidEmailAddress("sami-hour@gm-ail.com"));

	}
}
