package org.qsheker.productservice.service;

import org.qsheker.productservice.web.dto.ProductRequest;
import org.qsheker.productservice.web.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);

    List<ProductResponse> read();

    ProductResponse update(String id,ProductRequest request);

    void delete(String id);
}
