package model;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

public class StocksService {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String host;
    private final int port;

    public StocksService(final String host, final int port) {
        this.host = host;
        this.port = port;
    }

    private HttpResponse<String> doRequest(final HttpRequest request) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (final IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpResponse<String> getRequest(final String uri) {
        try {
            return doRequest(HttpRequest.newBuilder(new URI(uri)).GET().build());
        } catch (final URISyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private HttpResponse<String> postRequest(final String uri) {
        try {
            return doRequest(HttpRequest.newBuilder(new URI(uri)).POST(BodyPublishers.noBody()).build());
        } catch (final URISyntaxException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private String request(final Object... args) {
        final StringBuilder builder = new StringBuilder(String.format("%s:%d/exchange", host, port));
        for (final Object arg : args) {
            builder.append(arg);
        }
        return builder.toString();
    }

    private int getInteger(final HttpResponse<String> response) {
        if (checkSuccess(response)) {
            return Integer.parseInt(response.body());
        }
        return -1;
    }

    private double getDouble(final HttpResponse<String> response) {
        if (checkSuccess(response)) {
            return Double.parseDouble(response.body());
        }
        return -1;
    }

    private boolean getBoolean(final HttpResponse<String> response) {
        if (checkSuccess(response)) {
            return Boolean.parseBoolean(response.body());
        }
        return false;
    }

    public double getPrice(final int companyId) {
        final HttpResponse<String> response = getRequest(request("/get_price", "/", companyId));
        return getDouble(response);
    }

    public int getAmount(final int companyId) {
        final HttpResponse<String> response = getRequest(request("/get_amount", "/", companyId));
        return getInteger(response);
    }

    public boolean buy(final int companyId, final int amount) {
        final HttpResponse<String> response = postRequest(request("/buy",
                "?companyId=", companyId,
                "&amount=", amount));
        return checkSuccess(response);
    }

    public boolean sell(final int companyId, final int amount) {
        final HttpResponse<String> response = postRequest(request("/sell",
                "?companyId=", companyId,
                "&amount=", amount));
        return checkSuccess(response);
    }

    public boolean changePrice(final int companyId, final double newPrice) {
        final HttpResponse<String> response = postRequest(request("/change_price",
                "?companyId=", companyId,
                "&newPrice=", newPrice));
        return checkSuccess(response);
    }

    public int addCompany(final double initialPrice) {
        final HttpResponse<String> response = postRequest(request("/create_company", "/", initialPrice));
        return getInteger(response);
    }

    public boolean addStocks(final int companyId, final int amount) {
        final HttpResponse<String> response = postRequest(request("/add_stock",
                "?companyId=", companyId,
                "&amount=", amount));
        return checkSuccess(response);
    }

    private boolean checkSuccess(final HttpResponse<String> response) {
        return checkSuccess(response);
    }
}
