package models;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("content")
    private String content;

    @SerializedName("filePath")
    private String filePath;

    @SerializedName("author")
    private String author;

    @SerializedName("dateCreated")
    private String dateCreated;

    @SerializedName("post")
    private Post post;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "content='" + content + '\'' +
                ", filePath='" + filePath + '\'' +
                ", author='" + author + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", post=" + post +
                '}';
    }
}
