package com.ektaara.open_gem_gem.services;

import com.ektaara.open_gem_gem.dto.CartDTO;
import com.ektaara.open_gem_gem.entity.Cart;
import com.ektaara.open_gem_gem.entity.User;
import com.ektaara.open_gem_gem.repository.CartRepository;
import com.ektaara.open_gem_gem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;


    private final UserRepository userRepository;


    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart addOrUpdateCart(CartDTO cartDTO) {
        Optional<User> userOptional = userRepository.findById(cartDTO.getUserId());
        if (userOptional.isPresent()) {
            Optional<Cart> existingCartOptional = cartRepository.findByUserId(cartDTO.getUserId());
            if (existingCartOptional.isPresent()) {
                Cart existingCart = existingCartOptional.get();
                existingCart.setProducts(cartDTO.getProducts());
                return cartRepository.save(existingCart);
            } else {
                Cart newCart = new Cart();
                newCart.setUser(userOptional.get());
                newCart.setProducts(cartDTO.getProducts());
                return cartRepository.save(newCart);
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
