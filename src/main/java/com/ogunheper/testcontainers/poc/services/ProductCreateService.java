package com.ogunheper.testcontainers.poc.services;

import com.ogunheper.testcontainers.poc.models.Product;
import com.ogunheper.testcontainers.poc.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProductCreateService {

    private final ProductRepository productRepository;

    public void create(Date date) {
        Product aProduct = this.aProduct();
        aProduct.setDate(date);

        productRepository.save(
                aProduct
        );
    }

    private Product aProduct() {
        return Product.builder().build();
    }
}
