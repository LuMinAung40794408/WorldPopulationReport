package com.group12.report.reports;

import com.group12.report.models.Population;
import java.util.List;
/**
 * This class handles displaying population reports for
 * different categories such as world, continent, region,
 * country, district, and city.
 */
public class PopulationReport {

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
        System.out.println("\n================ " + "Population Report" + " ================\n");
    }

    /**
     * Displays population breakdown for categories like world, continent, region, or country.
     * @param populations List of Population objects containing data.
     * @param title Title of the report section.
     */
    public void displayPopulations(List<Population> populations, String title) {
        if (populations == null || populations.isEmpty()) {
            System.out.println("No population data to display for: " + title);
            return;
        }

        System.out.println("\n" + title + "\n");

        // Table header
        System.out.println("+------------------------------+---------------------+---------------------+------------------------+---------------------+------------------------+");
        System.out.printf("| %-28s | %19s | %19s | %22s | %19s | %22s |%n",
                "Name", "Total", "City", "City %", "Non-City", "Non-City %");
        System.out.println("+------------------------------+---------------------+---------------------+------------------------+---------------------+------------------------+");

        // Display each record (up to displayLimit)
        for (int i = 0; i < Math.min(displayLimit, populations.size()); i++) {
            Population p = populations.get(i);
            System.out.printf("| %-28s | %,19d | %,19d | %21.2f%% | %,19d | %21.2f%% |%n",
                    p.getName(),
                    p.getTotalPopulation(),
                    p.getCityPopulation(),
                    p.getCityPopulationPercent(),
                    p.getNonCityPopulation(),
                    p.getNonCityPopulationPercent());
        }

        System.out.println("+------------------------------+---------------------+---------------------+------------------------+---------------------+------------------------+");

        // If there are more results than the display limit
        if (populations.size() > displayLimit) {
            System.out.printf("Showing top %d of %d entries.%n", displayLimit, populations.size());
        }
    }

    /**
     * Displays district-level population report.
     * @param districts List of Population objects for districts.
     * @param title Title of the report section.
     */
    public void displayDistrictPopulations(List<Population> districts, String title) {
        if (districts == null || districts.isEmpty()) {
            System.out.println("No district population data to display for: " + title);
            return;
        }

        System.out.println("\n" + title + "\n");
        System.out.println("+----------------------+-----------------+");
        System.out.printf("| %-20s | %15s |%n", "District", "Population");
        System.out.println("+----------------------+-----------------+");

        for (int i = 0; i < Math.min(displayLimit, districts.size()); i++) {
            Population p = districts.get(i);
            System.out.printf("| %-20s | %,15d |%n", p.getDistrict(), p.getTotalPopulation());
        }

        System.out.println("+----------------------+-----------------+");

        if (districts.size() > displayLimit) {
            System.out.printf("Showing top %d of %d districts.%n", displayLimit, districts.size());
        }
    }

    /**
     * Displays city-level population report including city, country, and district.
     * @param cities List of Population objects for cities.
     * @param title Title of the report section.
     */
    public void displayCityPopulations(List<Population> cities, String title) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No city population data to display for: " + title);
            return;
        }

        System.out.println("\n" + title + "\n");
        System.out.println("+----------------------+----------------------+----------------------+-----------------+");
        System.out.printf("| %-20s | %-20s | %-20s | %15s |%n", "City", "Country", "District", "Population");
        System.out.println("+----------------------+----------------------+----------------------+-----------------+");

        // Display each city (up to displayLimit)
        for (int i = 0; i < Math.min(displayLimit, cities.size()); i++) {
            Population p = cities.get(i);
            System.out.printf("| %-20s | %-20s | %-20s | %,15d |%n",
                    p.getName(), p.getCountry(), p.getDistrict(), p.getTotalPopulation());
        }

        System.out.println("+----------------------+----------------------+----------------------+-----------------+");

        if (cities.size() > displayLimit) {
            System.out.printf("Showing top %d of %d cities.%n", displayLimit, cities.size());
        }
    }
}