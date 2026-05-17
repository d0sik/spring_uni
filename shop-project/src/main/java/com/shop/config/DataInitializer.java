package com.shop.config;

import com.shop.entity.Product;
import com.shop.repository.CustomerRepository;
import com.shop.repository.ProductRepository;
import com.shop.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    public DataInitializer(ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           CustomerService customerService) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Добавляем тестовые товары только если БД пустая
        if (productRepository.count() == 0) {
            Product p1 = new Product();
            p1.setName("Laptop Pro");
            p1.setDescription("High performance laptop");
            p1.setPrice(new BigDecimal("1299.99"));
            p1.setStock(10);
            productRepository.save(p1);

            Product p2 = new Product();
            p2.setName("Wireless Mouse");
            p2.setDescription("Ergonomic wireless mouse");
            p2.setPrice(new BigDecimal("29.99"));
            p2.setStock(50);
            productRepository.save(p2);

            Product p3 = new Product();
            p3.setName("Mechanical Keyboard");
            p3.setDescription("RGB mechanical keyboard");
            p3.setPrice(new BigDecimal("89.99"));
            p3.setStock(25);
            productRepository.save(p3);

            System.out.println("Sample products added to database");
        }

        // Добавляем тестового пользователя
        if (customerRepository.count() == 0) {
            customerService.register("admin", "admin@shop.com", "admin123");
            System.out.println("Test user created: admin / admin123");
        }
    }
}
