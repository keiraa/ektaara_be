package com.ektaara.open_gem_gem.serviceImpl;

import com.ektaara.open_gem_gem.dto.ProductResponse;
import com.ektaara.open_gem_gem.entity.Category;
import com.ektaara.open_gem_gem.entity.Product;
import com.ektaara.open_gem_gem.exceptions.InvalidProductDescriptionException;
import com.ektaara.open_gem_gem.model.ProductDescription;
import com.ektaara.open_gem_gem.repository.CategoryRepository;
import com.ektaara.open_gem_gem.repository.ProductRepository;
import com.ektaara.open_gem_gem.service.StorageService;
import com.ektaara.open_gem_gem.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StorageService storageService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ProductResponse convertToResponse(Product product) {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDescription productDescription = null;

        try {
            if (product.getProductDescriptionJson() != null) {
                productDescription = objectMapper.readValue(product.getProductDescriptionJson(), ProductDescription.class);
            }
        } catch (Exception e) {
            // handle or log error
        }

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .sellingPrice(product.getSellingPrice())
                .mrp(product.getMrp())
                .discountType(product.getDiscountType())
                .discount(product.getDiscount())
                .iconImageUrl(product.getIconImageUrl())
                .imageUrl(product.getImageUrl())
                .sku(product.getSku())
                .productDescription(productDescription) // mapped here
                .build();
    }

    public List<ProductResponse> convertToResponseList(List<Product> products) {
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public Product createProduct(String productName,
                                 Double sellingPrice,
                                 Double mrp,
                                 String discountType,
                                 Double discount,
                                 String sku,
                                 String productDescription,
                                 List<MultipartFile> images,
                                 Set<Long> categoryIds) throws SQLException {

        if (productDescription != null) {
            try {
                ProductDescription pd = objectMapper.readValue(productDescription, ProductDescription.class);
                if (pd == null) {
                    throw new InvalidProductDescriptionException("Product description cannot be null");
                }
            } catch (Exception e) {
                throw new InvalidProductDescriptionException("Invalid product description JSON");
            }
        }

        if (images == null || images.isEmpty()) {
            throw new InvalidProductDescriptionException("Product images should be uploaded");
        }

        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : images) {
            String imageUrl = storageService.uploadFile(file);
            imageUrls.add(imageUrl);
        }

        // Fetch categories by IDs
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
        if (categories.isEmpty()) {
            throw new RuntimeException("Invalid category IDs provided");
        }

        Product product = Product.builder()
                .productName(productName)
                .sellingPrice(sellingPrice)
                .mrp(mrp)
                .discountType(discountType)
                .discount(discount)
                .sku(sku)
                .iconImageUrl(imageUrls.get(0))
                .imageUrl(imageUrls)
                .productDescriptionJson(productDescription)
                .categories(categories)
                .build();

        return productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        return convertToResponseList(productRepository.findAllByCategories_Id(categoryId));
    }
}

