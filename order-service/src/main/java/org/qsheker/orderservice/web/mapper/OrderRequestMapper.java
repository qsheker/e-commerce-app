package org.qsheker.orderservice.web.mapper;

import org.mapstruct.Mapper;
import org.qsheker.orderservice.models.Order;
import org.qsheker.orderservice.web.dto.order.OrderRequest;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper
{
    Order toEntity(OrderRequest orderRequest);
}
