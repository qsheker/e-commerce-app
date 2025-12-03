package org.qsheker.productservice.web.mapper;

import org.mapstruct.Mapper;
import org.qsheker.productservice.model.Product;
import org.qsheker.productservice.web.dto.ProductResponse;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper
{
    ProductResponse toDto(Product product);
}
