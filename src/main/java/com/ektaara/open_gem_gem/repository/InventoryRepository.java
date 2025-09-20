package com.ektaara.open_gem_gem.repository;

import com.ektaara.open_gem_gem.entity.Inventory;
import com.ektaara.open_gem_gem.entity.Product;
import com.ektaara.open_gem_gem.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductAndStore(Product product, Store store);

    // Optional for direct query by SKU and storeName
    Optional<Inventory> findByProduct_SkuAndStore_StoreName(String sku, String storeName);
}
