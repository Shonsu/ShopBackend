package pl.shonsu.shop.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shonsu.shop.admin.order.model.AdminOrder;
import pl.shonsu.shop.admin.order.model.dto.AdminOrderStats;
import pl.shonsu.shop.admin.order.repository.AdminOrderRepository;
import pl.shonsu.shop.common.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class AdminOrderStatsService {

    private final AdminOrderRepository orderRepository;

    public AdminOrderStats getStatistics(LocalDateTime from, LocalDateTime to, OrderStatus orderStatus) {

        List<AdminOrder> orders = orderRepository.findAllByPlaceDateIsBetweenAndOrderStatus(
                from,
                to,
                orderStatus);
        TreeMap<LocalDate, DaySale> result = new TreeMap<>();
        from.toLocalDate().datesUntil(to.toLocalDate().plusDays(1L))
                .forEach(date -> result.put(date,
                        aggregateValues(date, orders)));

        return AdminOrderStats.builder()
                .placeDate(result.keySet().stream().toList())
                .sale(result.values().stream().map(DaySale::getSale).toList())
                .order(result.values().stream().map(DaySale::getCount).toList())
                .ordersCount(result.values().stream().map(DaySale::getCount).reduce(Long::sum).orElse(0L))
                .salesSum(result.values().stream().map(DaySale::getSale).reduce(BigDecimal::add).orElse(BigDecimal.ZERO))
                .build();
    }

    private DaySale aggregateValues(LocalDate placeDate, List<AdminOrder> orders) {
        return orders.stream()
                .filter(adminOrder -> adminOrder.getPlaceDate().toLocalDate().equals(placeDate))
                .collect(new SalesCollector());
    }
}
