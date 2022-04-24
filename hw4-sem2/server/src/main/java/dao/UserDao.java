package dao;

import model.Stock;

import java.util.List;

public interface UserDao {
    int createUser();
    boolean makeDeposit(final int userId, final double amount);
    List<Stock> getStocks(final int userId);
    double getFullPortfolioValue(final int userId);
    boolean buyStock(final int userId, final int companyId, final int amount);
    boolean sellStock(final int companyId, final int amount, final int userId);
    boolean changePrice(final int companyId, final double delta, final int userId);
}
