package com.nessam.client;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import models.User;
import utils.BetterLogger;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_Controller {

    int sceneNum = 0;

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
    private ImageView ProfilePicture_tf;
    @FXML
    private ImageView BackgroundPicture_tf;


    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String additionalName;
    private String profilePicture;
    private String backgroundPicture;
    private String location;


    public void signIn() throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(1);
    }

    public void signUp1(ActionEvent event) {
        if (Email_tf.getText().isEmpty() || Password_tf.getText().isEmpty() || PasswordCheck_tf.getText().isEmpty()) {
            Error_lbl.setText("Please fill all the fields");
        } else if (Email_tf.getText().length() != 0 && !isValidEmail(Email_tf.getText())) {
            Error_lbl.setText("Email is invalid");
        } else if (!validatePassword(Password_tf.getText())) {
            Error_lbl.setText("Password is invalid");
        } else if (!PasswordCheck_tf.getText().equals(Password_tf.getText())) {
            Error_lbl.setText("Passwords are not equal");
        } else {
            try {
                // Check if the user already exists
                String response;
                URL url = new URL("http://localhost:8080/users/" + Email_tf.getText());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder responseBuilder = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                in.close();
                response = responseBuilder.toString();


                if (!response.equals("No User")) {
                    Error_lbl.setText("Username exists");
                } else {
                    email = Email_tf.getText();
                    password = Password_tf.getText();

                    UserData.getInstance().setEmail(email);
                    UserData.getInstance().setPassword(password);

                    User user = new User(email, password, "", "", "", "", "", "", "", "", "");
                    // Serialize User object to JSON using Gson
                    Gson gson = new Gson();
                    String json = gson.toJson(user);


                    // Send POST request
                    url = new URL("http://localhost:8080/users");
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json; utf-8");
                    con.setRequestProperty("Accept", "application/json");
                    con.setDoOutput(true);

                    try (OutputStream os = con.getOutputStream()) {
                        byte[] input = json.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    int code = con.getResponseCode();


                    try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                        StringBuilder responseText = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            responseText.append(responseLine.trim());
                        }
                        response = responseText.toString();
                    }

                    if (response.equals("User created successfully")) {
                        Error_lbl.setText("Registration completed successfully!");

                        HelloApplication application = new HelloApplication();
                        application.changeScene(3);
                    } else {
                        Error_lbl.setText("Server error");

                    }
                }
            } catch (ConnectException e) {
                Error_lbl.setText("Connection failed");
                BetterLogger.ERROR("Connection failed: " + e.getMessage());
            } catch (ProtocolException e) {
                Error_lbl.setText("Protocol error");
                BetterLogger.ERROR("Protocol error: " + e.getMessage());
            } catch (MalformedURLException e) {
                Error_lbl.setText("URL is malformed");
                BetterLogger.ERROR("URL is malformed: " + e.getMessage());
            } catch (IOException e) {
                Error_lbl.setText("IO Exception occurred");
                BetterLogger.ERROR("IO Exception occurred: " + e.getMessage());
            }
        }
    }

    public void signUp2(ActionEvent event) throws IOException {
        String response;
        String inputLine;

        if (FirstName_tf.getText().isEmpty() || LastName_tf.getText().isEmpty()) {
            Error_lbl.setText("Please fill all the fields");
        } else {
            email = UserData.getInstance().getEmail();
            password = UserData.getInstance().getPassword();
            firstName = FirstName_tf.getText();
            lastName = LastName_tf.getText();
            additionalName = AdditionalName_tf.getText();


            UserData.getInstance().setFirstName(firstName);
            UserData.getInstance().setLastName(lastName);
            UserData.getInstance().setAditionalName(additionalName);

            User user = new User(email, password, firstName, lastName, additionalName, "", "", "", "", "", "");

            // Serialize User object to JSON using Gson
            Gson gson = new Gson();
            String json = gson.toJson(user);

            // Send POST request
            URL url = new URL("http://localhost:8080/users");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();


            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder responseText = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    responseText.append(responseLine.trim());
                }
                response = responseText.toString();
            }

            if (response.equals("User created successfully")) {
                Error_lbl.setText("Registration completed successfully!");
                HelloApplication application = new HelloApplication();
                application.changeScene(4);

            } else {
                Error_lbl.setText("Server error");
                BetterLogger.ERROR("Server error");
            }
        }
    }

    public void signUp3(ActionEvent event) throws IOException {
        String response;
        String inputLine;

        if (ProfilePicture_tf.getImage() == null || BackgroundPicture_tf.getImage() == null) {
            Error_lbl.setText("Please chose your pictures");
        } else {
            email = UserData.getInstance().getEmail();
            password = UserData.getInstance().getPassword();
            firstName = UserData.getInstance().getFirstName();
            lastName = UserData.getInstance().getLastName();
            additionalName = UserData.getInstance().getAditionalName();
            profilePicture = UserData.getInstance().getProfilePicture();
            backgroundPicture = UserData.getInstance().getBackgroundPicture();


            User user = new User(email, password, firstName, lastName, additionalName, profilePicture, backgroundPicture, "", "", "", "");

            // Serialize User object to JSON using Gson
            Gson gson = new Gson();
            String json = gson.toJson(user);


            // Send POST request
            URL url = new URL("http://localhost:8080/users");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();


            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder responseText = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    responseText.append(responseLine.trim());
                }
                response = responseText.toString();
            }

            if (response.equals("User created successfully")) {
                Error_lbl.setText("Registration completed successfully!");
                HelloApplication application = new HelloApplication();
                application.changeScene(5);

            } else {
                Error_lbl.setText("Server error");
                BetterLogger.ERROR("Server error");
            }
        }
    }

    public void signUp4(ActionEvent event) throws IOException {
        String response;
        String inputLine;

        email = UserData.getInstance().getEmail();
        password = UserData.getInstance().getPassword();
        firstName = UserData.getInstance().getFirstName();
        lastName = UserData.getInstance().getLastName();
        additionalName = UserData.getInstance().getAditionalName();
        profilePicture = UserData.getInstance().getProfilePicture();
        backgroundPicture = UserData.getInstance().getBackgroundPicture();
//            UserData.getInstance().setLocation(countryBox.getValue() + ", " + cityBox.getValue());
//            location = UserData.getInstance().getLocation();

        User user = new User(email, password, firstName, lastName, additionalName, profilePicture, backgroundPicture, "", "location", "", "");

        Gson gson = new Gson();
        String json = gson.toJson(user);

        URL url = new URL("http://localhost:8080/users");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = con.getResponseCode();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder responseText = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                responseText.append(responseLine.trim());
            }
            response = responseText.toString();
        }
        if (response.equals("User created successfully")) {
            Error_lbl.setText("Registration completed successfully!");
        } else {
            Error_lbl.setText("Server error");
            BetterLogger.ERROR("Server error");
        }
    }


    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        final Pattern passPattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = passPattern.matcher(password);
        return matcher.matches();
    }


    @FXML
    private void imageViewDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        ImageView imageView = (ImageView) event.getGestureTarget();
        if (dragboard.hasFiles() || dragboard.hasFiles()) {
            try {
                // Load the image into the ImageView
                File imageFile = dragboard.getFiles().get(0);
                imageView.setImage(new Image(new FileInputStream(imageFile)));

                // Define the target directory and create it if it doesn't exist
                Path targetDir = Paths.get("src/main/resources/pictures");
                if (!Files.exists(targetDir)) {
                    Files.createDirectories(targetDir);
                }

                // Define the target file path
                Path targetFile = targetDir.resolve(imageFile.getName());
                if (event.getSource() == ProfilePicture_tf) {
                    UserData.getInstance().setProfilePicture(targetFile.toString());

                } else if (event.getSource() == BackgroundPicture_tf) {
                    UserData.getInstance().setBackgroundPicture(targetFile.toString());
                }


                // Copy the file to the target directory
                Files.copy(imageFile.toPath(), targetFile);

            } catch (FileNotFoundException e) {
                BetterLogger.WARNING("File not found");
            } catch (IOException e) {
                BetterLogger.WARNING("There's something went wrong");
            }
        }
    }

    @FXML
    private void imageViewDragOver(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasImage() || dragboard.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);

        }
        event.consume();

    }


}
