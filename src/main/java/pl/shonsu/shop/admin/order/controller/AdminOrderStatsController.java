package pl.shonsu.shop.admin.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.shonsu.shop.admin.order.model.dto.AdminOrderStats;
import pl.shonsu.shop.admin.order.service.AdminOrderStatsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/orders/stats")
public class AdminOrderStatsController {

    private final AdminOrderStatsService adminOrderStatsService;
    @GetMapping
    public AdminOrderStats getOrderStatistics() {
        return adminOrderStatsService.getStatistics();
    }
}
