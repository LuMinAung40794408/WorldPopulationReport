package com.group12.report.data_access;

import com.group12.report.models.Capital;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for retrieving capital city reports
 * from the world database. This class handles SQL queries related to
 * capital cities, including fetching by population, continent, and region.
 *
 * <p>All methods in this class return a list of {@link Capital} objects,
 * ordered by population in descending order.</p>
 *
 */
public class CapitalDAO {

    /** Database connection used to execute SQL queries */
    private final Connection con;

    /**
     * Constructs a CapitalDAO with an active database connection.
     *
     * @param con Active SQL connection to be used for queries.
     */
    public CapitalDAO(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves all capital cities in the world ordered by population (largest to smallest).
     *
     * @param limit Optional limit for number of records to return (null or <= 0 means no limit).
     * @return A list of {@link Capital} objects sorted by population descending.
     */
    public List<Capital> getAllCapitalsByPopulation(Integer limit) {
        String sql = """
            SELECT ci.Name AS Name, co.Name AS Country, ci.Population
            FROM country co
            JOIN city ci ON ci.ID = co.Capital
            ORDER BY ci.Population DESC
        """;

        // Append limit to SQL if valid
        if (limit != null && limit > 0) sql += " LIMIT ?";

        return fetchCapitals(sql, limit);
    }

    /**
     * Retrieves all capital cities in a specific continent, ordered by population.
     *
     * @param continent The continent to filter by.
     * @param limit Optional limit for number of records to return (null or <= 0 means no limit).
     * @return A list of {@link Capital} objects sorted by population descending.
     */
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

    /**
     * Retrieves all capital cities in a specific region, ordered by population.
     *
     * @param region The region to filter by.
     * @param limit Optional limit for number of records to return (null or <= 0 means no limit).
     * @return A list of {@link Capital} objects sorted by population descending.
     */
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

    /**
     * Executes the given SQL query and maps the result set into a list of {@link Capital} objects.
     *
     * @param sql   The SQL query string to execute.
     * @param limit Optional limit value to bind in the prepared statement.
     * @param param Optional parameters (e.g., continent or region names) to bind to the query.
     * @return A list of populated {@link Capital} objects.
     */
    private List<Capital> fetchCapitals(String sql, Integer limit, String... param) {
        List<Capital> out = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            // Bind all string parameters (continent/region) dynamically
            for (int i = 0; i < param.length; i++) {
                ps.setString(i + 1, param[i]);
            }

            // Bind limit parameter if applicable
            if (limit != null && limit > 0) {
                ps.setInt(param.length + 1, limit);
            }

            // Execute query and iterate through result set
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Map each row into a Capital model object
                    out.add(new Capital(
                            rs.getString("Name"),      // Capital city name
                            rs.getString("Country"),   // Associated country
                            rs.getLong("Population")   // Population count
                    ));
                }
            }

        } catch (SQLException e) {
            // Log error to console for debugging
            System.err.println("Failed to get capital report: " + e.getMessage());
        }

        return out;
    }
}
