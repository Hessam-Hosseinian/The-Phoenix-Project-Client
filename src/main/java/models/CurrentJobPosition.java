package models;


import com.google.gson.annotations.SerializedName;


public class CurrentJobPosition {

    private User user;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("title")
    private String jobTitle;

    @SerializedName("employmentType")
    private String employmentType;

    @SerializedName("companyName")
    private String companyName;

    @SerializedName("workLocation")
    private String workLocation;

    @SerializedName("workLocationType")
    private String workplaceType;

    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("description")
    private String description;

    @SerializedName("skills")
    private String skills;

    @SerializedName("notifyChanges")
    private boolean notifyChanges;

    public CurrentJobPosition() {

    }
// Getters and setters



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getWorkplaceType() {
        return workplaceType;
    }

    public void setWorkplaceType(String workplaceType) {
        this.workplaceType = workplaceType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public boolean isNotifyChanges() {
        return notifyChanges;
    }

    public void setNotifyChanges(boolean notifyChanges) {
        this.notifyChanges = notifyChanges;
    }

    @Override
    public String toString() {
        return "CurrentJobPosition{" +
                "user=" + user +
                ", userEmail='" + userEmail + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", companyName='" + companyName + '\'' +
                ", workLocation='" + workLocation + '\'' +
                ", workplaceType='" + workplaceType + '\'' +
                ", isActive=" + isActive +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", description='" + description + '\'' +
                ", skills='" + skills + '\'' +
                ", notifyChanges=" + notifyChanges +
                '}';
    }
}
