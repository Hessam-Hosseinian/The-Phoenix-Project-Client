module com.nessam.thephoenixprojectclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;


    opens com.nessam.client to javafx.fxml;
    exports com.nessam.client;
    opens models to com.google.gson;  // Add this line
    exports models;
}