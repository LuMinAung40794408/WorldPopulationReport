package com.group12.report.data_access;

import com.group12.report.models.Country;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 40794374 Thu Ta Minn Lu
 *
 * Data Access Object (DAO) for country reports.
 * Handles all SQL queries related to countries.
 */
public class CountryDAO {

    private static final Logger LOGGER = Logger.getLogger(CountryDAO.class.getName());

    /** Holds a live JDBC connection used for all queries in this DAO. */
    private final Connection con;

    /** Dependency-inject the connection so the caller manages lifecycle. */
    public CountryDAO(Connection con) { this.con = con; }

    // ========================= MAIN REPORT METHODS =========================

    /**
     * All countries in the world ordered by population (largest to smallest).
     *
     * @param limit Optional limit (null or <= 0 means no limit).
     */
    public List<Country> getAllCountriesByPopulation(Integer limit) {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   c.Capital AS CapitalId, cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            ORDER BY c.Population DESC
        """;
        // Base query: all countries with their capital name, ordered by population.

        if (limit != null && limit > 0) {
            sql += " LIMIT ?";
        }
        // Append LIMIT only when a positive limit is provided.

        return fetchCountries(sql, limit);
        // Delegate to shared logic that binds optional limit and maps rows -> Country objects.
    }

    /**
     * All countries in a given continent ordered by population.
     *
     * @param continent Continent name (e.g., "Asia").
     * @param limit Optional limit (null or <= 0 means no limit).
     */
    public List<Country> getCountriesByContinent(String continent, Integer limit) {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   c.Capital AS CapitalId, cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            WHERE c.Continent = ?
            ORDER BY c.Population DESC
        """;
        // Filter by continent using a bind parameter to avoid SQL injection.

        return fetchCountries(sql, limit, continent);
        // Bind the continent and (optionally) the limit, then map into Country objects.
    }

    /**
     * All countries in a given region ordered by population.
     *
     * @param region Region name (e.g., "Southeast Asia").
     * @param limit Optional limit (null or <= 0 means no limit).
     */
    public List<Country> getCountriesByRegion(String region, Integer limit) {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   c.Capital AS CapitalId, cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            WHERE c.Region = ?
            ORDER BY c.Population DESC
        """;
        // Filter by region in the WHERE clause.

        return fetchCountries(sql, limit, region);
        // Bind the region and (optional) limit, then map results.
    }

    // ======================== TOP 10 COUNTRY REPORTS ========================

    /**
     * Top 10 populated countries in the world.
     */
    public List<Country> getTop10CountriesInWorld() {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   c.Capital AS CapitalId, cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            ORDER BY c.Population DESC
            LIMIT 10
        """;

        return executeFixedCountryQuery(sql);
    }

    /**
     * Top 10 populated countries in a continent (hard-coded 'Europe').
     */
    public List<Country> getTop10CountriesInEurope() {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   c.Capital AS CapitalId, cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            WHERE c.Continent = 'Europe'
            ORDER BY c.Population DESC
            LIMIT 10
        """;

        return executeFixedCountryQuery(sql);
    }

    /**
     * Top 10 populated countries in a region (hard-coded 'Western Europe').
     */
    public List<Country> getTop10CountriesInWesternEurope() {
        String sql = """
            SELECT c.Code, c.Name, c.Continent, c.Region, c.Population,
                   c.Capital AS CapitalId, cap.Name AS CapitalName
            FROM country c
            LEFT JOIN city cap ON cap.ID = c.Capital
            WHERE c.Region = 'Western Europe'
            ORDER BY c.Population DESC
            LIMIT 10
        """;

        return executeFixedCountryQuery(sql);
    }

    // ========================= SHARED HELPER METHODS =========================

    private List<Country> fetchCountries(String sql, Integer limit, String... params) {
        List<Country> out = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setString(i + 1, params[i]);
            }

            if (limit != null && limit > 0) {
                ps.setInt(params.length + 1, limit);
            }

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

            LOGGER.log(Level.SEVERE, "Failed to get country report", e);
        }

        return out;
    }

    private List<Country> executeFixedCountryQuery(String sql) {
        List<Country> out = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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

        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, "Failed to execute fixed country query", e);
        }

        return out;
    }
}