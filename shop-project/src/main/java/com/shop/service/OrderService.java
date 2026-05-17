package com.shop.service;

import com.shop.entity.Customer;
import com.shop.entity.Order;
import com.shop.entity.Product;
import com.shop.repository.CustomerRepository;
import com.shop.repository.OrderRepository;
import com.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    // Создание заказа с подсчётом суммы и проверкой стока
    public Order createOrder(String username, List<Long> productIds) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Product> products = productRepository.findAllById(productIds);

        if (products.isEmpty()) {
            throw new RuntimeException("No valid products found");
        }

        // Проверяем наличие на складе
        for (Product product : products) {
            if (product.getStock() <= 0) {
                throw new RuntimeException("Product out of stock: " + product.getName());
            }
        }

        // Считаем общую стоимость
        BigDecimal total = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Уменьшаем остаток на складе
        products.forEach(p -> {
            p.setStock(p.getStock() - 1);
            productRepository.save(p);
        });

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(products);
        order.setTotalPrice(total);
        order.setStatus("PENDING");

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomer(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return orderRepository.findByCustomerId(customer.getId());
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
