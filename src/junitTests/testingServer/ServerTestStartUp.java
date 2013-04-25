package junitTests.testingServer;

import java.io.IOException;

import junit.framework.TestCase;
import junitTests.driverServer.ServerBridge;
import junitTests.driverServer.ServerDriver;

public class ServerTestStartUp extends TestCase {
	protected ServerBridge bridge;

	public ServerTestStartUp(){
		this.bridge = ServerDriver.getBridge();
	}
	
	public void setUp() {
		this.bridge = ServerDriver.getBridge();
	}
	
	public void tearDown() throws IOException {
		this.bridge = null;
	}
	
	
	
	
	
}