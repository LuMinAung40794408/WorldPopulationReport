package com.group12.report.reports;

import com.group12.report.data_access.CountryDAO;
import com.group12.report.models.Country;
import java.util.List;

public class CountryReport {
    private CountryDAO countryDAO;

    public CountryReport(CountryDAO dao) {
        this.countryDAO = dao;
    }

    public void printAllCountriesByPopulation() {
        try {
            List<Country> countries = countryDAO.getAllCountriesByPopulation();
            System.out.printf("%-5s %-30s %-20s %-20s %-10s%n", "Code", "Name", "Continent", "Region", "Population");
            for (Country c : countries) {
                System.out.printf("%-5s %-30s %-20s %-20s %-10d%n",
                        c.getCode(), c.getName(), c.getContinent(), c.getRegion(), c.getPopulation());
            }
        } catch (Exception e) {
            System.out.println("Error generating country report: " + e.getMessage());
        }
    }
}
