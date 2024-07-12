package com.nessam.client;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import models.User;
import utils.BetterLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp4_Controller implements Initializable {

    private static final String BASE_URL = "http://localhost:8080/users";
    private static final String USER_CREATED_SUCCESSFULLY = "User created successfully";
    private static final String CONTENT_TYPE_JSON = "application/json; utf-8";
    private static final String ACCEPT_JSON = "application/json";

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
    @FXML
    private ChoiceBox<String> countryBox;
    @FXML
    private ChoiceBox<String> cityBox;

    private Map<String, String[]> countryCitiesMap;


    @FXML
    private TextArea profession;

    @FXML
    private CheckBox seeking_opportunity;

    @FXML
    private TextField title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCountryCityMap();
        populateCountryChoiceBox();
    }

    private void initializeCountryCityMap() {
        countryCitiesMap = new HashMap<>();
        countryCitiesMap.put("Argentina", new String[]{"Buenos Aires", "Cordoba", "Rosario"});
        countryCitiesMap.put("Australia", new String[]{"Sydney", "Melbourne", "Brisbane"});
        countryCitiesMap.put("Brazil", new String[]{"Sao Paulo", "Rio de Janeiro", "Brasilia"});
        countryCitiesMap.put("Canada", new String[]{"Toronto", "Vancouver", "Montreal"});
        countryCitiesMap.put("China", new String[]{"Beijing", "Shanghai", "Guangzhou"});
        countryCitiesMap.put("Denmark", new String[]{"Copenhagen", "Aarhus", "Odense"});
        countryCitiesMap.put("Egypt", new String[]{"Cairo", "Alexandria", "Giza"});
        countryCitiesMap.put("France", new String[]{"Paris", "Marseille", "Lyon"});
        countryCitiesMap.put("Germany", new String[]{"Berlin", "Munich", "Frankfurt"});
        countryCitiesMap.put("Hungary", new String[]{"Budapest", "Debrecen", "Szeged"});
        countryCitiesMap.put("India", new String[]{"New Delhi", "Mumbai", "Bangalore"});
        countryCitiesMap.put("Iran", new String[]{"Tehran", "Mashhad", "Isfahan"});
        countryCitiesMap.put("Italy", new String[]{"Rome", "Milan", "Naples"});
        countryCitiesMap.put("Japan", new String[]{"Tokyo", "Osaka", "Kyoto"});
        countryCitiesMap.put("Kazakhstan", new String[]{"Nur-Sultan", "Almaty", "Shymkent"});
        countryCitiesMap.put("Latvia", new String[]{"Riga", "Daugavpils", "Liepaja"});
        countryCitiesMap.put("Mexico", new String[]{"Mexico City", "Guadalajara", "Monterrey"});
        countryCitiesMap.put("Netherlands", new String[]{"Amsterdam", "Rotterdam", "The Hague"});
        countryCitiesMap.put("Oman", new String[]{"Muscat", "Salalah", "Sohar"});
        countryCitiesMap.put("Russia", new String[]{"Moscow", "Saint Petersburg", "Novosibirsk"});
        countryCitiesMap.put("Saudi Arabia", new String[]{"Riyadh", "Jeddah", "Mecca"});
        countryCitiesMap.put("South Africa", new String[]{"Johannesburg", "Cape Town", "Durban"});
        countryCitiesMap.put("South Korea", new String[]{"Seoul", "Busan", "Incheon"});
        countryCitiesMap.put("Spain", new String[]{"Madrid", "Barcelona", "Valencia"});
        countryCitiesMap.put("Turkey", new String[]{"Istanbul", "Ankara", "Izmir"});
        countryCitiesMap.put("United Kingdom", new String[]{"London", "Manchester", "Birmingham"});
        countryCitiesMap.put("United States", new String[]{"New York", "Los Angeles", "Chicago"});
        countryCitiesMap.put("Zimbabwe", new String[]{"Harare", "Bulawayo", "Chitungwiza"});
    }

    private void populateCountryChoiceBox() {
        countryBox.setItems(FXCollections.observableArrayList(countryCitiesMap.keySet()));
        countryBox.getSelectionModel().selectFirst();
        countryBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateCityChoiceBox(newValue);
        });
        updateCityChoiceBox(countryBox.getSelectionModel().getSelectedItem());
    }

    private void updateCityChoiceBox(String country) {
        String[] cities = countryCitiesMap.get(country);
        cityBox.setItems(FXCollections.observableArrayList(cities));
        cityBox.getSelectionModel().selectFirst();
    }



    @FXML
    private void signUp4(ActionEvent event) {


        UserData.getInstance().setLocation(countryBox.getValue() + ", " + cityBox.getValue());
        String s = String.valueOf(seeking_opportunity.isSelected());
        User user = new User(UserData.getInstance().getEmail(), UserData.getInstance().getPassword(), UserData.getInstance().getFirstName(), UserData.getInstance().getLastName(), UserData.getInstance().getAdditionalName(), UserData.getInstance().getProfilePicture(), UserData.getInstance().getBackgroundPicture(), title.getText(), UserData.getInstance().getLocation(), profession.getText(), s

        );

        sendUserCreationRequest(user);
    }

    private void sendUserCreationRequest(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        System.out.println(json);
        try {
            HttpURLConnection con = createPostConnection(BASE_URL);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            handleUserCreationResponse(con);
        } catch (IOException e) {
            handleException(e);
        }
    }

    private HttpURLConnection createPostConnection(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
        con.setRequestProperty("Accept", ACCEPT_JSON);
        con.setDoOutput(true);
        return con;
    }

    private void handleUserCreationResponse(HttpURLConnection con) throws IOException {
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String response = getResponseText(con);
            if (USER_CREATED_SUCCESSFULLY.equals(response)) {
                setErrorLabel("Registration completed successfully!");
                navigateToNextScene(1);
            } else {
                setErrorLabel("Server error");
                BetterLogger.ERROR("Server error: " + response);
            }
        } else {
            setErrorLabel("Server error");
            BetterLogger.ERROR("Server error: HTTP " + responseCode);
        }
    }

    private String getResponseText(HttpURLConnection con) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder responseText = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                responseText.append(responseLine.trim());
            }
            return responseText.toString();
        }
    }

    private void handleException(IOException e) {
        setErrorLabel("Connection failed: " + e.getMessage());
        BetterLogger.ERROR(e.getMessage());
    }

    private void setErrorLabel(String message) {
        Error_lbl.setText(message);
    }

    private void navigateToNextScene(int sceneNumber) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(sceneNumber);
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        return password.length() >= 8; // Simple length check; can be extended to more complex rules
    }

    private void navigateToNextSignUpStep(int sceneNum) throws IOException {
        HelloApplication application = new HelloApplication();
        application.changeScene(sceneNum);
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

}
