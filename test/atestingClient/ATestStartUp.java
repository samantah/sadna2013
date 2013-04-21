package atestingClient;

import adriverClient.Bridge;
import adriverClient.Driver;
import junit.framework.TestCase;

public class ATestStartUp extends TestCase {
	protected Bridge bridge;
	
	
	
	
	public ATestStartUp(){
		this.bridge = Driver.getBridge();
	}
	
	
	public void setUp() {
		this.bridge=Driver.getBridge();
	}
	
	
	
	
	
	
}