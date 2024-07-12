package com.nessam.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.CurrentJobPosition;
import models.UserEducation;

public class JobPositionController {
    @FXML
    private Label companyName;

    @FXML
    private Label description;

    @FXML
    private Label employmentType;

    @FXML
    private Label endDate;

    @FXML
    private Label skills;

    @FXML
    private Label startDate;

    @FXML
    private Label title;

    @FXML
    private Label workLocatinType;

    @FXML
    private Label workLocation;

    public void setData(CurrentJobPosition job) {
        companyName.setText(job.getCompanyName());
        description.setText(job.getDescription());
        employmentType.setText(job.getEmploymentType());
        endDate.setText(job.getEndDate());
        skills.setText(job.getSkills());
        startDate.setText(job.getStartDate());
        title.setText(job.getJobTitle());
        workLocation.setText(job.getWorkLocation());
        workLocatinType.setText(job.getWorkplaceType());
    }



}
