package com.ektaara.open_gem_gem.controllers;

import com.ektaara.open_gem_gem.dto.ProductResponse;
import com.ektaara.open_gem_gem.entity.Product;
import com.ektaara.open_gem_gem.exceptions.InvalidProductDescriptionException;

import com.ektaara.open_gem_gem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createProduct(
            @RequestParam String productName,
            @RequestParam Double sellingPrice,
            @RequestParam Double mrp,
            @RequestParam String discountType,
            @RequestParam Double discount,
            @RequestParam String sku,
            @RequestParam(required = false) String productDescription,
            @RequestParam Set<Long> categoryIds,
            @RequestPart List<MultipartFile> images
    ) {
        try {
            Product product = productService.createProduct(productName, sellingPrice, mrp,
                    discountType, discount, sku, productDescription, images, categoryIds);
            return ResponseEntity.ok(product);
        } catch (InvalidProductDescriptionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload files or save product");
        }
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductResponse> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
}

