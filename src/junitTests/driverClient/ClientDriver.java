package junitTests.driverClient;




public class ClientDriver {


	public static ClientProxyBridge getBridge() {
		ClientProxyBridge bridge=new ClientProxyBridge (); 
		bridge.setRealBridge(new ClientRealBridgeImpl("192.168.1.100", 3248)); // add real bridge here
		return bridge;
		
	}
}

	
	
