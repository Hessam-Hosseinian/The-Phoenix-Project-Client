package com.nessam.client;

import utils.BetterLogger;
import utils.MyUrl;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloadClient {


    public static void downloadFile(String FILE_ID) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try {

            URL url = new URL(MyUrl.DOWNLOAD_URL +"/"+ FILE_ID);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");



            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream of the connection
                inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                fileOutputStream = new FileOutputStream(MyUrl.OUTPUT_FILE_PATH + FILE_ID);

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }

                BetterLogger.INFO("File downloaded successfully to " + MyUrl.OUTPUT_FILE_PATH + FILE_ID);
            } else {

                throw new RuntimeException("Failed to download file. Server returned HTTP code: " + connection.getResponseCode() + "  " + FILE_ID);
            }
        } catch (IOException e) {
            BetterLogger.ERROR("Failed to download file: " + e.getMessage());
        } finally {

            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                BetterLogger.ERROR("Failed to download file: " + e.getMessage());
            }
        }
    }
}
