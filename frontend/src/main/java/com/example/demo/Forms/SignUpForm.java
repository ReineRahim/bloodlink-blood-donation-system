package com.example.demo.Forms;

import com.example.demo.Controllers.SignUpController;
import com.example.demo.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignUpForm {
    private final SignUpController signUpController = new SignUpController();

    public Scene createSignUpPage(Stage primaryStage) {
        VBox signUpForm = new VBox(15);
        signUpForm.setPadding(new Insets(30));
        signUpForm.setAlignment(Pos.CENTER);
        signUpForm.setStyle("-fx-background-color: linear-gradient(to bottom right, #e3f2fd, #ffffff);");

        Label title = new Label("Donor Sign Up");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #c7002e;");

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        firstNameField.setMaxWidth(300);

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        lastNameField.setMaxWidth(300);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);

        Label genderLabel = new Label("Gender:");
        ToggleGroup genderGroup = new ToggleGroup();

        RadioButton maleRadio = new RadioButton("Male");
        maleRadio.setToggleGroup(genderGroup);
        maleRadio.setUserData("Male");
        maleRadio.setSelected(true);

        RadioButton femaleRadio = new RadioButton("Female");
        femaleRadio.setToggleGroup(genderGroup);
        femaleRadio.setUserData("Female");

        HBox genderBox = new HBox(20, maleRadio, femaleRadio);
        genderBox.setAlignment(Pos.CENTER);

        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        addressField.setMaxWidth(300);

        TextField contactNumberField = new TextField();
        contactNumberField.setPromptText("Contact Number");
        contactNumberField.setMaxWidth(300);

        TextField birthDateField = new TextField();
        birthDateField.setPromptText("Birth Date (YYYY-MM-DD)");
        birthDateField.setMaxWidth(300);

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #c7002e; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20;");
        signUpButton.setOnAction(e -> {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String gender = genderGroup.getSelectedToggle() != null ? genderGroup.getSelectedToggle().getUserData().toString() : "";
            String address = addressField.getText().trim();
            String contact = contactNumberField.getText().trim();
            String birthDate = birthDateField.getText().trim();

            try {
                boolean success = signUpController.signUpDonor(firstName, lastName, username, password, gender, address, contact, birthDate);
                if (success) {
                    showInfo("Sign Up Successful", "User created successfully! You can now log in.");
                    // Optionally, switch to login scene
                    // primaryStage.setScene(new LogInDonorForm().createLoginPage(primaryStage));
                } else {
                    showError("Sign Up Failed", "Please check your details and try again.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                showError("Error", "Server error. Please try again later.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle(
                "-fx-background-color: #e0e0e0; -fx-text-fill: #c7002e; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;");
        backButton.setOnAction(event -> primaryStage.setScene(new Main().logInUser()));

        HBox buttonBox = new HBox(15, signUpButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        signUpForm.getChildren().addAll(title, firstNameField, lastNameField, usernameField, passwordField,
                genderLabel, genderBox, addressField, contactNumberField, birthDateField, buttonBox);

        return new Scene(signUpForm, 600, 600);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
