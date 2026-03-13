package com.example.demo.Forms;

import com.example.demo.Controllers.DonationController;
import com.example.demo.Controllers.LogInDonorController;
import com.example.demo.Main;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;

public class DonationForm {

    private Stage primaryStage;
    private String username;
    private final DonationController donationController = new DonationController();

    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Blood Donation Form");
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);

        Scene donationPage = createDonationForm();
        primaryStage.setScene(donationPage);
        primaryStage.show();
    }

    public Scene createDonationForm() {

        LogInDonorController logInDonor = new LogInDonorController();
        username = logInDonor.getUsername();

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: #F5F5F5; -fx-padding: 40;");

        VBox formBox = new VBox(20);
        formBox.setStyle("-fx-background-color: white; -fx-border-color: #C70039; -fx-border-width: 2px; -fx-border-radius: 10;");
        formBox.setPadding(new Insets(30));
        formBox.setMaxWidth(500);
        formBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Blood Donation Form 🩸");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #C70039;");
        titleLabel.setEffect(new DropShadow(5, Color.GRAY));

        Label usernameLabel = new Label("Logged in as: " + username);
        usernameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #900C3F; -fx-font-style: italic;");

        Image bloodBagImage = new Image(getClass().getResource("/images/blood_bag.png").toExternalForm());
        ImageView imageView = new ImageView(bloodBagImage);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        Label bloodTypeLabel = new Label("");
        bloodTypeLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #b81313; -fx-font-weight: bold; "
                + " -fx-padding: 5px; -fx-border-radius: 5;");

        StackPane imageContainer = new StackPane();
        imageContainer.getChildren().addAll(imageView, bloodTypeLabel);
        bloodTypeLabel.setPadding(new Insets(5, 10, 5, 5));
        StackPane.setMargin(bloodTypeLabel, new Insets(-80, 0, 0, -124));
        StackPane.setAlignment(bloodTypeLabel, Pos.CENTER);

        ComboBox<String> bloodTypeCombo = new ComboBox<>();
        bloodTypeCombo.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        bloodTypeCombo.setPromptText("Select Blood Type");
        bloodTypeCombo.setStyle("-fx-border-color: #C70039; -fx-border-radius: 5;");
        bloodTypeCombo.setMaxWidth(350);

        bloodTypeCombo.setOnAction(event -> {
            String selectedBloodType = bloodTypeCombo.getValue();
            bloodTypeLabel.setText(selectedBloodType);
        });

        Label volumeLabel = new Label("Select Donation Volume (ml):");
        volumeLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #C70039;");

        Slider volumeSlider = new Slider(100, 1000, 500);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(200);
        volumeSlider.setBlockIncrement(100);
        volumeSlider.setMaxWidth(350);

        Label selectedVolume = new Label("500 ml");
        selectedVolume.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            selectedVolume.setText(newVal.intValue() + " ml");
        });

        Label dateLabel = new Label("Select Donation Date:");
        dateLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #C70039;");

        DatePicker donationDate = new DatePicker();
        donationDate.setStyle("-fx-border-color: #C70039; -fx-border-radius: 5;");

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setStyle("-fx-accent: #C70039;");
        progressBar.setPrefWidth(250);

        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        Button submitButton = new Button("Submit Donation");
        submitButton.setStyle("-fx-font-size: 18; -fx-background-color: #C70039; -fx-text-fill: white; -fx-border-radius: 5;");
        submitButton.setOnAction(e -> {
            String selectedBloodType = bloodTypeCombo.getValue();
            LocalDate selectedDate = donationDate.getValue();

            if (selectedBloodType == null || selectedDate == null) {
                statusLabel.setText("⚠️ Please fill out all fields.");
                progressBar.setProgress(0);
                return;
            }

            statusLabel.setText("Submitting donation...");
            progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

            // Call controller (async would be better but simplified here)
            try {
                donationController.createDonation(
                        username,
                        selectedBloodType,
                        (int) volumeSlider.getValue(),
                        selectedDate.toString()
                );
                statusLabel.setText("✅ Donation successfully submitted!");
                progressBar.setProgress(1);
            } catch (Exception ex) {
                statusLabel.setText("❌ Error submitting donation.");
                progressBar.setProgress(0);
                ex.printStackTrace();
            }
        });

        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #900C3F; -fx-text-fill: white;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #C70039; -fx-text-fill: white;"));

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), formBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        Button backButton = new Button("Log Off");
        backButton.setStyle("-fx-font-size: 18; -fx-background-color: #555; -fx-text-fill: white; -fx-border-radius: 5;");
        backButton.setOnAction(event -> Main.setScene(new Main().FrontPage()));

        VBox buttonBox = new VBox(15, imageContainer, statusLabel, progressBar, submitButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        formBox.getChildren().addAll(titleLabel, usernameLabel, bloodTypeCombo, selectedVolume, volumeSlider, dateLabel, donationDate, buttonBox);
        container.getChildren().add(formBox);

        return new Scene(container, 800, 600);
    }

}
