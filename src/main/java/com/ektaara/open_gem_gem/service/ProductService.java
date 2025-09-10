package com.ektaara.open_gem_gem.service;

import com.ektaara.open_gem_gem.dto.ProductRequest;
import com.ektaara.open_gem_gem.entity.Product;

public interface ProductService {

   Product createProducts(ProductRequest productRequest);
}
