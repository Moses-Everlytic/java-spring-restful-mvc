package com.nextstudio.mvcrest.services;

import java.util.List;

import com.nextstudio.mvcrest.api.v1.model.CategoryDTO;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);
}
