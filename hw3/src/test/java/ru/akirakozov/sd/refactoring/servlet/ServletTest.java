package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ServletTest {

    private static final String TEST_PRODUCT_NAME = "Book";
    private static final long TEST_PRODUCT_PRICE = 1000;

    @BeforeEach
    public void dropTable() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "DROP TABLE IF EXISTS PRODUCT";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @BeforeEach
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
            Assertions.fail();
        }
    }

    @Test
    public void emptyGetProductsTest() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

            long total = 0;
            while (rs.next()) {
                total += 1;
            }
            Assertions.assertEquals(0, total);

            rs.close();
            stmt.close();
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void simpleAddGetProductsTest() {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                String sql = "INSERT INTO PRODUCT " +
                        "(NAME, PRICE) VALUES (\"" + TEST_PRODUCT_NAME + "\"," + TEST_PRODUCT_PRICE + ")";
                Statement stmt = c.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (Exception e) {
            Assertions.fail();
        }


        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

            long total = 0;
            while (rs.next()) {
                total += 1;
                String name = rs.getString("name");
                int price = rs.getInt("price");
                Assertions.assertEquals(TEST_PRODUCT_NAME, name);
                Assertions.assertEquals(TEST_PRODUCT_PRICE, price);
            }
            Assertions.assertEquals(1, total);

            rs.close();
            stmt.close();
        } catch (Exception e) {
            Assertions.fail();
        }

    }

}
