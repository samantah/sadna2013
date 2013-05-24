/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.gui;

import Sadna.Client.ClientConnectionHandler;

/**
 *
 * @author chen
 */
public class ListenerClass implements Runnable {

    ClientConnectionHandler chForListen;
    private ClientConnectionHandler mainCH;

    public ListenerClass(String host, int port) {
        chForListen = new ClientConnectionHandler(host, port);
    }

    ListenerClass(String host, int port, ClientConnectionHandler ch) {
        chForListen = new ClientConnectionHandler(host, port);
        mainCH = ch;
    }

    @Override
    public void run() {
        boolean sendListenerIdentifier = chForListen.sendListenerIdentifier();
        if (sendListenerIdentifier) {
//            System.out.println("added to the list of sockets");
        }
        while (true) {
            boolean listenToServer = chForListen.listenToServer();
            if (!listenToServer) {
                continue;
            }
            CurrentStatus.currFrame.askForNotification();
//            CurrentStatus.currFrame.makeAnEvent();
        }
    }
}
