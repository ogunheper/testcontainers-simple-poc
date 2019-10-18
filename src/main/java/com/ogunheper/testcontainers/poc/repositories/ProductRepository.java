package com.ogunheper.testcontainers.poc.repositories;

import com.ogunheper.testcontainers.poc.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
