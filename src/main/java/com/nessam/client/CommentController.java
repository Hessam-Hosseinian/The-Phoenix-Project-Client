package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.Comment;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;


public class CommentController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchCommentsFromServer();
    }

    @FXML
    private Button addComment;

    @FXML
    private GridPane commentGrid;

    @FXML
    private TextArea commenttxt;

    private List<Comment> comments;




    @FXML
    public void addComment(ActionEvent event) {
        String commentContent = commenttxt.getText();

        String postTitle = UserData.getInstance().getPostTitle();
        String postEmail = UserData.getInstance().getPostEmail();

        String urlString = MyUrl.COMMENT_URL+ postEmail + "/" + postTitle + "/" + commentContent;


        try {
            HttpURLConnection connection = createHttpConnection(urlString, "POST");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String responseText = getResponseText(connection);

            } else {
                System.out.println("Failed to add comment. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private HttpURLConnection createHttpConnection(String urlString, String method) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        String jwtToken = HelloApplication.getJWT_token();
        if (jwtToken != null && !jwtToken.isEmpty()) {
            jwtToken = jwtToken.trim();
            connection.setRequestProperty("Authorization",  jwtToken);
        }

        return connection;
    }

    private String getResponseText(HttpURLConnection connection) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }


    public void fetchCommentsFromServer () {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(MyUrl.COMMENT_URL + "" + UserData.getInstance().getPostEmail() + "/" + UserData.getInstance().getPostTitle());
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
                comments = gson.fromJson(response.toString(), new TypeToken<List<Comment>>() {
                }.getType());


                javafx.application.Platform.runLater(() -> {
                    int columns = 0;
                    int rows = 1;
                    commentGrid.getChildren().clear();
                    for  (int i = comments.size() - 1; i >= 0; i--) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/nessam/client/CommentCard2.fxml"));
                            HBox commentBox = fxmlLoader.load();
                            CommentCardController commentCardController = fxmlLoader.getController();
                            commentCardController.setData(comments.get(i));
                            commentGrid.add(commentBox, columns++, rows);
                            GridPane.setMargin(commentBox, new Insets(10));
                            if (columns == 1) {
                                columns = 0;
                                rows++;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void back(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(6);
    }
}
