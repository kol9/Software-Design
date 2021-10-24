package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        try {
            ProductInfo product = databaseManager.getProductWithMinPrice();
            Assertions.assertEquals(Collections.min(prices), product.getPrice());
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
        try {
            int count = databaseManager.getProductsCount();
            Assertions.assertEquals(prices.size(), count);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void querySumTest() {
        List<Integer> prices = new ArrayList<>(Arrays.asList(3, 45, 100, 55, 32, 1, 64, 2));
        addSomeProducts(prices);
        try {
            int sum = databaseManager.getProductsPriceSum();
            Assertions.assertEquals(prices.stream().mapToInt(a -> a).sum(), sum);
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
