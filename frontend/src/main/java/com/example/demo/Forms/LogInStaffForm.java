package com.example.demo.Forms;

import com.example.demo.Controllers.LogInStaffController;
import com.example.demo.Main;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LogInStaffForm {
    private final LogInStaffController controller = new LogInStaffController();

    public Scene createLoginPage(Stage primaryStage) {
        VBox loginForm = new VBox(20);
        loginForm.setPadding(new Insets(30));
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setStyle("-fx-background-color: linear-gradient(to bottom right, #e3f2fd, #ffffff);");

        Label title = new Label("Staff Login");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #c7002e;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);
        usernameField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Phlebotomist", "Nurse", "Receptionist", "Laboratory Technician", "Manager", "Support Staff");
        roleCombo.setPromptText("Select Role");
        roleCombo.setMaxWidth(300);
        roleCombo.setStyle("-fx-font-size: 16px; -fx-color-label-visible: #c7002e ");

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #c7002e; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #bdc3c7; -fx-text-fill: #c7002e; -fx-font-size: 16px; -fx-padding: 10 20 10 20;");

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton, backButton);

        loginForm.getChildren().addAll(title, usernameField, passwordField, roleCombo, buttonBox);

        loginButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = roleCombo.getValue();

            if (username.isEmpty() || password.isEmpty() || role == null) {
                showError("Please fill all fields.");
                return;
            }
            new Thread(() -> {
                try {
                    boolean success = controller.loginStaff(username, role, password);
                    Platform.runLater(() -> {
                        if (success) {
                            // On success: go to staff dashboard
                            StaffDashboardForm dashboard = new StaffDashboardForm(primaryStage);
                            primaryStage.setScene(dashboard.getScene());
                        } else {
                            showError("Invalid credentials. Please try again.");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> showError("Server error. Please try again later."));
                }
            }).start();
        });

        backButton.setOnAction(event -> {
            primaryStage.setScene(new Main().logInAdmin());
        });

        return new Scene(loginForm, 800, 600);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText("Invalid Credentials");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
