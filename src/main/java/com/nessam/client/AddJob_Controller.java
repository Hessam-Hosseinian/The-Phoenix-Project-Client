package com.nessam.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import org.json.JSONObject;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddJob_Controller {

    @FXML
    private Button AddPost_bt;

    @FXML
    private Button HomeBt;

    @FXML
    private Button addEdu_bt;

    @FXML
    private Button addJob_bt;

    @FXML
    private ImageView background;

    @FXML
    private Circle circle;

    @FXML
    private TextField cpmpanyName;

    @FXML
    private TextField description;

    @FXML
    private TextField employmentTyple;

    @FXML
    private DatePicker endDate;

    @FXML
    private MenuItem profileViewBotton;

    @FXML
    private TextField searchBox;

    @FXML
    private MenuItem settingsButton;

    @FXML
    private TextField skills;

    @FXML
    private DatePicker startDate;

    @FXML
    private TextField title;

    @FXML
    private Label welcome;

    @FXML
    private TextField workLocation;

    @FXML
    private TextField workType;

    @FXML
    public void addJobToProfile(ActionEvent event) {
        try {
            String jobTitle = title.getText();
            String jobDescription = description.getText();
            String jobEmployment = employmentTyple.getText();
            String jobEndDate = endDate.getValue().toString();
            String jobStartDate = startDate.getValue().toString();
            String jobWorkLocation = workLocation.getText();
            String jobWorkType = workType.getText();
            String jobSkills = skills.getText();
            String jwtToken = HelloApplication.getJWT_token();

            URL url = new URL(MyUrl.JOBS_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", jwtToken);
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("title", jobTitle);
            json.put("description", jobDescription);
            json.put("employment", jobEmployment);
            json.put("endDate", jobEndDate);
            json.put("startDate", jobStartDate);
            json.put("workLocation", jobWorkLocation);
            json.put("workType", jobWorkType);
            json.put("skills", jobSkills);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                }
            } else {
                System.err.println("Error: " + responseCode);

            }
        } catch (Exception e) {
            System.err.println("Error: ");
        }
    }

    @FXML
    void handleAddingPostActions(ActionEvent event) {

    }

    @FXML
    void handleHomeAction(ActionEvent event) {
        HelloApplication application = new HelloApplication();
        application.changeScene(6);

    }

    @FXML
    void handleProfileAction(ActionEvent event) {

    }

    @FXML
    void handleSettingsAction(ActionEvent event) {

    }

    @FXML
    public void addComment(ActionEvent actionEvent) {
    }

    @FXML
    public void addEdu(ActionEvent actionEvent) {
    }

    @FXML
    public void back(ActionEvent actionEvent) {
    }
}
