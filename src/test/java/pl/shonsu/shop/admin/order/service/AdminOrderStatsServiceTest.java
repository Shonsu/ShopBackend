package pl.shonsu.shop.admin.order.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.shonsu.shop.admin.order.model.AdminOrder;
import pl.shonsu.shop.admin.order.model.AdminOrderStatus;
import pl.shonsu.shop.admin.order.model.dto.AdminOrderStats;
import pl.shonsu.shop.admin.order.repository.AdminOrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminOrderStatsServiceTest {

    @Mock
    private AdminOrderRepository adminOrderRepository;
    @InjectMocks
    private AdminOrderStatsService adminOrderStatsService;

//    Ogólnie kod powinieneś mieć przetestowany zarówno unitowo jak i integracyjnie, z przewagą tych pierwszych testów.
//    Tutaj, żeby odpowiedzieć na pytanie potrzebny jest szerszy kontekst - na jakiej warstwie jest ta metoda getStatistics,
//    czy stanowi ona use case, czy dopiero jest jego częścią?
//    Ja ostatnio testuję sobie podejście w którym mam dokładnie te same testy jednostkowe i integracyjne,
//    uruchamiam je po prostu z innymi zależnościami (unit in memory, integracyjne z test containers i wiremock)
//    Ok, czyli warstwa aplikacji.
//    Jednostkowo przetestowałbym różne przypadki, czy dobrze agreguje, edge case. A integracyjnie uderzając w kontroler
//    sprawdziłbym happy path, jakąś niedostępność bazy itd. Do tego może integracyjnie samo wyszukiwanie w repo,
//    żeby sprawdzić czy zapytanie SQL działa dobrze

    @Test
    void shouldRetrunOrderStatsBetweenDates() {
        //given
        LocalDateTime from = LocalDateTime.of(2022, 5, 12, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2022, 5, 13, 23, 59, 59);
        when(adminOrderRepository.findAllByPlaceDateIsBetweenAndOrderStatus(any(), any(), any())).thenReturn(createAdminOrderList());
        //when
        AdminOrderStats adminOrderStats = adminOrderStatsService.getStatistics(from, to, AdminOrderStatus.NEW);
        //then
        assertThat(adminOrderStats.getOrder().size()).isEqualTo(2);
        assertThat(adminOrderStats.getPlaceDate().get(0)).isEqualTo(LocalDate.of(2022,5,12));
        assertThat(adminOrderStats.getSale().get(1)).isEqualTo(new BigDecimal("26"));
    }

    private List<AdminOrder> createAdminOrderList() {
        List<AdminOrder> adminOrders = new ArrayList<>();
        return List.of(
                AdminOrder.builder()
                        .id(1L)
                        .placeDate(LocalDateTime.of(2022, 5, 11, 23, 59, 59))
                        .orderStatus(AdminOrderStatus.NEW)
                        .grossValue(BigDecimal.valueOf(5))
                        .build(),
                AdminOrder.builder()
                        .id(2L)
                        .placeDate(LocalDateTime.of(2022, 5, 12, 0, 0, 0))
                        .orderStatus(AdminOrderStatus.NEW)
                        .grossValue(BigDecimal.valueOf(10))
                        .build(),
                AdminOrder.builder()
                        .id(3L)
                        .placeDate(LocalDateTime.of(2022, 5, 12, 0, 0, 0))
                        .orderStatus(AdminOrderStatus.NEW)
                        .grossValue(BigDecimal.valueOf(10))
                        .build(),
                AdminOrder.builder()
                        .id(4L)
                        .placeDate(LocalDateTime.of(2022, 5, 13, 0, 0, 0))
                        .orderStatus(AdminOrderStatus.NEW)
                        .grossValue(BigDecimal.valueOf(13))
                        .build(),
                AdminOrder.builder()
                        .id(5L)
                        .placeDate(LocalDateTime.of(2022, 5, 13, 0, 0, 0))
                        .orderStatus(AdminOrderStatus.NEW)
                        .grossValue(BigDecimal.valueOf(13))
                        .build(),
                AdminOrder.builder()
                        .id(6L)
                        .placeDate(LocalDateTime.of(2022, 5, 14, 0, 0, 0))
                        .orderStatus(AdminOrderStatus.NEW)
                        .grossValue(BigDecimal.valueOf(13))
                        .build()
        );
    }

}