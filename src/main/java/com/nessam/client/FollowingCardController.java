package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.Follow;
import models.User;
import utils.BetterLogger;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FollowingCardController {


    @FXML
    private Circle profile;

    @FXML
    private Hyperlink username;


    public void setData(Follow follow) {


        username.setText(follow.getFollowed());

        try {

            User user = getUserByEmail(follow.getFollowed());



            FileDownloadClient.downloadFile(user.getProfilePicture());

            Image profileImage = new Image(user.getProfilePicture());
            profile.setFill(new ImagePattern(profileImage));
        } catch (Exception e) {
            BetterLogger.WARNING("failed to load profile image");
            profile.setFill(new ImagePattern(new Image("Default_Profile_Picture.png")));
        }

    }


    public Circle getProfile() {
        return profile;
    }

    public void setProfile(Circle profile) {
        this.profile = profile;
    }

    public Hyperlink getUsername() {
        return username;
    }

    public void setUsername(Hyperlink username) {
        this.username = username;
    }

    public User getUserByEmail(String email) throws IOException {
        String endpoint = MyUrl.USER_URL + email;
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


