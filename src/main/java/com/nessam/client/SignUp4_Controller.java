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
import javafx.scene.input.TransferMode;
import models.User;
import utils.BetterLogger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp4_Controller implements Initializable {

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

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String additionalName;
    private String profilePicture;
    private String backgroundPicture;
    private String location;
    private Map<String, String[]> countryCitiesMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] countries = {
                "Argentina", "Australia", "Brazil", "Canada", "China",
                "Denmark", "Egypt", "France", "Germany", "Hungary", "India", "Iran",
                "Italy", "Japan", "Kazakhstan", "Latvia", "Mexico", "Netherlands", "Oman", "Russia", "Saudi Arabia",
                "South Africa", "South Korea", "Spain", "Turkey",
                "United Kingdom", "United States", "Zimbabwe"
        };

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

        countryBox.setItems(FXCollections.observableArrayList(countries));
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
            UserData.getInstance().setLocation(countryBox.getValue() + ", " + cityBox.getValue());
            location = UserData.getInstance().getLocation();

        User user = new User(email, password, firstName, lastName, additionalName, profilePicture, backgroundPicture, "", location, "", "");

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
            HelloApplication application = new HelloApplication();
            application.changeScene(1);
        } else {
            Error_lbl.setText("Server error");
            BetterLogger.ERROR("Server error");
        }
    }


}
