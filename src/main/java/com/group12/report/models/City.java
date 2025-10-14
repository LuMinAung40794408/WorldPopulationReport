package com.group12.report.models;

/**
 * @author 40794374PhoneMyatKyaw
 */
public class City {
    private final String name;
    // Holds the city's name (e.g., "Yangon").

    private final String country;
    // Stores the country this city belongs to (joined from Country table).

    private final String district;
    // The district or administrative division within the country.

    private final long population;
    // City's population count as a long type to handle large values.

    public City(String name, String country, String district, long population) {
        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
    }
    // Constructor initializes all fields when a City object is created.

    public String getName() { return name; }
    // Returns the name of the city.

    public String getCountry() { return country; }
    // Returns the name of the country the city belongs to.

    public String getDistrict() { return district; }
    // Returns the district name.

    public long getPopulation() { return population; }
    // Returns the population count of the city.
}
