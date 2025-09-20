package com.ektaara.open_gem_gem.services;

import com.ektaara.open_gem_gem.dto.CartDTO;
import com.ektaara.open_gem_gem.entity.Cart;

import java.util.Optional;

public interface CartService {
    Optional<Cart> getCartByUserId(Long userId);
    Cart addOrUpdateCart(CartDTO cartDTO);
}
