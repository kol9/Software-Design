package ru.akirakozov.sd.refactoring.products;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikolay Yarlychenko
 */

public class ProductsDatabaseManager {

    private static final String DATABASE_NAME = "PRODUCT";
    private static final String PRODUCT_COLUMN_NAME = "NAME";
    private static final String PRICE_COLUMN_NAME = "PRICE";

    private final String databaseUrl;

    public ProductsDatabaseManager(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void createDatabase() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + DATABASE_NAME +
                "(ID                    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                PRODUCT_COLUMN_NAME + " TEXT    NOT NULL, " +
                PRICE_COLUMN_NAME + "   INT     NOT NULL)";
        executeUpdateWithSqlRequest(sql);
    }

    public void dropDatabase() throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + DATABASE_NAME;
        executeUpdateWithSqlRequest(sql);
    }

    public List<ProductInfo> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM PRODUCT";
        return getProductListWithSqlRequest(sql);
    }

    public void addProduct(String name, long price) throws SQLException {
        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
        executeUpdateWithSqlRequest(sql);
    }

    public ProductInfo getProductWithMaxPrice() throws SQLException {
        return getProductWithSqlRequest("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
    }

    public ProductInfo getProductWithMinPrice() throws SQLException {
        return getProductWithSqlRequest("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
    }

    public int getProductsCount() throws SQLException {
        return getIntWithSqlRequest("SELECT COUNT(*) FROM PRODUCT");
    }

    public int getProductsPriceSum() throws SQLException {
        return getIntWithSqlRequest("SELECT SUM(price) FROM PRODUCT");
    }

    private void executeUpdateWithSqlRequest(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(databaseUrl)) {
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    private ProductInfo getProductWithSqlRequest(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(databaseUrl)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            String name = rs.getString(PRODUCT_COLUMN_NAME);
            int price = rs.getInt(PRICE_COLUMN_NAME);

            rs.close();
            stmt.close();

            return new ProductInfo(name, price);
        }
    }

    private List<ProductInfo> getProductListWithSqlRequest(String sql) throws SQLException {
        List<ProductInfo> products = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(databaseUrl)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString(PRODUCT_COLUMN_NAME);
                int price = rs.getInt(PRICE_COLUMN_NAME);
                products.add(new ProductInfo(name, price));
            }

            rs.close();
            stmt.close();
        }
        return products;
    }

    private int getIntWithSqlRequest(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(databaseUrl)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            int result = rs.getInt(1);

            rs.close();
            stmt.close();

            return result;
        }
    }
}
