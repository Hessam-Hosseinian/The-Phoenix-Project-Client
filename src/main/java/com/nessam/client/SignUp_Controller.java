package com.nessam.client;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import models.User;
import utils.BetterLogger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUp_Controller {
    @FXML
    private TextField Email_tf;
    @FXML
    private PasswordField Password_tf;
    @FXML
    private PasswordField PasswordCheck_tf;
    @FXML
    private Button SignUp_btn;
    @FXML
    private Label Error_lbl;
    @FXML
    private TextField FirstName_tf;
    @FXML
    private TextField LastName_tf;
    @FXML
    private TextField AdditionalName_tf;
    @FXML
    private Circle proPic;
    @FXML
    private ImageView BackgroundPicture_tf;

    private File selectedFile_pro;
    private File selectedFile_back;

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(EMAIL_REGEX);
    }

    public static boolean validatePassword(String password) {
        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return password.matches(PASSWORD_PATTERN);
    }

    @FXML
    private void initialize() {
        // Initialization code here if necessary
    }

    public void signIn() throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(1);
    }

    public void signUp1(ActionEvent event) throws IOException {
        if (!validateSignUp1Inputs()) return;

        String email = Email_tf.getText();
        String password = Password_tf.getText();
        UserData.getInstance().setEmail(email);
        UserData.getInstance().setPassword(password);

        if (userExists(email)) {
            Error_lbl.setText("Username exists");
            return;
        }

        User user = new User(email, password, "", "", "", "", "", "", "", "", "");
        if (registerUser(user)) {
            Error_lbl.setText("Registration completed successfully!");
            navigateToNextSignUpStep(3);
        } else {
            Error_lbl.setText("Server error");
        }
    }

    private boolean validateSignUp1Inputs() {
        if (Email_tf.getText().isEmpty() || Password_tf.getText().isEmpty() || PasswordCheck_tf.getText().isEmpty()) {
            Error_lbl.setText("Please fill all the fields");
            return false;
        }
        if (!isValidEmail(Email_tf.getText())) {
            Error_lbl.setText("Email is invalid");
            return false;
        }
        if (!validatePassword(Password_tf.getText())) {
            Error_lbl.setText("Password is invalid");
            return false;
        }
        if (!PasswordCheck_tf.getText().equals(Password_tf.getText())) {
            Error_lbl.setText("Passwords are not equal");
            return false;
        }
        return true;
    }

    public void signUp2(ActionEvent event) throws IOException {
        if (!validateSignUp2Inputs()) return;
        UserData.getInstance().setFirstName(FirstName_tf.getText());
        UserData.getInstance().setLastName(LastName_tf.getText());
        UserData.getInstance().setAdditionalName(AdditionalName_tf.getText());
        User user = new User(
                UserData.getInstance().getEmail(),
                UserData.getInstance().getPassword(),
                FirstName_tf.getText(),
                LastName_tf.getText(),
                AdditionalName_tf.getText(),
                "", "", "", "", "", ""
        );

        if (registerUser(user)) {
            Error_lbl.setText("Registration completed successfully!");
            navigateToNextSignUpStep(4);
        } else {
            Error_lbl.setText("Server error");
        }
    }

    private boolean validateSignUp2Inputs() {
        if (FirstName_tf.getText().isEmpty() || LastName_tf.getText().isEmpty()) {
            Error_lbl.setText("Please fill all the fields");
            return false;
        }
        return true;
    }

    public void signUp3(ActionEvent event) {
        if (selectedFile_pro == null || selectedFile_back == null) {
            Error_lbl.setText("Please choose your pictures");
            return;
        }

        try {
            FileUploadClient.upload(selectedFile_pro.getAbsolutePath());
            FileUploadClient.upload(selectedFile_back.getAbsolutePath());
            String profilePicture = UserData.getInstance().getEmail() + ":" + selectedFile_pro.getName();
            String backgroundPicture = UserData.getInstance().getEmail() + ":" + selectedFile_back.getName();
            UserData.getInstance().setProfilePicture(profilePicture);
            UserData.getInstance().setBackgroundPicture(backgroundPicture);

            User user = new User(
                    UserData.getInstance().getEmail(),
                    UserData.getInstance().getPassword(),
                    UserData.getInstance().getFirstName(),
                    UserData.getInstance().getLastName(),
                    UserData.getInstance().getAdditionalName(),
                    profilePicture,
                    backgroundPicture,
                    "", "", "", ""
            );

            if (registerUser(user)) {
                navigateToNextSignUpStep(5);
            } else {
                Error_lbl.setText("Server error");
            }
        } catch (Exception e) {
            BetterLogger.ERROR("Error uploading files: " + e.getMessage());
        }
    }

    private boolean userExists(String email) {
        try {
            URL url = new URL("http://localhost:8080/users/" + email);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String response = in.readLine();
                    return !response.equals("No User");
                }
            }
        } catch (IOException e) {
            Error_lbl.setText("Error checking user existence: " + e.getMessage());
        }
        return false;
    }

    private boolean registerUser(User user) {
        try {
            URL url = new URL("http://localhost:8080/users");
            HttpURLConnection con = createHttpConnection(url, "POST");

            String json = new Gson().toJson(user);
            System.out.println(json);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    String response = br.readLine();
                    return response.equals("User created successfully");
                }
            }
        } catch (IOException e) {
            BetterLogger.ERROR("Error registering user: " + e.getMessage());
        }
        return false;
    }

    private HttpURLConnection createHttpConnection(URL url, String method) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        return con;
    }

    private void navigateToNextSignUpStep(int sceneNum) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(sceneNum);
    }

    @FXML
    public void selectImagePro(ActionEvent event) {
        selectedFile_pro = selectImageFile();
        if (selectedFile_pro != null) {
            Image image = new Image(selectedFile_pro.toURI().toString());
            proPic.setFill(new ImagePattern(image));
        }
    }

    @FXML
    public void selectImageBack(ActionEvent event) {
        selectedFile_back = selectImageFile();
        if (selectedFile_back != null) {
            Image image = new Image(selectedFile_back.toURI().toString());
            BackgroundPicture_tf.setImage(image);
        }
    }

    private File selectImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        return fileChooser.showOpenDialog(null);
    }
}
