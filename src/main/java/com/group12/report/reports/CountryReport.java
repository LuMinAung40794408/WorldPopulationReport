package com.group12.report.reports;

import com.group12.report.models.Country;
import java.util.ArrayList;

public class CountryReport
{
    public void displayCountries(ArrayList<Country> countries)
    {
        if (countries == null)
        {
            System.out.println("No countries to display.");
            return;
        }

        System.out.printf("%-6s %-45s %-15s %-20s %-12s%n",
                "Code", "Name", "Continent", "Region", "Population");
        System.out.println("-------------------------------------------------------------------------------------");

        for (Country c : countries)
        {
            System.out.printf("%-6s %-45s %-15s %-20s %-12d%n",
                    c.code, c.name, c.continent, c.region, c.population);
        }
    }
}
