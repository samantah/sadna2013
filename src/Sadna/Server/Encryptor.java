package Sadna.Server;

import org.jasypt.util.password.BasicPasswordEncryptor;

public class Encryptor {

	private static BasicPasswordEncryptor _encryptor = new BasicPasswordEncryptor();
	
	public static String encrypt(String plainPassword){
		return _encryptor.encryptPassword(plainPassword);
	}
	
	public static boolean checkPassword(String plainPassword, String encryptedPassword){
		return _encryptor.checkPassword(plainPassword, encryptedPassword);
	}
	
	
}
