package com.group12.report.data_access;

import com.group12.report.models.Language;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 40794418yuyakoko
 * Data Access Object (DAO) class responsible for retrieving
 * language-related information from the database.
 *
 * This class interacts with the `countrylanguage` and `country` tables
 * to generate reports on languages and their total speakers across the world.
 */
public class LanguageDAO {

    private static final Logger LOGGER = Logger.getLogger(LanguageDAO.class.getName());

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
        String placeholders = String.join(",", languages.stream().map(l -> "?").toList());

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

        List<Language> out = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < languages.size(); i++) {
                ps.setString(i + 1, languages.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new Language(
                            rs.getString("Language"),
                            rs.getLong("Speakers"),
                            rs.getDouble("PercentOfWorld")
                    ));
                }
            }
        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, "Failed to get language report", e);
        }

        return out;
    }
}