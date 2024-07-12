package com.nessam.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.JobPosition;
import utils.BetterLogger;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class JobPositionFormController {

    @FXML
    private TextField userEmailField;
    @FXML
    private TextField titleField;
    @FXML
    private ComboBox<JobPosition.EmploymentType> employmentTypeComboBox;
    @FXML
    private TextField companyNameField;
    @FXML
    private TextField workLocationField;
    @FXML
    private ComboBox<JobPosition.WorkLocationType> workLocationTypeComboBox;
    @FXML
    private CheckBox currentlyWorkingCheckBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextArea skillsArea;
    @FXML
    private CheckBox notifyNetworkCheckBox;

    @FXML
    public void initialize() {
        employmentTypeComboBox.getItems().setAll(JobPosition.EmploymentType.values());
        workLocationTypeComboBox.getItems().setAll(JobPosition.WorkLocationType.values());
    }

    @FXML
    private void handleSubmit() {
        String startDate = startDatePicker.getValue() != null ? startDatePicker.getValue().format(DateTimeFormatter.ISO_DATE) : "";
        String endDate = endDatePicker.getValue() != null ? endDatePicker.getValue().format(DateTimeFormatter.ISO_DATE) : "";

        JobPosition jobPosition = new JobPosition(userEmailField.getText(), titleField.getText(), employmentTypeComboBox.getValue(), companyNameField.getText(), workLocationField.getText(), workLocationTypeComboBox.getValue(), currentlyWorkingCheckBox.isSelected(), startDate, endDate, descriptionArea.getText(), skillsArea.getText(), notifyNetworkCheckBox.isSelected());

        try {
            URL url = new URL("http://localhost:8080/jobPosition");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            String jsonInputString = mapper.writeValueAsString(jobPosition);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("JobPosition was created successfully.");
            } else {
                System.out.println("POST request not worked");
            }

            HelloApplication application = new HelloApplication();
            application.changeScene(8);
        } catch (Exception e) {
            HelloApplication application = new HelloApplication();
            application.changeScene(8);
            BetterLogger.WARNING("warning");
        }
    }

    @FXML
    public void handleHomeAction(ActionEvent actionEvent) {
        HelloApplication application = new HelloApplication();
        application.changeScene(6);
    }
}
