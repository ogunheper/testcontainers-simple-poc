package com.ogunheper.testcontainers.poc.services;

import com.ogunheper.testcontainers.poc.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductFilterService {

    private final MongoTemplate mongoTemplate;

    public List<Product> filter(Date date) {
        final Query query = new Query();

        if (Objects.nonNull(date)) {
            query.addCriteria(
                    Criteria.where("date").is(date)
            );
        }

        return mongoTemplate.find(query, Product.class);
    }
}
