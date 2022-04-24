package dao;

public interface ExchangeDao {
    int createCompany(double initialPrice);
    boolean addCompanyStock(final int companyId, final int amount);
    int getCompanyStocksAmount(final int companyId);
    double getCompanyStockPrice(final int companyId);
    boolean buyStock(final int companyId, final int amount);
    boolean sellStock(final int companyId, final int amount);
    boolean changePrice(final int companyId, final double delta);
}
