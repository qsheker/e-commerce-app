package org.qsheker.inventoryservice.web.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponseDto
{
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean inStock;
}
