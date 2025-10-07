package com.group12.report.models;
/**
 * Represents a capital city report record.
 */
public class Capital {
    private String name;
    private String country;
    private int population;

    public Capital() {}

    public Capital(String name, String country, int population) {
        this.name = name;
        this.country = country;
        this.population = population;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public int getPopulation() { return population; }
    public void setPopulation(int population) { this.population = population; }

    @Override
    public String toString() {
        return String.format("%-30s %-30s %-10d", name, country, population);
    }
}
