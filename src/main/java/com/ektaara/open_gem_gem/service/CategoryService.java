package com.ektaara.open_gem_gem.service;

import com.ektaara.open_gem_gem.entity.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory(Category category);
    List<Category> getAllCategories();
}
