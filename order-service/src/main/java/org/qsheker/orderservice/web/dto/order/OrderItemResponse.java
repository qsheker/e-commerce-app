package org.qsheker.orderservice.web.dto.order;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
