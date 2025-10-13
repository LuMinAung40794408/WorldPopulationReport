package com.group12.report.reports;

import com.group12.report.models.City;
import java.util.List;

public class CityReport {

    private final int displayLimit;
    // Max rows to print in the table (caps console output).



    // Default constructor: uses a sensible limit for readability.

    public CityReport(int displayLimit) {
        this.displayLimit = displayLimit;
    }
    // Overloaded constructor: caller controls how many rows to show.

    public void printCategory(String categoryName) {
        System.out.println("\n================   City Report   ================\n");

    }

    public void displayCities(List<City> cities, String title) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities to display for: " + title);
            return;
        }
        // Defensive check: avoids NPE and informs user when there’s no data.

        System.out.println("\n" + title + "\n");
        // Prints a descriptive title for the current table (e.g., filter context).

        System.out.println("+----------------------+----------------------+----------------------+-----------------+");
        System.out.printf("| %-20s | %-20s | %-20s | %15s |%n",
                "City", "Country", "District", "Population");
        System.out.println("+----------------------+----------------------+----------------------+-----------------+");
        // Fixed-width ASCII table header for consistent alignment in consoles.

        for (int i = 0; i < Math.min(displayLimit, cities.size()); i++) {
            City c = cities.get(i);
            System.out.printf("| %-20s | %-20s | %-20s | %,15d |%n",
                    c.getName(),
                    c.getCountry(),
                    c.getDistrict(),
                    c.getPopulation());
            // Each row: left-align text columns to 20 chars; right-align population with grouping.
            // Values longer than 20 will be truncated visually by the console width.
        }

        System.out.println("+----------------------+----------------------+----------------------+-----------------+");
        // Table footer line for visual closure.

        if (cities.size() > displayLimit) {
            System.out.printf("Showing top %d of %d cities.%n", displayLimit, cities.size());
        }
        // Hint to user that more rows exist than displayed (basic pagination cue).
    }
}
