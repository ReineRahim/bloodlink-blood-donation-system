package com.example.demo.Forms;

import com.example.demo.Controllers.LogInDonorController;
import com.example.demo.Main;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInDonorForm {
    private LogInDonorController loginController;

    public LogInDonorForm() {
        loginController = new LogInDonorController();
    }

    public Scene createLoginScene(Stage primaryStage) {
        primaryStage.setFullScreen(true);

        VBox loginLayout = new VBox(20);
        loginLayout.setPadding(new Insets(30));
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, #e3f2fd, #ffffff);");

        Label titleLabel = new Label("Donor Login");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #c7002e;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Donor Username");
        usernameField.setMaxWidth(300);
        usernameField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #c7002e; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #bdc3c7; -fx-text-fill: #c7002e; -fx-font-size: 16px; -fx-padding: 10 20;");

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton, backButton);

        loginLayout.getChildren().addAll(titleLabel, usernameField, passwordField, buttonBox);

        loginButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // Run login on a background thread (to avoid freezing UI)
            new Thread(() -> {
                try {
                    boolean success = loginController.login(username, password);
                    Platform.runLater(() -> {
                        if (success) {
                            primaryStage.setScene(new DonationForm().createDonationForm());
                        } else {
                            showAlert("Login Failed", "Invalid Credentials", "Please try again.");
                        }
                    });
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    Platform.runLater(() -> showAlert("Error", "Network Error", "Could not connect to server."));
                }
            }).start();
        });

        backButton.setOnAction(event -> primaryStage.setScene(new Main().FrontPage()));

        return new Scene(loginLayout, 800, 600);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
