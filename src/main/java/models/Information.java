package models;



import java.sql.Date;


public class Information {



    private String profileLink;

    private String email;

    private String phoneNumber;

    private int phoneType;

    private String address;


    private Date birthMonth;


    private Date birthDay;


    private int birthPrivacyPolicy;


    private String instantContactMethod;

    private User user;

    // Constructors, getters, and setters
    public Information() {
    }

    public Information(String profileLink, String email, String phoneNumber, int phoneType, String address, Date birthMonth, Date birthDay, int birthPrivacyPolicy, String instantContactMethod) {
        this.profileLink = profileLink;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.address = address;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.birthPrivacyPolicy = birthPrivacyPolicy;
        this.instantContactMethod = instantContactMethod;

    }
    // Getters and setters
    // ...


    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(int phoneType) {
        this.phoneType = phoneType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(Date birthMonth) {
        this.birthMonth = birthMonth;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public int getBirthPrivacyPolicy() {
        return birthPrivacyPolicy;
    }

    public void setBirthPrivacyPolicy(int birthPrivacyPolicy) {
        this.birthPrivacyPolicy = birthPrivacyPolicy;
    }

    public String getInstantContactMethod() {
        return instantContactMethod;
    }

    public void setInstantContactMethod(String instantContactMethod) {
        this.instantContactMethod = instantContactMethod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
