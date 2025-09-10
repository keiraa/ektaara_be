package com.ektaara.open_gem_gem.repository;

import com.ektaara.open_gem_gem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
