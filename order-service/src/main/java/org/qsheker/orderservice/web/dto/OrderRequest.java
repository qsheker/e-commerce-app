package org.qsheker.orderservice.web.dto;


import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String orderNumber;
    private List<OrderItemRequest> orderItems;
}