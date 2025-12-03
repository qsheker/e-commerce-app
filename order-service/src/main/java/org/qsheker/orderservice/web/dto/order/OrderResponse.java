package org.qsheker.orderservice.web.dto.order;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private LocalDateTime orderDate;
    private List<OrderItemResponse> orderItems;
    private BigDecimal total;
}
