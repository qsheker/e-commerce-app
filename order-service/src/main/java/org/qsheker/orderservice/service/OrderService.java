package org.qsheker.orderservice.service;


import org.qsheker.orderservice.web.dto.order.OrderCreateRequestDto;
import org.qsheker.orderservice.web.dto.order.OrderRequest;
import org.qsheker.orderservice.web.dto.order.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> findAllOrder();

    OrderResponse findOrderById(Long id);

    OrderResponse create(OrderCreateRequestDto request);

    OrderResponse update(Long id,OrderRequest request);

    void delete(Long id);
}
