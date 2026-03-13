package com.example.demo;

import com.example.demo.Controllers.*;
import com.example.demo.Forms.LogInDonorForm;
import com.example.demo.Forms.LogInHospitalForm;
import com.example.demo.Forms.LogInStaffForm;
import com.example.demo.Forms.SignUpForm;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        // Load front page
        Scene frontPage = FrontPage();
        primaryStage.setTitle("Blood Link");
        primaryStage.setScene(frontPage);
        primaryStage.setFullScreen(true);

        primaryStage.show();
    }

    // Getter and Setter for PrimaryStage
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setScene(Scene scene) {
        double width = primaryStage.getWidth();
        double height = primaryStage.getHeight();
        primaryStage.setScene(scene);
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }

    public Scene FrontPage() {
        BorderPane root = new BorderPane();

        // Load Background Image
        Image backgroundImage = new Image(getClass().getResource("/images/bloodLinkBg.png").toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );
        root.setBackground(new Background(bgImage));

        Hyperlink helpLink = new Hyperlink("Help?");
        helpLink.setStyle("-fx-font-size: 18px; -fx-text-fill: #bd0000; -fx-underline: true;");
        helpLink.setOnAction(event -> new HelpController().getHelpContent());


        HBox helpBox = new HBox(helpLink);
        helpBox.setStyle("-fx-padding: 10 20 0 0;");
        helpBox.setAlignment(Pos.TOP_RIGHT);
        BorderPane.setAlignment(helpBox, Pos.TOP_RIGHT);


        // Title Label
        Label title = new Label("BLOOD LINK +");
        title.setStyle("-fx-font-size: 54px; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox titleBox = new VBox(title);
        titleBox.setStyle("-fx-alignment: center-left; -fx-padding: 0 0 0 50;");

        // Buttons
        Button btnBloodRequest = new Button("Admin");
        btnBloodRequest.setStyle("-fx-font-size: 22px; -fx-text-fill: #bd0000; -fx-font-weight: bold; " +
                "-fx-background-radius: 30px; -fx-border-radius: 30px; -fx-border-color: #bd0000; -fx-cursor: hand;" +
                "-fx-border-width: 2.5px;");
        btnBloodRequest.setOnAction(event -> Main.setScene(logInAdmin()));


        Button btnDonate = new Button("User");
        btnDonate.setStyle("-fx-font-size: 22px; -fx-text-fill: #bd0000; -fx-font-weight: bold; " +
                "-fx-background-radius: 30px; -fx-border-radius: 30px; -fx-border-color: #bd0000; -fx-cursor: hand;" +
                "-fx-border-width: 2.5px;");
        btnDonate.setOnAction(event -> Main.setScene(logInUser()));


        HBox buttonBox = new HBox(15, btnBloodRequest, btnDonate);
        buttonBox.setStyle("-fx-alignment: center; -fx-padding: 0, 0, 40, 0 ;");
        buttonBox.setPrefHeight(200);

        // Add elements to layout
        root.setTop(helpBox);
        root.setLeft(titleBox);
        root.setBottom(buttonBox);

        return new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
    }

    public Scene logInAdmin() {
        VBox container = new VBox(40);
        container.setPadding(new Insets(30));
        container.setAlignment(Pos.CENTER); // Ensures vertical centering
        container.setStyle("-fx-background-color: linear-gradient(to bottom right, #e3f2fd, #ffffff);");

        Label title = new Label("Admin Login");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #bd0000;");

        Button btnHosp = new Button("Log in as Hospital");
        Button btnStaff = new Button("Log in as Staff Member");

        btnHosp.setStyle("-fx-background-color: #bd0000; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;");
        btnStaff.setStyle("-fx-background-color: #bd0000; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;");

        Button btnBack = new Button("Back");
        btnBack.setStyle("-fx-background-color: #bd0000; -fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-padding: 8 18; -fx-background-radius: 10;");
        btnBack.setOnAction(event -> Main.setScene(FrontPage()));


        btnHosp.setPrefWidth(300);
        btnStaff.setPrefWidth(300);

        btnHosp.setOnAction(event -> Main.setScene(new LogInHospitalForm().createLoginPage(Main.getPrimaryStage())));
        btnStaff.setOnAction(event -> Main.setScene(new LogInStaffForm().createLoginPage(Main.getPrimaryStage())));

        VBox buttonBox = new VBox(20, btnHosp, btnStaff, btnBack);
        buttonBox.setAlignment(Pos.CENTER);

        container.getChildren().addAll(title, buttonBox);
        return new Scene(container, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
    }

    public Scene logInUser() {
        VBox container = new VBox(40);
        container.setPadding(new Insets(30));
        container.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: linear-gradient(to bottom right, #e3f2fd, #ffffff);");

        Label title = new Label("User Login");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #bd0000;");

        Button btnsignup = new Button("New User ? Sign up");
        Button btnlogin = new Button("Already a User ? Log in");


        btnsignup.setStyle("-fx-background-color: #bd0000; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;");
        btnlogin.setStyle("-fx-background-color: #bd0000; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;");

        Button btnBack = new Button("Back");
        btnBack.setStyle("-fx-background-color: #bd0000; -fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-padding: 8 18; -fx-background-radius: 10;");
        btnBack.setOnAction(event -> Main.setScene(FrontPage()));

        btnsignup.setPrefWidth(300);
        btnlogin.setPrefWidth(300);

        btnsignup.setOnAction(event -> Main.setScene(new SignUpForm().createSignUpPage(Main.getPrimaryStage())));
        btnlogin.setOnAction(event -> Main.setScene(new LogInDonorForm().createLoginScene(Main.getPrimaryStage())));
        btnBack.setOnAction(event -> Main.setScene(FrontPage()));

        VBox buttonBox = new VBox(20, btnsignup, btnlogin, btnBack);
        buttonBox.setAlignment(Pos.CENTER);

        container.getChildren().addAll(title, buttonBox);
        return new Scene(container, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
