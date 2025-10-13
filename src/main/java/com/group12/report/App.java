package com.group12.report;

import com.group12.report.data_access.CityDAO;
import com.group12.report.reports.CityReport;

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
            String url = System.getenv().getOrDefault("DB_URL",
                    "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false");
            String user = System.getenv().getOrDefault("DB_USER", "root");
            String pass = System.getenv().getOrDefault("DB_PASS", "example");

            app.connect(url, user, pass);


            //city report
            CityDAO cityDAO = new CityDAO(app.con);
// Create the data-access object using the existing DB connection from 'app'.

            CityReport cityReport = new CityReport(10); // show only top 10 cities per report
// Configure the report to display at most 10 rows per section in the console.

            cityReport.printCategory("City Report");
// Print a banner/header for the City Report section.

            cityReport.displayCities(
                    cityDAO.getAllCitiesByPopulation(null),
                    "4.All Cities in the World Organized by Largest to Smallest Population"
            );
// Query all cities (no DB limit applied because null), then print the top 10 per CityReport.

            cityReport.displayCities(
                    cityDAO.getCitiesByContinent("Asia", null),
                    "5.All Cities in Asia Organized by Largest to Smallest Population(Asia)"
            );
// Filter by continent = 'Asia' in the DB (no DB limit), then display top 10.

            cityReport.displayCities(
                    cityDAO.getCitiesByRegion("Southeast Asia", null),
                    "6.All Cities in Southeast Asia Organized by Largest to Smallest Population(Southeast Asia"
            );
// Filter by region = 'Southeast Asia' (no DB limit), then display top 10.


            cityReport.displayCities(
                    cityDAO.getCitiesByDistrict("California", null),
                    "7.All Cities in California Organized by Largest to Smallest Population(California)"
            );
// Filter by city.district = 'California' (no DB limit), then display top 10.
// Works with the MySQL 'world' sample where many CA cities use district 'California'.

            cityReport.displayCities(
                    cityDAO.getCitiesByCountry("Myanmar", null),
                    "8.All Cities in Myanmar Organized by Largest to Smallest Population(Myanmar)"
            );
// Filter by country name = 'Myanmar' (no DB limit), then display top 10.
// Ensure 'Myanmar' matches the Country.Name value in your dataset.

        } catch (Exception e) {
            System.err.println("Startup error: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }
}
