package models;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JobPosition {

    public JobPosition(String userEmail, String title, EmploymentType employmentType, String companyName, String workLocation, WorkLocationType workLocationType, boolean currentlyWorking, String startDate, String endDate, String description, String skills, boolean notifyNetwork) {
        this.userEmail = userEmail;
        this.title = title;
        this.employmentType = employmentType;
        this.companyName = companyName;
        this.workLocation = workLocation;
        this.workLocationType = workLocationType;
        this.currentlyWorking = currentlyWorking;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.skills = skills;
        this.notifyNetwork = notifyNetwork;
    }

    public enum EmploymentType {
        FULL_TIME, PART_TIME, SELF_EMPLOYED, FREELANCE, CONTRACT, INTERNSHIP, PAID_INTERNSHIP, SEASONAL
    }

    public enum WorkLocationType {
        ONSITE, HYBRID, REMOTE
    }




    @JsonProperty("userEmail")
    private String userEmail;

    @JsonProperty("title")
    private String title;

    @JsonProperty("employmentType")
    private EmploymentType employmentType;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("workLocation")
    private String workLocation;

    @JsonProperty("workLocationType")
    private WorkLocationType workLocationType;

    @JsonProperty("currentlyWorking")
    private boolean currentlyWorking;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("description")
    private String description;

    @JsonProperty("skills")
    private String skills;

    @JsonProperty("notifyNetwork")
    private boolean notifyNetwork;

    public JobPosition() {
    }

    @Override
    public String toString() {
        return "JobPosition{" +

                ", userEmail='" + userEmail + '\'' +
                ", title='" + title + '\'' +
                ", employmentType=" + employmentType +
                ", companyName='" + companyName + '\'' +
                ", workLocation='" + workLocation + '\'' +
                ", workLocationType=" + workLocationType +
                ", currentlyWorking=" + currentlyWorking +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", description='" + description + '\'' +
                ", skills='" + skills + '\'' +
                ", notifyNetwork=" + notifyNetwork +
                '}';
    }
}
