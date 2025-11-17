package com.group12.testing;

import com.group12.report.models.Population;
import com.group12.report.reports.PopulationReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for PopulationReport.
 * Pattern is the same as CapitalReportTest:
 *  - null list
 *  - empty list
 *  - normal list (within display limit)
 *  - list larger than display limit
 */
class PopulationReportTest {

    static PopulationReport reportDefault;   // uses default limit (15)
    static PopulationReport reportLimit5;    // custom smaller limit to hit "showing top X of Y" branch

    @BeforeAll
    static void init() {
        reportDefault = new PopulationReport();
        reportLimit5  = new PopulationReport(5);
    }

    // ================== displayPopulations ==================

    @Test
    void displayPopulations_NullList_DoesNotCrash() {
        // Like printSalariesTestNull in the lab
        reportLimit5.displayPopulations(null, "Null population list");
    }

    @Test
    void displayPopulations_EmptyList_DoesNotCrash() {
        reportLimit5.displayPopulations(new ArrayList<>(), "Empty population list");
    }

    @Test
    void displayPopulations_NormalList_WithinLimit() {
        List<Population> list = new ArrayList<>();
        list.add(new Population("World", 8_000_000_000L, 4_000_000_000L,
                50.0, 4_000_000_000L, 50.0));
        list.add(new Population("Asia", 4_500_000_000L, 2_500_000_000L,
                55.0, 2_000_000_000L, 45.0));

        reportLimit5.displayPopulations(list, "World/Continent populations");
    }

    @Test
    void displayPopulations_MoreThanDisplayLimit_ShowsTopOnly() {
        List<Population> list = new ArrayList<>();
        // create 7 entries while displayLimit is 5 -> triggers "showing top X of Y" line
        for (int i = 0; i < 7; i++) {
            list.add(new Population(
                    "Region" + i,
                    1_000_000L + i,
                    500_000L + i,
                    50.0,
                    500_000L + i,
                    50.0
            ));
        }

        reportLimit5.displayPopulations(list, "More than displayLimit (regions)");
    }

    // ================== displayDistrictPopulations ==================

    @Test
    void displayDistrictPopulations_NullList_DoesNotCrash() {
        reportDefault.displayDistrictPopulations(null, "Null district list");
    }

    @Test
    void displayDistrictPopulations_EmptyList_DoesNotCrash() {
        reportDefault.displayDistrictPopulations(new ArrayList<>(), "Empty district list");
    }

    @Test
    void displayDistrictPopulations_NormalList_WithinLimit() {
        List<Population> districts = new ArrayList<>();
        districts.add(new Population("District A", 150_000L));
        districts.add(new Population("District B", 200_000L));

        reportDefault.displayDistrictPopulations(districts, "District populations");
    }

    @Test
    void displayDistrictPopulations_MoreThanDisplayLimit_ShowsTopOnly() {
        List<Population> districts = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            districts.add(new Population("District" + i, 50_000L + i));
        }

        reportLimit5.displayDistrictPopulations(districts, "Many districts");
    }

    // ================== displayCityPopulations ==================

    @Test
    void displayCityPopulations_NullList_DoesNotCrash() {
        reportDefault.displayCityPopulations(null, "Null city list");
    }

    @Test
    void displayCityPopulations_EmptyList_DoesNotCrash() {
        reportDefault.displayCityPopulations(new ArrayList<>(), "Empty city list");
    }

    @Test
    void displayCityPopulations_NormalList_WithinLimit() {
        List<Population> cities = new ArrayList<>();
        cities.add(new Population("Yangon", "Myanmar", "Yangon", 5_000_000L));
        cities.add(new Population("Mandalay", "Myanmar", "Mandalay", 1_200_000L));

        reportDefault.displayCityPopulations(cities, "Myanmar cities");
    }

    @Test
    void displayCityPopulations_MoreThanDisplayLimit_ShowsTopOnly() {
        List<Population> cities = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            cities.add(new Population(
                    "City" + i,
                    "Country" + i,
                    "District" + i,
                    100_000L + i
            ));
        }

        reportLimit5.displayCityPopulations(cities, "Many cities");
    }
    @Test
    void printCategory_PrintsCorrectHeader() {
        PopulationReport report = new PopulationReport(10);

        // Capture System.out
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            // Act
            report.printCategory("Anything");

            // Assert
            String printed = out.toString();
            assertTrue(printed.contains("================ Population Report ================"));

        } finally {
            // Restore System.out
            System.setOut(oldOut);
        }
    }

}
