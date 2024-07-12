module com.nessam.thephoenixprojectclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires org.json;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpmime;
    requires org.apache.httpcomponents.httpclient;


    opens com.nessam.client to javafx.fxml;
    exports com.nessam.client;
    opens models to com.google.gson;  // Add this line
    exports models;
}