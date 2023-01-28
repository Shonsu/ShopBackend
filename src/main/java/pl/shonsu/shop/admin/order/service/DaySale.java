package pl.shonsu.shop.admin.order.service;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class DaySale implements Consumer<BigDecimal> {

    private BigDecimal sale = BigDecimal.ZERO;
    private Long count = 0L;

    @Override
    public void accept(BigDecimal price) {
        sale = sale.add(price);
        count++;
    }

    public void combine(DaySale daySale) {
        sale = sale.add(daySale.sale);
        count = daySale.count;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public Long getCount() {
        return count;
    }

    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
