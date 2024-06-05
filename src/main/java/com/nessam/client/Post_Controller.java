package com.nessam.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Post;

public class Post_Controller {

    @FXML
    private ImageView Profile_pic;

    @FXML
    private Label caption;



    @FXML
    private ImageView post_pic;



    @FXML
    private Button send_bt;

    @FXML
    private Label title;

    @FXML
    private Hyperlink username;

    @FXML
    private Label Date_lb;

    public void setData(Post post) {
        Image postImage = new Image(post.getFilePath());
        post_pic.setImage(postImage);
        Image profileImage = new Image("/linkedin.png"); // Assuming the profile image is the same as post image
        Profile_pic.setImage(profileImage);
        username.setText(post.getAuthor());
        Date_lb.setText(post.getDateCreated());
        caption.setText(post.getContent());
        title.setText(post.getTitle());

        // Set button texts dynamically





    }
}
