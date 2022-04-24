package model;

public class Stock {
    private final int id;
    private int amount;

    public Stock(final int id, final int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void put(final int amount) {
        this.amount += amount;
        assert this.amount >= 0;
    }

    public int getAmount() {
        return amount;
    }
}
