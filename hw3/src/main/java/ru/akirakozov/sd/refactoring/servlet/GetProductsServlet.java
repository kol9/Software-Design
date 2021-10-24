package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.products.ProductInfo;
import ru.akirakozov.sd.refactoring.products.ProductsDatabaseManager;
import ru.akirakozov.sd.refactoring.products.ProductsHtmlResponseWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        ProductsDatabaseManager manager = new ProductsDatabaseManager("jdbc:sqlite:products.db");
        ProductsHtmlResponseWriter responseWriter = new ProductsHtmlResponseWriter(response);
        try {
            List<ProductInfo> products = manager.getAllProducts();
            responseWriter.printProductsInfo(products);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
