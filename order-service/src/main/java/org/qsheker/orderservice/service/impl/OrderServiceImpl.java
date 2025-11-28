package org.qsheker.orderservice.service.impl;

import org.qsheker.orderservice.models.Order;
import org.qsheker.orderservice.models.OrderItem;
import org.qsheker.orderservice.repository.OrderRepository;
import org.qsheker.orderservice.service.OrderService;
import org.qsheker.orderservice.web.dto.OrderItemRequest;
import org.qsheker.orderservice.web.dto.OrderItemResponse;
import org.qsheker.orderservice.web.dto.OrderRequest;
import org.qsheker.orderservice.web.dto.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderResponse> findAllOrder() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse findOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return mapToResponse(order);
    }

    @Override
    public OrderResponse create(OrderRequest request) {
        Order order = Order.builder()
                .orderNumber(request.getOrderNumber())
                .orderItems(
                        request.getOrderItems().stream()
                                .map(this::mapToEntity)
                                .collect(Collectors.toList())
                )
                .build();
        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    @Override
    public OrderResponse update(Long id, OrderRequest request) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        existingOrder.setOrderNumber(request.getOrderNumber());
        existingOrder.getOrderItems().clear();
        existingOrder.getOrderItems().addAll(
                request.getOrderItems().stream()
                        .map(this::mapToEntity)
                        .collect(Collectors.toList())
        );

        Order updatedOrder = orderRepository.save(existingOrder);
        return mapToResponse(updatedOrder);
    }

    @Override
    public void delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderDate(order.getOrderDate())
                .orderItems(order.getOrderItems().stream()
                        .map(this::mapToItemResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private OrderItemResponse mapToItemResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .id(item.getId())
                .skuCode(item.getSkuCode())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
    }

    private OrderItem mapToEntity(OrderItemRequest request) {
        return OrderItem.builder()
                .skuCode(request.getSkuCode())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }
}
