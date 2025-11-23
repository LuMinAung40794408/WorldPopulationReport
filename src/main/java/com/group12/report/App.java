package com.group12.report;

import com.group12.report.data_access.*;
import com.group12.report.reports.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    public Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location, int delay) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Could not load SQL driver", e);
            System.exit(-1);
        }

        int retries = 15;
        for (int i = 0; i < retries; ++i) {
            LOGGER.info("Connecting to database...");
            try {
                Thread.sleep(10000);

                con = DriverManager.getConnection(
                        "jdbc:mysql://" + location + "/world?useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "example"
                );

                LOGGER.info("Successfully connected to the database");
                break;
            }
            catch (SQLException sqle) {
                final int attempt = i;
                LOGGER.log(Level.WARNING, () ->
                        "Failed to connect to database attempt " + attempt + ": " + sqle.getMessage()
                );
            }
            catch (InterruptedException ie) {
                LOGGER.log(Level.WARNING, () -> "Thread interrupted unexpectedly");
            }

        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
                LOGGER.info("Connection closed");
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error closing connection to database", e);
            }
        }
    }

    public static void main(String[] args) {

        // <<< Add this line FIRST >>>
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%n");

        App app = new App();

        try {
            if (args.length < 1) {
                app.connect("localhost:33060", 30000);
            } else {
                app.connect("db:3306", 10000);
            }

            // Country
            CountryDAO dao = new CountryDAO(app.con);
            CountryReport countryReport = new CountryReport(10);

            countryReport.printCategory("Country Report");
            countryReport.displayCountries(dao.getAllCountriesByPopulation(null),
                    "1. All countries in the world organized by largest to smallest population");
            countryReport.displayCountries(dao.getCountriesByContinent("Asia", null),
                    "2. All countries in a continent (Asia)");
            countryReport.displayCountries(dao.getCountriesByRegion("Southeast Asia", null),
                    "3. All countries in a region (Southeast Asia)");
            countryReport.displayCountries(dao.getTop10CountriesInWorld(),
                    "4. Top 10 populated countries in the world");
            countryReport.displayCountries(dao.getTop10CountriesInEurope(),
                    "5. Top 10 populated countries in Europe");
            countryReport.displayCountries(dao.getTop10CountriesInWesternEurope(),
                    "6. Top 10 populated countries in Western Europe");

            // City
            CityDAO cityDAO = new CityDAO(app.con);
            CityReport cityReport = new CityReport(10);

            cityReport.printCategory("City Report");
            cityReport.displayCities(cityDAO.getAllCitiesByPopulation(null),
                    "7. All Cities in the World Organized by Largest to Smallest Population");
            cityReport.displayCities(cityDAO.getCitiesByContinent("Asia", null),
                    "8. All Cities in Asia Organized by Largest to Smallest Population");
            cityReport.displayCities(cityDAO.getCitiesByRegion("Southeast Asia", null),
                    "9. All Cities in Southeast Asia Organized by Largest to Smallest Population");
            cityReport.displayCities(cityDAO.getCitiesByDistrict("California", null),
                    "10. All Cities in California Organized by Largest to Smallest Population");
            cityReport.displayCities(cityDAO.getCitiesByCountry("Myanmar", null),
                    "11. All Cities in Myanmar Organized by Largest to Smallest Population");

            cityReport.displayCities(cityDAO.getTop10CitiesInWorld(),
                    "12. Top 10 Populated Cities in the World");
            cityReport.displayCities(cityDAO.getTop10CitiesInContinent(),
                    "13. Top 10 Populated Cities in a Continent (Asia)");
            cityReport.displayCities(cityDAO.getTop10CitiesInRegion(),
                    "14. Top 10 Populated Cities in a Region (Southeast Asia)");
            cityReport.displayCities(cityDAO.getTop10CitiesInCountry(),
                    "15. Top 10 Populated Cities in a Country (Myanmar)");
            cityReport.displayCities(cityDAO.getTop10CitiesInDistrict(),
                    "16. Top 10 Populated Cities in a District (California)");

            // Capital
            CapitalDAO capitalDAO = new CapitalDAO(app.con);
            CapitalReport capitalReport = new CapitalReport(10);

            capitalReport.printCategory("Capital Report");
            capitalReport.displayCapitals(capitalDAO.getAllCapitalsByPopulation(null),
                    "17. All Capital Cities in the World Organized by Population (Largest to Smallest)");
            capitalReport.displayCapitals(capitalDAO.getCapitalsByContinent("Asia", null),
                    "18. All Capital Cities in a Continent Organized by Population (Asia)");
            capitalReport.displayCapitals(capitalDAO.getCapitalsByRegion("Southeast Asia", null),
                    "19. All Capital Cities in a Region Organized by Population (Southeast Asia)");

            capitalReport.displayCapitals(capitalDAO.getTop10CapitalsInWorld(),
                    "20. Top 10 Populated Capital Cities in the World");
            capitalReport.displayCapitals(capitalDAO.getTop10CapitalsInContinent(),
                    "21. Top 10 Populated Capital Cities in a Continent (Asia)");
            capitalReport.displayCapitals(capitalDAO.getTop10CapitalsInRegion(),
                    "22. Top 10 Populated Capital Cities in a Region (Southeast Asia)");


            // Population
            PopulationDAO popDAO = new PopulationDAO(app.con);
            PopulationReport popReport = new PopulationReport(10);

            popReport.printCategory("Population Report");

            popReport.displayPopulations(popDAO.getWorldPopulation(),
                    "23. Population of the World");
            popReport.displayPopulations(popDAO.getPopulationByContinent(),
                    "24. Population of Each Continent");
            popReport.displayPopulations(popDAO.getPopulationByRegion(),
                    "25. Population of Each Region");
            popReport.displayPopulations(popDAO.getPopulationByCountry(),
                    "26. Population of Each Country");

            popReport.displayDistrictPopulations(popDAO.getPopulationByDistrict("Myanmar"),
                    "27. Population of Each District in Myanmar");

            popReport.displayCityPopulations(popDAO.getPopulationByCity(),
                    "28. Population of Each City");

            popReport.displayPopulations(popDAO.getCityVsNonCityByContinent(),
                    "29. City vs Non-City Population by Continent");
            popReport.displayPopulations(popDAO.getCityVsNonCityByRegion(),
                    "30. City vs Non-City Population by Region");
            popReport.displayPopulations(popDAO.getCityVsNonCityByCountry(),
                    "31. City vs Non-City Population by Country");

            // Language
            LanguageDAO languageDAO = new LanguageDAO(app.con);
            LanguageReport languageReport = new LanguageReport(10);
            languageReport.printCategory("Language Report");

            List<String> langs = List.of("English", "Chinese", "Hindi", "Spanish", "Arabic");
            languageReport.displayLanguages(
                    languageDAO.getLanguagesBySpeakerCount(langs),
                    "32. Languages by Number of Speakers"
            );

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e, () -> "Startup error: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }
}
