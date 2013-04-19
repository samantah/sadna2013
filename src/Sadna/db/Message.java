/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import Sadna.db.Generators.PostIDGenerator;
import java.io.Serializable;

/**
 *
 * @author fistuk
 */
public abstract class Message implements Serializable {

    protected int id;
    protected String title;
    protected String content;

    public Message(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public void setMessage(String message) {
        this.content = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    
    
}
