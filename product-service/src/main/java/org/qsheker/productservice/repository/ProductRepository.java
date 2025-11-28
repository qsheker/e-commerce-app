package org.qsheker.productservice.repository;


import org.qsheker.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    Optional<Product> findById(String id);

    Optional<Product> findByName(String name);
}
