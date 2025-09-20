package com.ektaara.open_gem_gem.repository;

import com.ektaara.open_gem_gem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategories_Id(Long categoryId);
    Optional<Product> findBySku(String sku);
}
