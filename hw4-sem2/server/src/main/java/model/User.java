package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final int id;
    private double money;
    private final List<Stock> stocks = new ArrayList<>();

    public User(final int id) {
        this.id = id;
        this.money = 0;
    }

    public User(final int id, final double money) {
        this.id = id;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public double getMoney() {
        return money;
    }

    public void makeDeposit(final double amount) {
        money += amount;
    }

    public Stock findStock(final int stockId) {
        for (final Stock stock : stocks) {
            if (stock.getId() == stockId) {
                return stock;
            }
        }
        return null;
    }

    public void addStock(final int stockId, final int amount) {
        final Stock stock = findStock(stockId);
        if (stock != null) {
            stock.put(amount);
        } else {
            stocks.add(new Stock(stockId, amount));
        }
    }

    public List<Stock> getStocks() {
        return stocks;
    }
}
