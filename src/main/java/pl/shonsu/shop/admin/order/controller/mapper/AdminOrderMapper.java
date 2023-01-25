package pl.shonsu.shop.admin.order.controller.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import pl.shonsu.shop.admin.order.controller.dto.AdminOrderDto;
import pl.shonsu.shop.admin.order.model.AdminOrder;

import java.util.List;

public class AdminOrderMapper {

    public static PageImpl<AdminOrderDto> mapToPageDtos(Page<AdminOrder> orders) {
        return new PageImpl<>(mapToDtoList(orders), orders.getPageable(), orders.getTotalElements());
    }

    private static List<AdminOrderDto> mapToDtoList(Page<AdminOrder> orders) {
        return orders.getContent().stream().map(AdminOrderMapper::mapToOrderDto).toList();
    }

    private static AdminOrderDto mapToOrderDto(AdminOrder adminOrder) {
        return AdminOrderDto.builder()
                .id(adminOrder.getId())
                .placeDate(adminOrder.getPlaceDate())
                .orderStatus(adminOrder.getOrderStatus())
                .grossValue(adminOrder.getGrossValue())
                .build();
    }


}
