package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.Follow;
import models.Like;
import org.json.JSONObject;
import utils.BetterLogger;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;

public class LikedListController implements Initializable {


    @FXML
    private GridPane likedGrid;
    private List<Like> likes;


    private void fetchLikesFromServer() {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(MyUrl.LIKE_URL);
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

                JSONObject json = new JSONObject();
                json.put("postAuthor", UserData.getInstance().getPostEmail());
                json.put("postTitle", UserData.getInstance().getPostTitle());

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Gson gson = new Gson();
//                followers.clear();
                likes = gson.fromJson(response.toString(), new TypeToken<List<Like>>() {
                }.getType());



                javafx.application.Platform.runLater(() -> {
                    int columns = 0;
                    int rows = 1;
                    likedGrid.getChildren().clear();
                    for (Like like : likes) {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/nessam/client/LikedBy.fxml"));
                            HBox followBox = fxmlLoader.load();
                            LikedByController likedByController = fxmlLoader.getController();
                            likedByController.setData(like.getLiker());

                            likedGrid.add(followBox, columns++, rows);
                            GridPane.setMargin(followBox, new Insets(10));


                            if (columns == 1) {
                                columns = 0;
                                rows++;
                            }
                        } catch (Exception e) {
                            BetterLogger.WARNING("failed to fetch followers");
                        }
                    }


                });

            } catch (Exception e) {
                BetterLogger.WARNING("failed to fetch followers");
            }
        });
        thread.setDaemon(true);
        thread.start();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchLikesFromServer();
    }
}
