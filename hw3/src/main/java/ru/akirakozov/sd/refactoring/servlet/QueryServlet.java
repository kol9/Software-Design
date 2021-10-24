package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.products.ProductInfo;
import ru.akirakozov.sd.refactoring.products.ProductsDatabaseManager;
import ru.akirakozov.sd.refactoring.products.ProductsHtmlResponseWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        ProductsDatabaseManager manager = new ProductsDatabaseManager("jdbc:sqlite:products.db");
        ProductsHtmlResponseWriter responseWriter = new ProductsHtmlResponseWriter(response);

        if ("max".equals(command)) {
            try {
                ProductInfo product = manager.getProductWithMaxPrice();
                responseWriter.printSingleProductInfo(product, "Product with max price:");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                ProductInfo product = manager.getProductWithMinPrice();
                responseWriter.printSingleProductInfo(product, "Product with min price:");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                int sum = manager.getProductsPriceSum();
                responseWriter.printSingleString("Summary price: " + sum);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                int count = manager.getProductsCount();
                responseWriter.printSingleString("Number of products: " + count);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
    }

}
