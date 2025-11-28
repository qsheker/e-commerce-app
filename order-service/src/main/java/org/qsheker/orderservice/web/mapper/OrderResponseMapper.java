package org.qsheker.orderservice.web.mapper;

import org.mapstruct.Mapper;
import org.qsheker.orderservice.models.Order;
import org.qsheker.orderservice.web.dto.OrderResponse;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper
{
    OrderResponse toDto(Order order);
}
