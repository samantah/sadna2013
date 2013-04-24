package junitTests.testingClient;

import junit.framework.TestCase;
import junitTests.driverClient.ClientBridge;
import junitTests.driverClient.ClientDriver;


public class ClientTestStartUp extends TestCase {
	protected ClientBridge bridge;
	
	public ClientTestStartUp(){
		this.bridge = ClientDriver.getBridge();
	}
	
	public void setUp() {
		this.bridge = ClientDriver.getBridge();
	}
	
	
	
	
	
	
}