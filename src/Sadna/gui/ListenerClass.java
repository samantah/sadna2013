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
    ClientConnectionHandler ch;

    public ListenerClass(String host,int port, ClientConnectionHandler ch) {
        this.ch = ch;
        chForListen = new ClientConnectionHandler(host, port);
    }

    @Override
    public void run() {
        chForListen.sendListenerIdentifier();
        while(true){
            chForListen.listenToServer();
        }
    }
}
