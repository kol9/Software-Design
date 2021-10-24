package ru.akirakozov.sd.refactoring.products;

/**
 * @author Nikolay Yarlychenko
 */
public class ProductInfo {

    private final String name;
    private final int price;

    ProductInfo(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
