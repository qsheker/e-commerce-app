package org.qsheker.productservice.service.impl;

import org.qsheker.productservice.model.Product;
import org.qsheker.productservice.repository.ProductRepository;
import org.qsheker.productservice.service.ProductService;
import org.qsheker.productservice.web.dto.ProductRequest;
import org.qsheker.productservice.web.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        var product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        var result = productRepository.save(product);

        var response = ProductResponse.builder()
                .id(result.getId())
                .name(result.getName())
                .description(result.getDescription())
                .price(result.getPrice())
                .build();
        return response;
    }

    @Override
    public List<ProductResponse> read() {
        return productRepository.findAll().stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build())
                .toList();
    }

    @Override
    public ProductResponse update(String id,ProductRequest request) {
        var result = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Product not found with id: "+id)
        );
        productRepository.save(result);

        var response = ProductResponse.builder()
                .id(result.getId())
                .name(result.getName())
                .description(result.getDescription())
                .price(result.getPrice())
                .build();

        return response;
    }

    @Override
    public void delete(String id) {
        var result = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Product not found with id: "+id)
        );
        productRepository.delete(result);
    }
}
