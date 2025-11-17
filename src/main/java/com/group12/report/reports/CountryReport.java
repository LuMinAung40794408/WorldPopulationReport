package com.group12.report.reports;

import com.group12.report.models.Country;
import java.util.List;

/**
 * @author 40794374 Thu Ta Minn Lu
 *
 * Displays country data in a clean, table-style format.
 * Layout and behaviour matches CityReport for consistency.
 */
public class CountryReport {

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
        System.out.println("\n================   Country Report   ================\n");
    }

    /**
     * Displays a formatted table of countries.
     *
     * @param countries List of Country objects to print.
     * @param title     Section title (e.g., “All countries in the world…”).
     */
    public void displayCountries(List<Country> countries, String title) {
        // Defensive check: avoids NPE and informs user when there’s no data.
        if (countries == null || countries.isEmpty()) {
            System.out.println("No countries to display for: " + title);
            return;
        }

        // Prints a descriptive title for the current table (e.g., filter context).
        System.out.println("\n" + title + "\n");

        // Fixed-width ASCII table header for consistent alignment in consoles.
        System.out.println("+--------+----------------------+-----------------+---------------------------+-----------------+----------------------+");
        System.out.printf("| %-6s | %-20s | %-15s | %-25s | %15s | %-20s |%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");
        System.out.println("+--------+----------------------+-----------------+---------------------------+-----------------+----------------------+");

        // Loop through the list of countries, but only up to the display limit.
        for (int i = 0; i < Math.min(displayLimit, countries.size()); i++) {
            Country c = countries.get(i);
            System.out.printf("| %-6s | %-20s | %-15s | %-25s | %,15d | %-20s |%n",
                    c.getCode(),
                    c.getName(),
                    c.getContinent(),
                    c.getRegion(),
                    c.getPopulation(),
                    c.getCapitalName());
        }

        // Table footer line for visual closure.
        System.out.println("+--------+----------------------+-----------------+---------------------------+-----------------+----------------------+");

        // Hint to user that more rows exist than displayed (basic pagination cue).
        if (countries.size() > displayLimit) {
            System.out.printf("Showing top %d of %d countries.%n", displayLimit, countries.size());
        }
    }
}
