package com.example.projetjavafx.root.organizer;

import com.example.projetjavafx.root.DbConnection.AivenMySQLManager;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnalyticsRepository {

    public static List<EventStats> getEventStats(int organizerId) throws SQLException {
        List<EventStats> stats = new ArrayList<>();
        String sql = "SELECT e.event_id, e.name, "
                + "COUNT(p.id) AS total, "
                + "SUM(CASE WHEN u.gender = 'Male' THEN 1 ELSE 0 END) AS male, "
                + "SUM(CASE WHEN u.gender = 'Female' THEN 1 ELSE 0 END) AS female "
                + "FROM Events e "
                + "LEFT JOIN participation p ON e.event_id = p.event_id "
                + "LEFT JOIN Users u ON p.participant_id = u.user_id "
                + "WHERE e.organizer_id = ? "
                + "GROUP BY e.event_id";

        try (Connection conn = AivenMySQLManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, organizerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                stats.add(new EventStats(
                        rs.getInt("event_id"),
                        rs.getString("name"),
                        rs.getInt("total"),
                        rs.getInt("male"),
                        rs.getInt("female")
                ));
            }
        }
        return stats;
    }

    public static XYChart.Series<String, Number> getParticipationTrend(int organizerId) throws SQLException {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        String sql = "SELECT e.name, COUNT(p.id) AS participants "
                + "FROM Events e "
                + "LEFT JOIN participation p ON e.event_id = p.event_id "
                + "WHERE e.organizer_id = ? "
                + "GROUP BY e.event_id "
                + "ORDER BY e.start_time";

        try (Connection conn = AivenMySQLManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, organizerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(
                        rs.getString("name"),
                        rs.getInt("participants")
                ));
            }
        }
        return series;
    }
}