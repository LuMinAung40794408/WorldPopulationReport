package com.group12.report.data_access;

import com.group12.report.models.Capital;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CapitalDAO {
    private final Connection con;

    public CapitalDAO(Connection con) {
        this.con = con;
    }

    public List<Capital> getAllCapitals() {
        String sql = "SELECT city.Name, country.Name AS Country, city.Population " +
                "FROM city JOIN country ON city.ID = country.Capital " +
                "ORDER BY city.Population DESC;";
        List<Capital> capitals = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                capitals.add(new Capital(
                        rs.getString("Name"),
                        rs.getString("Country"),
                        rs.getInt("Population")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching capitals: " + e.getMessage());
        }
        return capitals;
    }
}
