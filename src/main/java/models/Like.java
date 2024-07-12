package models;


import com.google.gson.annotations.SerializedName;

public class Like {


    @SerializedName("liker")
    private String liker;
    @SerializedName("postTitle")
    private String postTitle;

    @SerializedName("postAuthor")
    private String postAuthor;
    private Post post;

    public Like() {
    }

    public Like( String postTitle, String postAuthor) {

        this.postTitle = postTitle;
        this.postAuthor = postAuthor;
    }




    public Post getPost() {
        return post;
    }

    public String getLiker() {
        return liker;
    }


    public void setPost(Post post) {
        this.post = post;
    }

    public void setLiker(String liker) {
        this.liker = liker;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    @Override
    public String toString() {
        return "Like{" +
                "liker='" + liker + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", postAuthor='" + postAuthor + '\'' +
                ", post=" + post +
                '}';
    }
}
