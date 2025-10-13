package com.group12.report.reports;

import com.group12.report.models.Capital;
import java.util.List;

/**
 * The CapitalReport class is responsible for displaying formatted reports
 * of capital cities based on their population, country, and name.
 *
 * <p>This class outputs the data in a clean tabular format, making it easy
 * to read in the console. It supports limiting the number of displayed records.</p>
 *
 */
public class CapitalReport {

    // The maximum number of capital records to display in the report
    private final int displayLimit;

    /**
     * Constructor to initialize the report with a specific display limit.
     *
     * @param displayLimit Maximum number of records to show per report
     */
    public CapitalReport(int displayLimit) {
        this.displayLimit = displayLimit;
    }

    /**
     * Prints the report category header to the console.
     *
     * @param categoryName The name of the report category (e.g., "World", "Continent", "Region")
     */
    public void printCategory(String categoryName) {
        System.out.println("\n================ Capital Report ================\n");
    }

    /**
     * Displays a list of capitals in a formatted table.
     *
     * <p>The table includes columns for capital name, country name,
     * and population. The method respects the {@code displayLimit} value.</p>
     *
     * @param capitals The list of capital data to display
     * @param title    A title for the report (e.g., "Top Capital Cities in Asia")
     */
    public void displayCapitals(List<Capital> capitals, String title) {

        // Check if the list is null or empty
        if (capitals == null || capitals.isEmpty()) {
            System.out.println("No capital cities to display for: " + title);
            return;
        }

        // Print the report title
        System.out.println("\n" + title + "\n");

        // Print the table header
        System.out.println("+----------------------+----------------------+-----------------+");
        System.out.printf("| %-20s | %-20s | %15s |%n", "Capital", "Country", "Population");
        System.out.println("+----------------------+----------------------+-----------------+");

        // Loop through and display up to 'displayLimit' number of records
        for (int i = 0; i < Math.min(displayLimit, capitals.size()); i++) {
            Capital c = capitals.get(i);
            System.out.printf("| %-20s | %-20s | %,15d |%n",
                    c.getName(),
                    c.getCountry(),
                    c.getPopulation());
        }

        // Print table footer line
        System.out.println("+----------------------+----------------------+-----------------+");

        // Indicate if not all records are displayed
        if (capitals.size() > displayLimit) {
            System.out.printf("Showing top %d of %d capital cities.%n", displayLimit, capitals.size());
        }
    }
}
