package com.group12.report.reports;

import com.group12.report.models.Country;
import java.util.List;

/**
 * @author 40794374 Thu Ta Minn Lu
 */

// This class is responsible for showing country data in a clean, table-style format.
public class CountryReport2 {

    // The maximum number of countries to display at once
    private final int displayLimit;

    // Prints the main title for the report (just a simple header)
    public void printCategory(String categoryName) {
        System.out.println("\n================   Country Report   ================\n");
    }

    // Optional constructor
    public CountryReport2(int displayLimit) {
        this.displayLimit = displayLimit;
    }

    // This method takes a list of Country objects and prints them as a formatted table.
    public void displayCountries(List<Country> countries, String title) {

        // If there are no countries to show, print the warning message and stop
        if (countries == null || countries.isEmpty()) {
            System.out.println("No countries to display for: " + title);
            return;
        }


        System.out.println(); // Adds a blank line before the table
        System.out.println(title); // Prints the title of the section (e.g., "Top 10 by Population")
        System.out.println();  // Another blank line for readability


        System.out.println("+--------+----------------------+-----------------+---------------------------+-----------------+----------------------+");
        System.out.printf("| %-6s | %-20s | %-15s | %-25s | %15s | %-20s |%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");
        System.out.println("+--------+----------------------+-----------------+---------------------------+-----------------+----------------------+");

        // Loop through the list of countries, but only up to the display limit
        for (int i = 0; i < Math.min(displayLimit, countries.size()); i++) {
            Country c = countries.get(i);  // Get the country at this position

            // Print each country's details in a neatly formatted row
            System.out.printf("| %-6s | %-20s | %-15s | %-25s | %,15d | %-20s |%n",
                    c.getCode(),
                    c.getName(),
                    c.getContinent(),
                    c.getRegion(),
                    c.getPopulation(),
                    c.getCapitalName());
        }

        System.out.println("+--------+----------------------+-----------------+---------------------------+-----------------+----------------------+");

        // If there are more countries than the display limit, mention that to the user
        if (countries.size() > displayLimit) {
            System.out.printf("Showing top %d of %d countries.%n", displayLimit, countries.size());
        }
    }
}