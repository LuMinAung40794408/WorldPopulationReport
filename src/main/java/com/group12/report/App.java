package com.group12.report;

import com.group12.report.data_access.CountryDAO;
import com.group12.report.models.Country;
import com.group12.report.reports.CountryReport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class App
{
    private Connection con = null;

    public void connect(String location, int delay)
    {
        try
        {
            System.out.println("Connecting to database...");
            Thread.sleep(delay);
            con = DriverManager.getConnection(location, "root", "example");
            System.out.println("Connected successfully!");
        }
        catch (Exception e)
        {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public void disconnect()
    {
        try
        {
            if (con != null)
            {
                con.close();
                System.out.println("Disconnected.");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error closing connection.");
        }
    }

    public static void main(String[] args)
    {
        App app = new App();
        app.connect("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", 20000);

        CountryDAO dao = new CountryDAO(app.con);
        ArrayList<Country> countries = dao.getAllCountriesByPopulation();

        CountryReport report = new CountryReport();
        report.displayCountries(countries);

        app.disconnect();
    }
}
