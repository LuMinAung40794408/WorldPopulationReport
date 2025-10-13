package com.group12.report.data_access;

import com.group12.report.models.Capital;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CapitalDAO {
    private final Connection con;
    public CapitalDAO(Connection con) { this.con = con; }

    public List<Capital> getAllCapitalsByPopulation(Integer limit) {
        String sql = """
            SELECT ci.Name AS Name, co.Name AS Country, ci.Population
            FROM country co
            JOIN city ci ON ci.ID = co.Capital
            ORDER BY ci.Population DESC
        """;
        if (limit != null && limit > 0) sql += " LIMIT ?";

        return fetchCapitals(sql, limit);
    }

    public List<Capital> getCapitalsByContinent(String continent, Integer limit) {
        String sql = """
            SELECT ci.Name AS Name, co.Name AS Country, ci.Population
            FROM country co
            JOIN city ci ON ci.ID = co.Capital
            WHERE co.Continent = ?
            ORDER BY ci.Population DESC
        """;
        return fetchCapitals(sql, limit, continent);
    }

    public List<Capital> getCapitalsByRegion(String region, Integer limit) {
        String sql = """
            SELECT ci.Name AS Name, co.Name AS Country, ci.Population
            FROM country co
            JOIN city ci ON ci.ID = co.Capital
            WHERE co.Region = ?
            ORDER BY ci.Population DESC
        """;
        return fetchCapitals(sql, limit, region);
    }

    private List<Capital> fetchCapitals(String sql, Integer limit, String... param) {
        List<Capital> out = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < param.length; i++) {
                ps.setString(i + 1, param[i]);
            }
            if (limit != null && limit > 0) ps.setInt(param.length + 1, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new Capital(
                            rs.getString("Name"),
                            rs.getString("Country"),
                            rs.getLong("Population")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get capital report: " + e.getMessage());
        }
        return out;
    }
}