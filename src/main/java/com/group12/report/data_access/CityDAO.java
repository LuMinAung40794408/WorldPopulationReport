package com.group12.report.data_access;

import com.group12.report.models.City;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {
    private final Connection con;
    // Holds a live JDBC connection used for all queries in this DAO.

    public CityDAO(Connection con) { this.con = con; }
    // Dependency-inject the connection so the caller controls lifecycle (open/close, pooling, etc.).

    public List<City> getAllCitiesByPopulation(Integer limit) {
        String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON co.Code = ci.CountryCode
            ORDER BY ci.Population DESC
        """;
        // Base query: all cities joined to their country, ordered by population (largest first).

        if (limit != null && limit > 0) sql += " LIMIT ?";
        // Append LIMIT only when a positive limit is provided (keeps SQL valid and parameterized).

        return fetchCities(sql, limit);
        // Delegate to shared fetch logic that binds parameters and maps rows -> City objects.
    }

    public List<City> getCitiesByContinent(String continent, Integer limit) {
        String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON co.Code = ci.CountryCode
            WHERE co.Continent = ?
            ORDER BY ci.Population DESC
        """;
        // Filter by continent using a positional parameter to avoid SQL injection.

        return fetchCities(sql, limit, continent);
        // Pass the filter value so fetchCities can bind it, then (optionally) the LIMIT.
    }

    public List<City> getCitiesByRegion(String region, Integer limit) {
        String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON co.Code = ci.CountryCode
            WHERE co.Region = ?
            ORDER BY ci.Population DESC
        """;
        // Region-level filter; same projection/order as other queries for consistency.

        return fetchCities(sql, limit, region);
        // Reuse the same mapping/binding routine.
    }

    public List<City> getCitiesByDistrict(String district, Integer limit) {
        String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON co.Code = ci.CountryCode
            WHERE ci.District = ?
            ORDER BY ci.Population DESC
        """;
        // District filter applied on the city table.

        return fetchCities(sql, limit, district);
        // Bind the district value and (optional) limit.
    }

    public List<City> getCitiesByCountry(String country, Integer limit) {
        String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON co.Code = ci.CountryCode
            WHERE co.Name = ?
            ORDER BY ci.Population DESC
        """;
        // Country filter by country name (exact match as stored in the DB).

        return fetchCities(sql, limit, country);
        // Execute with parameter binding and return mapped results.
    }

    // Shared logic
    private List<City> fetchCities(String sql, Integer limit, String... param) {
        List<City> out = new ArrayList<>();
        // Prepare an output list; keeps method null-safe for callers.

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Create a PreparedStatement to safely bind parameters.

            for (int i = 0; i < param.length; i++) {
                ps.setString(i + 1, param[i]);
            }
            // Bind all string parameters in order (continent/region/district/country if present).

            if (limit != null && limit > 0) ps.setInt(param.length + 1, limit);
            // If LIMIT is active, bind it after the other parameters (correct 1-based index).

            try (ResultSet rs = ps.executeQuery()) {
                // Execute the query and iterate over the result rows.

                while (rs.next()) {
                    out.add(new City(
                            rs.getString("Name"),
                            rs.getString("Country"),
                            rs.getString("District"),
                            rs.getLong("Population")
                    ));
                    // Map each row to a City model using column labels (matches SELECT aliases).
                }
            }
            // ResultSet closed automatically by try-with-resources.
        } catch (SQLException e) {
            System.err.println("Failed to get city report: " + e.getMessage());
            // Log a concise error; caller gets an empty list rather than a thrown exception.
        }

        return out;
        // Return the collected results (possibly empty on error or no matches).
    }
}
