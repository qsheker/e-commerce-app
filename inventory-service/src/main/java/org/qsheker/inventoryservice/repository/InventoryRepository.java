package org.qsheker.inventoryservice.repository;

import org.qsheker.inventoryservice.model.Inventory;
import org.qsheker.inventoryservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long>
{
    Optional<Inventory> findBySkuCode(String skuCode);

    Optional<Inventory> existsBySkuCode(String skuCode);
}
