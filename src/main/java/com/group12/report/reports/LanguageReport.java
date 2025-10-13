package com.group12.report.reports;

import com.group12.report.models.Language;
import java.util.List;

/**
 * The {@code LanguageReport} class is responsible for displaying
 * formatted reports of language statistics, including the total number
 * of speakers and percentage of world population.
 *
 * It presents data in a readable tabular format and limits
 * the number of rows displayed (default = 15).
 */
public class LanguageReport {

    /** The maximum number of language records to display in a report. */
    private final int displayLimit;

    /**
     * Default constructor.
     * Initializes the report to show a maximum of 15 languages.
     */
    public LanguageReport() {
        this.displayLimit = 15;
    }

    /**
     * Overloaded constructor that allows a custom display limit.
     *
     * @param displayLimit The maximum number of rows to display in the output table.
     */
    public LanguageReport(int displayLimit) {
        this.displayLimit = displayLimit;
    }

    /**
     * Prints the report category heading.
     *
     * @param categoryName A label for the type of report (not currently used,
     *                     but can be used to show different report categories later).
     */
    public void printCategory(String categoryName) {
        System.out.println("\n================ " + "Language Report" + " ================\n");
    }

    /**
     * Displays the list of languages in a neatly formatted table.
     *
     * <p>Each row shows:
     * <ul>
     *     <li>Language name</li>
     *     <li>Total speakers</li>
     *     <li>Percent of world population</li>
     * </ul>
     * </p>
     *
     * <p>If the number of records exceeds {@code displayLimit}, the report will
     * only show the top entries and print a note indicating that some rows
     * have been omitted.</p>
     *
     * @param languages A list of {@link Language} objects to display.
     * @param title     The title of the report section (e.g., "Top Languages in the World").
     */
    public void displayLanguages(List<Language> languages, String title) {
        // Handle the case where there is no data to display.
        if (languages == null || languages.isEmpty()) {
            System.out.println("No language data to display for: " + title);
            return;
        }

        // Print report title.
        System.out.println("\n" + title + "\n");

        // Print table header with column borders.
        System.out.println("+----------------------+-----------------+----------------------+");
        System.out.printf("| %-20s | %15s | %20s |%n", "Language", "Speakers", "Percent of World");
        System.out.println("+----------------------+-----------------+----------------------+");

        // Loop through each language and display formatted data.
        // Only show up to the display limit.
        for (int i = 0; i < Math.min(displayLimit, languages.size()); i++) {
            Language l = languages.get(i);
            System.out.printf("| %-20s | %,15d | %19.2f%% |%n",
                    l.getName(),              // Language name
                    l.getSpeakers(),          // Total speakers (formatted with commas)
                    l.getPercentOfWorld());   // Percentage of world population
        }

        // Print table footer line.
        System.out.println("+----------------------+-----------------+----------------------+");

        // If the data list exceeds the display limit, show an informational message.
        if (languages.size() > displayLimit) {
            System.out.printf("Showing top %d of %d languages.%n", displayLimit, languages.size());
        }
    }
}
