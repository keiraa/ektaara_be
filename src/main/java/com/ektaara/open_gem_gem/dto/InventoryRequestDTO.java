package com.ektaara.open_gem_gem.dto;

import lombok.Data;

@Data
public class InventoryRequestDTO {
    private String sku;
    private String storeName;
    private Integer quantity;
    private Boolean active;
}
