package org.qsheker.orderservice.web.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemRequest {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}