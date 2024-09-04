package com.example.cart.service;

import com.example.cart.model.OrderCart;
import com.example.cart.model.OrderItem;
import com.example.cart.model.Product;
import com.example.cart.repository.OrderCartRepository;
import com.example.cart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class OrderCartService {
    @Autowired
    private OrderCartRepository orderCartRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderCart addProductToCart(Long cartId, Long productId, int quantity) {
        Optional<OrderCart> cart = orderCartRepository.findById(cartId);
        if (cart.isPresent()) {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                OrderItem item = new OrderItem();
                item.setProduct(product.get());
                item.setQuantity(quantity);
                cart.get().getItems().add(item);
                return orderCartRepository.save(cart.get());
            } else {
                throw new RuntimeException("Product not found");
            }
        } else {
            throw new RuntimeException("Order Cart not found");
        }
    }

    public OrderCart createCart() {
        OrderCart cart = new OrderCart();
        return orderCartRepository.save(cart);
    }

    public OrderCart getOrderCart(Long id) {
        return orderCartRepository.findById(id).orElseThrow(() -> new RuntimeException("Order Cart not found"));
    }

    public OrderCart placeOrder(Long cartId, String customerName, String address) {
        OrderCart orderCart = orderCartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));

        // Add logic here to process the order
        orderCart.setCustomerName(customerName);
        orderCart.setAddress(address);


        // Additional business logic like stock validation, payment processing, etc.

        return orderCartRepository.save(orderCart);
    }

    public List<OrderCart> getAllOrderCarts() {
        // Logic to retrieve all OrderCart objects from the database
        // Assuming you have a repository or some data source to get the carts
        return orderCartRepository.findAll(); // This is just an example
    }
}



