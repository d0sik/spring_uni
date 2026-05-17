package com.shop.controller;

import com.shop.entity.Order;
import com.shop.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // POST /api/orders — создать заказ (защищён)
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, List<Long>> body,
                                          Authentication authentication) {
        try {
            List<Long> productIds = body.get("productIds");
            Order order = orderService.createOrder(authentication.getName(), productIds);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // GET /api/orders/my — мои заказы (защищён)
    @GetMapping("/my")
    public List<Order> getMyOrders(Authentication authentication) {
        return orderService.getOrdersByCustomer(authentication.getName());
    }

    // GET /api/orders — все заказы (защищён)
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // PUT /api/orders/{id}/status — обновить статус (защищён)
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                           @RequestBody Map<String, String> body) {
        try {
            Order order = orderService.updateOrderStatus(id, body.get("status"));
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
