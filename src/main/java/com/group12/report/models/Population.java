package com.group12.report.models;
/**
 * Represents population statistics for continent/region/country.
 */
public class Population {
    private String name; // Continent, region, or country
    private long totalPopulation;
    private long cityPopulation;
    private long nonCityPopulation;
    private double cityPercentage;
    private double nonCityPercentage;

    public Population() {}

    public Population(String name, long totalPopulation, long cityPopulation, long nonCityPopulation) {
        this.name = name;
        this.totalPopulation = totalPopulation;
        this.cityPopulation = cityPopulation;
        this.nonCityPopulation = nonCityPopulation;
        calculatePercentages();
    }

    private void calculatePercentages() {
        if (totalPopulation > 0) {
            this.cityPercentage = (cityPopulation * 100.0) / totalPopulation;
            this.nonCityPercentage = (nonCityPopulation * 100.0) / totalPopulation;
        }
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getTotalPopulation() { return totalPopulation; }
    public void setTotalPopulation(long totalPopulation) { this.totalPopulation = totalPopulation; }

    public long getCityPopulation() { return cityPopulation; }
    public void setCityPopulation(long cityPopulation) { this.cityPopulation = cityPopulation; }

    public long getNonCityPopulation() { return nonCityPopulation; }
    public void setNonCityPopulation(long nonCityPopulation) { this.nonCityPopulation = nonCityPopulation; }

    public double getCityPercentage() { return cityPercentage; }
    public double getNonCityPercentage() { return nonCityPercentage; }

    @Override
    public String toString() {
        return String.format("%-25s %-15d %-15d (%.2f%%) %-15d (%.2f%%)",
                name, totalPopulation, cityPopulation, cityPercentage,
                nonCityPopulation, nonCityPercentage);
    }
}
