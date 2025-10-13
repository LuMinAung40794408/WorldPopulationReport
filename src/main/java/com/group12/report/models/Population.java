package com.group12.report.models;
/**
 * Represents population data at various geographic levels,
 * such as the world, continent, region, country, city, or district.
 *
 * This model supports multiple use cases:
 * - World / Continent / Region / Country population breakdown (with city/non-city)
 * - City-level population (with country and district info)
 * - District-level population (for a single country)
 */

public class Population {
    private final String name;               // World, continent, region, country, city, or district
    private final String country;            // For cities only
    private final String district;           // For cities and districts
    private final long totalPopulation;      // Always present
    private final Long cityPopulation;       // Optional
    private final Double cityPopulationPercent;
    private final Long nonCityPopulation;
    private final Double nonCityPopulationPercent;
    /**
     * Constructor for world, continent, region, or country-level reports.
     * These reports include city/non-city population breakdown.
     *
     * @param name                    Geographic name (World, Continent, Region, or Country)
     * @param totalPopulation         Total population of the entity
     * @param cityPopulation          Population living in cities
     * @param cityPopulationPercent   Percentage of population living in cities
     * @param nonCityPopulation       Population not living in cities
     * @param nonCityPopulationPercent Percentage of population not living in cities
     */

    // Constructor for world/continent/region/country with breakdown
    public Population(String name, long totalPopulation, long cityPopulation,
                      double cityPopulationPercent, long nonCityPopulation, double nonCityPopulationPercent) {
        this.name = name;
        this.country = null;
        this.district = null;
        this.totalPopulation = totalPopulation;
        this.cityPopulation = cityPopulation;
        this.cityPopulationPercent = cityPopulationPercent;
        this.nonCityPopulation = nonCityPopulation;
        this.nonCityPopulationPercent = nonCityPopulationPercent;
    }
    /**
     * Constructor for district-level population reports.
     * Used when displaying population grouped by district inside a country.
     *
     * @param district        District name
     * @param totalPopulation Total population of the district
     */

    // Constructor for district (name + total only)
    public Population(String district, long totalPopulation) {
        this.name = null;
        this.country = null;
        this.district = district;
        this.totalPopulation = totalPopulation;
        this.cityPopulation = null;
        this.cityPopulationPercent = null;
        this.nonCityPopulation = null;
        this.nonCityPopulationPercent = null;
    }
    /**
     * Constructor for city-level population reports.
     * Used when displaying population for all cities in the world or in a country.
     *
     * @param name            City name
     * @param country         Country name that the city belongs to
     * @param district        District where the city is located
     * @param totalPopulation Total population of the city
     */

    // Constructor for city (name, country, district, total only)
    public Population(String name, String country, String district, long totalPopulation) {
        this.name = name;
        this.country = country;
        this.district = district;
        this.totalPopulation = totalPopulation;
        this.cityPopulation = null;
        this.cityPopulationPercent = null;
        this.nonCityPopulation = null;
        this.nonCityPopulationPercent = null;
    }


    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getDistrict() { return district; }
    public long getTotalPopulation() { return totalPopulation; }
    public Long getCityPopulation() { return cityPopulation; }
    public Double getCityPopulationPercent() { return cityPopulationPercent; }
    public Long getNonCityPopulation() { return nonCityPopulation; }
    public Double getNonCityPopulationPercent() { return nonCityPopulationPercent; }
}