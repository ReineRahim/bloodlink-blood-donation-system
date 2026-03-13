package com.example.demo.Forms;

import com.example.demo.Controllers.BloodRequestController;
import com.example.demo.Controllers.DonationController;
import com.example.demo.Controllers.HospitalDashboardController;
import com.example.demo.Controllers.LogInHospitalController;
import com.example.demo.Main;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

public class HospitalDashboardForm {

    private final Stage primaryStage;
    private final HospitalDashboardController controller = new HospitalDashboardController();
    private final TableView<JSONObject> table = new TableView<>();

    private final String loggedInHospitalName;

    public HospitalDashboardForm(Stage stage, String loggedInHospitalName) {
        this.primaryStage = stage;
        this.loggedInHospitalName = loggedInHospitalName;
    }


    public Scene getScene() {
        TabPane tabPane = new TabPane();
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);

        Tab viewRequestsTab = new Tab("All Requests", createRequestTable());
        viewRequestsTab.setClosable(false);

        Tab submitRequestTab = new Tab("New Request", createBloodRequestForm());
        submitRequestTab.setClosable(false);

        Tab logOffTab = new Tab("Log Off", createLogOffScreen());
        logOffTab.setClosable(false);

        tabPane.getTabs().addAll(viewRequestsTab, submitRequestTab, logOffTab);
        return new Scene(tabPane, 800, 600);
    }

    private VBox createRequestTable() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        TableColumn<JSONObject, String> idColumn = new TableColumn<>("Request ID");
        idColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("requestId")));

        TableColumn<JSONObject, String> hospitalColumn = new TableColumn<>("Hospital Name");
        hospitalColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("hospitalName")));

        TableColumn<JSONObject, String> staffColumn = new TableColumn<>("Requested by");
        staffColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("staffName")));

        TableColumn<JSONObject, String> bloodTypeColumn = new TableColumn<>("Blood Type");
        bloodTypeColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("bloodTypeNeeded")));

        TableColumn<JSONObject, String> volumeColumn = new TableColumn<>("Volume (ml)");
        volumeColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("requiredVolume")));

        TableColumn<JSONObject, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("status")));
        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Pending", "Fulfilled"));

        TableColumn<JSONObject, Void> deleteColumn = new TableColumn<>("Action");
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    deleteButton.setOnAction(event -> {
                        JSONObject bloodRequest = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm Deletion");
                        alert.setHeaderText("Are you sure you want to delete this request?");

                        ButtonType confirmButton = new ButtonType("Yes, Delete", ButtonBar.ButtonData.OK_DONE);
                        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(confirmButton, cancelButton);

                        alert.showAndWait().ifPresent(response -> {
                            if (response == confirmButton) {
                                DonationController donationService = new DonationController();
                                BloodRequestController bloodRequestService =  new BloodRequestController();
                                bloodRequestService.deleteBloodRequest(bloodRequest.optInt("request_id"));

                                getTableView().getItems().remove(bloodRequest);
                                getTableView().refresh();
                            }
                        });
                    });

                    setGraphic(deleteButton);
                }
            }
        });

        statusColumn.setOnEditCommit(event -> {
            JSONObject request = event.getRowValue();
            request.put("status", event.getNewValue());

            BloodRequestController service = new BloodRequestController();
            service.updateRequestStatus(request.optInt("request_id"), request.optString("status"));
            table.refresh();
        });

        table.setEditable(true);
        table.getColumns().addAll(idColumn, hospitalColumn, staffColumn, bloodTypeColumn, volumeColumn, statusColumn, deleteColumn);
        table.setItems(fetchRequests());

        container.getChildren().addAll(new Label("Blood Requests"), table);
        return container;
    }

    private ObservableList<JSONObject> fetchRequests() {
        ObservableList<JSONObject> list = FXCollections.observableArrayList();
        JSONArray array = controller.getRequestsByHospital(loggedInHospitalName);
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getJSONObject(i));
        }
        return list;
    }

    private VBox createBloodRequestForm() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("New Blood Request");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #C70039;");
        titleLabel.setEffect(new DropShadow(5, Color.GRAY));

        Label hospitalLabel = new Label("Hospital Name: " + loggedInHospitalName);
        hospitalLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #900C3F; -fx-font-style: italic;");


        Label bloodLabel = new Label("");


        ComboBox<String> bloodTypeCombo = new ComboBox<>();
        bloodTypeCombo.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        bloodTypeCombo.setPromptText("Select Blood Type");

        bloodTypeCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                bloodLabel.setText("Blood Type: " + newVal);
            }
        });

        bloodLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;" +
                " -fx-background-color: #C70039; -fx-padding: 8px;" +
                " -fx-border-radius: 5; -fx-background-radius: 5;");



        Image bloodBagImage = new Image(getClass().getResource("/images/blood_bag2.png").toExternalForm()); // Update path to actual image file
        ImageView imageView = new ImageView(bloodBagImage);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);



        StackPane imageContainer = new StackPane();
        imageContainer.getChildren().addAll(imageView, bloodLabel);
        imageContainer.setPrefSize(300, 300);


        StackPane.setAlignment(bloodLabel, Pos.TOP_CENTER);
        bloodLabel.setTranslateY(80);
        bloodLabel.setTranslateX(10);

        TextField staffNameField = new TextField();
        staffNameField.setPromptText("Staff Name");

        Slider volumeSlider = new Slider(100, 2000, 500);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);

        Label selectedVolume = new Label("Selected Volume: 500 ml");
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            selectedVolume.setText("Selected Volume: " + newVal.intValue() + " ml");
        });

        Button submit = new Button("Submit Request");
        submit.setOnAction(e -> {
            String bloodType = bloodTypeCombo.getValue();
            String staffName = staffNameField.getText();
            int volume = (int) volumeSlider.getValue();
            String hospitalName = loggedInHospitalName;

            if (bloodType == null || staffName.isEmpty()) {
                showAlert("Error", "Please fill out all fields!");
                return;
            }

            controller.createRequest(hospitalName, staffName, bloodType, volume);
            showAlert("Success", "Blood request submitted successfully!");
        });

        VBox form = new VBox(10, imageContainer,staffNameField, titleLabel, hospitalLabel,  bloodTypeCombo, selectedVolume, volumeSlider, submit, bloodLabel);
        form.setAlignment(Pos.CENTER);
        container.getChildren().add(form);
        return container;
    }

    private VBox createLogOffScreen() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        Button logOff = new Button("Log Off");
        logOff.setOnAction(e -> Main.setScene(new Main().FrontPage()));
        box.getChildren().addAll(new Label("Log Off and Return Home"), logOff);
        return box;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
