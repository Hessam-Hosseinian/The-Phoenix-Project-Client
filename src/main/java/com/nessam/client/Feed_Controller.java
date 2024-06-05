package com.nessam.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.Post;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Feed_Controller implements Initializable {

    @FXML
    private GridPane postGrid;
    @FXML
    private Circle circle;
    private List<Post> posts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image profile = new Image("/Jackman.jpg");
        circle.setFill(new ImagePattern(profile));
        posts = new ArrayList<>(data());
        int columns = 0;
        int rows = 1;
        try {
            for (int i = 0; i < posts.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/nessam/client/Post.fxml"));
                VBox postBox = fxmlLoader.load();
                Post_Controller postController = fxmlLoader.getController();
                postController.setData(posts.get(i));
                if (columns == 1) {
                    columns = 0;
                    rows++;
                }
                postGrid.add(postBox, columns++, rows);
                GridPane.setMargin(postBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Consider logging the error or showing an alert to the user
        }
    }

    private List<Post> data() {
        List<Post> ls = new ArrayList<>();

        Post post1 = new Post();
        post1.setFilePath("/Jackman.jpg");
        post1.setAuthor("Hessam");
        post1.setTitle("First Post");
        post1.setContent("This is the first post content.");
        post1.setDateCreated("01/01/2023");

        Post post2 = new Post();
        post2.setFilePath("/1-intro-photo-final.jpg");
        post2.setAuthor("Negar");
        post2.setTitle("Second Post");
        post2.setContent("This is the second post content.");
        post2.setDateCreated("02/02/2023");

        Post post3= new Post();
        post3.setFilePath("/linkedin.png");
        post3.setAuthor("Alex");
        post3.setTitle("Third Post");
        post3.setContent("This is the Third post content.");
        post3.setDateCreated("03/03/2023");
        Post post4 = new Post();
        post4.setFilePath("/linkedin(1).png");
        post4.setAuthor("Alex");
        post4.setTitle("4TH Post");
        post4.setContent("This is the 4TH post content.");
        post4.setDateCreated("04/04/2023");
        ls.add(post1);
        ls.add(post2);
        ls.add(post3);
        ls.add(post4);

        // Add more posts as needed
        return ls;
    }
}
