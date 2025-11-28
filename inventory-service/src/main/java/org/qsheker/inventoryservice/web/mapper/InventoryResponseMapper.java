package org.qsheker.inventoryservice.web.mapper;

import org.mapstruct.Mapper;
import org.qsheker.inventoryservice.model.Inventory;
import org.qsheker.inventoryservice.web.dto.InventoryResponseDto;

@Mapper(componentModel = "spring")
public interface InventoryResponseMapper
{
    InventoryResponseDto toDto(Inventory inventory);
}
