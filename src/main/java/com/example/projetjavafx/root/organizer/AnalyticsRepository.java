package com.example.projetjavafx.root.organizer;

import com.example.projetjavafx.root.DbConnection.AivenMySQLManager;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalyticsRepository {

    public static ObservableList<PieChart.Data> getSourceData() throws SQLException {
        ObservableList<PieChart.Data> data = javafx.collections.FXCollections.observableArrayList();
        String sql = "SELECT source, COUNT(*) as clicks FROM ads GROUP BY source";

        try (Connection conn = AivenMySQLManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                data.add(new PieChart.Data(rs.getString("source"), rs.getInt("clicks")));
            }
        }
        return data;
    }

    public static XYChart.Series<String, Number> getSpendData() throws SQLException {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        String sql = "SELECT date, SUM(amount_spent) as total_spent FROM ads GROUP BY date";

        try (Connection conn = AivenMySQLManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString("date"), rs.getDouble("total_spent")));
            }
        }
        return series;
    }

    // Add more methods for other charts and data as needed
}