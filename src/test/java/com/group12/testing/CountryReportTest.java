package com.group12.testing;

import com.group12.report.models.Country;
import com.group12.report.reports.CountryReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for CountryReport.
 * Pattern is the same as the lab's printSalaries tests:
 *  - null list
 *  - empty list
 *  - single element
 *  - more than displayLimit
 */
class CountryReportTest {

    static CountryReport report;

    @BeforeAll
    static void init() {
        // Same display limit as App.java (you use 10 there)
        report = new CountryReport(10);
    }

    /**
     * Case 1: countries list is null.
     * Expectation: no exception, just prints "No countries to display..."
     */
    @Test
    void displayCountries_NullList_DoesNotCrash() {
        report.displayCountries(null, "Null Country List");
    }

    /**
     * Case 2: countries list is empty.
     * Expectation: no exception.
     */
    @Test
    void displayCountries_EmptyList_DoesNotCrash() {
        List<Country> countries = new ArrayList<>();
        report.displayCountries(countries, "Empty Country List");
    }

    /**
     * Case 3: one normal Country.
     * Expectation: prints one row, no exception.
     */
    @Test
    void displayCountries_SingleCountry_DoesNotCrash() {
        Country c = new Country(
                "MMR",
                "Myanmar",
                "Asia",
                "Southeast Asia",
                54_000_000L,
                null,
                "Naypyidaw"
        );

        List<Country> countries = List.of(c);
        report.displayCountries(countries, "Single Country List");
    }

    /**
     * Case 4: more items than displayLimit (10).
     * Expectation: prints at most 10, maybe shows "Showing top 10 of 12 countries.",
     * but we only care that it does not throw.
     */
    @Test
    void displayCountries_MoreThanDisplayLimit_DoesNotCrash() {
        List<Country> countries = new ArrayList<>();

        // Create 12 fake countries
        for (int i = 0; i < 12; i++) {
            countries.add(new Country(
                    "C" + i,
                    "Country" + i,
                    "Continent" + i,
                    "Region" + i,
                    1_000_000L + i,
                    null,
                    "Capital" + i
            ));
        }

        report.displayCountries(countries, "More than displayLimit");
    }
}
