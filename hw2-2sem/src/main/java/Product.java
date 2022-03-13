import org.bson.Document;

/**
 * @author Nikolay Yarlychenko
 */
public class Product {
    public final int id;
    public final String name;
    public final String description;
    public final double priceInUsd;

    public Product(int id, String name, String description, double priceInUsd) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priceInUsd = priceInUsd;
    }

    public Product(Document document) {
        this(document.getInteger("id"),
                document.getString("name"),
                document.getString("description"),
                document.getDouble("priceInUsd"));
    }

    public Document toDocument() {
        return new Document("id", id)
                .append("name", name)
                .append("description", description)
                .append("priceInUsd", priceInUsd);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priceInUsd=" + priceInUsd +
                '}';
    }

    public String toString(Currency currency) {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priceInUsd=" + priceInUsd / currency.rate() +
                '}';
    }
}
