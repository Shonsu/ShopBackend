package pl.shonsu.shop.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shonsu.shop.admin.order.model.AdminOrder;
import pl.shonsu.shop.admin.order.model.AdminOrderStatus;
import pl.shonsu.shop.admin.order.model.dto.AdminOrderStats;
import pl.shonsu.shop.admin.order.repository.AdminOrderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class AdminOrderStatsService {

    private final AdminOrderRepository orderRepository;

    public AdminOrderStats getStatistics(LocalDateTime from, LocalDateTime to, AdminOrderStatus orderStatus) {

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
                .build();
    }

    private DaySale aggregateValues(LocalDate placeDate, List<AdminOrder> orders) {
        return orders.stream()
                .filter(adminOrder -> adminOrder.getPlaceDate().toLocalDate().equals(placeDate))
                .collect(new SalesCollector());
        //System.out.println(collect);
//        Long ordersCount = orders.stream()
//                .filter(order->order.getPlaceDate().getDayOfMonth()==i)
//                .count();
//        BigDecimal ordersSale = orders.stream()
//                .filter(order->order.getPlaceDate().getDayOfMonth()==i)
//                .map(AdminOrder::getGrossValue)
//                .reduce(BigDecimal::add)
//                .orElse(BigDecimal.ZERO);
        //.map(AdminOrder::getGrossValue)
        //.collect(DaySale::new, DaySale::accept, DaySale::combine);
    }
}
