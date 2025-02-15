package com.example.projetjavafx.root.organizer;

import com.example.projetjavafx.root.DbConnection.AivenMySQLManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateJobOfferController {

    @FXML
    private TextField jobTitleField;

    @FXML
    private TextField eventTitleField;

    @FXML
    private TextField jobLocationField;

    @FXML
    private ComboBox<String> employmentTypeComboBox;

    @FXML
    private DatePicker applicationDeadlinePicker;

    @FXML
    private TextField minSalaryField;

    @FXML
    private TextField maxSalaryField;

    @FXML
    private ComboBox<String> currencyComboBox;

    @FXML
    private TextArea jobDescriptionArea;

    @FXML
    private TextField recruiterNameField;

    @FXML
    private TextField recruiterEmailField;

    @FXML
    private Button postJobButton;

    @FXML
    private GridPane formGridPane;

    @FXML
    private VBox mainVBox;

    @FXML
    public void initialize() {
        // Initialize ComboBoxes
        employmentTypeComboBox.getItems().addAll("Full-Time", "Part-Time", "Contract", "Internship");
        currencyComboBox.getItems().addAll("USD", "EUR", "TND");

        // Set default values
        employmentTypeComboBox.setValue("Full-Time");
        currencyComboBox.setValue("USD");

        // Add event handlers
        postJobButton.setOnAction(event -> handlePostJob());
    }

    private void handlePostJob() {
        // Validate form data
        if (!validateForm()) {
            showAlert("Error", "Please fill all required fields.");
            return;
        }

        // Insert job offer into the database
        try (Connection conn = AivenMySQLManager.getConnection()) {
            String sql = "INSERT INTO jobs (job_title, event_title, job_location, employment_type, application_deadline, min_salary, max_salary, currency, job_description, recruiter_name, recruiter_email, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Set parameters
            pstmt.setString(1, jobTitleField.getText());
            pstmt.setString(2, eventTitleField.getText());
            pstmt.setString(3, jobLocationField.getText());
            pstmt.setString(4, employmentTypeComboBox.getValue());
            pstmt.setString(5, applicationDeadlinePicker.getValue().toString());
            pstmt.setDouble(6, Double.parseDouble(minSalaryField.getText()));
            pstmt.setDouble(7, Double.parseDouble(maxSalaryField.getText()));
            pstmt.setString(8, currencyComboBox.getValue());
            pstmt.setString(9, jobDescriptionArea.getText());
            pstmt.setString(10, recruiterNameField.getText());
            pstmt.setString(11, recruiterEmailField.getText());
            pstmt.setInt(12, 1); // Replace with the logged-in user's ID (e.g., from session)

            // Execute the query
            pstmt.executeUpdate();
            showAlert("Success", "Job offer posted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to post job offer: " + e.getMessage());
        }
    }

    private boolean validateForm() {
        // Check if all required fields are filled
        return !jobTitleField.getText().isEmpty() &&
                !eventTitleField.getText().isEmpty() &&
                !jobLocationField.getText().isEmpty() &&
                applicationDeadlinePicker.getValue() != null &&
                !minSalaryField.getText().isEmpty() &&
                !maxSalaryField.getText().isEmpty() &&
                !jobDescriptionArea.getText().isEmpty() &&
                !recruiterNameField.getText().isEmpty() &&
                !recruiterEmailField.getText().isEmpty();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}