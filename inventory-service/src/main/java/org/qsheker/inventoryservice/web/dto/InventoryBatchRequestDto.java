package org.qsheker.inventoryservice.web.dto;

import lombok.Data;


@Data
public class InventoryBatchRequestDto
{
    private String skuCode;
    private String productId;
    private Integer quantity;
}
