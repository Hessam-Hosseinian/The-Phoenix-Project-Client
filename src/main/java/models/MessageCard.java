package models;

import com.google.gson.annotations.SerializedName;

public class MessageCard {


    @SerializedName("chatUser")
    private String chatUser;
    @SerializedName("text")
    private String text;
    @SerializedName("dateCreated")
    private String dateCreated;

    public String getChatUser() {
        return chatUser;
    }

    public void setChatUser(String chatUser) {
        this.chatUser = chatUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "MessageCard{" +
                "chatUser='" + chatUser + '\'' +
                ", text='" + text + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
