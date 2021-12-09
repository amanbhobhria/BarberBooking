package android.example.barberbooking.model;

public class NotificationModel {
    String nid,title,date,message;

    public NotificationModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public NotificationModel(String nid, String title, String date, String message) {
        this.nid = nid;
        this.title = title;
        this.date = date;
        this.message = message;
    }
}
