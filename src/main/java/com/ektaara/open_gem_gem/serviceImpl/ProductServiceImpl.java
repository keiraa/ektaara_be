package com.ektaara.open_gem_gem.serviceImpl;

import com.ektaara.open_gem_gem.dto.ProductRequest;
import com.ektaara.open_gem_gem.entity.Product;
import com.ektaara.open_gem_gem.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Product createProducts(ProductRequest productRequest) {
        return new Product();
    }
}
