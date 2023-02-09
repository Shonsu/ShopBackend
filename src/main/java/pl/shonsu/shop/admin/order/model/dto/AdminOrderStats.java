package pl.shonsu.shop.admin.order.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class AdminOrderStats {
    private List<LocalDate> placeDate;
    private List<BigDecimal> sale;
    private List<Long> order;
    private Long ordersCount;
    private BigDecimal salesSum;
}
