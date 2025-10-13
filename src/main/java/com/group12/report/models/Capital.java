package com.group12.report.models;

public class Capital {
    private final String name;
    private final String country;
    private final long population;

    public Capital(String name, String country, long population) {
        this.name = name;
        this.country = country;
        this.population = population;
    }

    public String getName() { return name; }
    public String getCountry() { return country; }
    public long getPopulation() { return population; }
}