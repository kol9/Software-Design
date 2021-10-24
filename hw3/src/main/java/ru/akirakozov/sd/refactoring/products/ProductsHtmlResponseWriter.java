package ru.akirakozov.sd.refactoring.products;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Nikolay Yarlychenko
 */
public class ProductsHtmlResponseWriter {
    private final HttpServletResponse response;

    public ProductsHtmlResponseWriter(HttpServletResponse response) {
        response.setContentType("text/html");
        this.response = response;
    }

    public void printSingleProductInfo(ProductInfo product, String header) throws IOException {
        printHead();
        printSingleString("<h1>" + header + "</h1>");
        print(product.getName() + "\t" + product.getPrice() + "</br>");
        printFooter();
    }

    public void printSingleString(String s) throws IOException {
        printHead();
        print(s);
        printFooter();
    }

    public void printProductsInfo(List<ProductInfo> products) throws IOException {
        printHead();
        for (ProductInfo product : products) {
            print(product.getName() + "\t" + product.getPrice() + "</br>");
        }
        printFooter();
    }

    private void printHead() throws IOException {
        print("<html><body>");
    }

    private void printFooter() throws IOException {
        print("</body></html>");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void print(String s) throws IOException {
        response.getWriter().println(s);
    }

}
