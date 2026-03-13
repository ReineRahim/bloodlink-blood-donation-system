package com.example.demo.Forms;

import com.example.demo.Controllers.DonationController;
import com.example.demo.Controllers.DonorController;
import com.example.demo.Controllers.StaffDashboardController;
import com.example.demo.Main;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

public class StaffDashboardForm {
    private BorderPane mainLayout;
    private final StaffDashboardController controller = new StaffDashboardController();
    private final Stage primaryStage;

    public StaffDashboardForm(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainLayout = new BorderPane();
        MenuBar menuBar = createMenuBar();
        mainLayout.setTop(menuBar);
        mainLayout.setCenter(createRequestsView());
    }

    public Scene getScene() {
        return new Scene(mainLayout, 800, 600);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Options");

        MenuItem viewRequests = new MenuItem("View Blood Requests");
        MenuItem viewDonations = new MenuItem("View Blood Donations");
        MenuItem viewDonors = new MenuItem("View Registered Donors");
        MenuItem logOff = new MenuItem("Log Off");

        viewRequests.setOnAction(e -> mainLayout.setCenter(createRequestsView()));
        viewDonations.setOnAction(e -> mainLayout.setCenter(createDonationsView()));
        viewDonors.setOnAction(e -> mainLayout.setCenter(createDonorsView()));
        logOff.setOnAction(e -> Main.setScene(new Main().FrontPage()));

        menu.getItems().addAll(viewRequests, viewDonations, viewDonors, logOff);
        menuBar.getMenus().add(menu);
        return menuBar;
    }

    private VBox createRequestsView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        TableView<JSONObject> table = new TableView<>();
        table.setEditable(true);

        TableColumn<JSONObject, String> idColumn = new TableColumn<>("Request ID");
        idColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("id")));

        TableColumn<JSONObject, String> hospitalColumn = new TableColumn<>("Hospital Name");
        hospitalColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("hospitalName")));

        TableColumn<JSONObject, String> staffColumn = new TableColumn<>("Requested by");
        staffColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("staffName")));

        TableColumn<JSONObject, String> bloodTypeColumn = new TableColumn<>("Blood Type");
        bloodTypeColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("bloodTypeNeeded")));

        TableColumn<JSONObject, String> volumeColumn = new TableColumn<>("Volume (ml)");
        volumeColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("requiredVolume")));

        TableColumn<JSONObject, String> dateColumn = new TableColumn<>("Date of Request");
        dateColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("dateOfRequest")));

        TableColumn<JSONObject, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("status")));
        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Pending", "Fulfilled"));

        statusColumn.setOnEditCommit(event -> {
            JSONObject request = event.getRowValue();
            request.put("status", event.getNewValue());
            controller.updateRequestStatus(request.getInt("id"), event.getNewValue());
        });

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
                        JSONObject request = getTableView().getItems().get(getIndex());
                        controller.deleteRequest(request.getInt("id"));
                        getTableView().getItems().remove(request);
                        getTableView().refresh();
                    });
                    setGraphic(deleteButton);
                }
            }
        });

        table.getColumns().addAll(idColumn, hospitalColumn, staffColumn, bloodTypeColumn, volumeColumn, dateColumn, statusColumn, deleteColumn);

        ObservableList<JSONObject> list = FXCollections.observableArrayList();
        JSONArray array = controller.getAllRequests();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getJSONObject(i));
        }
        table.setItems(list);

        container.getChildren().addAll(new Label("Blood Requests"), table);
        return container;
    }

    private VBox createDonationsView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        TableView<JSONObject> table = new TableView<>();
        table.setEditable(true);

        TableColumn<JSONObject, String> idColumn = new TableColumn<>("Donation ID");
        idColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("donationId")));

        TableColumn<JSONObject, String> donorColumn = new TableColumn<>("Donor Username");
        donorColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("donorUsername")));

        TableColumn<JSONObject, String> bloodTypeColumn = new TableColumn<>("Blood Type");
        bloodTypeColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("bloodType")));

        TableColumn<JSONObject, String> volumeColumn = new TableColumn<>("Volume (ml)");
        volumeColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("volumeMl")));

        TableColumn<JSONObject, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("donationDate")));


        TableColumn<JSONObject, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("status")));
        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn("pending", "fulfilled"));

        statusColumn.setOnEditCommit(event -> {
            JSONObject donation = event.getRowValue();
            donation.put("status", event.getNewValue());
            new DonationController().updateDonationStatus(donation.optInt("donation_id"), event.getNewValue());
        });

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
                        JSONObject donation = getTableView().getItems().get(getIndex());
                        new DonationController().deleteDonation(donation.getLong("donation_id"));
                        getTableView().getItems().remove(donation);
                        getTableView().refresh();
                    });
                    setGraphic(deleteButton);
                }
            }
        });

        table.getColumns().addAll(idColumn, donorColumn, bloodTypeColumn, volumeColumn, dateColumn, statusColumn, deleteColumn);

        ObservableList<JSONObject> list = FXCollections.observableArrayList();
        JSONArray array = new DonationController().getAllDonations();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getJSONObject(i));
        }
        table.setItems(list);

        container.getChildren().addAll(new Label("Blood Donations"), table);
        return container;
    }



    private VBox createDonorsView() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));

        Label title = new Label("Registered Donors");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TableView<JSONObject> table = new TableView<>();

        TableColumn<JSONObject, String> idColumn = new TableColumn<>("Donor ID");
        idColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("donor_id")));

        TableColumn<JSONObject, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("dfirstName")));

        TableColumn<JSONObject, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("dlastName")));

        TableColumn<JSONObject, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("username")));

        TableColumn<JSONObject, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("gender")));

        TableColumn<JSONObject, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("daddress")));

        TableColumn<JSONObject, String> contactColumn = new TableColumn<>("Contact");
        contactColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("dcontactNumber")));

        TableColumn<JSONObject, String> birthDateColumn = new TableColumn<>("Date of Birth");
        birthDateColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().optString("dateOfBirth")));

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
                        JSONObject donor = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this donor?", ButtonType.YES, ButtonType.NO);
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.YES) {
                                new DonorController().deleteDonor(donor.getInt("donor_id"));
                                getTableView().getItems().remove(donor);
                                getTableView().refresh();
                            }
                        });
                    });
                    setGraphic(deleteButton);
                }
            }
        });

        table.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, usernameColumn, genderColumn, addressColumn, contactColumn, birthDateColumn, deleteColumn);

        ObservableList<JSONObject> list = FXCollections.observableArrayList();
        JSONArray array = new DonorController().getAllDonors();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getJSONObject(i));
        }
        table.setItems(list);

        container.getChildren().addAll(new Label("Donors "), table);
        return container;
    }

}