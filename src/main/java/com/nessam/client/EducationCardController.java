package com.nessam.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.UserEducation;

public class EducationCardController {

    @FXML
    private Label schoolNameLabel;
    @FXML
    private Label fieldOfStudyLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label gradeLabel;
    @FXML
    private Label activitiesLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label skillsLabel;
    @FXML
    private Label notifyNetworkLabel;
    @FXML
    private Label userEmailLabel;

    public void setUserEducation(UserEducation userEducation) {


        schoolNameLabel.setText(userEducation.getSchoolName());
        fieldOfStudyLabel.setText(userEducation.getFieldOfStudy());
        startDateLabel.setText(userEducation.getStart_date());
        endDateLabel.setText(userEducation.getEnd_date());
        gradeLabel.setText(userEducation.getGrade());
        activitiesLabel.setText(userEducation.getActivities());
        descriptionLabel.setText(userEducation.getDescription());
        skillsLabel.setText(userEducation.getSkills());

        notifyNetworkLabel.setText(userEducation.getNotifyNetwork() ? "yse" : "no");

        userEmailLabel.setText(userEducation.getUser_email());
    }

    public Label getSchoolNameLabel() {
        return schoolNameLabel;
    }

    public void setSchoolNameLabel(Label schoolNameLabel) {
        this.schoolNameLabel = schoolNameLabel;
    }

    public Label getFieldOfStudyLabel() {
        return fieldOfStudyLabel;
    }

    public void setFieldOfStudyLabel(Label fieldOfStudyLabel) {
        this.fieldOfStudyLabel = fieldOfStudyLabel;
    }

    public Label getStartDateLabel() {
        return startDateLabel;
    }

    public void setStartDateLabel(Label startDateLabel) {
        this.startDateLabel = startDateLabel;
    }

    public Label getEndDateLabel() {
        return endDateLabel;
    }

    public void setEndDateLabel(Label endDateLabel) {
        this.endDateLabel = endDateLabel;
    }

    public Label getGradeLabel() {
        return gradeLabel;
    }

    public void setGradeLabel(Label gradeLabel) {
        this.gradeLabel = gradeLabel;
    }

    public Label getActivitiesLabel() {
        return activitiesLabel;
    }

    public void setActivitiesLabel(Label activitiesLabel) {
        this.activitiesLabel = activitiesLabel;
    }

    public Label getDescriptionLabel() {
        return descriptionLabel;
    }

    public void setDescriptionLabel(Label descriptionLabel) {
        this.descriptionLabel = descriptionLabel;
    }

    public Label getSkillsLabel() {
        return skillsLabel;
    }

    public void setSkillsLabel(Label skillsLabel) {
        this.skillsLabel = skillsLabel;
    }

    public Label getNotifyNetworkLabel() {
        return notifyNetworkLabel;
    }

    public void setNotifyNetworkLabel(Label notifyNetworkLabel) {
        this.notifyNetworkLabel = notifyNetworkLabel;
    }

    public Label getUserEmailLabel() {
        return userEmailLabel;
    }

    public void setUserEmailLabel(Label userEmailLabel) {
        this.userEmailLabel = userEmailLabel;
    }
    @FXML
    void handleHomeAction(ActionEvent event) {
        HelloApplication application = new HelloApplication();
        application.changeScene(6);

    }
}
