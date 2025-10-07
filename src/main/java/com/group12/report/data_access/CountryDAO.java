package com.group12.report.data_access;

import com.group12.report.models.Country;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {
    private final Connection con;

    public CountryDAO(Connection con) {
        this.con = con;
    }

    // 1. All countries in the world (largest → smallest)
    public List<Country> getAllCountriesByPopulation() {
        String sql = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country ORDER BY Population DESC;";
        return executeCountryQuery(sql);
    }

    // 2. All countries in a continent (largest → smallest)
    public List<Country> getCountriesByContinent(String continent) {
        String sql = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country WHERE Continent = ? ORDER BY Population DESC;";
        return executeCountryQuery(sql, continent);
    }

    // 3. All countries in a region (largest → smallest)
    public List<Country> getCountriesByRegion(String region) {
        String sql = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country WHERE Region = ? ORDER BY Population DESC;";
        return executeCountryQuery(sql, region);
    }

    // 4. Top N populated countries in the world
    public List<Country> getTopNCountries(int n) {
        String sql = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country ORDER BY Population DESC LIMIT ?;";
        return executeCountryQuery(sql, n);
    }

    // Generalized private method (no parameter)
    private List<Country> executeCountryQuery(String sql) {
        List<Country> countries = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Country c = new Country(
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getInt("Population"),
                        rs.getString("Capital")
                );
                countries.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
        return countries;
    }

    // Generalized private method (single string parameter)
    private List<Country> executeCountryQuery(String sql, String param) {
        List<Country> countries = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, param);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                countries.add(new Country(
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getInt("Population"),
                        rs.getString("Capital")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
        return countries;
    }

    // Generalized private method (integer parameter)
    private List<Country> executeCountryQuery(String sql, int limit) {
        List<Country> countries = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                countries.add(new Country(
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getInt("Population"),
                        rs.getString("Capital")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
        return countries;
    }
}
