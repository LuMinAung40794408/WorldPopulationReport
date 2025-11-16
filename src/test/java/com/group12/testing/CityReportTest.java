package com.group12.testing;

import com.group12.report.models.City;
import com.group12.report.reports.CityReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CityReportTest {

    static CityReport report;

    @BeforeAll
    static void init() {
        // Same display limit as in App.java
        report = new CityReport(10);
    }

    @Test
    void displayCities_NullList_DoesNotCrash() {
        // Like "employees is null" test in the lab
        report.displayCities(null, "Null City List");
        // If this throws an exception, the test will fail automatically.
    }

    @Test
    void displayCities_EmptyList_DoesNotCrash() {
        List<City> cities = new ArrayList<>();
        report.displayCities(cities, "Empty City List");
    }

    @Test
    void displayCities_ListWithOneCity_DoesNotCrash() {
        City c = new City("Yangon", "Myanmar", "Yangon", 5000000L);
        List<City> cities = List.of(c);
        report.displayCities(cities, "Single City List");
    }
}
