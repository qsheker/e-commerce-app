package org.qsheker.inventoryservice.service.impl;

import org.qsheker.inventoryservice.repository.InventoryRepository;
import org.qsheker.inventoryservice.service.InventoryService;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService
{
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository)
    {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public boolean isInStock(String code)
    {
        return inventoryRepository.findBySkuCode(code).isPresent();
    }
}
