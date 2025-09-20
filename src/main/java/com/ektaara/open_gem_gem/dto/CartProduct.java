package com.ektaara.open_gem_gem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProduct {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
}
