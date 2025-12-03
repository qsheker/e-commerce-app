package org.qsheker.orderservice.web.dto.order;

import lombok.Data;
import org.qsheker.orderservice.web.dto.inventory.InventoryBatchRequestDto;

import java.util.List;

@Data
public class OrderCreateRequestDto
{
    private List<InventoryBatchRequestDto> list;
}
