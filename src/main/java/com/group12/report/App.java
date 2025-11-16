package com.group12.report;

import com.group12.report.data_access.PopulationDAO;
import com.group12.report.reports.PopulationReport;


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

            //population report
            PopulationDAO popDAO = new PopulationDAO(app.con);
            PopulationReport popReport = new PopulationReport(10);

            popReport.printCategory("Population Report");

            // 1–4: Breakdown reports
            popReport.displayPopulations(popDAO.getWorldPopulation(), "23.Population of the World");
            popReport.displayPopulations(popDAO.getPopulationByContinent(), "24.Population of Each Continent");
            popReport.displayPopulations(popDAO.getPopulationByRegion(), "25.Population of Each Region");
            popReport.displayPopulations(popDAO.getPopulationByCountry(), "26.Population of Each Country");

            // 5: District population (filtered by country)
            popReport.displayDistrictPopulations(popDAO.getPopulationByDistrict("Myanmar"), "17.Population of Each District in Myanmar");

            // 6: City population
            popReport.displayCityPopulations(popDAO.getPopulationByCity(), "28.Population of Each City");

            // New city vs non-city breakdowns
            popReport.displayPopulations(popDAO.getCityVsNonCityByContinent(), "29.City vs Non-City Population by Continent");
            popReport.displayPopulations(popDAO.getCityVsNonCityByRegion(), "30.City vs Non-City Population by Region");
            popReport.displayPopulations(popDAO.getCityVsNonCityByCountry(), "31.City vs Non-City Population by Country");


        } catch (Exception e) {
            System.err.println("Startup error: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }
}
