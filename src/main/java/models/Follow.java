package models;


import com.google.gson.annotations.SerializedName;

public class Follow {


    @SerializedName("follower")
    private String follower;

    @SerializedName("followed")
    private String followed;

    public Follow(String follower, String followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public Follow() {

    }


    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "follower='" + follower + '\'' +
                ", followed='" + followed + '\'' +
                '}';
    }
}
