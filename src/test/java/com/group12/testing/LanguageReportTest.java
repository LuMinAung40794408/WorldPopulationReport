package com.group12.testing;

import com.group12.report.models.Language;
import com.group12.report.reports.LanguageReport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for LanguageReport.
 * Pattern is the same as the lab (null / empty / normal / > limit).
 */
class LanguageReportTest {

    static LanguageReport reportWithCustomLimit;

    @BeforeAll
    static void init() {
        // Use the custom constructor (like in App.java where you use new LanguageReport(10))
        reportWithCustomLimit = new LanguageReport(10);
    }

    /**
     * Also exercise the default constructor to hit that code path.
     */
    @Test
    void defaultConstructor_DoesNotCrash() {
        LanguageReport defaultReport = new LanguageReport(); // displayLimit = 15
        defaultReport.printCategory("Default Constructor Language Report");
    }

    /**
     * Case 1: languages list is null.
     * Expectation: method should not throw, just print "No language data to display..."
     */
    @Test
    void displayLanguages_NullList_DoesNotCrash() {
        reportWithCustomLimit.displayLanguages(null, "Null Language List");
    }

    /**
     * Case 2: languages list is empty.
     * Expectation: method should not throw.
     */
    @Test
    void displayLanguages_EmptyList_DoesNotCrash() {
        List<Language> languages = new ArrayList<>();
        reportWithCustomLimit.displayLanguages(languages, "Empty Language List");
    }

    /**
     * Case 3: one normal language.
     * Expectation: prints one row, no exception.
     */
    @Test
    void displayLanguages_SingleLanguage_DoesNotCrash() {
        Language lang = new Language(
                "English",
                1_500_000_000L,  // fake speakers count
                19.5             // fake percent of world
        );

        List<Language> languages = List.of(lang);
        reportWithCustomLimit.displayLanguages(languages, "Single Language");
    }

    /**
     * Case 4: more languages than displayLimit (10).
     * Expectation: prints max 10 rows; if size > limit,
     * it prints "Showing top 10 of X languages." — we only care that it doesn't crash.
     */
    @Test
    void displayLanguages_MoreThanDisplayLimit_DoesNotCrash() {
        List<Language> languages = new ArrayList<>();

        // Create 12 fake languages
        for (int i = 0; i < 12; i++) {
            languages.add(new Language(
                    "Lang" + i,
                    10_000_000L + i, // fake speakers
                    0.5 + i          // fake percentage
            ));
        }

        reportWithCustomLimit.displayLanguages(languages, "More than displayLimit");
    }

    /**
     * Ensure printCategory itself is covered and does not throw.
     */
    @Test
    void printCategory_DoesNotCrash() {
        reportWithCustomLimit.printCategory("Language Report Category");
    }
}
