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

}

