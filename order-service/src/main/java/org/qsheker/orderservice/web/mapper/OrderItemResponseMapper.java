package org.qsheker.orderservice.web.mapper;

import org.mapstruct.Mapper;
import org.qsheker.orderservice.models.OrderItem;
import org.qsheker.orderservice.web.dto.OrderItemResponse;

@Mapper(componentModel = "spring")
public interface OrderItemResponseMapper
{
    OrderItemResponse toDto(OrderItem orderItem);
}
