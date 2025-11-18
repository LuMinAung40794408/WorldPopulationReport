package com.group12.testing;

import com.group12.report.models.Capital;
import com.group12.report.reports.CapitalReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for CapitalReport.
 * Follows the same pattern as the lab's printSalaries tests:
 *  - null list
 *  - empty list
 *  - single element
 *  - more than displayLimit
 */
class CapitalReportTest {

    static CapitalReport report;

    @BeforeAll
    static void init() {
        // Same kind of limit you use in App (10)
        report = new CapitalReport(10);
    }

    /**
     * Case 1: capitals list is null.
     * Expectation: method should not throw, just print "No capital cities to display..."
     */
    @Test
    void displayCapitals_NullList_DoesNotCrash() {
        report.displayCapitals(null, "Null Capital List");
    }

    /**
     * Case 2: capitals list is empty.
     * Expectation: method should not throw.
     */
    @Test
    void displayCapitals_EmptyList_DoesNotCrash() {
        List<Capital> capitals = new ArrayList<>();
        report.displayCapitals(capitals, "Empty Capital List");
    }

    /**
     * Case 3: one normal capital.
     * Expectation: prints one row, no exception.
     */
    @Test
    void displayCapitals_SingleCapital_DoesNotCrash() {
        Capital c = new Capital(
                "Naypyidaw",
                "Myanmar",
                1_000_000L
        );

        List<Capital> capitals = List.of(c);
        report.displayCapitals(capitals, "Single Capital List");
    }

    /**
     * Case 4: more capitals than displayLimit (10).
     * Expectation: prints max 10 rows; if size > limit,
     * it may print "Showing top 10 of X capital cities." — we only care that it doesn't crash.
     */
    @Test
    void displayCapitals_MoreThanDisplayLimit_DoesNotCrash() {
        List<Capital> capitals = new ArrayList<>();

        // Create 12 fake capitals
        for (int i = 0; i < 12; i++) {
            capitals.add(new Capital(
                    "Capital" + i,
                    "Country" + i,
                    1_000_000L + i
            ));
        }

        report.displayCapitals(capitals, "More than displayLimit");
    }

    /**
     * Optional: test printCategory doesn't throw.
     */
    @Test
    void printCategory_DoesNotCrash() {
        report.printCategory("Capital Report Category");
    }
}
