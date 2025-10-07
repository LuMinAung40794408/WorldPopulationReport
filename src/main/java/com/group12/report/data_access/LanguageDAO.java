package com.group12.report.data_access;

import com.group12.report.models.Language;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAO {
    private final Connection con;

    public LanguageDAO(Connection con) {
        this.con = con;
    }

    public List<Language> getTopLanguages() {
        String sql = """
            SELECT cl.Language,
                   SUM(cl.Percentage * c.Population / 100) AS Speakers
            FROM countrylanguage cl
            JOIN country c ON cl.CountryCode = c.Code
            WHERE cl.Language IN ('Chinese','English','Hindi','Spanish','Arabic')
            GROUP BY cl.Language
            ORDER BY Speakers DESC;
            """;

        List<Language> languages = new ArrayList<>();
        long worldPopulation = getWorldPopulation();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String lang = rs.getString("Language");
                long speakers = rs.getLong("Speakers");
                double percent = (speakers * 100.0) / worldPopulation;
                languages.add(new Language(lang, speakers, percent));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching language data: " + e.getMessage());
        }
        return languages;
    }

    private long getWorldPopulation() {
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUM(Population) AS WorldPop FROM country;")) {
            if (rs.next()) return rs.getLong("WorldPop");
        } catch (SQLException e) {
            System.out.println("Error fetching world population: " + e.getMessage());
        }
        return 0;
    }
}
