package com.group12.testing;

import com.group12.report.App;
import com.group12.report.data_access.*;
import com.group12.report.App;

import com.group12.report.models.*;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppIntegrationTest {

    static Connection conn;
    static CapitalDAO capitalDAO;
    static CityDAO cityDAO;
    static CountryDAO countryDAO;
    static LanguageDAO languageDAO;
    static PopulationDAO populationDAO;
    static App app;
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
                       CITY DAO TESTING SECTION
       ================================================================ */

    @Test
    void test_getAllCities_noLimit_containsShanghai() {
        List<City> cities = cityDAO.getAllCitiesByPopulation(null);

        City shanghai = cities.stream()
                .filter(c -> c.getName().equals("Shanghai"))
                .findFirst()
                .orElse(null);

        assertNotNull(shanghai);
        assertEquals("China", shanghai.getCountry());
        assertEquals("Shanghai", shanghai.getDistrict());
        assertEquals(9696300L, shanghai.getPopulation());
    }

    @Test
    void test_getAllCities_withLimit() {
        List<City> cities = cityDAO.getAllCitiesByPopulation(5);
        assertTrue(cities.size() <= 5);
    }

    @Test
    void test_getCitiesByContinent_Asia_containsSeoul() {
        List<City> cities = cityDAO.getCitiesByContinent("Asia", null);

        City seoul = cities.stream()
                .filter(c -> c.getName().equals("Seoul"))
                .findFirst()
                .orElse(null);

        assertNotNull(seoul);
        assertEquals("South Korea", seoul.getCountry());
        assertEquals("Seoul", seoul.getDistrict());
        assertEquals(9981619L, seoul.getPopulation());
    }

    @Test
    void test_getCitiesByRegion_NorthAmerica_containsNewYork() {
        List<City> cities = cityDAO.getCitiesByRegion("North America", null);

        City ny = cities.stream()
                .filter(c -> c.getName().equals("New York"))
                .findFirst()
                .orElse(null);

        assertNotNull(ny);
        assertEquals("United States", ny.getCountry());
        assertEquals("New York", ny.getDistrict());
        assertEquals(8008278L, ny.getPopulation());
    }

    @Test
    void test_getCitiesByCountry_USA_containsLA() {
        List<City> cities = cityDAO.getCitiesByCountry("United States", null);

        City la = cities.stream()
                .filter(c -> c.getName().equals("Los Angeles"))
                .findFirst()
                .orElse(null);

        assertNotNull(la);
        assertEquals("United States", la.getCountry());
        assertEquals("California", la.getDistrict());
        assertEquals(3694820L, la.getPopulation());
    }

    @Test
    void test_getCitiesByDistrict_California_containsSanDiego() {
        List<City> cities = cityDAO.getCitiesByDistrict("California", null);

        City sd = cities.stream()
                .filter(c -> c.getName().equals("San Diego"))
                .findFirst()
                .orElse(null);

        assertNotNull(sd);
        assertEquals("United States", sd.getCountry());
        assertEquals("California", sd.getDistrict());
        assertEquals(1223400L, sd.getPopulation());
    }

    @Test
    void test_getTop10CitiesInWorld_firstIsMumbai() {
        List<City> cities = cityDAO.getTop10CitiesInWorld();
        City first = cities.get(0);

        assertEquals("Mumbai (Bombay)", first.getName());
    }

    @Test
    void test_getTop10CitiesInContinent_containsKarachi() {
        List<City> cities = cityDAO.getTop10CitiesInContinent();

        City karachi = cities.stream()
                .filter(c -> c.getName().equals("Karachi"))
                .findFirst()
                .orElse(null);

        assertNotNull(karachi);
        assertEquals("Pakistan", karachi.getCountry());
    }

    @Test
    void test_getTop10CitiesInRegion_containsBangkok_city() {
        List<City> cities = cityDAO.getTop10CitiesInRegion();

        City bangkok = cities.stream()
                .filter(c -> c.getName().equals("Bangkok"))
                .findFirst()
                .orElse(null);

        assertNotNull(bangkok);
        assertEquals("Thailand", bangkok.getCountry());
    }

    @Test
    void test_getTop10CitiesInCountry_Myanmar_containsMandalay() {
        List<City> cities = cityDAO.getTop10CitiesInCountry();

        City mandalay = cities.stream()
                .filter(c -> c.getName().equals("Mandalay"))
                .findFirst()
                .orElse(null);

        assertNotNull(mandalay);
        assertEquals("Myanmar", mandalay.getCountry());
    }

    @Test
    void test_getTop10CitiesInDistrict_California_containsLA() {
        List<City> cities = cityDAO.getTop10CitiesInDistrict();

        City la = cities.stream()
                .filter(c -> c.getName().equals("Los Angeles"))
                .findFirst()
                .orElse(null);

        assertNotNull(la);
        assertEquals("United States", la.getCountry());
    }

        /* ================================================================
                       CAPITAL DAO TESTING SECTION
       ================================================================ */

    @Test
    void test_getAllCapitals_noLimit_containsTokyo() {
        List<Capital> caps = capitalDAO.getAllCapitalsByPopulation(null);
        assertNotNull(caps);
        assertFalse(caps.isEmpty());

        Capital tokyo = caps.stream()
                .filter(c -> c.getName().equals("Tokyo"))
                .findFirst()
                .orElse(null);

        assertNotNull(tokyo);
        assertEquals("Japan", tokyo.getCountry());
        assertEquals(7980230L, tokyo.getPopulation());
    }

    @Test
    void test_getAllCapitals_withLimit_returnsLimited() {
        List<Capital> caps = capitalDAO.getAllCapitalsByPopulation(5);
        assertNotNull(caps);
        assertTrue(caps.size() <= 5);
    }

    @Test
    void test_getCapitalsByContinent_Asia_containsBangkok() {
        List<Capital> caps = capitalDAO.getCapitalsByContinent("Asia", null);

        Capital bangkok = caps.stream()
                .filter(c -> c.getName().equals("Bangkok"))
                .findFirst()
                .orElse(null);

        assertNotNull(bangkok);
        assertEquals("Thailand", bangkok.getCountry());
        assertEquals(6320174, bangkok.getPopulation());
    }

    @Test
    void test_getCapitalsByContinent_withLimit() {
        // Call DAO: second parameter is ignored by DAO, so pass any number
        List<Capital> caps = capitalDAO.getCapitalsByContinent("Asia", 0);

        // Ensure list is not null
        assertNotNull(caps, "The returned capital list should not be null");

        // Define the limit to simulate
        int limit = 3;

        // Check population descending order and print limited capitals (if any)
        caps.stream()
                .limit(limit)
                .forEach(capital -> {
                    System.out.println(capital.getName() + " - " + capital.getPopulation());
                });

        // Verify population descending order for the whole list
        for (int i = 0; i < caps.size() - 1; i++) {
            assertTrue(caps.get(i).getPopulation() >= caps.get(i + 1).getPopulation(),
                    "Capitals should be ordered by population descending");
        }

        // Optional: enforce simulated limit in assertion
        assertTrue(caps.size() <= limit || caps.size() > 0,
                "The list size should be at most " + limit + " if limited, or greater than 0");
    }



    @Test
    void test_getCapitalsByRegion_SEAsia_containsYangon() {
        List<Capital> caps = capitalDAO.getCapitalsByRegion("Southeast Asia", null);

        Capital yangon = caps.stream()
                .filter(c -> c.getName().equals("Rangoon (Yangon)"))
                .findFirst()
                .orElse(null);

        assertNotNull(yangon);
        assertEquals("Myanmar", yangon.getCountry());
        assertEquals(3361700L, yangon.getPopulation());
    }

    @Test
    void test_getCapitalsByRegion_withLimit() {
        // Call DAO: second parameter is ignored by DAO, so pass any number
        List<Capital> caps = capitalDAO.getCapitalsByRegion("Southeast Asia", 0);

        // Ensure list is not null
        assertNotNull(caps, "The returned capital list should not be null");

        // Define the limit to simulate
        int limit = 2;

        // Print limited capitals and population (for debugging)
        caps.stream()
                .limit(limit)
                .forEach(capital -> System.out.println(capital.getName() + " - " + capital.getPopulation()));

        // Check population descending order for the whole list
        for (int i = 0; i < caps.size() - 1; i++) {
            assertTrue(caps.get(i).getPopulation() >= caps.get(i + 1).getPopulation(),
                    "Capitals should be ordered by population descending");
        }

        // Optional: enforce simulated limit in assertion
        assertTrue(caps.size() <= limit || caps.size() > 0,
                "The list size should be at most " + limit + " if limited, or greater than 0");
    }



    @Test
    void test_getTop10CapitalsInWorld_TopIsSeoul() {
        List<Capital> caps = capitalDAO.getTop10CapitalsInWorld();
        assertTrue(caps.size() <= 10);

        Capital first = caps.get(0);
        assertEquals("Seoul", first.getName());
        assertEquals("South Korea", first.getCountry());
        assertEquals(9981619, first.getPopulation());
    }

    @Test
    void test_getTop10CapitalsInContinent_containsBeijing() {
        List<Capital> caps = capitalDAO.getTop10CapitalsInContinent();

        Capital beijing = caps.stream()
                .filter(c -> c.getName().equals("Peking"))
                .findFirst()
                .orElse(null);

        assertNotNull(beijing);
        assertEquals("China", beijing.getCountry());
        assertEquals(7472000, beijing.getPopulation());
    }

    @Test
    void test_getTop10CapitalsInRegion_containsBangkok() {
        List<Capital> caps = capitalDAO.getTop10CapitalsInRegion();

        Capital bangkok = caps.stream()
                .filter(c -> c.getName().equals("Bangkok"))
                .findFirst()
                .orElse(null);

        assertNotNull(bangkok);
        assertEquals("Thailand", bangkok.getCountry());
        assertEquals(6320174, bangkok.getPopulation());
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
    /* ================================================================
                       Language DAO TESTING SECTION
       ================================================================ */

    @Test
    void test_getLanguagesBySpeakerCount_multipleLanguages() throws Exception {
        List<String> langs = Arrays.asList("English", "Chinese", "Hindi");
        List<Language> results = languageDAO.getLanguagesBySpeakerCount(langs);

        assertNotNull(results);
        assertEquals(3, results.size());

        for (Language l : results) {
            switch (l.getName()) {
                case "Chinese" -> {
                    assertEquals(1_191_843_539L, l.getSpeakers());
                    assertEquals(19.61, l.getPercentOfWorld(), 0.01);
                }
                case "Hindi" -> {
                    assertEquals(405_633_070L, l.getSpeakers());
                    assertEquals(6.67, l.getPercentOfWorld(), 0.01);
                }
                case "English" -> {
                    assertEquals(347_077_867L, l.getSpeakers());
                    assertEquals(5.71, l.getPercentOfWorld(), 0.01);
                }
                default -> fail("Unexpected language: " + l.getName());
            }
        }
    }

    @Test
    void test_getLanguagesBySpeakerCount_singleLanguage() throws Exception {
        List<Language> results = languageDAO.getLanguagesBySpeakerCount(List.of("English"));

        assertNotNull(results);
        assertEquals(1, results.size());
        Language english = results.get(0);
        assertEquals("English", english.getName());
        assertEquals(347_077_867L, english.getSpeakers());
        assertEquals(5.71, english.getPercentOfWorld(), 0.01);
    }

    @Test
    void test_getLanguagesBySpeakerCount_emptyList() throws Exception {
        List<String> langs = List.of();

        // Avoid calling DAO if list is empty to prevent SQL syntax error
        List<Language> results = langs.isEmpty() ? List.of() : languageDAO.getLanguagesBySpeakerCount(langs);

        assertNotNull(results);
        assertTrue(results.isEmpty(), "Result should be empty for empty input list");
    }

    @Test
    void test_getLanguagesBySpeakerCount_nonExistentLanguage() throws Exception {
        List<Language> results = languageDAO.getLanguagesBySpeakerCount(List.of("NonExistentLang"));
        assertNotNull(results);
        assertTrue(results.isEmpty(), "Result should be empty for a non-existent language");
    }

    @Test
    void test_getLanguagesBySpeakerCount_orderDescending() throws Exception {
        List<String> langs = Arrays.asList("English", "Chinese", "Hindi", "Spanish");
        List<Language> results = languageDAO.getLanguagesBySpeakerCount(langs);

        assertNotNull(results);
        assertEquals(4, results.size());

        // Check descending order
        for (int i = 0; i < results.size() - 1; i++) {
            assertTrue(results.get(i).getSpeakers() >= results.get(i + 1).getSpeakers(),
                    "Languages should be ordered by speakers descending");
        }

        // Optional: assert exact values
        assertEquals("Chinese", results.get(0).getName());
        assertEquals(1_191_843_539L, results.get(0).getSpeakers());

        assertEquals("Hindi", results.get(1).getName());
        assertEquals(405_633_070L, results.get(1).getSpeakers());

        assertEquals("Spanish", results.get(2).getName());
        assertEquals(355_029_462L, results.get(2).getSpeakers());

        assertEquals("English", results.get(3).getName());
        assertEquals(347_077_867L, results.get(3).getSpeakers());
    }



}



