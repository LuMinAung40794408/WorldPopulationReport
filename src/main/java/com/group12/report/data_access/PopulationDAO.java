package com.group12.report.data_access;

import com.group12.report.models.Population;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PopulationDAO {
    private final Connection con;

    public PopulationDAO(Connection con) {
        this.con = con;
    }

    public List<Population> getContinentPopulations() {
        String sql = """
            SELECT country.Continent AS Name,
                   SUM(country.Population) AS TotalPopulation,
                   SUM(city.Population) AS CityPopulation
            FROM country
            JOIN city ON country.Code = city.CountryCode
            GROUP BY country.Continent;
            """;
        List<Population> list = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                long total = rs.getLong("TotalPopulation");
                long city = rs.getLong("CityPopulation");
                long nonCity = total - city;
                list.add(new Population(rs.getString("Name"), total, city, nonCity));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching population data: " + e.getMessage());
        }
        return list;
    }
}
