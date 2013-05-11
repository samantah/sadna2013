package Sadna.Server;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ForumNotification implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -622839538827284549L;
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

	public String getText() {
		return text;
	}
}