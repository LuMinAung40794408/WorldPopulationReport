package com.group12.report;

import com.group12.report.data_access.CountryDAO;
import com.group12.report.reports.CountryReport;
import com.group12.report.data_access.CityDAO;
import com.group12.report.reports.CityReport;
import com.group12.report.data_access.CapitalDAO;
import com.group12.report.reports.CapitalReport;
import com.group12.report.data_access.PopulationDAO;
import com.group12.report.reports.PopulationReport;
import com.group12.report.data_access.LanguageDAO;
import com.group12.report.reports.LanguageReport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class App {
    private Connection con;

    public void connect(String url, String user, String pass) throws InterruptedException {
        int maxRetries = 10;
        int retryDelay = 10000; // 3 seconds
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                System.out.printf("Connecting to database (attempt %d/%d)...%n", attempt + 1, maxRetries);
                con = DriverManager.getConnection(url, user, pass);
                System.out.println("Connected successfully.");
                return;
            } catch (SQLException e) {
                attempt++;
                System.err.println("Connection failed: " + e.getMessage());
                if (attempt >= maxRetries) {
                    throw new RuntimeException("Failed to connect to database after " + maxRetries + " attempts.", e);
                }
                System.out.printf("Retrying in %d seconds...%n", retryDelay / 1000);
                Thread.sleep(retryDelay);
            }
        }
    }

    public void disconnect() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Disconnected.");
            }
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        App app = new App();

        try {
            String url = System.getenv().getOrDefault("DB_URL",
                    "jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false");
            String user = System.getenv().getOrDefault("DB_USER", "root");
            String pass = System.getenv().getOrDefault("DB_PASS", "example");

            app.connect(url, user, pass);

            //country report
            CountryDAO dao = new CountryDAO(app.con);
            CountryReport countryReport = new CountryReport(10); // show only top 10 countries per report

            countryReport.printCategory("Country Report");
            countryReport.displayCountries(dao.getAllCountriesByPopulation(null), "1.All countries in the world organized by largest to smallest population");
            countryReport.displayCountries(dao.getCountriesByContinent("Asia", null), "2.All countries in a continent organized by largest to smallest population (Asia) ");
            countryReport.displayCountries(dao.getCountriesByRegion("Southeast Asia", null), "3.All countries in a region organized by largest to smallest population (Southeast Asia)");

            // ==================== TOP 10 COUNTRY REPORTS (NEW) ====================

            countryReport.displayCountries(
                    dao.getTop10CountriesInWorld(),
                    "4. Top 10 populated countries in the world"
            );

            countryReport.displayCountries(
                    dao.getTop10CountriesInEurope(),
                    "5. Top 10 populated countries in a continent (Europe)"
            );

            countryReport.displayCountries(
                    dao.getTop10CountriesInWesternEurope(),
                    "6. Top 10 populated countries in a region (Western Europe)"
            );

            //city report
            CityDAO cityDAO = new CityDAO(app.con);
            // Create the data-access object using the existing DB connection from 'app'.

            CityReport cityReport = new CityReport(10); // show only top 10 cities per report
            // Configure the report to display at most 10 rows per section in the console.

            cityReport.printCategory("City Report");
            // Print a banner/header for the City Report section.

            // Query all cities (no DB limit applied because null), then print the top 10 per CityReport.
            cityReport.displayCities(
                    cityDAO.getAllCitiesByPopulation(null),
                    "7.All Cities in the World Organized by Largest to Smallest Population"
            );

            // Filter by continent = 'Asia' in the DB (no DB limit), then display top 10.
            cityReport.displayCities(
                    cityDAO.getCitiesByContinent("Asia", null),
                    "8.All Cities in Asia Organized by Largest to Smallest Population(Asia)"
            );

            // Filter by region = 'Southeast Asia' (no DB limit), then display top 10.
            cityReport.displayCities(
                    cityDAO.getCitiesByRegion("Southeast Asia", null),
                    "9.All Cities in Southeast Asia Organized by Largest to Smallest Population(Southeast Asia"
            );

            // Filter by city.district = 'California' (no DB limit), then display top 10
            // Works with the MySQL 'world' sample where many CA cities use district 'California'.
            cityReport.displayCities(
                    cityDAO.getCitiesByDistrict("California", null),
                    "10.All Cities in California Organized by Largest to Smallest Population(California)"
            );

            // Filter by country name = 'Myanmar' (no DB limit), then display top 10.
            // Ensure 'Myanmar' matches the Country.Name value in your dataset.
            // Define a list of specific languages to be included in the report.
            cityReport.displayCities(
                    cityDAO.getCitiesByCountry("Myanmar", null),
                    "11.All Cities in Myanmar Organized by Largest to Smallest Population(Myanmar)"
            );

            // ==================== TOP 10 CITY REPORTS (NEW) ====================


            cityReport.displayCities(
                    cityDAO.getTop10CitiesInWorld(),
                    "12.Top 10 Populated Cities in the World"
            );

            cityReport.displayCities(
                    cityDAO.getTop10CitiesInContinent(),
                    "13.Top 10 Populated Cities in a Continent (Asia)"
            );

            cityReport.displayCities(
                    cityDAO.getTop10CitiesInRegion(),
                    "14.Top 10 Populated Cities in a Region (Southeast Asia)"
            );

            cityReport.displayCities(
                    cityDAO.getTop10CitiesInCountry(),
                    "15.Top 10 Populated Cities in a Country (Myanmar)"
            );

            cityReport.displayCities(
                    cityDAO.getTop10CitiesInDistrict(),
                    "16.Top 10 Populated Cities in a District (California)"
            );

            //capital report
            CapitalDAO capitalDAO = new CapitalDAO(app.con);
            CapitalReport capitalReport = new CapitalReport(10); // show top 10 per report

            capitalReport.printCategory("Capital Report");
            capitalReport.displayCapitals(capitalDAO.getAllCapitalsByPopulation(null), "17.All Capital Cities in the World Organized by Population (Largest to Smallest)");
            capitalReport.displayCapitals(capitalDAO.getCapitalsByContinent("Asia", null), "18.All Capital Cities in A Continent Organized by Population (Asia)");
            capitalReport.displayCapitals(capitalDAO.getCapitalsByRegion("Southeast Asia", null), "19.All Capital Cities in A Region Organized by Population (Southeast Asia)");

            // ==================== TOP 10 CAPITAL CITY REPORTS (NEW) ====================
            capitalReport.displayCapitals(
                    capitalDAO.getTop10CapitalsInWorld(),
                    "20. Top 10 Populated Capital Cities in the World"
            );

            capitalReport.displayCapitals(
                    capitalDAO.getTop10CapitalsInContinent(),
                    "21. Top 10 Populated Capital Cities in a Continent (Asia)"
            );

            capitalReport.displayCapitals(
                    capitalDAO.getTop10CapitalsInRegion(),
                    "22. Top 10 Populated Capital Cities in a Region (Southeast Asia)"
            );


            //population report
            PopulationDAO popDAO = new PopulationDAO(app.con);
            PopulationReport popReport = new PopulationReport(10);

            popReport.printCategory("Population Report");

            // 1–4: Breakdown reports
            popReport.displayPopulations(popDAO.getWorldPopulation(), "23.Population of the World");
            popReport.displayPopulations(popDAO.getPopulationByContinent(), "24.Population of Each Continent");
            popReport.displayPopulations(popDAO.getPopulationByRegion(), "25.Population of Each Region");
            popReport.displayPopulations(popDAO.getPopulationByCountry(), "26.Population of Each Country");

            // 5: District population (filtered by country)
            popReport.displayDistrictPopulations(popDAO.getPopulationByDistrict("Myanmar"), "17.Population of Each District in Myanmar");

            // 6: City population
            popReport.displayCityPopulations(popDAO.getPopulationByCity(), "28.Population of Each City");

            // New city vs non-city breakdowns
            popReport.displayPopulations(popDAO.getCityVsNonCityByContinent(), "29.City vs Non-City Population by Continent");
            popReport.displayPopulations(popDAO.getCityVsNonCityByRegion(), "30.City vs Non-City Population by Region");
            popReport.displayPopulations(popDAO.getCityVsNonCityByCountry(), "31.City vs Non-City Population by Country");

            // ==================== LANGUAGE REPORT SECTION ====================

            // Create a LanguageDAO instance to fetch language data from the database.
            // The DAO uses the active database connection (app.con).
            LanguageDAO languageDAO = new LanguageDAO(app.con);

            // Create a LanguageReport instance with a display limit of 10 rows.
            // This will control how many languages are shown in the console output.
            LanguageReport languageReport = new LanguageReport(10);

            // Print the category heading for the language report.
            languageReport.printCategory("Language Report");

            // Define a list of specific languages to be included in the report.
            // These are the languages whose total speaker counts will be retrieved.
            List<String> langs = List.of("English", "Chinese", "Hindi", "Spanish", "Arabic");

            // Fetch and display the report:
            // - Calls LanguageDAO.getLanguagesBySpeakerCount(langs) to retrieve data.
            // - Passes that data to LanguageReport.displayLanguages(...) for formatted output.
            languageReport.displayLanguages(
                    languageDAO.getLanguagesBySpeakerCount(langs),
                    "32.Languages by Number of Speakers (English, Chinese, Hindi, Spanish, Arabic)"
            );

            // =================================================================

            // Add similar calls for continent, region, country, district, city, and city-vs-noncity breakdowns
        } catch (Exception e) {
            System.err.println("Startup error: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }
}
