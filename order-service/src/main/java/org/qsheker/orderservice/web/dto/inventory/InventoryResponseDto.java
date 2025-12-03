package org.qsheker.orderservice.web.dto.inventory;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventoryResponseDto
{
    private String skuCode;
    private String productId;
    private Integer quantity;
    private BigDecimal price;
}
