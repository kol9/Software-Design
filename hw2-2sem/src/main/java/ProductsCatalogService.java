import com.mongodb.rx.client.Success;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;


/**
 * @author Nikolay Yarlychenko
 */

public class ProductsCatalogService {
    private final MongoDriver dbDriver;

    ProductsCatalogService(MongoDriver mongoDriver) {
        this.dbDriver = mongoDriver;
    }

    public Observable<String> handleRequest(HttpServerRequest<ByteBuf> request) {
        String path = request.getDecodedPath().substring(1);
        Observable<String> response;

        switch (path) {
            case "getUser":
                response = getUser(request);
                break;
            case "addUser":
                response = addUser(request);
                break;
            case "getProducts":
                response = getProducts(request);
                break;
            case "addProduct":
                response = addProduct(request);
                break;
            default:
                response = Observable.just("Wrong request");
                break;
        }

        return response;
    }

    private Observable<String> getUser(HttpServerRequest<ByteBuf> request) {
        String id = request.getQueryParameters().get("id").get(0);
        Observable<User> user = dbDriver.getUser(Integer.parseInt(id));
        return Observable.just(user.toString());
    }

    private Observable<String> addUser(HttpServerRequest<ByteBuf> request) {
        var parameters = request.getQueryParameters();
        int id = Integer.parseInt(parameters.get("id").get(0));
        String name = parameters.get("name").get(0);
        String surname = parameters.get("surname").get(0);
        int currency = Integer.parseInt(parameters.get("currency").get(0));
        User user = new User(id, name, surname, currency);
        Observable<Success> addUser = dbDriver.addUser(user);
        return addUser.map(s -> s == (Success.SUCCESS) ? "Successfully added user" + user : "Failure");
    }

    private Observable<String> addProduct(HttpServerRequest<ByteBuf> request) {
        var parameters = request.getQueryParameters();
        int id = Integer.parseInt(parameters.get("id").get(0));
        String name = parameters.get("name").get(0);
        String description = parameters.get("description").get(0);
        double price = Double.parseDouble(parameters.get("price").get(0));
        Product product = new Product(id, name, description, price);

        Observable<Success> addProduct = dbDriver.addProduct(product);
        return addProduct.map(s -> s == (Success.SUCCESS) ? "Successfully added product" + product : "Failure");
    }

    private Observable<String> getProducts(HttpServerRequest<ByteBuf> request) {
        var parameters = request.getQueryParameters();
        int id = Integer.parseInt(parameters.get("user_id").get(0));
        Observable<User> user = dbDriver.getUser(id);
        return user.flatMap(u -> dbDriver.getProducts(u.id));
    }
}
