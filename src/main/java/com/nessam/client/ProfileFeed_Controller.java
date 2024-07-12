package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.CurrentJobPosition;
import models.Follow;
import models.UserEducation;
import utils.BetterLogger;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileFeed_Controller implements Initializable {


    @FXML
    private GridPane EducationGrid;
    @FXML
    private GridPane followersGrid;

    @FXML
    private Label followersNum;


    @FXML
    private Label followingNum;

    @FXML
    private GridPane followingGrid;
    private List<UserEducation> educations;
    private List<Follow> followers;
    private List<Follow> following;
    private List<CurrentJobPosition> jobs;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            if (following != null) following.clear();
            if (followers != null) followers.clear();
            fetchEducationsFromServer();
            fetchJobPositionFromServer();


            fetchFollowersFromServer();

            fetchFollowingsFromServer();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/nessam/client/ProfileMain.fxml"));
            VBox postBox = fxmlLoader.load();


            EducationGrid.add(postBox, 0, 0);

            GridPane.setMargin(postBox, new Insets(400, 0, 600, 0));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        int columns = 0;
        int rows = 1;
        try {
            for (int i = 0; i < educations.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/nessam/client/Education.fxml"));
                VBox postBox = fxmlLoader.load();
                EducationCardController educationController = fxmlLoader.getController();


                try {
                    educationController.setUserEducation(educations.get(i));
                } catch (Exception e) {
                    BetterLogger.ERROR("ah");
                }

                if (columns == 1) {
                    columns = 0;
                    rows++;
                }


                EducationGrid.add(postBox, columns++, rows);


            }
        } catch (Exception e) {

            BetterLogger.WARNING("failed to load Educations");
        }


        try {
            for (int i = 0; i < jobs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/com/nessam/client/JobPositionFixedCard.fxml"));


                AnchorPane postBox = fxmlLoader.load();

                JobPositionController jobPositionController = fxmlLoader.getController();


                jobPositionController.setData(jobs.get(i));

                if (columns == 1) {
                    columns = 0;
                    rows++;
                }


                EducationGrid.add(postBox, columns++, rows);

            }
        } catch (Exception e) {

            BetterLogger.WARNING("failed to load jobs");

        }
    }

    private void fetchFollowersFromServer() {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(MyUrl.FOLLOWERS_URL + UserData.getInstance().getOcd_email());
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
//                followers.clear();
                followers = gson.fromJson(response.toString(), new TypeToken<List<Follow>>() {
                }.getType());


                // Update UI on JavaFX Application Thread
                javafx.application.Platform.runLater(() -> {
                    int columns = 0;
                    int rows = 1;
                    followersGrid.getChildren().clear();
                    for (Follow follow : followers) {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/nessam/client/FollowCard.fxml"));
                            HBox followBox = fxmlLoader.load();
                            FollowCardController followCardController = fxmlLoader.getController();
                            followCardController.setData(follow);

                            followersGrid.add(followBox, columns++, rows);
                            GridPane.setMargin(followBox, new Insets(10));

                            // Increment rows and reset columns if necessary
                            if (columns == 1) {
                                columns = 0;
                                rows++;
                            }
                        } catch (Exception e) {
                            BetterLogger.WARNING("failed to fetch followers");
                        }
                    }

                    followersNum.setText("Followers: " + followers.size());
                });

            } catch (Exception e) {
                BetterLogger.WARNING("failed to fetch followers");
            }
        });
        thread.setDaemon(true);
        thread.start();

    }


    private void fetchFollowingsFromServer() {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(MyUrl.FOLLOWINGS_URL + UserData.getInstance().getOcd_email());
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
//                following.clear();
                following = gson.fromJson(response.toString(), new TypeToken<List<Follow>>() {
                }.getType());


                // Update UI on JavaFX Application Thread
                javafx.application.Platform.runLater(() -> {
                    int columns = 0;
                    int rows = 1;
                    followingGrid.getChildren().clear();
                    for (Follow follow : following) {

                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/com/nessam/client/FollowingCard.fxml"));
                            HBox followBox = fxmlLoader.load();
                            FollowingCardController followingCardController = fxmlLoader.getController();
                            followingCardController.setData(follow);

                            followingGrid.add(followBox, columns++, rows);
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
            followingNum.setText("Followings: " + following.size());
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void fetchEducationsFromServer() {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(MyUrl.EDUCATION_URL + "/" + HelloApplication.loggedin_user.getEmail());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                String jwtToken = HelloApplication.getJWT_token();
                if (jwtToken != null && !jwtToken.isEmpty()) {
                    jwtToken = jwtToken.trim(); // Ensure the token is trimmed
                    connection.setRequestProperty("Authorization", jwtToken);
                }


                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                Gson gson = new Gson();
                Type userListType = new TypeToken<List<UserEducation>>() {
                }.getType();
                educations = gson.fromJson(response.toString(), userListType);


//

            } catch (IOException e) {
                BetterLogger.WARNING("failed to fetch educations");
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleHomeAction(ActionEvent event) throws IOException {
        HelloApplication app = new HelloApplication();
        app.changeScene(6);
    }

    @FXML
    private void handleAddingPostActions(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(9);
    }

    private void fetchJobPositionFromServer() {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(MyUrl.JOBS_URL + UserData.getInstance().getOcd_email());

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
                Type userListType = new TypeToken<List<CurrentJobPosition>>() {
                }.getType();
                jobs = gson.fromJson(response.toString(), userListType);


//

            } catch (Exception e) {
                BetterLogger.WARNING("failed to fetch job positions");
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void handleAddingPostAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(9);
    }

    @FXML
    public void handleAddingEducationAction() {
        HelloApplication application = new HelloApplication();
        application.changeScene(14);
    }

    @FXML
    private void handleProfileAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(8);
    }

    @FXML
    public void handleMessagingButton() throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(12);

    }

    @FXML
    public void handleAddingJob() {
        HelloApplication application = new HelloApplication();

        application.changeScene(15);
    }

    @FXML
    private void handleSettingsAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(10);
    }


}
