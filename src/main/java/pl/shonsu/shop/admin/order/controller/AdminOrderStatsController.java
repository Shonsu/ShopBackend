package pl.shonsu.shop.admin.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.shonsu.shop.admin.order.model.dto.AdminOrderStats;
import pl.shonsu.shop.admin.order.service.AdminOrderStatsService;
import pl.shonsu.shop.common.model.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders/stats")
public class AdminOrderStatsController {

    private final AdminOrderStatsService adminOrderStatsService;
    @GetMapping
    public AdminOrderStats getOrderStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to,
            @RequestParam OrderStatus orderStatus
    ) {
        return adminOrderStatsService.getStatistics(
                LocalDateTime.of(from, LocalTime.of(0,0,0)),
                LocalDateTime.of(to, LocalTime.of(23, 59,59)),
                orderStatus);
    }
}
