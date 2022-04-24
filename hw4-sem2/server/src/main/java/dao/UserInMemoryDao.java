package dao;

import model.Stock;
import model.StocksService;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserInMemoryDao implements UserDao {
    private final StocksService service;
    private final List<User> users = new ArrayList<>();

    public UserInMemoryDao(final StocksService service) {
        this.service = service;
    }

    private boolean isUserExists(final int userId) {
        return users.size() <= userId;
    }

    @Override
    public int createUser() {
        users.add(new User(users.size()));
        return users.size() - 1;
    }

    @Override
    public boolean makeDeposit(final int userId, final double amount) {
        if (!isUserExists(userId)) {
            return false;
        }
        users.get(userId).makeDeposit(amount);
        return true;
    }

    @Override
    public List<Stock> getStocks(final int userId) {
        if (!isUserExists(userId)) {
            return null;
        }
        return users.get(userId).getStocks();
    }

    @Override
    public double getFullPortfolioValue(final int userId) {
        if (!isUserExists(userId)) {
            return -1;
        }

        double total = users.get(userId).getMoney();
        for (var stock : users.get(userId).getStocks()) {
            total += service.getPrice(stock.getId()) * stock.getAmount();
        }
        return total;
    }

    @Override
    public boolean buyStock(final int userId, final int companyId, final int amount) {
        if (!isUserExists(userId)) {
            return false;
        }

        final User user = users.get(userId);
        final double price = service.getPrice(companyId);
        if (user.getMoney() < price * amount) {
            return false;
        }

        if (service.buy(companyId, amount)) {
            user.addStock(companyId, amount);
            user.makeDeposit(-price * amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean sellStock(final int companyId, final int amount, final int userId) {
        if (!isUserExists(userId)) {
            return false;
        }

        final User user = users.get(userId);
        final Stock owns = user.findStock(companyId);
        if (owns.getAmount() < amount) {
            return false;
        }

        if (service.sell(companyId, amount)) {
            user.addStock(companyId, -amount);
            user.makeDeposit(service.getPrice(companyId) * amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean changePrice(final int companyId, final double newPrice, final int userId) {
        if (!isUserExists(userId)) {
            return false;
        }

        return service.changePrice(companyId, newPrice);
    }
}
