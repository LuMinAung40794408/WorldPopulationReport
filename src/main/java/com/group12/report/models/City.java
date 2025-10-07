package com.group12.report.models;
/**
 * Represents a city record in the database.
 */
public class City {
    private int id;
    private String name;
    private String country;  // Country name
    private String district;
    private int population;

    public City() {}

    public City(int id, String name, String country, String district, int population) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public int getPopulation() { return population; }
    public void setPopulation(int population) { this.population = population; }

    @Override
    public String toString() {
        return String.format("%-30s %-30s %-25s %-10d",
                name, country, district, population);
    }
}
