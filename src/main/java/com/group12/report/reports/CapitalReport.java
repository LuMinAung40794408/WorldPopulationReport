package com.group12.report.reports;

import com.group12.report.models.Capital;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author 40794512 Zayar Than Htike
 * The CapitalReport class is responsible for displaying formatted reports
 * of capital cities based on their population, country, and name.
 *
 * <p>This class outputs the data in a clean tabular format, making it easy
 * to read in the console. It supports limiting the number of displayed records.</p>
 *
 */
public class CapitalReport {

    private static final Logger LOGGER = Logger.getLogger(CapitalReport.class.getName());

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
        LOGGER.info(" ");  // blank line before category
        LOGGER.info("================   Capital Report   ================");
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
            LOGGER.info(() -> "No capital cities to display for: " + title);
            return;
        }

        // Print the report title
        LOGGER.info(" ");        // blank line before title
        LOGGER.info(title);      // print title
        LOGGER.info(" ");        // blank line after title


        // Print the table header
        LOGGER.info("+----------------------+----------------------+-----------------+");
        LOGGER.info(() -> String.format(
                "| %-20s | %-20s | %15s |",
                "Capital", "Country", "Population"
        ));
        LOGGER.info("+----------------------+----------------------+-----------------+");

        // Loop through and display up to 'displayLimit' number of records
        for (int i = 0; i < Math.min(displayLimit, capitals.size()); i++) {
            Capital c = capitals.get(i);
            LOGGER.info(() -> String.format(
                    "| %-20s | %-20s | %,15d |",
                    c.getName(),
                    c.getCountry(),
                    c.getPopulation()
            ));
        }


        // Print table footer line
        LOGGER.info("+----------------------+----------------------+-----------------+");

        // Indicate if not all records are displayed
        if (capitals.size() > displayLimit) {
            final int total = capitals.size();
            LOGGER.info(() -> String.format(
                    "Showing top %d of %d capital cities.",
                    displayLimit, total
            ));
        }
    }
}
