package com.group12.report.data_access;

import com.group12.report.models.City;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 40794374PhoneMyatKyaw
 */
public class CityDAO {

    private static final Logger LOGGER = Logger.getLogger(CityDAO.class.getName());

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

    // ======================== TOP 10 CITY REPORTS (WITH SQL) ========================

    /**
     * Top 10 cities in the world.
     */
    public List<City> getTop10CitiesInWorld() {
        String sql = """
        SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
        FROM city ci
        JOIN country co ON co.Code = ci.CountryCode
        ORDER BY ci.Population DESC
        LIMIT 10;
    """;

        return executeFixedCityQuery(sql);
    }

    /**
     * Top 10 cities in a continent (hard-coded: Asia)
     */
    public List<City> getTop10CitiesInContinent() {
        String sql = """
        SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
        FROM city ci
        JOIN country co ON co.Code = ci.CountryCode
        WHERE co.Continent = 'Asia'
        ORDER BY ci.Population DESC
        LIMIT 10;
    """;

        return executeFixedCityQuery(sql);
    }

    /**
     * Top 10 cities in a region (hard-coded: Southeast Asia)
     */
    public List<City> getTop10CitiesInRegion() {
        String sql = """
        SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
        FROM city ci
        JOIN country co ON co.Code = ci.CountryCode
        WHERE co.Region = 'Southeast Asia'
        ORDER BY ci.Population DESC
        LIMIT 10;
    """;

        return executeFixedCityQuery(sql);
    }

    /**
     * Top 10 cities in a country (hard-coded: Myanmar)
     */
    public List<City> getTop10CitiesInCountry() {
        String sql = """
        SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
        FROM city ci
        JOIN country co ON co.Code = ci.CountryCode
        WHERE co.Name = 'Myanmar'
        ORDER BY ci.Population DESC
        LIMIT 10;
    """;

        return executeFixedCityQuery(sql);
    }

    /**
     * Top 10 cities in a district (hard-coded: California)
     */
    public List<City> getTop10CitiesInDistrict() {
        String sql = """
        SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
        FROM city ci
        JOIN country co ON co.Code = ci.CountryCode
        WHERE ci.District = 'California'
        ORDER BY ci.Population DESC
        LIMIT 10;
    """;

        return executeFixedCityQuery(sql);
    }


    // Shared logic
    private List<City> fetchCities(String sql, Integer limit, String... param) {
        List<City> out = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < param.length; i++) {
                ps.setString(i + 1, param[i]);
            }

            if (limit != null && limit > 0) {
                ps.setInt(param.length + 1, limit);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new City(
                            rs.getString("Name"),
                            rs.getString("Country"),
                            rs.getString("District"),
                            rs.getLong("Population")
                    ));
                }
            }
        } catch (SQLException e) {
            // replaced System.err with logger
            LOGGER.log(Level.SEVERE, "Failed to get city report", e);
        }

        return out;
    }

    private List<City> executeFixedCityQuery(String sql) {
        List<City> out = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(new City(
                        rs.getString("Name"),
                        rs.getString("Country"),
                        rs.getString("District"),
                        rs.getLong("Population")
                ));
            }

        } catch (SQLException e) {
            // replaced System.err with logger
            LOGGER.log(Level.SEVERE, "Failed to execute Top10 city query", e);
        }

        return out;
    }
}