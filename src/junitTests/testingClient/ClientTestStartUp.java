package junitTests.testingClient;

import java.io.IOException;

import Sadna.Client.ConnectionHandler;
import Sadna.Client.Member;
import Sadna.Client.SuperAdmin;
import Sadna.Client.User;
import Sadna.db.Forum;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;
import junit.framework.TestCase;
import org.junit.*;
import junitTests.driverClient.ClientBridge;
import junitTests.driverClient.ClientDriver;


public class ClientTestStartUp extends TestCase {
	protected ClientBridge bridge;

	public void setUp() {
		this.bridge = ClientDriver.getBridge();
	}

	public void tearDown() throws IOException {
		ConnectionHandler ch = (ConnectionHandler)this.bridge;
		ch.finishCommunication();
	}
	
}