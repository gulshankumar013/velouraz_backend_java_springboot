package com.Velouraz.velouraz.controller;

import com.Velouraz.velouraz.entity.Cart;
import com.Velouraz.velouraz.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174",
})
public class CartController {

    @Autowired
    private CartRepository cartRepo;

    // 🔹 ADD TO CART

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Cart cart) {

        Cart saved = cartRepo.save(cart);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Item added to cart",
                        "data", saved
                )
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCartByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cartRepo.findByUserId(userId));
    }


    // 🔹 FETCH CART ITEMS
    @GetMapping("/all")
    public ResponseEntity<?> getCartItems() {
        return ResponseEntity.ok(cartRepo.findAll());
    }

    // 🔹 REMOVE ITEM
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeItem(@PathVariable Long id) {
        cartRepo.deleteById(id);
        return ResponseEntity.ok("Item removed");
    }
}
