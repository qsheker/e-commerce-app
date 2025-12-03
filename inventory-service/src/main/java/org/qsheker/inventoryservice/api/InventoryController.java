package org.qsheker.inventoryservice.api;

import org.qsheker.inventoryservice.service.InventoryService;
import org.qsheker.inventoryservice.web.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController
{

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService)
    {
        this.inventoryService = inventoryService;
    }

    @GetMapping("{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto isInStock(@PathVariable("sku-code") String skuCode)
    {
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping("/light/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStockLightWeight(@PathVariable("sku-code") String skuCode)
    {
        return inventoryService.isInStockLightWeight(skuCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponseDto create(@RequestBody InventoryRequestDto requestDto)
    {
        return inventoryService.save(requestDto);
    }

    @PatchMapping("purchase/{sku-code}/{quantity}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponseDto doPurchase(@PathVariable("sku-code") String skuCode, @PathVariable Integer quantity)
    {
        return inventoryService.doPurchase(quantity,skuCode);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDto> getAll()
    {
        return inventoryService.getAll();
    }

    @PatchMapping("/batch")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDto> doPurchaseBatch(@RequestBody List<InventoryBatchRequestDto> dtoList)
    {
        return inventoryService.doPurchaseBatch(dtoList);
    }
}
