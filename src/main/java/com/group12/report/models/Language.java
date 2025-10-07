package com.group12.report.models;
/**
 * Represents the number of people speaking a specific language.
 */
public class Language {
    private String language;
    private long speakers;
    private double worldPercentage;

    public Language() {}

    public Language(String language, long speakers, double worldPercentage) {
        this.language = language;
        this.speakers = speakers;
        this.worldPercentage = worldPercentage;
    }

    // Getters and Setters
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public long getSpeakers() { return speakers; }
    public void setSpeakers(long speakers) { this.speakers = speakers; }

    public double getWorldPercentage() { return worldPercentage; }
    public void setWorldPercentage(double worldPercentage) { this.worldPercentage = worldPercentage; }

    @Override
    public String toString() {
        return String.format("%-20s %-15d %.2f%%", language, speakers, worldPercentage);
    }
}
