package org.qsheker.orderservice.web.dto;


import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private LocalDateTime orderDate;
    private List<OrderItemResponse> orderItems;
}