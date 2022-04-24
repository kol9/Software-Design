package controller;

import dao.ExchangeDao;
import dao.ExchangeInMemoryDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {
    private final ExchangeDao dao = new ExchangeInMemoryDao();

    @PostMapping("/create_company/{initialPrice}")
    public ResponseEntity<Integer> createCompany(@PathVariable final double initialPrice) {
        return ResponseEntity.ok(dao.createCompany(initialPrice));
    }

    private ResponseEntity<?> processBooleanMethod(final boolean result) {
        if (result) {
            return ResponseEntity.ok(null);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add_stock")
    public ResponseEntity<?> addCompanyStock(final int companyId, final int amount) {
        return processBooleanMethod(dao.addCompanyStock(companyId, amount));
    }

    @GetMapping("/get_amount/{companyId}")
    public ResponseEntity<Integer> getCompanyStocksAmount(@PathVariable final int companyId) {
        return ResponseEntity.ok(dao.getCompanyStocksAmount(companyId));
    }

    @GetMapping("/get_price/{companyId}")
    public ResponseEntity<Double> getCompanyStockPrice(@PathVariable final int companyId) {
        return ResponseEntity.ok(dao.getCompanyStockPrice(companyId));
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyStock(final int companyId, final int amount) {
        return processBooleanMethod(dao.buyStock(companyId, amount));
    }

    @PostMapping("/sell")
    public ResponseEntity<?> sell(final int companyId, final int amount) {
        return processBooleanMethod(dao.sellStock(companyId, amount));
    }

    @PostMapping("/change_price")
    public ResponseEntity<?> change(final int companyId, final double delta) {
        return processBooleanMethod(dao.changePrice(companyId, delta));
    }
}
