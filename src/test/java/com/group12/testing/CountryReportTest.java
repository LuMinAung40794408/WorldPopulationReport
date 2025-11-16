package com.group12.testing;

import com.group12.report.models.Country;
import com.group12.report.reports.CountryReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for CountryReport.
 * Follows the same style as the lab's printSalaries tests:
 *  - null list
 *  - empty list
 *  - normal list
 */
class CountryReportTest {

    static CountryReport report;

    @BeforeAll
    static void init() {
        // same display limit you use in App (10)
        report = new CountryReport(10);
    }

    @Test
    void displayCountries_NullList_DoesNotCrash() {
        // Like "employees is null" in the lab – just call method.
        // If it throws an exception, the test will fail.
        report.displayCountries(null, "Null Country List");
    }

    @Test
    void displayCountries_EmptyList_DoesNotCrash() {
        List<Country> countries = new ArrayList<>();
        report.displayCountries(countries, "Empty Country List");
    }

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

    @Test
    void displayCountries_MoreThanDisplayLimit_DoesNotCrash() {
        // Build 12 fake countries, displayLimit = 10
        List<Country> countries = new ArrayList<>();

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
