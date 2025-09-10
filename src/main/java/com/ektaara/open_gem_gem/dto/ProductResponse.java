package com.ektaara.open_gem_gem.dto;// ProductResponse.java
import com.ektaara.open_gem_gem.entity.Category;
import com.ektaara.open_gem_gem.model.ProductDescription;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String productName;
    private String l1Category;
    private Double sellingPrice;
    private Double mrp;
    private String discountType;
    private Double discount;
    private String iconImageUrl;
    private List<String> imageUrl;
    private String sku;
    private ProductDescription productDescription; // Object, not string
    private Set<Category> categories;
}
