package com.nessam.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.json.JSONObject;
import utils.MyUrl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddPost_Controller {

    @FXML
    private Button addPostButton;

    @FXML
    private TextArea captionText;

    @FXML
    private ImageView imagePost;

    @FXML
    private TextField titleText;

    private File selectedFile;

    @FXML
    private Button home_bt;

    @FXML
    public void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Display the selected image in ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imagePost.setImage(image);
        }
    }

    @FXML
    public void addPostToFeed(ActionEvent event) throws IOException {
        if (selectedFile == null) {
            System.out.println("No file selected");
            return;
        }


        FileUploadClient.upload(selectedFile.getAbsolutePath());


        try {
            String title = titleText.getText();
            String caption = captionText.getText();
            ;
            String jwtToken = HelloApplication.getJWT_token();
            URL url = new URL(MyUrl.POSTS_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", jwtToken);
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("title", title);
            json.put("content", caption);
            json.put("file_path", HelloApplication.loggedin_user.getEmail() + ":" + selectedFile.getName());


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

                }
            } else {
                System.err.println("Error: " + responseCode);

            }
        } catch (Exception e) {
            System.err.println("Error: ");
        }
        HelloApplication application = new HelloApplication();
        application.changeScene(6);
    }

    @FXML
    private void handleAddingPostAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(9);
    }

    @FXML
    public void handleAddingEducationAction() {
        HelloApplication application = new HelloApplication();
        application.changeScene(14);
    }

    @FXML
    public void handleAddingJob() {
        HelloApplication application = new HelloApplication();

        application.changeScene(15);
    }

    @FXML
    private void handleSettingsAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(10);
    }

    @FXML
    private void handleProfileAction(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(8);
    }

    public void backToHome(ActionEvent event) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(6);
    }

    @FXML
    public void handleMessagingButton() throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(12);

    }
}
