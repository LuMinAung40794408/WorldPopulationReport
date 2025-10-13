package com.group12.report;

import com.group12.report.data_access.CapitalDAO;
import com.group12.report.reports.CapitalReport;


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

            //capital report
            CapitalDAO capitalDAO = new CapitalDAO(app.con);
            CapitalReport capitalReport = new CapitalReport(10); // show top 10 per report

            capitalReport.printCategory("Capital Report");

            capitalReport.displayCapitals(capitalDAO.getAllCapitalsByPopulation(null), "9.All Capital Cities in the World Organized by Population (Largest to Smallest)");
            capitalReport.displayCapitals(capitalDAO.getCapitalsByContinent("Asia", null), "10.All Capital Cities in A Continent Organized by Population (Asia)");
            capitalReport.displayCapitals(capitalDAO.getCapitalsByRegion("Southeast Asia", null), "11.All Capital Cities in A Region Organized by Population (Southeast Asia)");


        } catch (Exception e) {
            System.err.println("Startup error: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }
}
