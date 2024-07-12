package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.Comment;
import models.Message;
import models.MessageCard;
import models.User;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DirectCardController {

    @FXML
    private Hyperlink commentAuthor;

    @FXML
    private Circle commentAuthorPic;

    @FXML
    private Label commentContent;

    @FXML
    private Label date;



    public void setData(MessageCard messageCard) throws IOException {
        try {
            String userPro = getUserByEmail(messageCard.getChatUser()).getProfilePicture();
            FileDownloadClient.downloadFile(userPro);
            Image image = new Image(userPro);
            commentAuthorPic.setFill(new ImagePattern(image));
        } catch (Exception e) {
            commentAuthorPic.setFill(new ImagePattern(new Image("Default_Profile_Picture.png")));
        }
        commentAuthor.setText(messageCard.getChatUser());
        commentContent.setText(messageCard.getText());
        date.setText(messageCard.getDateCreated());
    }
    public User getUserByEmail(String email) throws IOException {
        String endpoint =  MyUrl.USER_URL + email;
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                Gson gson = new GsonBuilder().create();
                return gson.fromJson(response.toString(), User.class);
            }
        } else {
            System.err.println("Failed to get user. Response code: " + responseCode);
            return null;
        }
    }
}
