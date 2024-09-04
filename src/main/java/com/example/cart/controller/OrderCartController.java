package com.example.cart.controller;

import com.example.cart.model.OrderCart;
import com.example.cart.service.OrderCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/carts")
public class OrderCartController {
    @Autowired
    private OrderCartService orderCartService;

    @PostMapping
    public OrderCart createCart() {
        return orderCartService.createCart();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderCart> getOrderCart(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderCartService.getOrderCart(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderCart>> getAllOrderCarts() {
        try {
            List<OrderCart> orderCarts = orderCartService.getAllOrderCarts();
            return ResponseEntity.ok(orderCarts);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<OrderCart> addProductToCart(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        try {
            return ResponseEntity.ok(orderCartService.addProductToCart(cartId, productId, quantity));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{cartId}/place")
    public ResponseEntity<OrderCart> placeOrder(
            @PathVariable Long cartId,
            @RequestParam String customerName,
            @RequestParam String address) {
        try {
            OrderCart placedOrder = orderCartService.placeOrder(cartId, customerName, address);
            return ResponseEntity.ok(placedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
