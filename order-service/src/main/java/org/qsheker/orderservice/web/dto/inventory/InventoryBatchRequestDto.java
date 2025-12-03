package org.qsheker.orderservice.web.dto.inventory;

import lombok.Data;

@Data
public class InventoryBatchRequestDto
{
    private String skuCode;
    private String productId;
    private Integer quantity;
}
