package org.qsheker.inventoryservice.web.mapper;

import org.mapstruct.Mapper;
import org.qsheker.inventoryservice.model.Product;
import org.qsheker.inventoryservice.web.dto.ProductResponseDto;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper
{
    ProductResponseDto toDto(Product product);
}
