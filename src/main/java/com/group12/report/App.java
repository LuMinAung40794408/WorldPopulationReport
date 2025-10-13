package com.group12.report;

import com.group12.report.data_access.CountryDAO;
import com.group12.report.reports.CountryReport;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            // Get database connection details from environment variables if they exist.
            // If not found, use the default values provided here.
            String url = System.getenv().getOrDefault("DB_URL",
                    "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false");
            String user = System.getenv().getOrDefault("DB_USER", "root");
            String pass = System.getenv().getOrDefault("DB_PASS", "example");

            // Connect to the MySQL database using the info above.
            app.connect(url, user, pass);

            // === COUNTRY REPORT SECTION ===

            // Create a new CountryDAO object (this handles talking to the database)
            CountryDAO dao = new CountryDAO(app.con);

            // Create a CountryReport object to display results in a table format.
            // The number (10) means: show only the top 10 results for each report.
            CountryReport countryReport = new CountryReport(10);

            // Print a simple header for the country report section
            countryReport.printCategory("Country Report");

            // Show all countries in the world sorted by population (largest to smallest)
            countryReport.displayCountries(dao.getAllCountriesByPopulation(null), "1.All countries in the world organized by largest to smallest population");

            // Show all countries in a specific continent (here: Asia) sorted by population
            countryReport.displayCountries(dao.getCountriesByContinent("Asia", null), "2.All countries in a continent organized by largest to smallest population (Asia) ");

            //Show all countries in a specific region (here: Southeast Asia) sorted by population
            countryReport.displayCountries(dao.getCountriesByRegion("Southeast Asia", null), "3.All countries in a region organized by largest to smallest population (Southeast Asia) ");


        } catch (Exception e) {
            System.err.println("Startup error: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }
}
