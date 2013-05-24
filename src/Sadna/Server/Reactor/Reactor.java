package Sadna.Server.Reactor;

import Sadna.Server.Protocol.AsyncServerProtocol;
import Sadna.Server.Protocol.RequestHandlerProtocol;
import Sadna.Server.Protocol.ServerProtocolFactory;
import Sadna.Server.ServerToDataBaseHandler;
import Sadna.Server.Tokenizer.FixedSeparatorMessageTokenizer;
import Sadna.Server.Tokenizer.MessageTokenizer;
import Sadna.Server.Tokenizer.StringMessage;
import Sadna.Server.Tokenizer.TokenizerFactory;
import Sadna.db.DataBase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * An implementation of the Reactor pattern.
 */
public class Reactor<T> implements Runnable {

    private static final Logger logger = Logger.getLogger("edu.spl.reactor");
    private final int _port;
    private final int _poolSize;
    private final ServerProtocolFactory<T> _protocolFactory;
    private final TokenizerFactory<T> _tokenizerFactory;
    private volatile boolean _shouldRun = true;
    private ReactorData<T> _data;
    private static final List<SocketChannel> _socketsList = new ArrayList<>();

    /**
     * Creates a new Reactor
     *
     * @param poolSize the number of WorkerThreads to include in the ThreadPool
     * @param port the port to bind the Reactor to
     * @param protocol the protocol factory to work with
     * @param tokenizer the tokenizer factory to work with
     * @throws IOException if some I/O problems arise during connection
     */
    public Reactor(int port, int poolSize, ServerProtocolFactory<T> protocol, TokenizerFactory<T> tokenizer) {
        _port = port;
        _poolSize = poolSize;
        _protocolFactory = protocol;
        _tokenizerFactory = tokenizer;
    }

    /**
     * Create a non-blocking server socket channel and bind to to the Reactor
     * port
     */
    private ServerSocketChannel createServerSocket(int port)
            throws IOException {
        try {
            ServerSocketChannel ssChannel = ServerSocketChannel.open();
            ssChannel.configureBlocking(false);
            ssChannel.socket().bind(new InetSocketAddress(port));
            return ssChannel;
        } catch (IOException e) {
            logger.info("Port " + port + " is busy");
            throw e;
        }
    }

    /**
     * Main operation of the Reactor:
     * <UL>
     * <LI>Uses the
     * <CODE>Selector.select()</CODE> method to find new requests from clients
     * <LI>For each request in the selection set:
     * <UL>
     * If it is <B>acceptable</B>, use the ConnectionAcceptor to accept it,
     * create a new ConnectionHandler for it register it to the Selector
     * <LI>If it is <B>readable</B>, use the ConnectionHandler to read it,
     * extract messages and insert them to the ThreadPool
     * </UL>
     */
    public void run() {
        // Create & start the ThreadPool

        ExecutorService executor = Executors.newFixedThreadPool(_poolSize);
        Selector selector = null;
        ServerSocketChannel ssChannel = null;

        try {
            selector = Selector.open();
            ssChannel = createServerSocket(_port);
        } catch (IOException e) {
            logger.info("cannot create the selector -- server socket is busy?");
            return;
        }

        _data = new ReactorData<T>(executor, selector, _protocolFactory, _tokenizerFactory);
        ConnectionAcceptor<T> connectionAcceptor = new ConnectionAcceptor<T>(ssChannel, _data);

        // Bind the server socket channel to the selector, with the new
        // acceptor as attachment

        try {
            ssChannel.register(selector, SelectionKey.OP_ACCEPT, connectionAcceptor);
        } catch (ClosedChannelException e) {
            logger.info("server channel seems to be closed!");
            return;
        }

        while (_shouldRun && selector.isOpen()) {
            // Wait for an event
            try {
                selector.select();
            } catch (IOException e) {
                logger.info("trouble with selector: " + e.getMessage());
                continue;
            }

            // Get list of selection keys with pending events
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            // Process each key
            while (it.hasNext()) {
                // Get the selection key
                SelectionKey selKey = (SelectionKey) it.next();

                // Remove it from the list to indicate that it is being
                // processed. it.remove removes the last item returned by next.
                it.remove();

                // Check if it's a connection request
                if (selKey.isValid() && selKey.isAcceptable()) {
                    logger.info("Accepting a connection");
                    ConnectionAcceptor<T> acceptor = (ConnectionAcceptor<T>) selKey.attachment();
                    try {
                        acceptor.accept();
                    } catch (IOException e) {
                        logger.info("problem accepting a new connection: "
                                + e.getMessage());
                    }
                    continue;
                }
                // Check if a message has been sent
                if (selKey.isValid() && selKey.isReadable()) {
                    ConnectionHandler<T> handler = (ConnectionHandler<T>) selKey.attachment();
                    logger.info("Channel is ready for reading");
                    handler.read();
                }
                // Check if there are messages to send
                if (selKey.isValid() && selKey.isWritable()) {
                    ConnectionHandler<T> handler = (ConnectionHandler<T>) selKey.attachment();
                    logger.info("Channel is ready for writing");
                    handler.write();
                }
            }
        }
        stopReactor();
    }

    /**
     * Returns the listening port of the Reactor
     *
     * @return the listening port of the Reactor
     */
    public int getPort() {
        return _port;
    }

    /**
     * Stops the Reactor activity, including the Reactor thread and the Worker
     * Threads in the Thread Pool.
     */
    public synchronized void stopReactor() {
        if (!_shouldRun) {
            return;
        }
        _shouldRun = false;
        _data.getSelector().wakeup(); // Force select() to return
        _data.getExecutor().shutdown();
        try {
            _data.getExecutor().awaitTermination(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // Someone didn't have patience to wait for the executor pool to
            // close
            e.printStackTrace();
        }
    }

    /**
     * Main program, used for demonstration purposes. Create and run a
     * Reactor-based server for the Echo protocol. Listening port number and
     * number of threads in the thread pool are read from the command line.
     */
    public static void main(String args[]) {
//        if (args.length != 2) {
//            System.err.println("Usage: java Reactor <port> <pool_size>");
//            System.exit(1);
//        }

        try {
//            int port = Integer.parseInt(args[0]);
//            int poolSize = Integer.parseInt(args[1]);
            int port = 3333;
            int poolSize = 10;

//            Reactor<HttpMessage> reactor = startHttpServer(port, poolSize);
//            Reactor<StringMessage> reactor = startEchoServer(port, poolSize);
            Reactor<StringMessage> reactor = startRequestHandlerServer(port, poolSize);


            Thread thread = new Thread(reactor);
            thread.start();
            logger.info("Reactor is ready on port " + reactor.getPort());
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Reactor<StringMessage> startRequestHandlerServer(int port, int poolSize) {
        ServerProtocolFactory<StringMessage> protocolMaker = new ServerProtocolFactory<StringMessage>() {
            public AsyncServerProtocol<StringMessage> create() {
                return new RequestHandlerProtocol(new ServerToDataBaseHandler(new DataBase()));
            }

            @Override
            public AsyncServerProtocol<StringMessage> create(SocketChannel sc) {
                return new RequestHandlerProtocol(new ServerToDataBaseHandler(new DataBase()), sc);
            }
        };


        final Charset charset = Charset.forName("UTF-8");
        TokenizerFactory<StringMessage> tokenizerMaker = new TokenizerFactory<StringMessage>() {
            public MessageTokenizer<StringMessage> create() {
                return new FixedSeparatorMessageTokenizer("\0", charset);
            }
        };

        Reactor<StringMessage> reactor = new Reactor<StringMessage>(port, poolSize, protocolMaker, tokenizerMaker);
        return reactor;
    }

    public static int SocketsListSize() {
        int size;
        synchronized (_socketsList) {
            size = _socketsList.size();
        }
        return size;
    }

    public static Iterator<SocketChannel> SocketsListIterator() {
        Iterator<SocketChannel> iterator;
        synchronized (_socketsList) {
            iterator = _socketsList.iterator();
        }
        return iterator;
    }

    public static boolean SocketsListAdd(SocketChannel e) {
        boolean add;
        synchronized (_socketsList) {
            add = _socketsList.add(e);
        }
        return add;
    }

    public static boolean SocketsListRemove(SocketChannel o) {
        boolean remove;
        synchronized (_socketsList) {
            remove = _socketsList.remove(o);
        }
        return remove;
    }

    public static void NotifyAllListeners() {
        Iterator<SocketChannel> SocketsListIterator = SocketsListIterator();
        ByteArrayOutputStream b = null;
        PrintWriter pw = null;
        String msg = "200ok";
        ByteBuffer bb;
        while (SocketsListIterator.hasNext()) {
            SocketChannel socketChannel = SocketsListIterator.next();
            try {
                b = new ByteArrayOutputStream();
                ObjectOutputStream o = new ObjectOutputStream(b);
                o.writeObject(msg);
                bb = ByteBuffer.wrap(b.toByteArray());
                socketChannel.write(bb);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
