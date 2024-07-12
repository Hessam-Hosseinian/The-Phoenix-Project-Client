package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("additionalName")
    private String additionalName;

    @SerializedName("profilePicture")
    private String profilePicture;

    @SerializedName("backgroundPicture")
    private String backgroundPicture;

    @SerializedName("title")
    private String title;

    @SerializedName("location")
    private String location;

    @SerializedName("profession")
    private String profession;

    @SerializedName("seekingOpportunity")
    private String seekingOpportunity;

    @SerializedName("education")
    private List<UserEducation> education;

    @SerializedName("job")
    private CurrentJobPosition job;

    // Constructors, getters, and setters
    public User() {
    }

    public User(String email, String password, String firstName, String lastName, String additionalName, String profilePicture, String backgroundPicture, String title, String location, String profession, String seekingOpportunity) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.additionalName = additionalName;
        this.profilePicture = profilePicture;
        this.backgroundPicture = backgroundPicture;
        this.title = title;
        this.location = location;
        this.profession = profession;
        this.seekingOpportunity = seekingOpportunity;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBackgroundPicture() {
        return backgroundPicture;
    }

    public void setBackgroundPicture(String backgroundPicture) {
        this.backgroundPicture = backgroundPicture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSeekingOpportunity() {
        return seekingOpportunity;
    }

    public void setSeekingOpportunity(String seekingOpportunity) {
        this.seekingOpportunity = seekingOpportunity;
    }

    public List<UserEducation> getEducation() {
        return education;
    }

    public CurrentJobPosition getJob () {
        return job;
    }
    public void setEducation(List<UserEducation> education) {
        this.education = education;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", additionalName='" + additionalName + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", backgroundPicture='" + backgroundPicture + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", profession='" + profession + '\'' +
                ", seekingOpportunity='" + seekingOpportunity + '\'' +
                ", education=" + education +
                '}';
    }
}
