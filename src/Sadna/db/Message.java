/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.db;

import java.io.Serializable;

/**
 *
 * @author fistuk
 */
public abstract class Message implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1241099472836278483L;
    protected int id;
    protected String title;
    protected String content;
    protected String publisher;

    public Message(String title, String content, String publisher) {
    	this.title = title;
        this.content = content;
        this.publisher = publisher;
        
    	if((title == null)||(title.equals(""))){
    		this.title = "No Title";
    	}
    	
      	if((content == null)||(content.equals(""))){
    		this.content = "No Content";
    	}
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

    public String getPublisher() {
        return publisher;
    }
}
