package junitTests.driverServer;




public class ServerDriver {


	public static ServerProxyBridge getBridge() {
		ServerProxyBridge bridge=new ServerProxyBridge (); 
		// add when real bridge is ready
		//bridge.real =  new RealBridge (); 
		bridge.setRealBridge(null); // add real bridge here
		return bridge;
		
	}
}

	
	
