package ru.akirakozov.sd.refactoring.servlet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikolay Yarlychenko
 */

public class ProductsDatabaseManager {

    private static final String DATABASE_NAME = "PRODUCT";
    private static final String PRODUCT_COLUMN_NAME = "name";
    private static final String PRICE_COLUMN_NAME = "price";

    private final String databaseUrl;

    public ProductsDatabaseManager(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void createDatabase() throws SQLException {
        try (Connection c = DriverManager.getConnection(databaseUrl)) {
            String sql = "CREATE TABLE IF NOT EXISTS " + DATABASE_NAME +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public void dropDatabase() throws SQLException {
        try (Connection c = DriverManager.getConnection(databaseUrl)) {
            String sql = "DROP TABLE IF EXISTS " + DATABASE_NAME;
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public List<ProductInfo> getAllProducts() throws SQLException {
        List<ProductInfo> products = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(databaseUrl)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

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

    public void addProduct(String name, long price) throws SQLException {
        try (Connection c = DriverManager.getConnection(databaseUrl)) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }
}
