package com.group12.report.reports;

import com.group12.report.models.Country;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author 40794374 Thu Ta Minn Lu
 *
 * Displays country data in a clean, table-style format.
 * Layout and behaviour matches CityReport for consistency.
 */
public class CountryReport {

    private static final Logger LOGGER = Logger.getLogger(CountryReport.class.getName());

    // Max rows to print in the table (caps console output).
    private final int displayLimit;

    // Overloaded constructor: caller controls how many rows to show.
    public CountryReport(int displayLimit) {
        this.displayLimit = displayLimit;
    }

    /**
     * Prints the category header for country reports.
     */
    public void printCategory(String categoryName) {
        LOGGER.info(() -> "\n================   Country Report   ================\n");
    }

    /**
     * Displays a formatted table of countries.
     *
     * @param countries List of Country objects to print.
     * @param title     Section title (e.g., “All countries in the world…”).
     */
    public void displayCountries(List<Country> countries, String title) {

        if (countries == null || countries.isEmpty()) {
            LOGGER.info(() -> "No countries to display for: " + title);
            return;
        }

        // Prints a descriptive title for the current table (e.g., filter context).
        LOGGER.info(() -> "\n" + title + "\n");

        // Fixed-width ASCII table header for consistent alignment in consoles.
        LOGGER.info("+--------+----------------------+-----------------+---------------------------+-----------------+----------------------+");
        LOGGER.info(() -> String.format(
                "| %-6s | %-20s | %-15s | %-25s | %15s | %-20s |",
                "Code", "Name", "Continent", "Region", "Population", "Capital"
        ));
        LOGGER.info("+--------+----------------------+-----------------+---------------------------+-----------------+----------------------+");

        // Loop through the list of countries, but only up to the display limit.
        for (int i = 0; i < Math.min(displayLimit, countries.size()); i++) {
            Country c = countries.get(i);
            LOGGER.info(() -> String.format(
                    "| %-6s | %-20s | %-15s | %-25s | %,15d | %-20s |",
                    c.getCode(),
                    c.getName(),
                    c.getContinent(),
                    c.getRegion(),
                    c.getPopulation(),
                    c.getCapitalName()
            ));
        }

        // Table footer line for visual closure.
        LOGGER.info("+--------+----------------------+-----------------+---------------------------+-----------------+----------------------+");

        // Hint to user that more rows exist than displayed (basic pagination cue).
        if (countries.size() > displayLimit) {
            final int total = countries.size();
            LOGGER.info(() -> String.format(
                    "Showing top %d of %d countries.",
                    displayLimit, total
            ));
        }
    }
}
