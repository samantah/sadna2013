/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;

public class Moderator extends Member {

    public Moderator(String userName, String password, String email,
            String forum, ClientCommunicationHandlerInterface ch) {
        super(userName, password, email, forum, ch);
        // TODO Auto-generated constructor stub
    }
}
