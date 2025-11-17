package com.group12.testing;

import com.group12.report.models.City;
import com.group12.report.reports.CityReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for CityReport.
 * Follows the same style as the lab's printSalaries tests:
 *  - null list
 *  - empty list
 *  - single element
 *  - more than displayLimit
 */
class CityReportTest {

    static CityReport report;

    @BeforeAll
    static void init() {
        // Same display limit you use in App.java (10)
        report = new CityReport(10);
    }

    /**
     * Case 1: cities list is null.
     * Expectation: method should not throw, just print "No cities to display..."
     */
    @Test
    void displayCities_NullList_DoesNotCrash() {
        report.displayCities(null, "Null City List");
    }

    /**
     * Case 2: cities list is empty.
     * Expectation: method should not throw.
     */
    @Test
    void displayCities_EmptyList_DoesNotCrash() {
        List<City> cities = new ArrayList<>();
        report.displayCities(cities, "Empty City List");
    }

    /**
     * Case 3: one normal city.
     * Expectation: prints one row, no exception.
     */
    @Test
    void displayCities_SingleCity_DoesNotCrash() {
        City c = new City(
                "Yangon",
                "Myanmar",
                "Yangon",
                5_000_000L
        );

        List<City> cities = List.of(c);
        report.displayCities(cities, "Single City List");
    }

    /**
     * Case 4: more cities than displayLimit (10).
     * Expectation: prints max 10 rows; if size > limit,
     * it may print "Showing top 10 of X cities." — we only care that it doesn't crash.
     */
    @Test
    void displayCities_MoreThanDisplayLimit_DoesNotCrash() {
        List<City> cities = new ArrayList<>();

        // Create 12 fake cities
        for (int i = 0; i < 12; i++) {
            cities.add(new City(
                    "City" + i,
                    "Country" + i,
                    "District" + i,
                    1_000_000L + i
            ));
        }

        report.displayCities(cities, "More than displayLimit");
    }
    @Test
    void printCategory_DoesNotCrash() {
        report.printCategory("City Report Category");
    }
}
