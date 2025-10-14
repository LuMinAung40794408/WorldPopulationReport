package com.group12.report.data_access;

import com.group12.report.models.Country; // Imports the Country model class
import java.sql.*; // Import SQL classes for database access
import java.util.ArrayList; // Imports ArrayList for storing query results
import java.util.List; // Imports List interface

/**
 * @author 40794374 Thu Ta Minn Lu
 */

// This class handles everything related to fetching country data from the database.
public class CountryDAO {
    private final Connection con; // This is the database connection (passed in when this class is created)

    // Constructor that we give it a database connection
    public CountryDAO(Connection con) { this.con = con; }

    // Get all countries, sorted by population
    public List<Country> getAllCountriesByPopulation(Integer limit) {
        // This SQL grabs all the info we need from the 'country' table,
        // and also joins the 'city' table to get the capital name.
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   c.Capital AS CapitalId, cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            ORDER BY c.Population DESC
        """;

        // If a limit is provided, add it to the query
        if (limit != null && limit > 0) sql += " LIMIT ?";

        // We'll store all the countries we find here
        List<Country> out = new ArrayList<>();

        // Try to run the SQL safely
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // If there's a limit, plug it into the query
            if (limit != null && limit > 0) ps.setInt(1, limit);

            // this run the query
            try (ResultSet rs = ps.executeQuery()) {

                // Loop through each row
                while (rs.next()) {

                    // Turn that row into a Country object
                    out.add(new Country(
                            rs.getString("Code"),
                            rs.getString("Name"),
                            rs.getString("Continent"),
                            rs.getString("Region"),
                            rs.getLong("Population"),
                            (Integer) rs.getObject("CapitalId"), // handles NULL
                            rs.getString("CapitalName")
                    ));
                }
            }
        } catch (SQLException e) {
            // If something goes wrong, print error message
            System.err.println("Failed to get countries report: " + e.getMessage());
        }
        return out;
    }

    // Get countries for a specific continent (e.g., "Asia"), sorted by population
    public List<Country> getCountriesByContinent(String continent, Integer limit) {

        // This filter by the continent name
        String sql = """
        SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
               c.Capital AS CapitalId, cap.Name AS CapitalName
        FROM country c
        LEFT JOIN city cap ON cap.ID = c.Capital
        WHERE c.Continent = ?
        ORDER BY c.Population DESC
    """;

        // Add a limit if requested
        if (limit != null && limit > 0) sql += " LIMIT ?";

        List<Country> out = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // First placeholder is for the continent
            ps.setString(1, continent);

            // Second placeholder (if any) is for the limit
            if (limit != null && limit > 0) ps.setInt(2, limit);

            // This run the query
            try (ResultSet rs = ps.executeQuery()) {

                // Loop through each result
                while (rs.next()) {
                    out.add(new Country(
                            rs.getString("Code"),
                            rs.getString("Name"),
                            rs.getString("Continent"),
                            rs.getString("Region"),
                            rs.getLong("Population"),
                            (Integer) rs.getObject("CapitalId"),
                            rs.getString("CapitalName")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get countries by continent: " + e.getMessage());
        }
        return out;
    }

    // Get countries for a specific region (e.g., "Southeast Asia"), sorted by population
    public List<Country> getCountriesByRegion(String region, Integer limit) {

        // Almost identical to the continent method, but filters by region instead
        String sql = """
        SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
               c.Capital AS CapitalId, cap.Name AS CapitalName
        FROM country c
        LEFT JOIN city cap ON cap.ID = c.Capital
        WHERE c.Region = ?
        ORDER BY c.Population DESC
    """;

        // Add a limit if one was provided
        if (limit != null && limit > 0) sql += " LIMIT ?";

        List<Country> out = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // First parameter: region
            ps.setString(1, region);

            // Second parameter: limit, if applicable
            if (limit != null && limit > 0) ps.setInt(2, limit);


            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new Country(
                            rs.getString("Code"),
                            rs.getString("Name"),
                            rs.getString("Continent"),
                            rs.getString("Region"),
                            rs.getLong("Population"),
                            (Integer) rs.getObject("CapitalId"),
                            rs.getString("CapitalName")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get countries by region: " + e.getMessage());
        }
        return out;
    }
}