package com.group12.report.reports;

import com.group12.report.models.City;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author 40794374PhoneMyatKyaw
 */
public class CityReport {

    private static final Logger LOGGER = Logger.getLogger(CityReport.class.getName());

    // Max rows to print in the table (caps console output).
    private final int displayLimit;

    // Default constructor: uses a sensible limit for readability.
    public CityReport(int displayLimit) {
        this.displayLimit = displayLimit;
    }


    // Overloaded constructor: caller controls how many rows to show.
    public void printCategory(String categoryName) {
        LOGGER.info(" ");  // blank line before category
        LOGGER.info("================   City Report   ================");
    }

    public void displayCities(List<City> cities, String title) {
        if (cities == null || cities.isEmpty()) {
            LOGGER.info(() -> "No cities to display for: " + title);
            return;
        }
        // Defensive check: avoids NPE and informs user when there’s no data.

        LOGGER.info(" ");        // blank line before title
        LOGGER.info(title);      // print title
        LOGGER.info(" ");        // blank line after title

        // Prints a descriptive title for the current table (e.g., filter context).

        LOGGER.info("+----------------------+----------------------+----------------------+-----------------+");
        LOGGER.info(() -> String.format(
                "| %-20s | %-20s | %-20s | %15s |",
                "City", "Country", "District", "Population"
        ));
        LOGGER.info("+----------------------+----------------------+----------------------+-----------------+");
        // Fixed-width ASCII table header for consistent alignment in consoles.

        for (int i = 0; i < Math.min(displayLimit, cities.size()); i++) {
            City c = cities.get(i);
            LOGGER.info(() -> String.format(
                    "| %-20s | %-20s | %-20s | %,15d |",
                    c.getName(),
                    c.getCountry(),
                    c.getDistrict(),
                    c.getPopulation()
            ));
            // Each row: left-align text columns to 20 chars; right-align population with grouping.
            // Values longer than 20 will be truncated visually by the console width.
        }

        LOGGER.info("+----------------------+----------------------+----------------------+-----------------+");
        // Table footer line for visual closure.

        if (cities.size() > displayLimit) {
            final int total = cities.size();
            LOGGER.info(() -> String.format(
                    "Showing top %d of %d cities.",
                    displayLimit, total
            ));
            // Hint to user that more rows exist than displayed (basic pagination cue).
        }
    }
}