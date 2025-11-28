package org.qsheker.orderservice.web.mapper;

import org.mapstruct.Mapper;
import org.qsheker.orderservice.models.OrderItem;
import org.qsheker.orderservice.web.dto.OrderItemRequest;

@Mapper(componentModel = "spring")
public interface OrderItemRequestMapper
{
    OrderItem toEntity(OrderItemRequest request);
}
