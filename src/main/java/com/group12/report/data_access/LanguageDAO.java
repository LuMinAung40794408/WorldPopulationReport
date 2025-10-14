package com.group12.report.data_access;

import com.group12.report.models.Language;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class responsible for retrieving
 * language-related information from the database.
 *
 * This class interacts with the `countrylanguage` and `country` tables
 * to generate reports on languages and their total speakers across the world.
 */
public class LanguageDAO {

    // Database connection instance shared with this DAO.
    private final Connection con;

    /**
     * Constructor to initialize LanguageDAO with a database connection.
     *
     * @param con The active SQL Connection object.
     */
    public LanguageDAO(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves a list of languages (provided by the user) along with:
     * - Total number of speakers worldwide.
     * - Percentage of world population speaking each language.
     *
     * The method performs aggregation using population data
     * from the `country` table and the `Percentage` column from `countrylanguage`.
     *
     * @param languages A list of language names to include in the report.
     * @return A list of Language model objects containing the query results.
     */
    public List<Language> getLanguagesBySpeakerCount(List<String> languages) {
        // Dynamically create placeholders (e.g., "?, ?, ?") for the SQL IN clause.
        String placeholders = String.join(",", languages.stream().map(l -> "?").toList());

        // SQL query:
        // - Joins `countrylanguage` (cl) with `country` (c) to access population data.
        // - Calculates total number of speakers for each language:
        //   SUM(c.Population * cl.Percentage / 100)
        // - Calculates what percent of the world population speaks that language.
        // - Orders results by descending number of speakers.
        String sql = """
            SELECT cl.Language,
                ROUND(SUM(c.Population * cl.Percentage / 100)) AS Speakers,
                ROUND(100 * SUM(c.Population * cl.Percentage / 100) / (
                    SELECT SUM(Population) FROM country
                ), 2) AS PercentOfWorld
            FROM countrylanguage cl
            JOIN country c ON c.Code = cl.CountryCode
            WHERE cl.Language IN (%s)
            GROUP BY cl.Language
            ORDER BY Speakers DESC
        """.formatted(placeholders);

        // List to hold the final output objects.
        List<Language> out = new ArrayList<>();

        // Execute the query safely using PreparedStatement.
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Bind each language name to its placeholder in the SQL query.
            for (int i = 0; i < languages.size(); i++) {
                ps.setString(i + 1, languages.get(i));
            }

            // Execute query and process the result set.
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Create a new Language object for each record and add it to the list.
                    out.add(new Language(
                            rs.getString("Language"),     // Language name
                            rs.getLong("Speakers"),       // Total speakers (rounded)
                            rs.getDouble("PercentOfWorld")// % of world population
                    ));
                }
            }
        } catch (SQLException e) {
            // If a database error occurs, print a descriptive message.
            System.err.println("Failed to get language report: " + e.getMessage());
        }

        // Return the final list of Language objects (may be empty if query fails).
        return out;
    }
}
