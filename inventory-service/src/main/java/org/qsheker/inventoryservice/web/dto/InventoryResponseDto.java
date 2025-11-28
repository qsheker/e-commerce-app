package org.qsheker.inventoryservice.web.dto;

import lombok.Data;

@Data
public class InventoryResponseDto
{
    private Long id;
    private String skuCode;
    private String productId;
    private Integer quantity;
}
