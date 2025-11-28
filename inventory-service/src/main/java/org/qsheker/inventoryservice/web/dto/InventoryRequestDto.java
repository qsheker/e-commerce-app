package org.qsheker.inventoryservice.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InventoryRequestDto
{
    @NotBlank
    private String skuCode;

    @NotBlank
    private String productId;

    @Min(0)
    private Integer quantity;
}
