package models;


public class Like {



    private String liker;


    private Post post;

    public Like() {
    }

    public Like(Post post, String liker) {
        this.post = post;
        this.liker = liker;
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



}
