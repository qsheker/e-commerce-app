package org.qsheker.inventoryservice.service;

import org.qsheker.inventoryservice.web.dto.InventoryRequestDto;
import org.qsheker.inventoryservice.web.dto.InventoryResponseDto;
import org.qsheker.inventoryservice.web.dto.ProductResponseDto;

import java.util.List;

public interface InventoryService
{
    ProductResponseDto isInStock(String code);
    boolean isInStockLightWeight(String code);
    InventoryResponseDto save(InventoryRequestDto dto);
    InventoryResponseDto doPurchase(Integer quantity,String skuCode);
    List<InventoryResponseDto> getAll();
}
