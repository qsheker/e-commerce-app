package org.qsheker.inventoryservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.qsheker.inventoryservice.model.Inventory;
import org.qsheker.inventoryservice.model.Product;
import org.qsheker.inventoryservice.repository.InventoryRepository;
import org.qsheker.inventoryservice.service.InventoryService;
import org.qsheker.inventoryservice.web.dto.InventoryRequestDto;
import org.qsheker.inventoryservice.web.dto.InventoryResponseDto;
import org.qsheker.inventoryservice.web.dto.ProductResponseDto;
import org.qsheker.inventoryservice.web.mapper.InventoryRequestMapper;
import org.qsheker.inventoryservice.web.mapper.InventoryResponseMapper;
import org.qsheker.inventoryservice.web.mapper.ProductResponseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService
{
    private final InventoryRepository inventoryRepository;
    private final WebClient webClient;
    private final ProductResponseMapper productResponseMapper;
    private final InventoryRequestMapper inventoryRequestMapper;
    private final InventoryResponseMapper inventoryResponseMapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, WebClient webClient, ProductResponseMapper productResponseMapper, InventoryRequestMapper inventoryRequestMapper, InventoryResponseMapper inventoryResponseMapper)
    {
        this.inventoryRepository = inventoryRepository;
        this.webClient = webClient;
        this.productResponseMapper = productResponseMapper;
        this.inventoryRequestMapper = inventoryRequestMapper;
        this.inventoryResponseMapper = inventoryResponseMapper;
    }

    @Override
    public List<InventoryResponseDto> getAll() {
        return inventoryRepository.findAll().stream()
                .map(inventory -> inventoryResponseMapper.toDto(inventory))
                .toList();
    }

    @Override
    @Transactional
    public InventoryResponseDto doPurchase(Integer quantity, String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode).orElseThrow(
                ()->new EntityNotFoundException("Product not found in inventory with sku-code: "+skuCode)
        );
        var totalQuantity = inventory.getQuantity()-quantity;
        if(totalQuantity<0){
            throw new RuntimeException("Operation not possible!");
        }
        inventory.setQuantity(totalQuantity);
        var response = inventoryResponseMapper.toDto(inventoryRepository.save(inventory));
        return response;
    }

    @Override
    public InventoryResponseDto save(InventoryRequestDto requestDto){
        var inventory = inventoryRequestMapper.toEntity(requestDto);

        var response = inventoryRepository.save(inventory);

        return inventoryResponseMapper.toDto(response);
    }

    @Override
    public ProductResponseDto isInStock(String code)
    {
        Inventory response = inventoryRepository.findBySkuCode(code).orElseThrow(
                ()-> new EntityNotFoundException("Product not found in Inventory with sku-code: "+code)
        );

        Product product = webClient.get()
                .uri("/api/v1/products/{id}",response.getProductId())
                .retrieve()
                .bodyToMono(Product.class)
                .block();

        var responseDto = productResponseMapper.toDto(product);
        responseDto.setInStock(true);

        return responseDto;
    }

    @Override
    public boolean isInStockLightWeight(String code) {
        return inventoryRepository.findBySkuCode(code).isPresent();
    }
}
