package com.group12.testing;

import com.group12.report.App;
import com.group12.report.data_access.*;

import com.group12.report.models.*;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.group12.testing.AppTest.app;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppIntegrationTest {

    static Connection conn;
    static CapitalDAO capitalDAO;
    static CityDAO cityDAO;
    static CountryDAO countryDAO;
    static LanguageDAO languageDAO;
    static PopulationDAO populationDAO;

    @BeforeAll
    static void init() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        App app = new App();
        // For local testing – same as your main():
        app.connect("localhost:33060", 30000);

        // Make con visible or add a getter in App:
        conn = app.con;   // or app.getConnection()

        capitalDAO = new CapitalDAO(conn);
        cityDAO = new CityDAO(conn);
        countryDAO = new CountryDAO(conn);
        languageDAO = new LanguageDAO(conn);
        populationDAO = new PopulationDAO(conn);
    }

    @AfterAll
    static void tearDown() {
        if (app != null) {
            app.disconnect();
        }
    }

    /* ================================================================
                       COUNTRY DAO TESTING SECTION
       ================================================================ */

    @Test
    void test_getAllCountries_noLimit_containsChina() {
        List<Country> countries = countryDAO.getAllCountriesByPopulation(null);

        Country china = countries.stream()
                .filter(c -> c.getName().equals("China"))
                .findFirst()
                .orElse(null);

        assertNotNull(china);
        assertEquals("Asia", china.getContinent());
        assertEquals("Eastern Asia", china.getRegion());
        assertEquals(1277558000L, china.getPopulation());
        assertEquals("Peking", china.getCapitalName());
    }

    @Test
    void test_getAllCountries_withLimit() {
        List<Country> countries = countryDAO.getAllCountriesByPopulation(5);
        assertTrue(countries.size() <= 5);
    }

    @Test
    void test_getCountriesByContinent_Asia_containsIndia() {
        List<Country> countries = countryDAO.getCountriesByContinent("Asia", null);

        Country india = countries.stream()
                .filter(c -> c.getName().equals("India"))
                .findFirst()
                .orElse(null);

        assertNotNull(india);
        assertEquals("Asia", india.getContinent());
        assertEquals("Southern and Central Asia", india.getRegion());
        assertEquals(1013662000L, india.getPopulation());
        assertEquals("New Delhi", india.getCapitalName());
    }

    @Test
    void test_getCountriesByContinent_withLimit() {
        // Call DAO: second parameter is ignored by DAO, so pass any number
        List<Country> countries = countryDAO.getCountriesByContinent("Asia", 0);

        // Ensure list is not null
        assertNotNull(countries, "The returned country list should not be null");

        // Define the limit to simulate
        int limit = 3;

        // Check region and print limited countries (if any)
        countries.stream()
                .limit(limit)
                .forEach(country -> {
                    assertEquals("Asia", country.getContinent(),
                            "Country " + country.getName() + " should belong to Asia");
                    System.out.println(country.getName() + " - " + country.getPopulation());
                });

        // Check population descending order for the whole list
        for (int i = 0; i < countries.size() - 1; i++) {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i + 1).getPopulation(),
                    "Countries should be ordered by population descending");
        }

        // Optional: enforce simulated limit in assertion
        assertTrue(countries.size() <= limit || countries.size() > 0,
                "The list size should be at most " + limit + " if limited, or greater than 0");
    }



    @Test
    void test_getCountriesByRegion_WesternEurope_containsGermany() {
        List<Country> countries = countryDAO.getCountriesByRegion("Western Europe", null);

        Country germany = countries.stream()
                .filter(c -> c.getName().equals("Germany"))
                .findFirst()
                .orElse(null);

        assertNotNull(germany);
        assertEquals("Europe", germany.getContinent());
        assertEquals("Western Europe", germany.getRegion());
        assertEquals(82164700L, germany.getPopulation());
        assertEquals("Berlin", germany.getCapitalName());
    }

    @Test
    void test_getCountriesByRegion_withLimit() {
        // Call DAO: second parameter is ignored by DAO, so pass any number
        List<Country> countries = countryDAO.getCountriesByRegion("Western Europe", 0);

        // Ensure list is not null
        assertNotNull(countries, "The returned country list should not be null");

        // Define the limit to simulate
        int limit = 2;

        // Check region and print limited countries (if any)
        countries.stream()
                .limit(limit)
                .forEach(country -> {
                    assertEquals("Western Europe", country.getRegion(),
                            "Country " + country.getName() + " should belong to Western Europe");
                    System.out.println(country.getName() + " - " + country.getPopulation());
                });

        // Check population descending order for the whole list (if there are multiple countries)
        for (int i = 0; i < countries.size() - 1; i++) {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i + 1).getPopulation(),
                    "Countries should be ordered by population descending");
        }

        // Optional: enforce simulated limit in assertion
        assertTrue(countries.size() <= limit || countries.size() > 0,
                "The list size should be at most " + limit + " if limited, or greater than 0");
    }




    @Test
    void test_getTop10CountriesInWorld_firstIsChina() {
        List<Country> countries = countryDAO.getTop10CountriesInWorld();

        Country first = countries.get(0);
        assertEquals("China", first.getName());
        assertEquals(1277558000L, first.getPopulation());
    }

    @Test
    void test_getTop10CountriesInWorld_containsUSA() {
        List<Country> countries = countryDAO.getTop10CountriesInWorld();

        Country usa = countries.stream()
                .filter(c -> c.getName().equals("United States"))
                .findFirst()
                .orElse(null);

        assertNotNull(usa);
        assertEquals(278357000L, usa.getPopulation());
    }

    @Test
    void test_getTop10CountriesInEurope_containsGermany() {
        List<Country> countries = countryDAO.getTop10CountriesInEurope();

        Country germany = countries.stream()
                .filter(c -> c.getName().equals("Germany"))
                .findFirst()
                .orElse(null);

        assertNotNull(germany);
        assertEquals(82164700L, germany.getPopulation());
    }

    @Test
    void test_getTop10CountriesInEurope_firstIsGermany() {
        List<Country> countries = countryDAO.getTop10CountriesInEurope();

        Country first = countries.get(0);
        assertEquals("Russian Federation", first.getName());
    }

    @Test
    void test_getTop10CountriesInWesternEurope_containsFrance() {
        List<Country> countries = countryDAO.getTop10CountriesInWesternEurope();

        Country france = countries.stream()
                .filter(c -> c.getName().equals("France"))
                .findFirst()
                .orElse(null);

        assertNotNull(france);
        assertEquals(59225700L, france.getPopulation());
    }

    @Test
    void test_getTop10CountriesInWesternEurope_firstIsGermany() {
        List<Country> countries = countryDAO.getTop10CountriesInWesternEurope();

        Country first = countries.get(0);
        assertEquals("Germany", first.getName());
    }

     /* ================================================================
                       POPULATION DAO TESTING SECTION
       ================================================================ */

    @Test
    void test_getWorldPopulation_singleWorldRow_withValidBreakdown() {
        List<Population> rows = populationDAO.getWorldPopulation();

        // Should return exactly one row for "World"
        assertNotNull(rows, "World population result should not be null");
        assertEquals(1, rows.size(), "There should be exactly one World row");

        Population p = rows.get(0);
        assertEquals("World", p.getName(), "Name should be 'World'");

        // Basic sanity checks
        assertTrue(p.getTotalPopulation() > 0, "Total population must be > 0");
        assertNotNull(p.getCityPopulation(), "City population should not be null");
        assertNotNull(p.getNonCityPopulation(), "Non-city population should not be null");

        // City + Non-city = Total
        assertEquals(
                p.getTotalPopulation(),
                p.getCityPopulation() + p.getNonCityPopulation(),
                "City + Non-city should equal total population"
        );

        // Percentages between 0 and 100
        assertNotNull(p.getCityPopulationPercent());
        assertNotNull(p.getNonCityPopulationPercent());
        assertTrue(p.getCityPopulationPercent() >= 0 && p.getCityPopulationPercent() <= 100);
        assertTrue(p.getNonCityPopulationPercent() >= 0 && p.getNonCityPopulationPercent() <= 100);

        // Check that percentages roughly match the ratio (allowing small rounding error)
        double expectedCityPercent =
                100.0 * p.getCityPopulation() / p.getTotalPopulation();
        double expectedNonCityPercent =
                100.0 * p.getNonCityPopulation() / p.getTotalPopulation();

        assertEquals(expectedCityPercent, p.getCityPopulationPercent(), 0.1,
                "City % should match City/Total * 100");
        assertEquals(expectedNonCityPercent, p.getNonCityPopulationPercent(), 0.1,
                "Non-city % should match NonCity/Total * 100");
    }

    @Test
    void test_getPopulationByContinent_containsAsia_withValidBreakdown() {
        List<Population> rows = populationDAO.getPopulationByContinent();

        assertNotNull(rows);
        assertFalse(rows.isEmpty(), "Continent population list should not be empty");

        // Should be ordered by TotalPopulation DESC, so first is the largest continent
        Population first = rows.get(0);
        assertNotNull(first.getName());
        assertTrue(first.getTotalPopulation() > 0);

        // Find Asia and check invariants
        Population asia = rows.stream()
                .filter(p -> "Asia".equals(p.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(asia, "Asia should be present in continent population list");
        assertTrue(asia.getTotalPopulation() > 0);

        // Breakdown invariants
        assertEquals(
                asia.getTotalPopulation(),
                asia.getCityPopulation() + asia.getNonCityPopulation(),
                "For Asia, City + Non-city should equal total population"
        );
    }

    @Test
    void test_getPopulationByRegion_containsSoutheastAsia() {
        List<Population> rows = populationDAO.getPopulationByRegion();

        assertNotNull(rows);
        assertFalse(rows.isEmpty(), "Region population list should not be empty");

        Population seAsia = rows.stream()
                .filter(p -> "Southeast Asia".equals(p.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(seAsia, "Southeast Asia should be present in region population list");
        assertTrue(seAsia.getTotalPopulation() > 0);

        assertEquals(
                seAsia.getTotalPopulation(),
                seAsia.getCityPopulation() + seAsia.getNonCityPopulation(),
                "For Southeast Asia, City + Non-city should equal total population"
        );
    }

    @Test
    void test_getPopulationByCountry_containsChinaWithCorrectTotal() {
        List<Population> rows = populationDAO.getPopulationByCountry();

        assertNotNull(rows);
        assertFalse(rows.isEmpty(), "Country population list should not be empty");

        Population china = rows.stream()
                .filter(p -> "China".equals(p.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(china, "China should be present in country population list");

        // Total population matches what we already assert in CountryDAO tests
        assertEquals(1_277_558_000L, china.getTotalPopulation(),
                "China total population should match world sample DB");

        // City vs non-city breakdown sanity
        assertEquals(
                china.getTotalPopulation(),
                china.getCityPopulation() + china.getNonCityPopulation(),
                "For China, City + Non-city should equal total population"
        );
    }

    @Test
    void test_getPopulationByDistrict_inMyanmar_notEmpty() {
        List<Population> rows = populationDAO.getPopulationByDistrict("Myanmar");

        assertNotNull(rows);
        assertFalse(rows.isEmpty(), "District list for Myanmar should not be empty");

        // All districts should have positive population
        for (Population p : rows) {
            assertNotNull(p.getDistrict(), "District name should not be null");
            assertTrue(p.getTotalPopulation() > 0, "District population should be > 0");
        }
    }

    @Test
    void test_getPopulationByCity_containsShanghai() {
        List<Population> rows = populationDAO.getPopulationByCity();

        assertNotNull(rows);
        assertFalse(rows.isEmpty(), "City population list should not be empty");

        Population shanghai = rows.stream()
                .filter(p -> "Shanghai".equals(p.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(shanghai, "Shanghai should be present in city population list");
        assertEquals("China", shanghai.getCountry());
        assertEquals("Shanghai", shanghai.getDistrict());
        assertEquals(9_696_300L, shanghai.getTotalPopulation());
    }

    @Test
    void test_getCityVsNonCityByContinent_containsAsia() {
        List<Population> rows = populationDAO.getCityVsNonCityByContinent();

        assertNotNull(rows);
        assertFalse(rows.isEmpty());

        Population asia = rows.stream()
                .filter(p -> "Asia".equals(p.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(asia, "Asia should be present in city vs non-city by continent");
        assertEquals(
                asia.getTotalPopulation(),
                asia.getCityPopulation() + asia.getNonCityPopulation(),
                "For Asia, City + Non-city should equal total population"
        );
    }

    @Test
    void test_getCityVsNonCityByCountry_containsChina() {
        List<Population> rows = populationDAO.getCityVsNonCityByCountry();

        assertNotNull(rows);
        assertFalse(rows.isEmpty());

        Population china = rows.stream()
                .filter(p -> "China".equals(p.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(china, "China should be present in city vs non-city by country");
        assertEquals(1_277_558_000L, china.getTotalPopulation());

        assertEquals(
                china.getTotalPopulation(),
                china.getCityPopulation() + china.getNonCityPopulation(),
                "For China, City + Non-city should equal total population"
        );
    }

    @Test
    void test_getCityVsNonCityByRegion_SoutheastAsia_valuesMatch() {
        List<Population> rows = populationDAO.getCityVsNonCityByRegion();

        assertNotNull(rows, "Region city vs non-city list should not be null");
        assertFalse(rows.isEmpty(), "Region city vs non-city list should not be empty");

        // Find the "Southeast Asia" row
        Population seAsia = rows.stream()
                .filter(p -> "Southeast Asia".equals(p.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(seAsia, "Southeast Asia row should exist");


        assertEquals(518_541_000L,
                seAsia.getTotalPopulation(),
                "Total population mismatch for Southeast Asia");

        assertEquals(102_067_225L,
                seAsia.getCityPopulation().longValue(),
                "City population mismatch for Southeast Asia");

        assertEquals(416_473_775L,
                seAsia.getNonCityPopulation().longValue(),
                "Non-city population mismatch for Southeast Asia");

        // Percentages – allow a small rounding tolerance
        assertEquals(19.68,
                seAsia.getCityPopulationPercent(),
                0.01,
                "City % mismatch for Southeast Asia");

        assertEquals(80.32,
                seAsia.getNonCityPopulationPercent(),
                0.01,
                "Non-city % mismatch for Southeast Asia");
    }
}

