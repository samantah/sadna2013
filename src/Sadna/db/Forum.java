/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.Client.Admin;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class Forum {
    private List<SubForum> listOfSubForums;
    private Admin admin;

    public Forum(List<SubForum> listOfSubForums, Admin admin) {
        this.listOfSubForums = listOfSubForums;
        this.admin = admin;
    }
}
