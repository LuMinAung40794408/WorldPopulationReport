package com.group12.report.reports;

import com.group12.report.models.Population;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author 40779661 Ann Min Nyo
 * This class handles displaying population reports for
 * different categories such as world, continent, region,
 * country, district, and city.
 */
public class PopulationReport {

    private static final Logger LOGGER = Logger.getLogger(PopulationReport.class.getName());

    // The number of rows to display in reports (default: 15)
    private final int displayLimit;

    /**
     * Default constructor that sets display limit to 15.
     */
    public PopulationReport() {
        this.displayLimit = 15;
    }

    /**
     * Constructor allowing a custom display limit.
     * @param displayLimit Number of entries to show.
     */
    public PopulationReport(int displayLimit) {
        this.displayLimit = displayLimit;
    }

    /**
     * Prints the main category title for the population report.
     * @param categoryName The category name (e.g., "World Population").
     */
    public void printCategory(String categoryName) {
        LOGGER.info(() -> "\n================ Population Report ================\n");
    }

    /**
     * Displays population breakdown for categories like world, continent, region, or country.
     * @param populations List of Population objects containing data.
     * @param title Title of the report section.
     */
    public void displayPopulations(List<Population> populations, String title) {
        if (populations == null || populations.isEmpty()) {
            LOGGER.info(() -> "No population data to display for: " + title);
            return;
        }

        LOGGER.info(() -> "\n" + title + "\n");

        // Table header
        LOGGER.info("+------------------------------+---------------------+---------------------+------------------------+---------------------+------------------------+");
        LOGGER.info(() -> String.format(
                "| %-28s | %19s | %19s | %22s | %19s | %22s |",
                "Name", "Total", "City", "City %", "Non-City", "Non-City %"
        ));
        LOGGER.info("+------------------------------+---------------------+---------------------+------------------------+---------------------+------------------------+");

        // Display each record (up to displayLimit)
        for (int i = 0; i < Math.min(displayLimit, populations.size()); i++) {
            Population p = populations.get(i);
            LOGGER.info(() -> String.format(
                    "| %-28s | %,19d | %,19d | %21.2f%% | %,19d | %21.2f%% |",
                    p.getName(),
                    p.getTotalPopulation(),
                    p.getCityPopulation(),
                    p.getCityPopulationPercent(),
                    p.getNonCityPopulation(),
                    p.getNonCityPopulationPercent()
            ));
        }

        LOGGER.info("+------------------------------+---------------------+---------------------+------------------------+---------------------+------------------------+");

        // If there are more results than the display limit
        if (populations.size() > displayLimit) {
            final int total = populations.size();
            LOGGER.info(() -> String.format(
                    "Showing top %d of %d entries.",
                    displayLimit, total
            ));
        }
    }

    /**
     * Displays district-level population report.
     * @param districts List of Population objects for districts.
     * @param title Title of the report section.
     */
    public void displayDistrictPopulations(List<Population> districts, String title) {
        if (districts == null || districts.isEmpty()) {
            LOGGER.info(() -> "No district population data to display for: " + title);
            return;
        }

        LOGGER.info(() -> "\n" + title + "\n");
        LOGGER.info("+----------------------+-----------------+");
        LOGGER.info(() -> String.format(
                "| %-20s | %15s |",
                "District", "Population"
        ));
        LOGGER.info("+----------------------+-----------------+");

        for (int i = 0; i < Math.min(displayLimit, districts.size()); i++) {
            Population p = districts.get(i);
            LOGGER.info(() -> String.format(
                    "| %-20s | %,15d |",
                    p.getDistrict(), p.getTotalPopulation()
            ));
        }

        LOGGER.info("+----------------------+-----------------+");

        if (districts.size() > displayLimit) {
            final int total = districts.size();
            LOGGER.info(() -> String.format(
                    "Showing top %d of %d districts.",
                    displayLimit, total
            ));
        }
    }

    /**
     * Displays city-level population report including city, country, and district.
     * @param cities List of Population objects for cities.
     * @param title Title of the report section.
     */
    public void displayCityPopulations(List<Population> cities, String title) {
        if (cities == null || cities.isEmpty()) {
            LOGGER.info(() -> "No city population data to display for: " + title);
            return;
        }

        LOGGER.info(() -> "\n" + title + "\n");
        LOGGER.info("+----------------------+----------------------+----------------------+-----------------+");
        LOGGER.info(() -> String.format(
                "| %-20s | %-20s | %-20s | %15s |",
                "City", "Country", "District", "Population"
        ));
        LOGGER.info("+----------------------+----------------------+----------------------+-----------------+");

        // Display each city (up to displayLimit)
        for (int i = 0; i < Math.min(displayLimit, cities.size()); i++) {
            Population p = cities.get(i);
            LOGGER.info(() -> String.format(
                    "| %-20s | %-20s | %-20s | %,15d |",
                    p.getName(), p.getCountry(), p.getDistrict(), p.getTotalPopulation()
            ));
        }

        LOGGER.info("+----------------------+----------------------+----------------------+-----------------+");

        if (cities.size() > displayLimit) {
            final int total = cities.size();
            LOGGER.info(() -> String.format(
                    "Showing top %d of %d cities.",
                    displayLimit, total
            ));
        }
    }
}
