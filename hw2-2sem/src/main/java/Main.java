import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import org.bson.ByteBuf;
import rx.Observable;

/**
 * @author Nikolay Yarlychenko
 */
public class Main {
    public static void main(String[] args) {
        final MongoDriver driver = new MongoDriver();
        HttpServer
                .newServer(8082)
                .start((req, resp) -> {
                    ProductsCatalogService service = new ProductsCatalogService(driver);
                    Observable<String> response = service.handleRequest(req);
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }
}
