package org.qsheker.orderservice.service.impl;

import org.qsheker.orderservice.models.Order;
import org.qsheker.orderservice.models.OrderItem;
import org.qsheker.orderservice.repository.OrderRepository;
import org.qsheker.orderservice.service.OrderService;
import org.qsheker.orderservice.web.dto.inventory.InventoryResponseDto;
import org.qsheker.orderservice.web.dto.order.OrderCreateRequestDto;
import org.qsheker.orderservice.web.dto.order.OrderRequest;
import org.qsheker.orderservice.web.dto.order.OrderResponse;
import org.qsheker.orderservice.web.mapper.OrderItemRequestMapper;
import org.qsheker.orderservice.web.mapper.OrderRequestMapper;
import org.qsheker.orderservice.web.mapper.OrderResponseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderResponseMapper orderResponseMapper;
    private final OrderRequestMapper orderRequestMapper;
    private final OrderItemRequestMapper orderItemRequestMapper;
    private final static String URL_PREFIX = "http://inventory-service";
    private final WebClient.Builder webClientBuilder;

    public OrderServiceImpl(OrderRepository orderRepository, OrderResponseMapper orderResponseMapper, OrderRequestMapper orderRequestMapper, OrderItemRequestMapper orderItemRequestMapper, WebClient.Builder webClientBuilder)
    {
        this.orderRepository = orderRepository;
        this.orderResponseMapper = orderResponseMapper;
        this.orderRequestMapper = orderRequestMapper;
        this.orderItemRequestMapper = orderItemRequestMapper;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public List<OrderResponse>  findAllOrder()
    {
        return orderRepository.findAll().stream()
                .map(order -> orderResponseMapper.toDto(order))
                .toList();
    }

    @Override
    public OrderResponse findOrderById(Long id)
    {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return orderResponseMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderResponse create(OrderCreateRequestDto request)
    {
        List<InventoryResponseDto> fetched = webClientBuilder.build()
                .patch()
                .uri(URL_PREFIX+"/api/v1/inventory/batch")
                .bodyValue(request.getList())
                .retrieve()
                .bodyToFlux(InventoryResponseDto.class)
                .collectList()
                .block();

        List<OrderItem> orderItems = fetched.stream()
                .map(inventoryResponseDto ->OrderItem.builder()
                        .skuCode(inventoryResponseDto.getSkuCode())
                        .price(inventoryResponseDto.getPrice())
                        .quantity(inventoryResponseDto.getQuantity())
                        .build())
                .toList();

        var uniqueOrderNumber = UUID.randomUUID().toString();

        BigDecimal total = orderItems.stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var order = Order.builder()
                .orderItems(orderItems)
                .orderNumber(uniqueOrderNumber)
                .total(total)
                .build();

        var result = orderRepository.save(order);


        var response = orderResponseMapper.toDto(result);

        response.setTotal(total);

        return response;
    }

    @Override
    public OrderResponse update(Long id, OrderRequest request)
    {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        existingOrder.getOrderItems().clear();

        for(var item: request.getOrderItems()){
            existingOrder.addItem(orderItemRequestMapper.toEntity(item));
        }

        Order updatedOrder = orderRepository.save(existingOrder);
        return orderResponseMapper.toDto(updatedOrder);
    }

    @Override
    public void delete(Long id)
    {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepository.delete(order);
    }
}
