package com.ektaara.open_gem_gem.controllers;

import com.ektaara.open_gem_gem.dto.CartDTO;
import com.ektaara.open_gem_gem.entity.Cart;
import com.ektaara.open_gem_gem.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        Optional<Cart> cartOptional = cartService.getCartByUserId(userId);
        return cartOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cart> addOrUpdateCart(@RequestBody CartDTO cartDTO) {
        return ResponseEntity.ok(cartService.addOrUpdateCart(cartDTO));
    }
}
