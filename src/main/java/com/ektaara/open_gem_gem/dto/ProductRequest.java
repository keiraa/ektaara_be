package com.ektaara.open_gem_gem.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Blob;
import java.util.List;

@Data
@Builder
public class ProductRequest {
    private String productName;

    private String l1Category;

    private List<Long> Categories;

    private Double sellingPrice;

    private Double mrp;

    private String discountType;

    private Double discount;

    private String iconImageUrl;

    private List<String> imageUrl;

    private String sku;

    private Blob productDescription;
}
