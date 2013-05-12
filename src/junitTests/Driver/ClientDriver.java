package junitTests.Driver;

public class ClientDriver {

    public static ClientProxyBridge getBridge() {
        ClientProxyBridge bridge = new ClientProxyBridge();
        bridge.setRealBridge(new ClientRealBridgeImpl("192.168.1.108", 3333)); // add real bridge here
        return bridge;

    }
}
