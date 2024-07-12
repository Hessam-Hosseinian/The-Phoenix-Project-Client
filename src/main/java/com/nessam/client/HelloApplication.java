package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;
import utils.BetterLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class HelloApplication extends Application {
    public static User loggedin_user = new User();
    private static String JWT_token;
    private static Stage stage;
    private static boolean isNightMode = false;

    private static String readTokenFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8).trim();
    }

    public static void toggleNightMode() {
        isNightMode = !isNightMode;
        Scene currentScene = stage.getScene();
        HelloApplication application = new HelloApplication();
        application.applyStylesheet(currentScene);
    }

    public static String getJWT_token() {
        return JWT_token;
    }

    public static void setJWT_token(String JWT_token) {
        HelloApplication.JWT_token = JWT_token;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        // Path to the JWT token file
        String tokenFilePath = "/assets.txt";  // Adjust this path as necessary
        String token = null;

        // Attempt to read the token from the file
        try {
            token = readTokenFromFile(tokenFilePath);

        } catch (IOException e) {
            System.out.println("Token file not found: " + e.getMessage());
        }

        // Validate the token
        if (token != null && isTokenValid(token)) {
            setJWT_token(token);

            if (isTokenValid(token)) {
                handleAutomaticLogin();
            } else {
                changeScene(1); // Show login page
            }
        } else {
            changeScene(1); // Show login page
        }

        primaryStage.show();
    }

    // Validate the token by making a request to the server
    private boolean isTokenValid(String token) {
        try {
            String validationUrl = "http://localhost:8080/req"; // Adjust with your validation endpoint
            HttpURLConnection connection = createHttpConnection(validationUrl, "POST");

            connection.setRequestProperty("Authorization", token);


            int responseCode = connection.getResponseCode();


            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void applyStylesheet(Scene scene) {
        scene.getStylesheets().clear();
        if (isNightMode) {
            scene.getStylesheets().add(getClass().getResource("DarkStyle.css").toExternalForm());
        } else {
            scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        }
    }

    public void changeScene(int sceneNum) {
        showLoadingScreen(() -> {
            String fxmlFile = "";
            switch (sceneNum) {
                case 1:
                    fxmlFile = "LoginPage.fxml";
                    break;
                case 2:
                    fxmlFile = "SignUp1.fxml";
                    break;
                case 3:
                    fxmlFile = "SignUp2.fxml";
                    break;
                case 4:
                    fxmlFile = "SignUp3.fxml";
                    break;
                case 5:
                    fxmlFile = "SignUp4.fxml";
                    break;
                case 6:
                    fxmlFile = "feed.fxml";
                    break;
                case 7:
                    fxmlFile = "Post.fxml";
                    break;
                case 8:
                    fxmlFile = "ProfileFeed.fxml";
                    break;
                case 9:
                    fxmlFile = "AddingPost.fxml";
                    break;
                case 10:
                    fxmlFile = "Setting.fxml";
                    break;
                case 11:
                    fxmlFile = "CommentCard.fxml";
                    break;
                case 12:
                    fxmlFile = "EmptyDirect.fxml";
                    break;
                case 13:
                    fxmlFile = "LikeList.fxml";
                    break;
                case 14:
                    fxmlFile = "AddingEducation.fxml";
                    break;
                case 15:
                    fxmlFile = "AddingJob.fxml";
                    break;

            }

            if (!fxmlFile.isEmpty()) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
                    Scene scene = new Scene(fxmlLoader.load());
                    applyStylesheet(scene);

                    // Update the scene on the JavaFX Application Thread
                    Platform.runLater(() -> stage.setScene(scene));
                } catch (IOException e) {
                    BetterLogger.ERROR("There is something went wrong: " + e.getMessage());
                }
            }
        });
    }

    private void showLoadingScreen(Runnable loadNextScene) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Loading.fxml"));
            Scene loadingScene = new Scene(loader.load());
            stage.setScene(loadingScene);

            CompletableFuture.runAsync(loadNextScene)
                    .thenRun(() -> Platform.runLater(() -> {
                        // Remove the loading screen and show the loaded scene
                        stage.getScene().getRoot().requestFocus();
                    }));
        } catch (IOException e) {
            BetterLogger.ERROR("Failed to load loading screen: " + e.getMessage());
        }
    }

    private void handleAutomaticLogin() throws IOException {
        try {
            String[] arrOfStr = getJWT_token().split(" ");
            String userUrl = "http://localhost:8080/users/jwt/" + arrOfStr[1];

            HttpURLConnection userConnection = createHttpConnection(userUrl, "GET");

            int responseCode = userConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String responseText = getResponseText(userConnection);
                User user = null;
                Gson gson = new Gson();
                try {

                    user = gson.fromJson(responseText, User.class);
                    loggedin_user = user;
                } catch (JsonSyntaxException e) {
                    System.out.println("GSON ?");
                }
                UserData.getInstance().setOcd_email(user.getEmail());
                changeScene(6);
            } else {
                changeScene(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            changeScene(1);
        }
    }

    private HttpURLConnection createHttpConnection(String urlString, String method) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        String jwtToken = getJWT_token();
        if (jwtToken != null && !jwtToken.isEmpty()) {
            connection.setRequestProperty("Authorization", "Bearer " + jwtToken.trim());
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
}
