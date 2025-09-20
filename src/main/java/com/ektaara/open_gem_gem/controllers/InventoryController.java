package com.ektaara.open_gem_gem.controllers;

import com.ektaara.open_gem_gem.dto.InventoryRequestDTO;
import com.ektaara.open_gem_gem.entity.Inventory;
import com.ektaara.open_gem_gem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<List<Inventory>> addInventory(@RequestBody List<InventoryRequestDTO> dtoList) {
        List<Inventory> saved = inventoryService.addInventory(dtoList);
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/fetchInv")
    public ResponseEntity<Inventory> getInventoryBySkuAndStoreName(
            @RequestParam String sku,
            @RequestParam String storeName) {
        Inventory inventory = inventoryService.getInventoryBySkuAndStoreName(sku, storeName);
        return ResponseEntity.ok(inventory);
    }
}

