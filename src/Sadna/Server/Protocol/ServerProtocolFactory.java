package Sadna.Server.Protocol;

public interface ServerProtocolFactory<T> {
   AsyncServerProtocol<T> create();
}
