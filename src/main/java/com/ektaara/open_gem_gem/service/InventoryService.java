package com.ektaara.open_gem_gem.service;

import com.ektaara.open_gem_gem.dto.InventoryRequestDTO;
import com.ektaara.open_gem_gem.entity.Inventory;

import java.util.List;

public interface InventoryService {
    List<Inventory> addInventory(List<InventoryRequestDTO> dtoList);
    Inventory getInventoryBySkuAndStoreName(String sku, String storeName);
}
