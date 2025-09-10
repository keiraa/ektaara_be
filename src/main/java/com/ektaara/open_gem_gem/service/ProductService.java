package com.ektaara.open_gem_gem.service;

import com.ektaara.open_gem_gem.dto.ProductResponse;
import com.ektaara.open_gem_gem.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface ProductService {
   Product createProduct(
           String productName,
           Double sellingPrice,
           Double mrp,
           String discountType,
           Double discount,
           String sku,
           String productDescription,
           List<MultipartFile> images,
           Set<Long> categoryIds
   ) throws SQLException;

   List<ProductResponse> getProductsByCategory(Long categoryId);
}
