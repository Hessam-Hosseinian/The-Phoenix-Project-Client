package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.User;
import utils.BetterLogger;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsSecurity_Controller implements Initializable {
    @FXML
    private Label emailAdress;
    @FXML
    private Label password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = null;
        try {
            user = getUserByEmail(UserData.getInstance().getOcd_email());
        } catch (IOException e) {
            BetterLogger.WARNING("failed");
        }
        emailAdress.setText(user.getEmail());
        password.setText(user.getPassword());
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
