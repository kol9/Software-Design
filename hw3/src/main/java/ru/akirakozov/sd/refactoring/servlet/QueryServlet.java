package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.products.ProductInfo;
import ru.akirakozov.sd.refactoring.products.ProductsDatabaseManager;
import ru.akirakozov.sd.refactoring.products.ProductsHtmlResponseWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private enum SupportedCommand {
        MIN,
        MAX,
        SUM,
        COUNT
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command").toUpperCase(Locale.ROOT);

        ProductsDatabaseManager manager = new ProductsDatabaseManager("jdbc:sqlite:products.db");
        ProductsHtmlResponseWriter responseWriter = new ProductsHtmlResponseWriter(response);

        try {
            SupportedCommand cmd = SupportedCommand.valueOf(command);

            switch (cmd) {
                case MIN:
                    ProductInfo product = manager.getProductWithMinPrice();
                    responseWriter.printSingleProductInfo(product, "Product with min price:");
                    break;
                case MAX:
                    product = manager.getProductWithMaxPrice();
                    responseWriter.printSingleProductInfo(product, "Product with max price:");
                    break;
                case SUM:
                    int sum = manager.getProductsPriceSum();
                    responseWriter.printSingleString("Summary price: " + sum);
                    break;
                case COUNT:
                    int count = manager.getProductsCount();
                    responseWriter.printSingleString("Number of products: " + count);
                    break;
            }

        } catch (IllegalArgumentException unknownCommandType) {
            responseWriter.printSingleString("Unknown command: " + command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
