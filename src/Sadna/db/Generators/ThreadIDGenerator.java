/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db.Generators;

import java.io.Serializable;

/**
 *
 * @author fistuk
 */
public class ThreadIDGenerator implements Serializable{

    private int idNumber = 0;

    public int getID() {
        return idNumber++;
    }
}
