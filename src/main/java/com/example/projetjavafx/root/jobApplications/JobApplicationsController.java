package com.example.projetjavafx.root.jobApplications;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JobApplicationsController {

    @FXML private TableView<Application> applicationsTable;
    @FXML private TableColumn<Application, Number> applicationIdColumn;
    @FXML private TableColumn<Application, Number> userIdColumn;
    @FXML private TableColumn<Application, String> coverLetterColumn;
    @FXML private TableColumn<Application, String> resumeColumn;
    @FXML private TableColumn<Application, String> statusColumn;
    @FXML private TableColumn<Application, Void> actionColumn;

    private final ObservableList<Application> applications = FXCollections.observableArrayList();
    private int userId;

    @FXML
    public void initialize() {
        setupTableColumns();
        // For demonstration, we set a default user id.
        // Replace this with your actual user context.
        setUserId(1);
    }

    private void setupTableColumns() {
        // Bind columns to Application properties
        applicationIdColumn.setCellValueFactory(cellData -> cellData.getValue().applicationIdProperty());
        userIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty());
        coverLetterColumn.setCellValueFactory(cellData -> cellData.getValue().coverLetterProperty());
        resumeColumn.setCellValueFactory(cellData -> cellData.getValue().resumePathProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Custom cell for Cover Letter with wrapping text
        coverLetterColumn.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item);
                setWrapText(true);
            }
        });

        // Hyperlink cell for Resume
        resumeColumn.setCellFactory(column -> new TableCell<>() {
            private final Hyperlink hyperlink = new Hyperlink("View Resume");
            {
                hyperlink.setOnAction(event -> handleViewResume(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hyperlink);
            }
        });

        // Editable status column with ComboBox
        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn("pending", "accepted", "rejected"));
        statusColumn.setOnEditCommit(event -> {
            Application application = event.getRowValue();
            application.setStatus(event.getNewValue());
        });

        // Action column with an Update button
        actionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button updateButton = new Button("Update");
            {
                updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                updateButton.setOnAction(event -> handleStatusUpdate(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : updateButton);
            }
        });

        applicationsTable.setItems(applications);
        applicationsTable.setEditable(true);
    }

    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("User ID set: " + userId);
        loadApplicationsForUserJobs();
    }

    private void loadApplicationsForUserJobs() {
        try {
            List<Application> applicationList = JobApplicationsRepository.getApplicationsForUserPostedJobs(userId);
            System.out.println("Applications fetched: " + applicationList.size());
            applications.setAll(applicationList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load applications", e.getMessage());
        }
    }

    private void handleStatusUpdate(Application application) {
        try {
            Optional<ButtonType> result = showConfirmationDialog(
                    "Update Status",
                    "Are you sure you want to update the application status to '" + application.getStatus() + "'?"
            );

            if (result.isPresent() && result.get() == ButtonType.OK) {
                JobApplicationsRepository.updateApplicationStatus(application.getApplicationId(), application.getStatus());
                showAlert(Alert.AlertType.INFORMATION, "Success", "Status Updated", "Application status updated successfully");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update status", e.getMessage());
        }
    }

    private void handleViewResume(Application application) {
        try {
            File resumeFile = new File(application.getResumePath());
            if (resumeFile.exists()) {
                Desktop.getDesktop().open(resumeFile);
            } else {
                showAlert(Alert.AlertType.ERROR, "File Not Found", "Resume Not Found", "The resume file could not be located");
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error Opening File", "Could not open resume", e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmationDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    public void setJobId(int yourActualJobId) {
    }

    // If needed, implement or remove setJobId depending on your design.

}
