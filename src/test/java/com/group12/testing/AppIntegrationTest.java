package com.group12.testing;

import com.group12.report.App;
import com.group12.report.data_access.*;

import com.group12.report.models.*;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.group12.testing.AppTest.app;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppIntegrationTest {

    static Connection conn;
    static CapitalDAO capitalDAO;
    static CityDAO cityDAO;
    static CountryDAO countryDAO;
    static LanguageDAO languageDAO;
    static PopulationDAO populationDAO;

    @BeforeAll
    static void init() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        App app = new App();
        // For local testing – same as your main():
        app.connect("localhost:33060", 30000);

        // Make con visible or add a getter in App:
        conn = app.con;   // or app.getConnection()

        capitalDAO = new CapitalDAO(conn);
        cityDAO = new CityDAO(conn);
        countryDAO = new CountryDAO(conn);
        languageDAO = new LanguageDAO(conn);
        populationDAO = new PopulationDAO(conn);
    }

    @AfterAll
    static void tearDown() {
        if (app != null) {
            app.disconnect();
        }
    }
}

