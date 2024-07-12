package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import models.Post;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.BetterLogger;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Feed_Controller implements Initializable {

    @FXML
    private GridPane postGrid;
    @FXML
    private Circle circle;
    @FXML
    private ImageView background;
    @FXML
    private Label welcome;

    @FXML
    private TextField searchBox;

    private Popup searchPopup;
    @FXML
    private GridPane mightBeKnow;

    private double userWidth;
    private double postPopUpWidth;
    private Popup searchPopupUser;
    private Popup searchPopupPost;
    private Popup searchPopupHashtag;
    private List<Post> posts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {


            FileDownloadClient.downloadFile(HelloApplication.loggedin_user.getProfilePicture());
            Image profile = new Image(HelloApplication.loggedin_user.getProfilePicture());
            circle.setFill(new ImagePattern(profile));
        } catch (Exception e) {
            BetterLogger.WARNING("profile image not found");
            Image profile = new Image("Default_Profile_Picture.png");
            circle.setFill(new ImagePattern(profile));
        }



        searchPopupUser = new Popup();
        searchPopupPost = new Popup();
        searchPopupHashtag = new Popup();
        searchPopupUser.setAutoHide(true);
        searchPopupPost.setAutoHide(true);
        searchPopupHashtag.setAutoHide(true);

        searchBox.addEventFilter(KeyEvent.KEY_RELEASED, event -> handleSearch());


        try {


            FileDownloadClient.downloadFile(HelloApplication.loggedin_user.getBackgroundPicture());
            Image back = new Image(HelloApplication.loggedin_user.getBackgroundPicture());
            background.setImage(back);
        } catch (Exception e) {
            BetterLogger.WARNING("background image not found");
            background.setImage(new Image("background.jpg"));
        }

        try {
            welcome.setText("Welcome " + HelloApplication.loggedin_user.getFirstName() + " =) !");
            posts = new ArrayList<>();
            fetchPostsFromServer();
        } catch (Exception e) {
            BetterLogger.WARNING("fetchPostsFromServer failed");
        }

        int columns = 0;
        int rows = 1;
        try {
            for (int i = 0; i < posts.size(); i++) {
                VBox postBox = null;
                Post_Controller postController = null;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/nessam/client/Post.fxml"));
                    postBox = fxmlLoader.load();
                    postController = fxmlLoader.getController();
                } catch (IOException e) {
                    BetterLogger.WARNING("PostController failed");
                }
                postController.setData(posts.get(i));
                if (columns == 1) {
                    columns = 0;
                    rows++;
                }
                postGrid.add(postBox, columns++, rows);
                GridPane.setMargin(postBox, new Insets(10));
            }
        } catch (IOException e) {
            BetterLogger.WARNING("post loading failed");
        }
    }

    private void fetchPostsFromServer() {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(MyUrl.POSTS_URL);
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
                posts = gson.fromJson(response.toString(), new TypeToken<List<Post>>() {
                }.getType());


                // Update the UI on the JavaFX Application Thread
                javafx.application.Platform.runLater(() -> {
                    int columns = 0;
                    int rows = 1;
                    postGrid.getChildren().clear();
                    try {
                        for (int i = posts.size() - 1; i >= 0; i--) {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/nessam/client/Post.fxml"));
                            VBox postBox = fxmlLoader.load();
                            Post_Controller postController = fxmlLoader.getController();
                            try {
                                postController.setData(posts.get(i));
                            } catch (Exception e) {
                                BetterLogger.WARNING("ridi1");
                            }
                            if (columns == 1) {
                                columns = 0;
                                rows++;
                            }
                            postGrid.add(postBox, columns++, rows);
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

    private void handleSearch() {
        String query = searchBox.getText();
        if (query.isEmpty()) {
            searchPopupUser.hide();
            searchPopupPost.hide();
            searchPopupHashtag.hide();
            return;
        }
        new Thread(() -> {
            try {
                String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
                URL url = new URL(MyUrl.SEARCHUser_URL + encodedQuery);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");


                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                connection.disconnect();
                List<String> results = parseSearchResultsUser(response.toString());
//                if (results.isEmpty()) {
//                    searchPopupUser.hide();
//                }
                Platform.runLater(() -> showSearchResultsUser(results, query));

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to fetch search results. Please try again.");
                    alert.showAndWait();
                });
            }
        }).start();

        new Thread(() -> {
            try {
                String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
                URL url = new URL(MyUrl.SEARCHPost_URL + encodedQuery);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                connection.disconnect();
                List<String> results = parseSearchResultsPost(response.toString());
//                if (results.isEmpty()) {
//                    searchPopupPost.hide();
//                }
                Platform.runLater(() -> showSearchResultsPost(results, query));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to fetch search results. Please try again.");
                    alert.showAndWait();
                });
            }
        }).start();

        new Thread(() -> {
            try {
                String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
                URL url = new URL(MyUrl.HASHTAG_URL + encodedQuery);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                connection.disconnect();
                List<String> results = parseSearchResultsHashtag(response.toString());
//                if (results.isEmpty()) {
//                    searchPopupHashtag.hide();
//                }
                Platform.runLater(() -> showSearchResultsHashtag(results, query));

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to fetch search results. Please try again.");
                    alert.showAndWait();
                });
            }
        }).start();

    }


    private List<String> parseSearchResults(String jsonResponse) {
        List<String> results = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String firstName = jsonObject.getString("firstName");
            String lastName = jsonObject.getString("lastName");
            String email = jsonObject.getString("email");

            results.add(firstName + " " + lastName + "\n" + email);
        }
        return results;
    }

    private void showSearchResults(List<String> results, String query) {
        VBox resultsBox = new VBox();
        resultsBox.setStyle("-fx-background-color: white; -fx-padding: 30;");
        resultsBox.setSpacing(5);

        for (String result : results) {
            String[] parts = result.split("\n", 2);
            String fullName = parts[0];
            String email = parts[1];

            Label nameLabel = new Label(fullName);
            Hyperlink emailLink = new Hyperlink(email);
            //  emailLink.setOnAction(event -> openUserProfile(email));

            VBox resultItemBox = new VBox(nameLabel, emailLink);
            resultItemBox.setSpacing(2);

            resultsBox.getChildren().add(resultItemBox);
        }

        searchPopup.getContent().clear();
        searchPopup.getContent().add(resultsBox);

        if (!searchPopup.isShowing()) {
            searchPopup.show(searchBox, searchBox.getScene().getWindow().getX() + searchBox.getLayoutX(), searchBox.getScene().getWindow().getY() + searchBox.getLayoutY() + searchBox.getHeight() + 32);
        }
    }



    private List<String> parseSearchResultsUser(String jsonResponse) {
        List<String> results = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String firstName = jsonObject.getString("firstName");
            String lastName = jsonObject.getString("lastName");
            String email = jsonObject.getString("email");

            results.add(firstName + " " + lastName + "\n" + email);
        }
        return results;
    }

    private List<String> parseSearchResultsPost(String jsonResponse) {
        List<String> results = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String title = jsonObject.getString("title");
            String author = jsonObject.getString("author");
            results.add(title + "\n" + author);
        }
        return results;
    }

    private List<String> parseSearchResultsHashtag(String jsonResponse) {
        List<String> results = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String title = jsonObject.getString("title");
            String author = jsonObject.getString("author");
            results.add(title + "\n" + author);
        }
        return results;
    }
    private void showSearchResultsUser(List<String> results, String query) {
        VBox resultsBox = new VBox();
        resultsBox.setStyle("-fx-background-color: white; -fx-padding: 30;");
        resultsBox.setSpacing(5);
        Label titleLabel = new Label("Search Results for Users:");
        titleLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 14px;");
        resultsBox.getChildren().add(titleLabel);

        for (String result : results) {
            String[] parts = result.split("\n", 2);
            String name = parts[0];
            String email = parts[1];

            Label nameLabel = new Label(name);
            Hyperlink emailLink = new Hyperlink(email);
            //  emailLink.setOnAction(event -> openUserProfile(email));

            VBox resultItemBox = new VBox(nameLabel, emailLink);
            resultItemBox.setSpacing(2);

            resultsBox.getChildren().add(resultItemBox);
        }

        searchPopupUser.getContent().clear();
        searchPopupUser.getContent().add(resultsBox);

        if (!searchPopupUser.isShowing()) {
            searchPopupUser.show(searchBox, searchBox.getScene().getWindow().getX() + searchBox.getLayoutX(), searchBox.getScene().getWindow().getY() + searchBox.getLayoutY() + searchBox.getHeight() + 32);
        }
        userWidth = searchPopupUser.getWidth();
    }
    private void showSearchResultsPost(List<String> results, String query) {
        VBox resultsBox = new VBox();
        resultsBox.setStyle("-fx-background-color: white; -fx-padding: 30;");
        resultsBox.setSpacing(5);
        Label titleLabel = new Label("Results for Posts:");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        resultsBox.getChildren().add(titleLabel);
        for (String result : results) {
            String[] parts = result.split("\n", 2);
            String postTitle = parts[0];
            String email = parts[1];

            Label nameLabel = new Label(postTitle);
            Hyperlink emailLink = new Hyperlink(email);
            //  emailLink.setOnAction(event -> openUserProfile(email));

            VBox resultItemBox = new VBox(nameLabel, emailLink);
            resultItemBox.setSpacing(2);

            resultsBox.getChildren().add(resultItemBox);
        }

        searchPopupPost.getContent().clear();
        searchPopupPost.getContent().add(resultsBox);

        if (!searchPopupPost.isShowing()) {
            searchPopupPost.show(searchBox, searchBox.getScene().getWindow().getX() + searchBox.getLayoutX() + userWidth, searchBox.getScene().getWindow().getY() + searchBox.getLayoutY() + searchBox.getHeight() + 32);
        }
        postPopUpWidth = searchPopupPost.getWidth();
    }

    private void showSearchResultsHashtag(List<String> results, String query) {
        VBox resultsBox = new VBox();
        resultsBox.setStyle("-fx-background-color: white; -fx-padding: 30;");
        resultsBox.setSpacing(5);
        Label titleLabel = new Label("Results for Hashtags:");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        resultsBox.getChildren().add(titleLabel);
        for (String result : results) {
            String[] parts = result.split("\n", 2);
            String postTitle = parts[0];
            String email = parts[1];

            Label nameLabel = new Label(postTitle);
            Hyperlink emailLink = new Hyperlink(email);
            //  emailLink.setOnAction(event -> openUserProfile(email));

            VBox resultItemBox = new VBox(nameLabel, emailLink);
            resultItemBox.setSpacing(2);

            resultsBox.getChildren().add(resultItemBox);
        }

        searchPopupHashtag.getContent().clear();
        searchPopupHashtag.getContent().add(resultsBox);

        if (!searchPopupHashtag.isShowing()) {
            searchPopupHashtag.show(searchBox, searchBox.getScene().getWindow().getX() + searchBox.getLayoutX() + userWidth + postPopUpWidth, searchBox.getScene().getWindow().getY() + searchBox.getLayoutY() + searchBox.getHeight() + 32);
        }
    }

    @FXML
    public void handleMessagingButton() throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(12);

    }

    @FXML
    public void handleAddingEducationAction(){
        HelloApplication application = new HelloApplication();
        application.changeScene(14);
    }

    @FXML
    public void handleAddingJob(){
        HelloApplication application = new HelloApplication();

        application.changeScene(15);
    }


}
