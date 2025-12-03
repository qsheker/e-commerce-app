package org.qsheker.productservice.service.impl;

import org.qsheker.productservice.model.Product;
import org.qsheker.productservice.repository.ProductRepository;
import org.qsheker.productservice.service.ProductService;
import org.qsheker.productservice.web.dto.ProductRequest;
import org.qsheker.productservice.web.dto.ProductResponse;
import org.qsheker.productservice.web.mapper.ProductRequestMapper;
import org.qsheker.productservice.web.mapper.ProductResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;
    private final ProductRequestMapper productRequestMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductResponseMapper productResponseMapper, ProductRequestMapper productRequestMapper)
    {
        this.productRepository = productRepository;
        this.productResponseMapper = productResponseMapper;
        this.productRequestMapper = productRequestMapper;
    }

    @Override
    public ProductResponse getProductById(String id)
    {
        Product product = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Product not found with id: "+id)
        );
        return productResponseMapper.toDto(product);
    }

    @Override
    public ProductResponse create(ProductRequest request)
    {
        var product = productRequestMapper.toEntity(request);

        var result = productRepository.save(product);

        var response = productResponseMapper.toDto(result);

        return response;
    }

    @Override
    public List<ProductResponse> read()
    {
        return productRepository.findAll().stream()
                .map(product -> productResponseMapper.toDto(product))
                .toList();
    }

    @Override
    public List<ProductResponse> getAllBatch(List<String> ids) {
        return productRepository.findAllById(ids).stream()
                .map(product -> productResponseMapper.toDto(product))
                .toList();
    }

    @Override
    public ProductResponse update(String id,ProductRequest request)
    {
        var result = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Product not found with id: "+id)
        );
        productRepository.save(result);

        var response = productResponseMapper.toDto(result);

        return response;
    }

    @Override
    public void delete(String id)
    {
        var result = productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Product not found with id: "+id)
        );
        productRepository.delete(result);
    }
}
