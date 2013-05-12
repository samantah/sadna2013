package junitTests.Driver;

public class ClientDriver {

    public static ClientProxyBridge getBridge() {
        ClientProxyBridge bridge = new ClientProxyBridge();
        bridge.setRealBridge(new ClientRealBridgeImpl("172.16.106.179", 3333)); // add real bridge here
        return bridge;

    }
}
