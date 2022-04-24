package controller;

import dao.UserDao;
import dao.UserInMemoryDao;
import model.Stock;
import model.StocksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final StocksService service = new StocksService("https://localhost", 8000);
    private final UserDao dao = new UserInMemoryDao(service);

    @PostMapping("/create")
    public ResponseEntity<Integer> createUser() {
        return ResponseEntity.ok(dao.createUser());
    }

    private ResponseEntity<?> performWithSuccessResult(final boolean result) {
        if (result) {
            return ResponseEntity.ok(null);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/put_money")
    public ResponseEntity<?> makeDeposit(final int userId, final double amount) {
        return performWithSuccessResult(dao.makeDeposit(userId, amount));
    }

    @GetMapping("/stocks/{userId}")
    public ResponseEntity<?> getStocks(final @PathVariable int userId) {
        final List<Stock> stocks = dao.getStocks(userId);
        if (stocks != null) {
            return ResponseEntity.ok(stocks);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/calc_total/{userId}")
    public ResponseEntity<?> getTotalAmount(final @PathVariable int userId) {
        final double sum = dao.getFullPortfolioValue(userId);
        if (sum == -1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(sum);
    }

    @PostMapping("/buy_stocks")
    public ResponseEntity<?> buy(
            final int userId,
            final int companyId,
            final int amount
    ) {
        return performWithSuccessResult(dao.buyStock(userId, companyId, amount));
    }

    @PostMapping("/sell_stocks")
    public ResponseEntity<?> sell(
            final int userId,
            final int companyId,
            final int amount
    ) {
        return performWithSuccessResult(dao.sellStock(companyId, amount, userId));
    }
}
