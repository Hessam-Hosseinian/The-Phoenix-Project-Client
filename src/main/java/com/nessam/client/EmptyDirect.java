package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import models.MessageCard;
import utils.BetterLogger;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EmptyDirect {
    @FXML
    private Button AddPost_bt;

    @FXML
    private Button HomeBt;

    @FXML
    private Button addEdu_bt;

    @FXML
    private ImageView background;

    @FXML
    private Circle circle;

    @FXML
    private MenuItem profileViewBotton;

    @FXML
    private TextField searchBox;

    @FXML
    private MenuItem settingsButton;

    @FXML
    private Label welcome;

    private List<MessageCard> messageCards;

    @FXML
    private GridPane directs;

    public void initialize() {
        fetchDirectFromServer();
    }


    private void fetchDirectFromServer() {
        Thread thread = new Thread(() -> {
            try {

                URL url = new URL(MyUrl.MESSAGE_URL + HelloApplication.loggedin_user + "/last");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                String jwtToken = HelloApplication.getJWT_token();
                if (jwtToken != null && !jwtToken.isEmpty()) {
                    jwtToken = jwtToken.trim(); // Ensure the token is trimmed
                    connection.setRequestProperty("Authorization", jwtToken);
                }

                connection.setDoOutput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Gson gson = new Gson();
                messageCards = gson.fromJson(response.toString(), new TypeToken<List<MessageCard>>() {
                }.getType());


                // Update the UI on the JavaFX Application Thread
                javafx.application.Platform.runLater(() -> {
                    int columns = 0;
                    int rows = 1;
                    directs.getChildren().clear();
                    try {

                        for (int i = messageCards.size() - 1; i >= 0; i--) {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/nessam/client/MessageCard.fxml"));
                            System.out.println("ok");
                            HBox postBox = fxmlLoader.load();
                            DirectCardController directCardController = fxmlLoader.getController();
                            directCardController.setData(messageCards.get(i));
                            if (columns == 1) {
                                columns = 0;
                                rows++;
                            }
                            directs.add(postBox, columns++, rows);
                            GridPane.setMargin(postBox, new Insets(0, 0, 10, 0));
                        }
                    } catch (IOException e) {
                        BetterLogger.WARNING("post loading failed");
                    }
                });

            } catch (IOException e) {
                BetterLogger.WARNING("post loading failed");
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleSettingsAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(10);
    }


    @FXML
    private void handleAddingPostAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(9);
    }


    @FXML
    private void handleProfileAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(8);
    }

    @FXML
    private void handleAddingPostActions(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();

        application.changeScene(9);
    }

    @FXML
    void addEdu(ActionEvent event) {

    }

    @FXML
    private void handleHomeAction(ActionEvent event) throws IOException {
        HelloApplication app = new HelloApplication();
        app.changeScene(6);
    }

    @FXML
    public void handleMessagingButton() throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(12);

    }

    @FXML
    public void handleAddingEducationAction() {
        HelloApplication application = new HelloApplication();
        application.changeScene(14);
    }

    @FXML
    public void handleAddingJob() {
        HelloApplication application = new HelloApplication();

        application.changeScene(15);
    }
}
