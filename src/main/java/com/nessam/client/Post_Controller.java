package com.nessam.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import models.Like;
import models.Post;
import models.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.BetterLogger;
import utils.MyUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Post_Controller {

    @FXML
    private ImageView Profile_pic;

    @FXML
    private Label caption;

    @FXML
    private ImageView post_pic;

    @FXML
    private Button send_bt;

    @FXML
    private Label title;

    @FXML
    private Hyperlink username;

    @FXML
    private Label Date_lb;

    @FXML
    private Button like_bt;
    @FXML
    private Button like_bt2;

    @FXML
    private Label name;

    @FXML
    private ImageView likepic;
    @FXML
    private Circle Profile_pic_circle;
    private List<String> likers = new ArrayList<>();

    private Tooltip likeTooltip = new Tooltip();
    private PauseTransition hoverDelay = new PauseTransition(Duration.seconds(2));

    private boolean isLiked = false;

    @FXML
    private Button followBT;

    public static boolean checkIfLiked(Post post) throws IOException {
        try {
            String postAuthor = post.getAuthor();
            String postTitle = post.getTitle();
            String jwtToken = HelloApplication.getJWT_token();
            URL url = new URL(MyUrl.LIKE_URL + "check");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", jwtToken);
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("postAuthor", postAuthor);
            json.put("postTitle", postTitle);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return Boolean.parseBoolean(response.toString());
                }
            } else {
                System.err.println("Error: " + responseCode);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkIfFollowing(String postAuthor) throws IOException {
        try {
            String loggedInUser = HelloApplication.loggedin_user.getEmail();
            String jwtToken = HelloApplication.getJWT_token();
            URL url = new URL(String.format("http://localhost:8080/follows/check/%s/%s", loggedInUser, postAuthor));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", jwtToken);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return Boolean.parseBoolean(response.toString());
                }
            } else {
                System.err.println("Error: " + responseCode);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void initialize() {
        like_bt2.setOnMouseEntered(event -> {
            try {
                List<String> likers = getLikers(username.getText(), title.getText());
                if (likers.isEmpty()) {
                    likeTooltip.setText("No likes yet.");
                } else {
                    likeTooltip.setText(String.join("\n", likers));
                }

                // Show tooltip relative to the button
                Bounds bounds = like_bt2.localToScreen(like_bt2.getBoundsInLocal());
                double x = bounds.getMinX() + like_bt2.getWidth() / 2;
                double y = bounds.getMinY() + like_bt2.getHeight();

                likeTooltip.show(like_bt2, x, y);
            } catch (Exception ex) {
                BetterLogger.WARNING(ex.getMessage());
            }
        });
        like_bt2.setOnMouseExited(event -> {
            likeTooltip.hide();
        });
    }

    public void setData(Post post) throws IOException {


        try {
            String userPro = getUserByEmail(post.getAuthor()).getProfilePicture();
            FileDownloadClient.downloadFile(userPro);

            Image profile_pic = new Image(userPro);

            Profile_pic_circle.setFill(new ImagePattern(profile_pic));

        } catch (Exception e) {
            Profile_pic_circle.setFill(new ImagePattern(new Image("Default_Profile_Picture.png")));
            BetterLogger.WARNING("failed to load profile picture");
        }

        try {

            FileDownloadClient.downloadFile(post.getFilePath());

            Image postImage = new Image(post.getFilePath());
            post_pic.setImage(postImage);
        } catch (Exception e) {
            post_pic.setImage(new Image("def.jpg"));
            BetterLogger.WARNING("failed to load profile picture");
        }

        username.setText(post.getAuthor());
        Date_lb.setText(post.getDateCreated());
        caption.setText(post.getContent());
        title.setText(post.getTitle());
        like_bt.setText("" + post.getLikes().size());

        try {
            isLiked = checkIfLiked(post);
            if (isLiked) {
                likepic.setImage(new Image("/heart(1).png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = getUserByEmail(username.getText());
        name.setText(user.getFirstName() + " " + user.getLastName());
        // Check if logged-in user follows the post's author
        boolean isFollowing = checkIfFollowing(username.getText());
        if (isFollowing) {
            followBT.setText("Followed");
        } else {
            followBT.setText("Follow");
        }
    }

    public User getUserByEmail(String email) throws IOException {
        String endpoint = MyUrl.USER_URL + email;
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                Gson gson = new GsonBuilder().create();
                return gson.fromJson(response.toString(), User.class);
            }
        } else {
            System.err.println("Failed to get user. Response code: " + responseCode);
            return null;
        }
    }

    public void like() throws IOException {
        String jwtToken = HelloApplication.getJWT_token();
        String emailOfPostAuthor = username.getText();
        String postTitle = title.getText();

        Like like = new Like(postTitle, emailOfPostAuthor);

        Gson gson = new Gson();
        String json = gson.toJson(like);

        HttpURLConnection con = null;

        try {
            URL url = new URL(MyUrl.LIKE_URL);
            con = (HttpURLConnection) url.openConnection();

            if (isLiked) {

                con.setRequestMethod("DELETE");
            } else {

                con.setRequestMethod("POST");
            }


            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", jwtToken);
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                if (isLiked) {
                    isLiked = false;
                    likepic.setImage(new Image("/heart(2).png"));
                    like_bt.setText(String.valueOf(Integer.parseInt(like_bt.getText()) - 1));
                } else {
                    isLiked = true;
                    likepic.setImage(new Image("/heart(1).png"));
                    like_bt.setText(String.valueOf(Integer.parseInt(like_bt.getText()) + 1));
                }
            } else {
                System.err.println("Failed: HTTP error code: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    @FXML
    public void addComment() throws IOException {
        UserData.getInstance().setPostTitle(title.getText());
        UserData.getInstance().setPostEmail(username.getText());

        HelloApplication application = new HelloApplication();
        application.changeScene(11);
    }

    @FXML
    public void seeUserProByHyperLink() throws IOException {
        UserData.getInstance().setOcd_email(username.getText());
        HelloApplication application = new HelloApplication();
        application.changeScene(8);
    }

    public List<String> getLikers(String postAuthor, String postTitle) throws IOException {
        String jwtToken = HelloApplication.getJWT_token();
        URL url = new URL("http://localhost:8080/likes/get");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", jwtToken);
        conn.setDoOutput(true);

        // Send JSON body
        JSONObject json = new JSONObject();
        json.put("postAuthor", postAuthor);
        json.put("postTitle", postTitle);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                // Assume response is a JSON array of likers
                JSONArray jsonArray = null;

                try {
                    jsonArray = new JSONArray(response.toString());
                    JSONObject jsonObj = jsonArray.getJSONObject(1);

                } catch (JSONException e) {
                    BetterLogger.WARNING(".");
                }

                List<String> likers = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json_obj = jsonArray.getJSONObject(i);


                    likers.add("liked by: " + json_obj.getString("liker"));
                }
                return likers;
            }
        } else {
            System.err.println("Error: " + responseCode);
            return new ArrayList<>();
        }

    }

    @FXML
    public void follow() throws IOException {
        String jwtToken = HelloApplication.getJWT_token();
        String loggedInUser = HelloApplication.loggedin_user.getEmail();
        String postAuthor = username.getText();

        boolean isFollowing = checkIfFollowing(postAuthor);
        HttpURLConnection con = null;

        try {
            URL url = new URL(String.format("%s/%s/%s", "http://localhost:8080/follows", loggedInUser, postAuthor));
            con = (HttpURLConnection) url.openConnection();

            if (isFollowing) {
                // Unfollow
                con.setRequestMethod("DELETE");

            } else {
                // Follow
                con.setRequestMethod("POST");

            }

            con.setRequestProperty("Authorization", jwtToken);
            con.setDoOutput(true);

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                if (isFollowing) {
                    followBT.setText("Follow");
                } else {
                    followBT.setText("Followed");
                }
            } else {
                System.err.println("Failed: HTTP error code: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }


}