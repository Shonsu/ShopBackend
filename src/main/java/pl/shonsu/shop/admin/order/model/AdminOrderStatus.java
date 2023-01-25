package pl.shonsu.shop.admin.order.model;

public enum AdminOrderStatus {
    NEW("Nowe"),
    PAID("Zapłacone"),
    PROCESSING("Przetwarzane"),
    WAIT_FOR_DELIVERY("Czeka na dostawę"),
    COMPLETED("Zrealizowane"),
    CANCELED("Anulowane"),
    REFUND("Zwrócone");

    private final String value;

    AdminOrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
