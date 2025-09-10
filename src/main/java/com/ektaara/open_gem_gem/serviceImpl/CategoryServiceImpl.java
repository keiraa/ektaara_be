package com.ektaara.open_gem_gem.serviceImpl;

import com.ektaara.open_gem_gem.entity.Category;
import com.ektaara.open_gem_gem.repository.CategoryRepository;
import com.ektaara.open_gem_gem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
