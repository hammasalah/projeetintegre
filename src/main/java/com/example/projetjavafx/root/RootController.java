package com.example.projetjavafx.root;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RootController {
    public Button organizerButton;
    public Button eventsButton;
    public Button profileButton;
    public Button registerButton;
    public Button loginButton;
    public Button groupButton;
    public Label welcomeText;
    public Button createJobButton;
    // ... [keep your existing button declarations]

    public void initialize() throws SQLException {
    }

    private void loadView(String fxmlPath, ActionEvent event) {
        try {
            // 1. Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // 2. Get the current stage
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            // 3. Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            welcomeText.setText("Error loading view: " + e.getMessage());
        }
    }

    // Modified button handlers
    @FXML
    protected void onOrganizerButtonClick(ActionEvent event) {
        loadView("/com/example/projetJavaFX/organizer/organizer-view.fxml", event);
    }

    @FXML
    protected void onEventsClick(ActionEvent event) {
        loadView("/com/example/projetjavafx/events/events-view.fxml", event);
    }

    @FXML
    protected void onProfileClick(ActionEvent event) {
        loadView("/com/example/projetjavafx/profile/profile-view.fxml", event);
    }

    @FXML
    protected void onLoginClick(ActionEvent event) {
        loadView("/com/example/projetjavafx/auth/login-view.fxml", event);
    }

    @FXML
    protected void onGroupButtonClick(ActionEvent event) {
        loadView("/com/example/projetjavafx/group/group-profile-view.fxml", event);
    }

    @FXML
    protected void onRegisterClick(ActionEvent event) {
        loadView("/com/example/projetjavafx/auth/register-view.fxml", event);
    }
    @FXML
    protected void onCreateJobClick(ActionEvent event) {
        loadView("/com/example/projetjavafx/organizer/create-job-offer-view.fxml", event);
    }
}