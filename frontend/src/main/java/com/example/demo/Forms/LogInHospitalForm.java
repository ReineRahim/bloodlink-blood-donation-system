package com.example.demo.Forms;

import com.example.demo.Controllers.LogInHospitalController;
import com.example.demo.Main;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class LogInHospitalForm {
    private final LogInHospitalController logInController = new LogInHospitalController();

    public Scene createLoginPage(Stage primaryStage) {
        VBox loginForm = new VBox(20);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setPadding(new Insets(30));
        loginForm.setStyle("-fx-background-color: linear-gradient(to bottom right, #e3f2fd, #ffffff);");

        Label title = new Label("Hospital Login");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #c7002e;");

        TextField hospitalNameField = new TextField();
        hospitalNameField.setPromptText("Hospital Name");
        hospitalNameField.setMaxWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Passcode");
        passwordField.setMaxWidth(300);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #c7002e; -fx-text-fill: white;");
        loginButton.setOnAction(event -> {
            String name = hospitalNameField.getText().trim();
            String password = passwordField.getText().trim();
            HospitalDashboardForm hospitalDashboardForm = new HospitalDashboardForm(primaryStage, hospitalNameField.getText());

            try {
                boolean success = logInController.loginHospital(name, password);
                if (success) {
                    // Proceed to dashboard
                    System.out.println("Login successful for hospital: " + logInController.getLoggedInHospitalName());
                    primaryStage.setScene(hospitalDashboardForm.getScene());
                } else {
                    showError("Invalid credentials. Try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showError("Server error. Please try again later.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #c7002e; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;");
        backButton.setOnAction(event -> primaryStage.setScene(new Main().logInAdmin()));

        HBox buttonBox = new HBox(15, loginButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        loginForm.getChildren().addAll(title, hospitalNameField, passwordField, buttonBox);

        return new Scene(loginForm, 600, 400);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
