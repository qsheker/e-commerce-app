package org.qsheker.productservice.api;

import org.qsheker.productservice.service.ProductService;
import org.qsheker.productservice.web.dto.ProductRequest;
import org.qsheker.productservice.web.dto.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse save(@RequestBody ProductRequest request)
    {
        return productService.create(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getById(@PathVariable String id)
    {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse update(@PathVariable String id, @RequestBody ProductRequest request)
    {
        return productService.update(id,request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts()
    {
        return productService.read();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String id)
    {
        productService.delete(id);
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllBatch(@RequestBody List<String> ids)
    {
        return productService.getAllBatch(ids);
    }

}
