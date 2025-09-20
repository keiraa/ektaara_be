package com.ektaara.open_gem_gem.serviceImpl;

import com.ektaara.open_gem_gem.dto.InventoryRequestDTO;
import com.ektaara.open_gem_gem.entity.Inventory;
import com.ektaara.open_gem_gem.entity.Product;
import com.ektaara.open_gem_gem.entity.Store;
import com.ektaara.open_gem_gem.repository.InventoryRepository;
import com.ektaara.open_gem_gem.repository.ProductRepository;
import com.ektaara.open_gem_gem.repository.StoreRepository;
import com.ektaara.open_gem_gem.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public List<Inventory> addInventory(List<InventoryRequestDTO> dtoList) {
        List<Inventory> inventoriesToSave = new ArrayList<>();

        for (InventoryRequestDTO dto : dtoList) {
            Product product = productRepository.findBySku(dto.getSku())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + dto.getSku()));

            Store store = storeRepository.findByStoreName(dto.getStoreName())
                    .orElseThrow(() -> new RuntimeException("Store not found with ID: " + dto.getStoreName()));

            Optional<Inventory> existingOpt = inventoryRepository.findByProductAndStore(product, store);

            Inventory inventory;

            if (existingOpt.isPresent()) {
                inventory = existingOpt.get();
                inventory.setQuantity(dto.getQuantity());
                inventory.setActive(dto.getActive());
            } else {
                inventory = Inventory.builder()
                        .product(product)
                        .store(store)
                        .quantity(dto.getQuantity())
                        .active(dto.getActive())
                        .build();
            }

            inventoriesToSave.add(inventory);
        }

        return inventoryRepository.saveAll(inventoriesToSave);
    }


    @Override
    public Inventory getInventoryBySkuAndStoreName(String sku, String storeName) {
        return inventoryRepository.findByProduct_SkuAndStore_StoreName(sku, storeName)
                .orElseThrow(() -> new RuntimeException("Inventory not found for SKU " + sku + " and store " + storeName));
    }

}
