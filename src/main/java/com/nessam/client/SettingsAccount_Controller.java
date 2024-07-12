package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import models.User;
import utils.BetterLogger;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsAccount_Controller implements Initializable {

    @FXML
    private ChoiceBox<String> LanguageChoiceBox;
    @FXML
    private Label location;
    @FXML
    private Button logOut_bt;
    @FXML
    private Label name;
    @FXML
    private Button changeTheme;

    @Override
    public void initialize(URL l, ResourceBundle resources) {
        LanguageChoiceBox.getItems().addAll("Afrikaans", "Albanian", "Arabic", "Armenian", "Azerbaijani", "Belarusian", "Bengali", "Bosnian", "Bulgarian", "Catalan", "Cebuano", "Chichewa", "Chinese", "Czech", "Danish", "English", "Esperanto", "Estonian", "Filipino", "French", "Galician", "Georgian", "German", "Greek", "Hausa", "Hungarian", "Icelandic", "Indonesian", "Irish", "Italian", "Japanese", "Javanese", "Kannada", "Korean", "Kyrgyz", "Latin", "Latvian", "Lithuanian", "Malagasy", "Nepali", "Norwegian", "Pashto", "Persian", "Polish", "Portuguese", "Romanian", "Russian", "Serbian", "Sindhi", "Sinhala", "Slovak", "Slovenian", "Somali", "Spanish", "Swedish", "Tajik", "Tamil", "Telugu", "Thai", "Turkish", "Ukrainian", "Urdu", "Uzbek", "Vietnamese", "Welsh", "Xhosa", "Yiddish", "Yoruba", "Zulu");

        User user = null;
        try {
            user = getUserByEmail(UserData.getInstance().getOcd_email());
        } catch (IOException e) {
            BetterLogger.WARNING("failed");
        }
        name.setText(user.getFirstName() + ", " + user.getLastName());
        location.setText(user.getLocation());
    }

    public void logOut() throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(1);
    }

    public void setData(String Name, String Location) {
        name.setText(Name);
        location.setText(Location);
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

    @FXML
    void changeTheme(ActionEvent event) {
        HelloApplication.toggleNightMode();

    }

}
