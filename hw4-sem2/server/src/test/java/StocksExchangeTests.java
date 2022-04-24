import dao.UserInMemoryDao;
import model.StocksService;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"deprecation", "unused"})
public class StocksExchangeTests {

    @ClassRule
    private final GenericContainer<?> __ = new FixedHostPortGenericContainer<>("docker/stocks_service:1.0")
            .withFixedExposedPort(8000, 8000)
            .withExposedPorts(8000);

    private static final StocksService service = new StocksService("http://localhost", 8000);

    private static final UserInMemoryDao dao = new UserInMemoryDao(service);

    @Test
    public void registerNewUser() {
        var user = dao.createUser();
        assertEquals(1, user);
    }

    @Test
    public void addCompany() {
        final int yandex = service.addCompany(3000);
        assertTrue(service.addStocks(yandex, 20));
        assertEquals(20, service.getAmount(yandex));
        assertEquals(3000, service.getPrice(yandex));
        assertEquals(1, service.addCompany(1000));
    }

    @Test
    public void changePrice() {
        final int yandex = service.addCompany(3000);
        assertEquals(3000, service.getPrice(0));

        assertTrue(dao.changePrice(0, 2999, 0));
        assertEquals(2999, service.getPrice(0));
    }

    @Test
    public void createAccount() {
        final int yandex = service.addCompany(3000);
        var user = dao.createUser();
        assertEquals(0, user);
        assertTrue(dao.makeDeposit(user, 10000));
        assertTrue(dao.buyStock(user, yandex, 2));
        assertEquals(2, dao.getStocks(user).get(0).getAmount());
        assertEquals(6000, dao.getFullPortfolioValue(1));
    }

    @Test
    public void getTotalPortfolioValue() {
        final int yandex = service.addCompany(3000);
        final int apple = service.addCompany(10000);

        service.addStocks(yandex, 100);
        service.addStocks(apple, 100);

        assertEquals(3000, service.getPrice(yandex));
        assertEquals(10000, service.getPrice(apple));

        var user = dao.createUser();

        dao.makeDeposit(user, 100000000);

        dao.buyStock(user, apple, 10);
        dao.buyStock(user, yandex, 10);

        assertEquals(130000, dao.getFullPortfolioValue(user));
    }
}
