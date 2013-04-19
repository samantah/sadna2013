/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;

public class Admin extends Member {

    public Admin(String userName, String password, String email, String forum,
            ClientCommunicationHandlerInterface ch) {
        super(userName, password, email, forum, ch);
        // TODO Auto-generated constructor stub
    }
}
