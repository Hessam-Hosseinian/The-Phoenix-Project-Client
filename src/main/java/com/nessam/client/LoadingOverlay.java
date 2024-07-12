package com.nessam.client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingOverlay {
    private Stage loadingStage;

    public LoadingOverlay(Stage owner) {
        loadingStage = new Stage(StageStyle.TRANSPARENT);
        loadingStage.initOwner(owner);
        loadingStage.initModality(Modality.WINDOW_MODAL);

        Label loadingLabel = new Label("Loading...");
        loadingLabel.setFont(new Font(24));
        loadingLabel.setTextFill(Color.WHITE);

        VBox vbox = new VBox(loadingLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 50px;");

        StackPane root = new StackPane(vbox);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        loadingStage.setScene(scene);
    }

    public void show() {
        loadingStage.show();
    }

    public void hide() {
        loadingStage.hide();
    }
}
