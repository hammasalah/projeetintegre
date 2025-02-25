package com.example.projetjavafx.root;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    public Button createJobButton;
    public Button jobButton;
    @FXML
    private ImageView imageView;

    public void initialize() throws SQLException {
    }

    private void loadView(String fxmlPath, ActionEvent event) {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    protected void onOrganizerButtonClick(ActionEvent event) {
        loadView("/com/example/projetjavafx/organizer/organizer-view.fxml", event);
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

    public void onDachClick(ActionEvent event) {
        loadView("/com/example/projetjavafx/organizer/organizer-view.fxml", event);
    }

    @FXML
    protected void onAnalyticsClick(ActionEvent event) {
        loadView("/com/example/projetjavafx/organizer/analytics-view.fxml", event);
    }

    @FXML
    protected void onJobFeedClick(ActionEvent event) {
        loadView("/com/example/projetjavafx/jobfeed/job-feed-view.fxml", event);
    }


}