package org.qsheker.orderservice.web.dto.order;

import lombok.Data;

@Data
public class OrderItemRequest
{
    private String skuCode;
    private String productId;
    private Integer quantity;
}