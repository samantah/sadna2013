/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.Client;

import Sadna.Client.API.ClientCommunicationHandlerInterface;

/**
 *
 * @author fistuk
 */
public class SuperAdmin extends Admin{

    public SuperAdmin(String userName, String password, String email, String forum, ClientCommunicationHandlerInterface ch) {
        super(userName, password, email, forum, ch);
    }
    
}
