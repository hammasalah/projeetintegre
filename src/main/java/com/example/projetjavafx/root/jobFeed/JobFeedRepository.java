package com.example.projetjavafx.root.jobFeed;

import com.example.projetjavafx.root.DbConnection.AivenMySQLManager;
import com.example.projetjavafx.root.organizer.Job;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JobFeedRepository {

    // Method to apply for a job
    public static void applyForJob(int userId, int jobId) throws SQLException {
        String sql = "INSERT INTO Applications (user_id, job_id, status, applied_at, rewarded) VALUES (?, ?, 'pending', ?, 0)";

        try (Connection conn = AivenMySQLManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters
            pstmt.setInt(1, userId);
            pstmt.setInt(2, jobId);
            pstmt.setString(3, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // Execute the query
            pstmt.executeUpdate();
        }
    }
    public static List<Job> getAllJobs() throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT job_id, job_title, event_title, job_location, employment_type, application_deadline, " +
                "min_salary, max_salary, currency, job_description, recruiter_name, recruiter_email, created_at " +
                "FROM jobs";

        try (Connection conn = AivenMySQLManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Job job = new Job(
                        rs.getInt("job_id"), // Add job_id
                        rs.getString("job_title"),
                        rs.getString("event_title"),
                        rs.getString("job_location"),
                        rs.getString("employment_type"),
                        rs.getString("application_deadline"),
                        rs.getDouble("min_salary"),
                        rs.getDouble("max_salary"),
                        rs.getString("currency"),
                        rs.getString("job_description"),
                        rs.getString("recruiter_name"),
                        rs.getString("recruiter_email"),
                        rs.getString("created_at")
                );
                jobs.add(job);
            }
        }
        return jobs;
    }

}