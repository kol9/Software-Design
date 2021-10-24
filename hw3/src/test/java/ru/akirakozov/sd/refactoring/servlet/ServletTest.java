package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ServletTest {

    @Before
    public void initialiseDatabaseIfNeeded() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @AfterEach
    public void dropTable() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "DROP TABLE PRODUCT";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void emptyGetProductsTest() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

            Assert.assertEquals(0, rs.getFetchSize());

            rs.close();
            stmt.close();
        } catch (Exception e) {
            Assert.fail();
        }
    }

}
