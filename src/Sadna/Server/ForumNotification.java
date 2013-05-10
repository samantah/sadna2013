package Sadna.Server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Sadna.db.Post;
import Sadna.db.ThreadMessage;
import java.io.Serializable;

public class ForumNotification implements Serializable{

    public static long notificationCounter = 0;
    private String text;
    private long notificationID;
    private String notificationTime;

    public ForumNotification(String text) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        this.notificationTime = sdfDate.format(now);
        this.text = text;
        this.notificationID = notificationCounter++;
    }
}