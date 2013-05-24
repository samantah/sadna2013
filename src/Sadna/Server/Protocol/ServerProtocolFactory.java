package Sadna.Server.Protocol;

import java.nio.channels.SocketChannel;

public interface ServerProtocolFactory<T> {
   AsyncServerProtocol<T> create();
   AsyncServerProtocol<T> create(SocketChannel sc);
}
