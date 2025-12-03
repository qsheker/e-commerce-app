package org.qsheker.inventoryservice.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.Synchronized;
import org.qsheker.inventoryservice.model.Inventory;
import org.qsheker.inventoryservice.model.Product;
import org.qsheker.inventoryservice.repository.InventoryRepository;
import org.qsheker.inventoryservice.service.InventoryService;
import org.qsheker.inventoryservice.web.dto.*;
import org.qsheker.inventoryservice.web.mapper.InventoryRequestMapper;
import org.qsheker.inventoryservice.web.mapper.InventoryResponseMapper;
import org.qsheker.inventoryservice.web.mapper.ProductResponseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService
{
    private final InventoryRepository inventoryRepository;
    private final ProductResponseMapper productResponseMapper;
    private final InventoryRequestMapper inventoryRequestMapper;
    private final InventoryResponseMapper inventoryResponseMapper;
    private final WebClient.Builder webClientBuilder;
    private final static String URL_PREFIX = "http://product-service";

    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductResponseMapper productResponseMapper, InventoryRequestMapper inventoryRequestMapper, InventoryResponseMapper inventoryResponseMapper, WebClient.Builder webClientBuilder)
    {
        this.inventoryRepository = inventoryRepository;
        this.productResponseMapper = productResponseMapper;
        this.inventoryRequestMapper = inventoryRequestMapper;
        this.inventoryResponseMapper = inventoryResponseMapper;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public List<InventoryResponseDto> getAll() {
        return inventoryRepository.findAll().stream()
                .map(inventory -> inventoryResponseMapper.toDto(inventory))
                .toList();
    }

    @Override
    @Transactional
    public List<InventoryResponseDto> doPurchaseBatch(List<InventoryBatchRequestDto> requestDtos) {
        var ids = requestDtos.stream()
                .map(InventoryBatchRequestDto::getProductId)
                .toList();

        List<Product> fetchedProducts = webClientBuilder.build()
                .post()
                .uri(URL_PREFIX + "/api/v1/products/batch")
                .bodyValue(ids)
                .retrieve()
                .bodyToFlux(Product.class)
                .collectList()
                .block();

        List<Inventory> inventoriesFromDb = inventoryRepository.findAllByProductIdIn(ids);

        Map<String, Integer> quantityMap = requestDtos.stream()
                .collect(Collectors.toMap(
                        InventoryBatchRequestDto::getProductId,
                        InventoryBatchRequestDto::getQuantity
                ));

        List<InventoryResponseDto> responseDtos = new ArrayList<>();

        for (var prod : fetchedProducts) {
            Integer qty = quantityMap.get(prod.getId());

            String sku = requestDtos.stream()
                    .filter(r -> r.getProductId().equals(prod.getId()))
                    .findFirst()
                    .map(InventoryBatchRequestDto::getSkuCode)
                    .orElse(null);

            inventoriesFromDb.stream()
                    .filter(inv -> inv.getProductId().equals(prod.getId()))
                    .findFirst()
                    .ifPresent(inv -> inv.setQuantity(inv.getQuantity() - qty));

            var dto = InventoryResponseDto.builder()
                    .productId(prod.getId())
                    .skuCode(sku)
                    .price(prod.getPrice())
                    .quantity(qty)
                    .build();

            responseDtos.add(dto);
        }


        inventoryRepository.saveAll(inventoriesFromDb);

        return responseDtos;

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
        Inventory inventory = inventoryRepository.findBySkuCode(code).orElseThrow(
                ()-> new EntityNotFoundException("Product not found in Inventory with sku-code: "+code)
        );

        Product product = webClientBuilder.build()
                .get()
                .uri(URL_PREFIX+"/api/v1/products/{id}", inventory.getProductId())
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
