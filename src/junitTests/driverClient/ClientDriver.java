package junitTests.driverClient;




public class ClientDriver {


	public static ClientProxyBridge getBridge() {
		ClientProxyBridge bridge=new ClientProxyBridge (); 
		// add when real bridge is ready
		//bridge.real =  new RealBridge (); 
		bridge.setRealBridge(null); // add real bridge here
		return bridge;
		
	}
}

	
	
