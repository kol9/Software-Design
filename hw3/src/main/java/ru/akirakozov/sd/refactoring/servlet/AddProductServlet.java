package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.products.ProductsDatabaseManager;
import ru.akirakozov.sd.refactoring.products.ProductsHtmlResponseWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter(ProductsDatabaseManager.PRODUCT_COLUMN_NAME);
        long price = Long.parseLong(request.getParameter(ProductsDatabaseManager.PRICE_COLUMN_NAME));

        ProductsDatabaseManager manager = new ProductsDatabaseManager("jdbc:sqlite:products.db");
        ProductsHtmlResponseWriter responseWriter = new ProductsHtmlResponseWriter(response);

        try {
            manager.addProduct(name, price);
            responseWriter.printSingleString("OK");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
