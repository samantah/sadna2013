package junitTests.driverServer;




public class ServerDriver {


	public static ServerProxyBridge getBridge() {
		ServerProxyBridge bridge=new ServerProxyBridge (); 
		bridge.setRealBridge(null); // add real bridge here
		return bridge;
		
	}
}

	
	
