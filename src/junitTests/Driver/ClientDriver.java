package Driver;

public class ClientDriver {

    public static ClientProxyBridge getBridge() {
        ClientProxyBridge bridge = new ClientProxyBridge();
        bridge.setRealBridge(new ClientRealBridgeImpl("132.73.199.251", 3333)); // add real bridge here
        return bridge;

    }
}
