package com.nessam.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.UserEducation;

public class Education_Controller  {

    @FXML
    private Label degree;


    @FXML
    private Label fieldOfStudy;

    @FXML
    private Label institution;

    @FXML
    private Label startDate;


    @FXML
    private Label endDate;

    public void setData(UserEducation education) {
        degree.setText(education.getDegree());
        fieldOfStudy.setText(education.getFieldOfStudy());
        institution.setText(education.getInstitution());
        startDate.setText(education.getStartDate());
        endDate.setText(education.getEndDate());
    }

    public Label getDegree() {
        return degree;
    }

    public void setDegree(Label degree) {
        this.degree = degree;
    }

    public Label getEndDate() {
        return endDate;
    }

    public void setEndDate(Label endDate) {
        this.endDate = endDate;
    }

    public Label getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(Label fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public Label getInstitution() {
        return institution;
    }

    public void setInstitution(Label institution) {
        this.institution = institution;
    }

    public Label getStartDate() {
        return startDate;
    }

    public void setStartDate(Label startDate) {
        this.startDate = startDate;
    }
}
