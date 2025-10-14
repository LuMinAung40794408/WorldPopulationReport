package com.group12.report.models;

/**
 * @author 40794512 Zayar Than Htike
 * The Capital class represents a capital city and its related information.
 * It includes the capital name, its country, and the population.
 *
 * <p>This class is used as a data model in the reporting system
 * to hold information retrieved from the database.</p>
 *
 */
public class Capital {

    // The name of the capital city
    private final String name;

    // The name of the country this capital belongs to
    private final String country;

    // The population of the capital city
    private final long population;

    /**
     * Constructor to initialize a Capital object with its details.
     *
     * @param name The name of the capital city
     * @param country The country that the capital belongs to
     * @param population The total population of the capital city
     */
    public Capital(String name, String country, long population) {
        this.name = name;
        this.country = country;
        this.population = population;
    }

    /**
     * Gets the name of the capital city.
     *
     * @return The capital city name
     */
    public String getName() { return name; }

    /**
     * Gets the name of the country the capital belongs to.
     *
     * @return The country name
     */
    public String getCountry() { return country; }

    /**
     * Gets the population of the capital city.
     *
     * @return The population value
     */
    public long getPopulation() { return population; }
}
