package com.group12.report.models;

/**
 * Represents a language entity with its global speaker statistics.
 *
 * This model is typically used to store results retrieved from
 * the `LanguageDAO` class, containing:
 * - The language name.
 * - The total number of speakers worldwide.
 * - The percentage of the world population speaking the language.
 */
public class Language {

    /** The name of the language (e.g., English, Mandarin, Spanish). */
    private final String name;

    /** The total number of speakers worldwide for this language. */
    private final long speakers;

    /** The percentage of the total world population that speaks this language. */
    private final double percentOfWorld;

    /**
     * Constructs a new Language object.
     *
     * @param name           The name of the language.
     * @param speakers       The total number of speakers (calculated sum).
     * @param percentOfWorld The percentage of the world population speaking this language.
     */
    public Language(String name, long speakers, double percentOfWorld) {
        this.name = name;
        this.speakers = speakers;
        this.percentOfWorld = percentOfWorld;
    }

    /**
     * Retrieves the language name.
     *
     * @return The name of the language.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the total number of speakers for this language.
     *
     * @return The total number of speakers.
     */
    public long getSpeakers() {
        return speakers;
    }

    /**
     * Retrieves the percentage of the world population that speaks this language.
     *
     * @return The percentage (0–100) of the world population speaking this language.
     */
    public double getPercentOfWorld() {
        return percentOfWorld;
    }
}
