package com.group12.report.data_access;

import com.group12.report.models.Country;
import java.sql.*;
import java.util.ArrayList;

public class CountryDAO
{
    private Connection con;

    public CountryDAO(Connection con)
    {
        this.con = con;
    }

    public ArrayList<Country> getAllCountriesByPopulation()
    {
        ArrayList<Country> countries = new ArrayList<>();
        try
        {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT Code, Name, Continent, Region, Population, Capital "
                            + "FROM country "
                            + "ORDER BY Population DESC";
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next())
            {
                Country c = new Country();
                c.code = rset.getString("Code");
                c.name = rset.getString("Name");
                c.continent = rset.getString("Continent");
                c.region = rset.getString("Region");
                c.population = rset.getInt("Population");
                c.capital = rset.getInt("Capital");
                countries.add(c);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get countries report.");
        }
        return countries;
    }

}
