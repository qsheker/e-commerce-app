package org.qsheker.orderservice.service;


import org.qsheker.orderservice.web.dto.OrderRequest;
import org.qsheker.orderservice.web.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> findAllOrder();

    OrderResponse findOrderById(Long id);

    OrderResponse create(OrderRequest request);

    OrderResponse update(Long id,OrderRequest request);

    void delete(Long id);
}
