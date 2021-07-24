package com.nextstudio.mvcrest.repositories;

import com.nextstudio.mvcrest.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
