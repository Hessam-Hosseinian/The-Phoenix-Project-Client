package models;


import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Post {



    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("filePath")
    private String filePath;

    @SerializedName("image")
    private String image;

    @SerializedName("dateCreated")
    private String dateCreated;

    @SerializedName("author")
    private String author;




    private List<Comment> comments;

    private List<Like> likes;


    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        this.dateCreated = formatter.format(new Date());
    }


    public Post() {
    }

    public Post(String title, String content, String filePath, String image, String dateCreated, String author, List<Comment> comments, List<Like> likes) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
        this.image = image;
        this.dateCreated = dateCreated;
        this.author = author;
        this.comments = comments;
        this.likes = likes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }
}

