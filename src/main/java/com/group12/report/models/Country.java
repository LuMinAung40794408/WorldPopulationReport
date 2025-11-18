package com.group12.report.models;

/**
 * @author 40794374 Thu Ta Minn Lu
 */

// This class represents a Country record from the database.
public class Country {
    private final String code;
    private final String name;
    private final String continent;
    private final String region;
    private final long population;
    private final Integer capitalId;   // nullable
    private final String capitalName;  // nullable

    // Constructor – used to create a Country object and fill in all its details
    public Country(String code, String name, String continent, String region,
                   long population, Integer capitalId, String capitalName) {
        this.code = code;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capitalId = capitalId;
        this.capitalName = capitalName;
    }

    // Below are simple "getter" methods. They let other parts of the program
    // read these values, but not change them (since fields are 'final').
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public String getRegion() {
        return region;
    }

    public long getPopulation() {
        return population;
    }

    public String getCapitalName() {
        return capitalName;
    }
}