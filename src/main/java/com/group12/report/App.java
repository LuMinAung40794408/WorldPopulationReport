package com.group12.report;

import com.group12.report.data_access.LanguageDAO;
import com.group12.report.reports.LanguageReport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class App {
    private Connection con;

    public void connect(String url, String user, String pass) throws InterruptedException {
        int maxRetries = 10;
        int retryDelay = 10000; // 3 seconds
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                System.out.printf("Connecting to database (attempt %d/%d)...%n", attempt + 1, maxRetries);
                con = DriverManager.getConnection(url, user, pass);
                System.out.println("Connected successfully.");
                return;
            } catch (SQLException e) {
                attempt++;
                System.err.println("Connection failed: " + e.getMessage());
                if (attempt >= maxRetries) {
                    throw new RuntimeException("Failed to connect to database after " + maxRetries + " attempts.", e);
                }
                System.out.printf("Retrying in %d seconds...%n", retryDelay / 1000);
                Thread.sleep(retryDelay);
            }
        }
    }

    public void disconnect() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Disconnected.");
            }
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        App app = new App();
        try {
            String url = System.getenv().getOrDefault("DB_URL",
                    "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false");
            String user = System.getenv().getOrDefault("DB_USER", "root");
            String pass = System.getenv().getOrDefault("DB_PASS", "example");

            app.connect(url, user, pass);

            // ==================== LANGUAGE REPORT SECTION ====================

            // Create a LanguageDAO instance to fetch language data from the database.
            // The DAO uses the active database connection (app.con).
            LanguageDAO languageDAO = new LanguageDAO(app.con);

            // Create a LanguageReport instance with a display limit of 10 rows.
            // This will control how many languages are shown in the console output.
            LanguageReport languageReport = new LanguageReport(10);

            // Print the category heading for the language report.
            languageReport.printCategory("Language Report");

            // Define a list of specific languages to be included in the report.
            // These are the languages whose total speaker counts will be retrieved.
            List<String> langs = List.of("English", "Chinese", "Hindi", "Spanish", "Arabic");

            // Fetch and display the report:
            // - Calls LanguageDAO.getLanguagesBySpeakerCount(langs) to retrieve data.
            // - Passes that data to LanguageReport.displayLanguages(...) for formatted output.
            languageReport.displayLanguages(
                    languageDAO.getLanguagesBySpeakerCount(langs),
                    "32.Languages by Number of Speakers (English, Chinese, Hindi, Spanish, Arabic)"
            );

            // =================================================================

            // Add similar calls for continent, region, country, district, city, and city-vs-noncity breakdowns
        } catch (Exception e) {
            System.err.println("Startup error: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }
}
