package com.group12.report.data_access;

import com.group12.report.models.Population;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for handling all population-related queries.
 * This class interacts with the database to retrieve population information
 * for various levels such as world, continent, region, country, district, and city.
 */
public class PopulationDAO {
    private final Connection con;
    public PopulationDAO(Connection con) { this.con = con; }

    /**
     * 1. Retrieves the total population of the world,
     * including city and non-city breakdown.
     * @return List containing one Population object for the world
     */
    public List<Population> getWorldPopulation() {
        String sql = """
            SELECT 'World' AS Name,
                   t.total_population AS TotalPopulation,
                   c.city_population AS CityPopulation,
                   ROUND(100 * c.city_population / t.total_population, 2) AS CityPopulationPercent,
                   (t.total_population - c.city_population) AS NonCityPopulation,
                   ROUND(100 * (t.total_population - c.city_population) / t.total_population, 2) AS NonCityPopulationPercent
            FROM (SELECT SUM(Population) AS total_population FROM country) t,
                 (SELECT SUM(Population) AS city_population FROM city) c
        """;
        return fetchBreakdownPopulation(sql);
    }

    /**
     * 2. Retrieves population details for each continent,
     * showing total, city, and non-city populations with percentages.
     * @return List of Population objects representing each continent
     */

    // 2. Population of each continent
    public List<Population> getPopulationByContinent() {
        String sql = """
            SELECT tp.Continent AS Name,
                   tp.TotalPopulation,
                   COALESCE(cp.CityPopulation, 0) AS CityPopulation,
                   ROUND(100 * COALESCE(cp.CityPopulation, 0) / tp.TotalPopulation, 2) AS CityPopulationPercent,
                   (tp.TotalPopulation - COALESCE(cp.CityPopulation, 0)) AS NonCityPopulation,
                   ROUND(100 * (tp.TotalPopulation - COALESCE(cp.CityPopulation, 0)) / tp.TotalPopulation, 2) AS NonCityPopulationPercent
            FROM (
                SELECT Continent, SUM(Population) AS TotalPopulation
                FROM country
                GROUP BY Continent
            ) tp
            LEFT JOIN (
                SELECT c.Continent, SUM(ci.Population) AS CityPopulation
                FROM country c
                JOIN city ci ON ci.CountryCode = c.Code
                GROUP BY c.Continent
            ) cp ON cp.Continent = tp.Continent
            ORDER BY tp.TotalPopulation DESC
        """;
        return fetchBreakdownPopulation(sql);
    }
    /**
     * 3. Retrieves population details for each region.
     * @return List of Population objects representing each region
     */
    public List<Population> getPopulationByRegion() {
        String sql = """
            SELECT tp.Region AS Name,
                   tp.TotalPopulation,
                   COALESCE(cp.CityPopulation, 0) AS CityPopulation,
                   ROUND(100 * COALESCE(cp.CityPopulation, 0) / tp.TotalPopulation, 2) AS CityPopulationPercent,
                   (tp.TotalPopulation - COALESCE(cp.CityPopulation, 0)) AS NonCityPopulation,
                   ROUND(100 * (tp.TotalPopulation - COALESCE(cp.CityPopulation, 0)) / tp.TotalPopulation, 2) AS NonCityPopulationPercent
            FROM (
                SELECT Region, SUM(Population) AS TotalPopulation
                FROM country
                GROUP BY Region
            ) tp
            LEFT JOIN (
                SELECT c.Region, SUM(ci.Population) AS CityPopulation
                FROM country c
                JOIN city ci ON ci.CountryCode = c.Code
                GROUP BY c.Region
            ) cp ON cp.Region = tp.Region
            ORDER BY tp.TotalPopulation DESC
        """;
        return fetchBreakdownPopulation(sql);
    }

    /**
     * 4. Retrieves population details for each country.
     * @return List of Population objects representing each country
     */
    public List<Population> getPopulationByCountry() {
        String sql = """
            SELECT c.Name AS Name,
                   c.Population AS TotalPopulation,
                   COALESCE(cp.CityPopulation, 0) AS CityPopulation,
                   ROUND(100 * COALESCE(cp.CityPopulation, 0) / c.Population, 2) AS CityPopulationPercent,
                   (c.Population - COALESCE(cp.CityPopulation, 0)) AS NonCityPopulation,
                   ROUND(100 * (c.Population - COALESCE(cp.CityPopulation, 0)) / c.Population, 2) AS NonCityPopulationPercent
            FROM country c
            LEFT JOIN (
                SELECT CountryCode, SUM(Population) AS CityPopulation
                FROM city
                GROUP BY CountryCode
            ) cp ON cp.CountryCode = c.Code
            ORDER BY c.Population DESC
        """;
        return fetchBreakdownPopulation(sql);
    }

    /**
     * 5. Retrieves population data for each district within a given country.
     * @param countryName Name of the country
     * @return List of Population objects representing districts
     */
    public List<Population> getPopulationByDistrict(String countryName) {
        String sql = """
            SELECT ci.District, SUM(ci.Population) AS Population
            FROM city ci
            JOIN country co ON co.Code = ci.CountryCode
            WHERE co.Name = ?
            GROUP BY ci.District
            ORDER BY Population DESC
        """;

        List<Population> out = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, countryName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new Population(
                            rs.getString("District"),
                            rs.getLong("Population")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get district population: " + e.getMessage());
        }
        return out;
    }

    /**
     * 6. Retrieves population information for all cities in the world.
     * Includes city name, country, district, and population.
     * @return List of Population objects representing each city
     */
    public List<Population> getPopulationByCity() {
        String sql = """
            SELECT ci.Name, co.Name AS Country, ci.District, ci.Population
            FROM city ci
            JOIN country co ON co.Code = ci.CountryCode
            ORDER BY ci.Population DESC
        """;

        List<Population> out = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Population(
                        rs.getString("Name"),
                        rs.getString("Country"),
                        rs.getString("District"),
                        rs.getLong("Population")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Failed to get city population: " + e.getMessage());
        }
        return out;
    }

    /**
     * Shared helper method used to execute SQL queries that return
     * total, city, and non-city population breakdowns.
     * @param sql SQL query string
     * @return List of Population objects
     */
    private List<Population> fetchBreakdownPopulation(String sql) {
        List<Population> out = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Population(
                        rs.getString("Name"),
                        rs.getLong("TotalPopulation"),
                        rs.getLong("CityPopulation"),
                        rs.getDouble("CityPopulationPercent"),
                        rs.getLong("NonCityPopulation"),
                        rs.getDouble("NonCityPopulationPercent")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Failed to get breakdown population: " + e.getMessage());
        }
        return out;
    }

    /**
     * 7. Retrieves city vs non-city population comparison by continent.
     * @return List of Population objects by continent
     */
    public List<Population> getCityVsNonCityByContinent() {
        String sql = """
        SELECT tp.Continent AS Name,
               tp.TotalPopulation,
               COALESCE(cp.CityPopulation, 0) AS CityPopulation,
               ROUND(100 * COALESCE(cp.CityPopulation, 0) / tp.TotalPopulation, 2) AS CityPopulationPercent,
               (tp.TotalPopulation - COALESCE(cp.CityPopulation, 0)) AS NonCityPopulation,
               ROUND(100 * (tp.TotalPopulation - COALESCE(cp.CityPopulation, 0)) / tp.TotalPopulation, 2) AS NonCityPopulationPercent
        FROM (
            SELECT Continent, SUM(Population) AS TotalPopulation
            FROM country
            GROUP BY Continent
        ) tp
        LEFT JOIN (
            SELECT c.Continent, SUM(ci.Population) AS CityPopulation
            FROM country c
            JOIN city ci ON ci.CountryCode = c.Code
            GROUP BY c.Continent
        ) cp ON cp.Continent = tp.Continent
        ORDER BY tp.TotalPopulation DESC
    """;
        return fetchBreakdownPopulation(sql);
    }

    /**
     * 8. Retrieves city vs non-city population comparison by region.
     * @return List of Population objects by region
     */
    public List<Population> getCityVsNonCityByRegion() {
        String sql = """
        SELECT tp.Region AS Name,
               tp.TotalPopulation,
               COALESCE(cp.CityPopulation, 0) AS CityPopulation,
               ROUND(100 * COALESCE(cp.CityPopulation, 0) / tp.TotalPopulation, 2) AS CityPopulationPercent,
               (tp.TotalPopulation - COALESCE(cp.CityPopulation, 0)) AS NonCityPopulation,
               ROUND(100 * (tp.TotalPopulation - COALESCE(cp.CityPopulation, 0)) / tp.TotalPopulation, 2) AS NonCityPopulationPercent
        FROM (
            SELECT Region, SUM(Population) AS TotalPopulation
            FROM country
            GROUP BY Region
        ) tp
        LEFT JOIN (
            SELECT c.Region, SUM(ci.Population) AS CityPopulation
            FROM country c
            JOIN city ci ON ci.CountryCode = c.Code
            GROUP BY c.Region
        ) cp ON cp.Region = tp.Region
        ORDER BY tp.TotalPopulation DESC
    """;
        return fetchBreakdownPopulation(sql);
    }

    /**
     * 9. Retrieves city vs non-city population comparison by country.
     * @return List of Population objects by country
     */
    public List<Population> getCityVsNonCityByCountry() {
        String sql = """
        SELECT c.Name AS Name,
               c.Population AS TotalPopulation,
               COALESCE(cp.CityPopulation, 0) AS CityPopulation,
               ROUND(100 * COALESCE(cp.CityPopulation, 0) / c.Population, 2) AS CityPopulationPercent,
               (c.Population - COALESCE(cp.CityPopulation, 0)) AS NonCityPopulation,
               ROUND(100 * (c.Population - COALESCE(cp.CityPopulation, 0)) / c.Population, 2) AS NonCityPopulationPercent
        FROM country c
        LEFT JOIN (
            SELECT CountryCode, SUM(Population) AS CityPopulation
            FROM city
            GROUP BY CountryCode
        ) cp ON cp.CountryCode = c.Code
        ORDER BY c.Population DESC
    """;
        return fetchBreakdownPopulation(sql);
    }
}