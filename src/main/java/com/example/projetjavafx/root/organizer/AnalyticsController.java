package com.example.projetjavafx.root.organizer;

import com.example.projetjavafx.root.organizer.AnalyticsRepository;
import com.example.projetjavafx.root.organizer.EventStats;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.List;

public class AnalyticsController {

    @FXML private ComboBox<EventStats> eventComboBox;
    @FXML private Label totalParticipantsLabel;
    @FXML private Label maleParticipantsLabel;
    @FXML private Label femaleParticipantsLabel;
    @FXML private BarChart<String, Number> genderBarChart;
    @FXML private LineChart<String, Number> participationLineChart;

    private int organizerId;

    @FXML
    public void initialize() {
        setupChartAxes();
        loadEventData();
        setupComboBoxListener();
    }

    private void setupChartAxes() {
        // Configure BarChart axes
        CategoryAxis barXAxis = (CategoryAxis) genderBarChart.getXAxis();
        NumberAxis barYAxis = (NumberAxis) genderBarChart.getYAxis();
        barXAxis.setLabel("Gender");
        barYAxis.setLabel("Participants");

        // Configure LineChart axes
        CategoryAxis lineXAxis = (CategoryAxis) participationLineChart.getXAxis();
        NumberAxis lineYAxis = (NumberAxis) participationLineChart.getYAxis();
        lineXAxis.setLabel("Events");
        lineYAxis.setLabel("Total Participants");

        // Set chart titles (optional if already set in FXML)
        genderBarChart.setTitle("Gender Distribution");
        participationLineChart.setTitle("Participation Trend");
    }

    private void loadEventData() {
        try {
            List<EventStats> stats = AnalyticsRepository.getEventStats(organizerId);
            eventComboBox.getItems().addAll(stats);

            XYChart.Series<String, Number> trendSeries = AnalyticsRepository.getParticipationTrend(organizerId);
            participationLineChart.getData().add(trendSeries);

        } catch (SQLException e) {
            showErrorAlert("Database Error", "Failed to load event data: " + e.getMessage());
        }
    }

    private void setupComboBoxListener() {
        eventComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateStatsDisplay(newVal);
                updateGenderChart(newVal);
            }
        });
    }

    private void updateStatsDisplay(EventStats stats) {
        totalParticipantsLabel.setText(String.valueOf(stats.getTotalParticipants()));
        maleParticipantsLabel.setText(String.valueOf(stats.getMaleCount()));
        femaleParticipantsLabel.setText(String.valueOf(stats.getFemaleCount()));
    }

    private void updateGenderChart(EventStats stats) {
        genderBarChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Male", stats.getMaleCount()));
        series.getData().add(new XYChart.Data<>("Female", stats.getFemaleCount()));
        genderBarChart.getData().add(series);
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Navigation methods remain unchanged
}