package org.qsheker.inventoryservice.web.mapper;

import org.mapstruct.Mapper;
import org.qsheker.inventoryservice.model.Inventory;
import org.qsheker.inventoryservice.web.dto.InventoryRequestDto;

@Mapper(componentModel = "spring")
public interface InventoryRequestMapper
{
    Inventory toEntity(InventoryRequestDto dto);
}
