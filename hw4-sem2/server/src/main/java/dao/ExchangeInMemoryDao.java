package dao;

import java.util.ArrayList;
import java.util.List;

public class ExchangeInMemoryDao implements ExchangeDao {
    private final List<Double> prices = new ArrayList<>();
    private final List<Integer> amounts = new ArrayList<>();

    private void assertCapacity() {
        assert amounts.size() == prices.size();
    }

    private boolean isCompanyExists(final int companyId) {
        return companyId >= amounts.size();
    }

    @Override
    public int createCompany(double initialPrice) {
        assertCapacity();

        amounts.add(0);
        prices.add(initialPrice);

        return amounts.size() - 1;
    }

    @Override
    public boolean addCompanyStock(final int companyId, final int amount) {
        assertCapacity();
        if (!isCompanyExists(companyId)) {
            return false;
        }

        amounts.set(companyId, amounts.get(companyId) + amount);
        return true;
    }

    @Override
    public int getCompanyStocksAmount(final int companyId) {
        assertCapacity();
        if (!isCompanyExists(companyId)) {
            return -1;
        }
        return amounts.get(companyId);
    }

    @Override
    public double getCompanyStockPrice(final int companyId) {
        assertCapacity();
        if (!isCompanyExists(companyId)) {
            return -1;
        }
        return prices.get(companyId);
    }

    @Override
    public boolean buyStock(final int companyId, final int amount) {
        assertCapacity();
        if (!isCompanyExists(companyId) || amount < 0 || amounts.get(companyId) < amount) {
            return false;
        }

        amounts.set(companyId, amounts.get(companyId) - amount);
        return true;
    }

    @Override
    public boolean sellStock(final int companyId, final int amount) {
        assertCapacity();
        if (!isCompanyExists(companyId) || amount < 0) {
            return false;
        }

        amounts.set(companyId, amounts.get(companyId) + amount);
        return false;
    }

    @Override
    public boolean changePrice(final int companyId, final double delta) {
        assertCapacity();
        if (!isCompanyExists(companyId)) {
            return false;
        }

        final double res = prices.get(companyId) + delta;
        prices.set(companyId, res);
        return true;
    }
}
