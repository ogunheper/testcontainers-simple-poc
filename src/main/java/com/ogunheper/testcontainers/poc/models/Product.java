package com.ogunheper.testcontainers.poc.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("products")
@Getter
@Setter
@Builder
public class Product {

    @Id
    private String id;

    private String name;

    private Date date;
}
