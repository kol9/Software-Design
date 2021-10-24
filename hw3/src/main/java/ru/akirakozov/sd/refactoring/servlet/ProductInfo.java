package ru.akirakozov.sd.refactoring.servlet;

/**
 * @author Nikolay Yarlychenko
 */
public class ProductInfo {

    private final String name;
    private final long price;

    ProductInfo(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }
}
