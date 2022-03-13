import org.bson.Document;

public class User {
    public final int id;
    public final String name;
    public final String surname;
    public final Currency selectedCurrency;

    public User(Document document) {
        this(document.getInteger("id"),
                document.getString("name"),
                document.getString("surname"),
                document.getInteger("currency"));
    }

    public User(int id, String name, String surname, int currency) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.selectedCurrency = Currency.fromInteger(currency);
    }

    public Document toDocument() {
        return new Document("id", id)
                .append("name", name)
                .append("surname", surname)
                .append("currency", selectedCurrency.getValue());
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", selectedCurrency=" + selectedCurrency +
                '}';
    }
}
