package models;


import com.google.gson.annotations.SerializedName;

public class UserEducation {


    @SerializedName("schoolName")
    private String schoolName;

    @SerializedName("fieldOfStudy")
    private String fieldOfStudy;



    @SerializedName("startDate")
    private String start_date;

    @SerializedName("endDate")
    private String end_date;

    @SerializedName("grade")
    private String grade;

    @SerializedName("activities")

    private String activities;

    @SerializedName("description")

    private String description;

    @SerializedName("skills")

    private String skills;

    @SerializedName("notifyNetwork")

    private Boolean notifyNetwork;

    @SerializedName("user_email")

    private String user_email;
    public UserEducation() {
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }



    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
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

    public Boolean getNotifyNetwork() {
        return notifyNetwork;
    }

    public void setNotifyNetwork(Boolean notifyNetwork) {
        this.notifyNetwork = notifyNetwork;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    @Override
    public String toString() {
        return "UserEducation{" +
                "schoolName='" + schoolName + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +

                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", grade='" + grade + '\'' +
                ", activities='" + activities + '\'' +
                ", description='" + description + '\'' +
                ", skills='" + skills + '\'' +
                ", notifyNetwork=" + notifyNetwork +
                ", user_email='" + user_email + '\'' +
                '}';
    }
}
