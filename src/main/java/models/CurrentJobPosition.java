package models;


import java.sql.Date;


public class CurrentJobPosition {


    private User user;


    private String jobTitle;


    private int employmentType;


    private String companyName;


    private String workLocation;

    private int workplaceType;


    private boolean isActive;


    private Date startDate;

    private Date endDate;

    private String description;


    private String skills;

    private boolean notifyChanges;

    public CurrentJobPosition(User user, String jobTitle, int employmentType, String companyName, String workLocation, int workplaceType, boolean isActive, Date startDate, Date endDate, String description, String skills, boolean notifyChanges) {

        this.user = user;
        this.jobTitle = jobTitle;
        this.employmentType = employmentType;
        this.companyName = companyName;
        this.workLocation = workLocation;
        this.workplaceType = workplaceType;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.skills = skills;
        this.notifyChanges = notifyChanges;
    }

    public CurrentJobPosition() {

    }
// Getters and setters



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(int employmentType) {
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

    public int getWorkplaceType() {
        return workplaceType;
    }

    public void setWorkplaceType(int workplaceType) {
        this.workplaceType = workplaceType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
}
