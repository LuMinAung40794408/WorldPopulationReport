package com.group12.report.reports;

import com.group12.report.models.Capital;
import java.util.List;

public class CapitalReport {

    private final int displayLimit;

    public CapitalReport(int displayLimit) {
        this.displayLimit = displayLimit;
    }

    public void printCategory(String categoryName) {
        System.out.println("\n================ Capital Report ================\n");
    }

    public void displayCapitals(List<Capital> capitals, String title) {
        if (capitals == null || capitals.isEmpty()) {
            System.out.println("No capital cities to display for: " + title);
            return;
        }

        System.out.println("\n" + title + "\n");

        System.out.println("+----------------------+----------------------+-----------------+");
        System.out.printf("| %-20s | %-20s | %15s |%n", "Capital", "Country", "Population");
        System.out.println("+----------------------+----------------------+-----------------+");

        for (int i = 0; i < Math.min(displayLimit, capitals.size()); i++) {
            Capital c = capitals.get(i);
            System.out.printf("| %-20s | %-20s | %,15d |%n",
                    c.getName(),
                    c.getCountry(),
                    c.getPopulation());
        }

        System.out.println("+----------------------+----------------------+-----------------+");

        if (capitals.size() > displayLimit) {
            System.out.printf("Showing top %d of %d capital cities.%n", displayLimit, capitals.size());
        }
    }
}