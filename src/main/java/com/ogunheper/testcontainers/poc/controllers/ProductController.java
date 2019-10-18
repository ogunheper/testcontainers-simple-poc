package com.ogunheper.testcontainers.poc.controllers;

import com.ogunheper.testcontainers.poc.models.Product;
import com.ogunheper.testcontainers.poc.services.ProductCreateService;
import com.ogunheper.testcontainers.poc.services.ProductFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductCreateService productCreateService;
    private final ProductFilterService productFilterService;

    @PostMapping
    public void create(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        productCreateService.create(date);
    }

    @GetMapping(params = "filter")
    public List<Product> filter(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return productFilterService.filter(date);
    }
}
