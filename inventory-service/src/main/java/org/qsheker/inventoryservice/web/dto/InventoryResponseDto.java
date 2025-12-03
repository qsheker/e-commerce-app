package org.qsheker.inventoryservice.web.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InventoryResponseDto
{
    private String skuCode;
    private String productId;
    private Integer quantity;
    private BigDecimal price;
}
