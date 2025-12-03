package org.qsheker.productservice.web.mapper;

import org.mapstruct.Mapper;
import org.qsheker.productservice.model.Product;
import org.qsheker.productservice.web.dto.ProductRequest;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper
{
    Product toEntity(ProductRequest request);
}
