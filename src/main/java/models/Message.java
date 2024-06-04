package models;



import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {


    private String sender;


    private String receiver;

    private String text;


    private String createdAt;

    public Message () {

    }


    public Message(String id, String sender, String receiver, String text, long createdAt) {

        this.receiver = receiver;
        this.sender = sender;
        this.text = text;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        this.createdAt = formatter.format(new Date());
    }

    public Message(String id, String sender, String receiver, String text) {

        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        this.createdAt = formatter.format(new Date());
    }

    public Message(String sender, String receiver, String text) {

        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        this.createdAt = formatter.format(new Date());
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }






}
