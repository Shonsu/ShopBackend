package pl.shonsu.shop.common.model;

public enum OrderStatus {
    NEW("Nowe"),
    PAID("Zapłacone"),
    PROCESSING("Przetwarzane"),
    WAIT_FOR_DELIVERY("Czeka na dostawę"),
    COMPLETED("Zrealizowane"),
    CANCELED("Anulowane"),
    REFUND("Zwrócone");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
