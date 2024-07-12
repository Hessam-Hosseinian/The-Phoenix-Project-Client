package com.nessam.client;

import utils.MyUrl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUploadClient {

    private static final int TIMEOUT = 5000;

    public static void upload(String filePath) {

        File file = new File(filePath);
        String fileName = file.getName();


        fileName = fileName.replace(' ', '-');


        HttpURLConnection connection = null;
        try {
            URL url = new URL(MyUrl.UPLOAD_URL + "/" + HelloApplication.loggedin_user.getEmail() + "/" + fileName);
            connection = (HttpURLConnection) url.openConnection();


            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);

            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setRequestProperty("Accept", "application/json");



            try (OutputStream os = connection.getOutputStream()) {
                Files.copy(Path.of(filePath), os);
            }


            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    System.out.println("Response: " + response.toString());
                }
            } else {
                System.out.println("POST request failed with response code: " + responseCode);
            }

        } catch (ProtocolException e) {
            throw new RuntimeException("Protocol exception occurred", e);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL exception occurred", e);
        } catch (IOException e) {
            throw new RuntimeException("IO exception occurred", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
