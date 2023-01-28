package pl.shonsu.shop.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shonsu.shop.admin.order.model.AdminOrder;
import pl.shonsu.shop.admin.order.model.AdminOrderStatus;
import pl.shonsu.shop.admin.order.model.dto.AdminOrderStats;
import pl.shonsu.shop.admin.order.repository.AdminOrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AdminOrderStatsService {

    private final AdminOrderRepository orderRepository;

    public AdminOrderStats getStatistics() {
        LocalDateTime from = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime to = LocalDateTime.now();
        List<AdminOrder> orders = orderRepository.findAllByPlaceDateIsBetweenAndOrderStatus(
                from,
                to,
                AdminOrderStatus.COMPLETED);
        TreeMap<Integer, DaySale> result = new TreeMap<>();
        IntStream.rangeClosed(from.getDayOfMonth(), to.getDayOfMonth())
                .forEach(value -> result.put(value, aggregateValues(value, orders)));
//        for (int i = from.getDayOfMonth(); i <= to.getDayOfMonth(); i++) {
//            result.put(i, aggregateValues(i, orders));
//        }
        return AdminOrderStats.builder()
                .label(result.keySet().stream().toList())
                .sale(result.values().stream().map(DaySale::getSale).toList())
                .order(result.values().stream().map(DaySale::getCount).toList())
                .build();
    }

    private DaySale aggregateValues(int i, List<AdminOrder> orders) {
        return orders.stream()
                .filter(adminOrder -> adminOrder.getPlaceDate().getDayOfMonth() == i)
                .collect(new SalesCollector());
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
