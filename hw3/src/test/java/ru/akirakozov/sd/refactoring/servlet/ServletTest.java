package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServletTest {

    private static final String TEST_DATABASE_URL = "jdbc:sqlite:test.db";

    private static final String TEST_PRODUCT_NAME = "Book";
    private static final long TEST_PRODUCT_PRICE = 1000;

    private final ProductsDatabaseManager databaseManager = new ProductsDatabaseManager(TEST_DATABASE_URL);

    @BeforeEach
    public void dropTable() {
        try {
            databaseManager.dropDatabase();
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @BeforeEach
    public void initialiseDatabaseIfNeeded() {
        try {
            databaseManager.createDatabase();
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void emptyGetProductsTest() {
        try {
            Assertions.assertEquals(0, databaseManager.getAllProducts().size());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void simpleAddGetProductsTest() {
        try {
            databaseManager.addProduct(TEST_PRODUCT_NAME, TEST_PRODUCT_PRICE);
            List<ProductInfo> products = databaseManager.getAllProducts();

            Assertions.assertEquals(1, products.size());
            Assertions.assertEquals(TEST_PRODUCT_NAME, products.get(0).getName());
            Assertions.assertEquals(TEST_PRODUCT_PRICE, products.get(0).getPrice());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void queryMinTest() {
        List<Integer> prices = new ArrayList<>(Arrays.asList(3, 45, 100, 55, 32, 1, 64, 2));
        addSomeProducts(prices);

        try (Connection c = DriverManager.getConnection(TEST_DATABASE_URL)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");

            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                Assertions.assertEquals(Collections.min(prices), price);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void queryMaxTest() {
        List<Integer> prices = new ArrayList<>(Arrays.asList(3, 45, 100, 55, 32, 1, 64, 2));
        addSomeProducts(prices);
        try {
            ProductInfo product = databaseManager.getProductWithMaxPrice();
            Assertions.assertEquals(Collections.max(prices), product.getPrice());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void queryCountTest() {
        List<Integer> prices = new ArrayList<>(Arrays.asList(3, 45, 100, 55, 32, 1, 64, 2));
        addSomeProducts(prices);

        try (Connection c = DriverManager.getConnection(TEST_DATABASE_URL)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");

            while (rs.next()) {
                int count = rs.getInt(1);
                Assertions.assertEquals(prices.size(), count);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void querySumTest() {
        List<Integer> prices = new ArrayList<>(Arrays.asList(3, 45, 100, 55, 32, 1, 64, 2));
        addSomeProducts(prices);

        try (Connection c = DriverManager.getConnection(TEST_DATABASE_URL)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");

            while (rs.next()) {
                int sum = rs.getInt(1);
                Assertions.assertEquals(prices.stream().mapToInt(a -> a).sum(), sum);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            Assertions.fail();
        }

    }

    // Helpers

    private void addSomeProducts(List<Integer> prices) {
        try {
            for (int i = 0; i < prices.size(); ++i) {
                databaseManager.addProduct("product" + i, prices.get(i));
            }
        } catch (Exception e) {
            Assertions.fail();
        }
    }

}
