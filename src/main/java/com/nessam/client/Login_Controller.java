package com.nessam.client;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import models.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login_Controller {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String LOGIN_ENDPOINT = "/req/";
    private static final String USER_ENDPOINT = "/users/";
    private static final String TOKEN_FILE_PATH = "/assets.txt";

    @FXML
    private TextField Username_textfield;

    @FXML
    private PasswordField Password_textfield;

    @FXML
    private Button signIn_button;

    @FXML
    private Label error_label;

    @FXML
    private Button toggleNightModeButton;

    @FXML
    public void signUp() throws IOException {
        changeScene(2);
    }

    @FXML
    public void signIn(ActionEvent actionEvent) {
        if (Username_textfield.getText().isEmpty() || Password_textfield.getText().isEmpty()) {
            setErrorLabel("Please fill all the fields", Color.RED);
        } else {
            try {
                String loginUrl = BASE_URL + LOGIN_ENDPOINT + Username_textfield.getText() + "/" + Password_textfield.getText();
                HttpURLConnection loginConnection = createHttpConnection(loginUrl, "GET");

                int responseCode = loginConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    handleSuccessfulLogin(loginConnection);
                } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    setErrorLabel("Incorrect username or password", Color.RED);
                } else {
                    setErrorLabel("Login failed: " + responseCode, Color.RED);
                }
            } catch (MalformedURLException e) {
                setErrorLabel("Invalid URL format", Color.RED);
            } catch (IOException e) {
                setErrorLabel("Connection failed: " + e.getMessage(), Color.RED);
            }
        }
    }

    @FXML
    public void handleToggleNightMode() {
        HelloApplication.toggleNightMode();
    }

    private void handleSuccessfulLogin(HttpURLConnection loginConnection) throws IOException {
        String jwtToken = loginConnection.getHeaderField("Authorization");
        if (jwtToken != null) {
            jwtToken = jwtToken.trim();
            HelloApplication.setJWT_token(jwtToken);

            String userUrl = BASE_URL + USER_ENDPOINT + Username_textfield.getText();
            HttpURLConnection userConnection = createHttpConnection(userUrl, "GET");

            int responseCode = userConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String responseText = getResponseText(userConnection);
                Gson gson = new Gson();
                User user = gson.fromJson(responseText, User.class);

                HelloApplication.loggedin_user = user;
                UserData.getInstance().setOcd_email(user.getEmail());

                writeTokenToFile(jwtToken);
                changeScene(6);
                setErrorLabel("Logged in successfully", Color.GREEN);
            } else {
                setErrorLabel("Failed to fetch user data: " + responseCode, Color.RED);
            }
        }
    }

    private HttpURLConnection createHttpConnection(String urlString, String method) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        String jwtToken = HelloApplication.getJWT_token();
        if (jwtToken != null && !jwtToken.isEmpty()) {
            connection.setRequestProperty("Authorization", "Bearer " + jwtToken.trim());
        }

        return connection;
    }

    private String getResponseText(HttpURLConnection connection) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }

    private void setErrorLabel(String message, Color color) {
        error_label.setText(message);
        error_label.setTextFill(color);
    }

    private void writeTokenToFile(String token) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TOKEN_FILE_PATH))) {
            writer.write(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeScene(int sceneNum) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(sceneNum);
    }
}
