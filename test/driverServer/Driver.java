package driverServer;




public class Driver {


	public static ProxyBridge getBridge() {
		ProxyBridge bridge=new ProxyBridge (); 
		// add when real bridge is ready
		//bridge.real =  new RealBridge (); 
		bridge.setRealBridge(null); // add real bridge here
		return bridge;
		
	}
}

	
	
