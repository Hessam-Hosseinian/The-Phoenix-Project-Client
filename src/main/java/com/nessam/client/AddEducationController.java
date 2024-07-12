package com.nessam.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

public class AddEducationController {

    @FXML
    private TextArea activitiesField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private DatePicker endDateField;

    @FXML
    private TextField fieldOfStudyField;

    @FXML
    private TextField gradeField;

    @FXML
    private CheckBox notifyNetworkCheckbox;

    @FXML
    private TextField schoolNameField;

    @FXML
    private TextField skillsField;

    @FXML
    private DatePicker startDateField;

    @FXML
    public void addEducation(ActionEvent event) throws IOException {
        try {

            String jwtToken = HelloApplication.getJWT_token();


            URL url = new URL(MyUrl.EDUCATION_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", jwtToken);
            conn.setDoOutput(true);


            JSONObject json = new JSONObject();
            json.put("schoolName", schoolNameField.getText());
            json.put("field_of_study", fieldOfStudyField.getText());
            json.put("start_date", startDateField.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
            json.put("end_date", endDateField.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
            json.put("grade", gradeField.getText());
            json.put("activities", activitiesField.getText());
            json.put("description", descriptionField.getText());
            json.put("skill", skillsField.getText());
            json.put("notify_network", notifyNetworkCheckbox.isSelected());
            json.put("user_email", HelloApplication.loggedin_user.getEmail());


            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }


            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Response: " + response.toString());
                }
            } else {
                System.err.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }


        HelloApplication application = new HelloApplication();
        application.changeScene(6);
    }

    @FXML
    void handleHomeAction(ActionEvent event) {
        HelloApplication application = new HelloApplication();
        application.changeScene(6);
    }



}
