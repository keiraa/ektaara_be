package com.ektaara.open_gem_gem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDescription {

    private String description;
    private ProductSpecs specifications;
    private String Care;

}
