package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;

public class MainClientSami {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientCommunicationHandlerInterface ch = new ConnectionHandler("192.168.1.101", 3248);
		Member mem = ch.register("forum1", "sami", "pp", "a@c.v");
		if (mem != null){
			System.out.println(mem.userName);
		}
		else{
			System.out.println(">> Got null from server");
		}
	}

}
