/**
 * @author Nikolay Yarlychenko
 */

public enum Currency {
    USD(0), EUR(1), RUB(2);
    private final int value;
    public double rate() {
        switch (this) {
            case USD:
                return 1.0;
            case EUR:
                return 1.12;
            case RUB:
                return 0.00764;
        }
        return 1.0;
    }

    Currency(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Currency fromInteger(int id) {
        for (Currency cur : values()) {
            if (cur.value == id) {
                return cur;
            }
        }
        throw new IllegalArgumentException("Invalid Currency id: " + id);
    }
}
