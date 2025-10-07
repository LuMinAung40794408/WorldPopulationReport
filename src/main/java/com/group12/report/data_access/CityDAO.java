package com.group12.report.data_access;

import com.group12.report.models.City;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {
    private final Connection con;

    public CityDAO(Connection con) {
        this.con = con;
    }

    // 1. All cities in the world (largest → smallest)
    public List<City> getAllCities() {
        String sql = "SELECT city.ID, city.Name, country.Name AS Country, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "ORDER BY city.Population DESC;";
        return executeCityQuery(sql);
    }

    // 2. All cities in a country (largest → smallest)
    public List<City> getCitiesByCountry(String country) {
        String sql = "SELECT city.ID, city.Name, country.Name AS Country, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = ? ORDER BY city.Population DESC;";
        return executeCityQuery(sql, country);
    }

    private List<City> executeCityQuery(String sql) {
        List<City> cities = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cities.add(new City(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Country"),
                        rs.getString("District"),
                        rs.getInt("Population")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error executing city query: " + e.getMessage());
        }
        return cities;
    }

    private List<City> executeCityQuery(String sql, String param) {
        List<City> cities = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, param);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cities.add(new City(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Country"),
                        rs.getString("District"),
                        rs.getInt("Population")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error executing city query: " + e.getMessage());
        }
        return cities;
    }
}
