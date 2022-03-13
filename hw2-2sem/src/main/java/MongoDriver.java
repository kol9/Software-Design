import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.rx.client.Success;
import org.bson.Document;
import rx.Observable;

/**
 * @author Nikolay Yarlychenko
 */

public class MongoDriver {
    private static final String USERS_KEY = "users";
    private static final String PRODUCTS_KEY = "products";

    private final MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("catalog");
    private final MongoCollection<Document> users;
    private final MongoCollection<Document> products;

    public MongoDriver() {
        this.users = database.getCollection(USERS_KEY);
        this.products = database.getCollection(PRODUCTS_KEY);
    }

    public Observable<User> getUser(Integer id) {
        return users.find(eq("id", id)).toObservable().map(User::new).first();
    }

    public Observable<Success> addUser(User user) {
        return users.insertOne(user.toDocument());
    }

    public Observable<Success> addProduct(Product product) {
        return products.insertOne(product.toDocument());
    }

    public Observable<String> getProducts(int userId) {
        Observable<User> user = users.find(eq("id", userId)).toObservable().map(User::new);
        Observable<Product> product = products.find().toObservable().map(Product::new);
        return user.flatMap(u -> product.map(p -> p.toString(u.selectedCurrency)));
    }
}
