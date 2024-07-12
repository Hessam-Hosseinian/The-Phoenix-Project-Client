package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.User;
import utils.BetterLogger;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileMainController implements Initializable {

    @FXML
    private Label Adres;

    @FXML
    private Label title;

    @FXML
    private Label UserName;

    @FXML
    private ImageView backgroundPic;


    @FXML
    private Circle circle;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = null;
        try {
            user = getUserByEmail(UserData.getInstance().getOcd_email());
        } catch (IOException e) {
            BetterLogger.WARNING("failed");
        }

        try {
            FileDownloadClient.downloadFile(user.getProfilePicture());
            Image profile = new Image(user.getProfilePicture());
            circle.setFill(new ImagePattern(profile));
        } catch (Exception e) {
            BetterLogger.WARNING("profile picture not found");
            circle.setFill(new ImagePattern(new Image("Default_Profile_Picture.png")));
        }

        try {
            FileDownloadClient.downloadFile(user.getBackgroundPicture());
            Image back = new Image(user.getBackgroundPicture());
            backgroundPic.setImage(back);
        } catch (Exception e) {
            BetterLogger.WARNING("background picture not found");
            backgroundPic.setImage(new Image("background.jpg"));
        }


        UserName.setText(user.getFirstName() + ", " + user.getLastName());
        title.setText(user.getTitle());
        Adres.setText(user.getLocation());


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
