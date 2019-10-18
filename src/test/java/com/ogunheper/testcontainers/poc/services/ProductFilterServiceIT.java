package com.ogunheper.testcontainers.poc.services;

import com.ogunheper.testcontainers.poc.models.Product;
import com.ogunheper.testcontainers.poc.repositories.ProductRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = ProductFilterServiceIT.Initializer.class)
public class ProductFilterServiceIT {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private ProductFilterService productFilterService;

    @Autowired
    private ProductCreateService productCreateService;

    @Autowired
    private ProductRepository productRepository;

    @ClassRule
    public static GenericContainer mongodb = new GenericContainer("mongo:4.0.13")
            .waitingFor(Wait.forLogMessage(".*waiting for connections on port 27017.*", 1))
            .withStartupTimeout(Duration.ofSeconds(15));

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues testPropertyValues = TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongodb.getContainerIpAddress(),
                    "spring.data.mongodb.port=" + mongodb.getMappedPort(27017)
            );

            testPropertyValues.applyTo(applicationContext);
        }
    }

    @Before
    public void setUp() throws Exception {
        productRepository.deleteAll();

        productCreateService.create(simpleDateFormat.parse("2019-01-01"));
        productCreateService.create(simpleDateFormat.parse("2019-01-01"));
        productCreateService.create(simpleDateFormat.parse("2019-01-02"));
        productCreateService.create(simpleDateFormat.parse("2019-01-03"));
    }

    @Test
    @SneakyThrows
    public void shouldFilterCorrectly() {
        final Date date = simpleDateFormat.parse("2019-01-01");

        List<Product> productList = productFilterService.filter(date);
        Assert.assertThat("", productList.size(), Matchers.is(2));
    }

    @Test
    @SneakyThrows
    public void shouldNotFilterNonExistingDate() {
        final Date date = simpleDateFormat.parse("2020-01-01");

        List<Product> productList = productFilterService.filter(date);
        Assert.assertThat("", productList.size(), Matchers.is(0));
    }
}
