package com.nextstudio.mvcrest.api.v1.mapper;

import com.nextstudio.mvcrest.api.v1.model.CategoryDTO;
import com.nextstudio.mvcrest.model.Category;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);
}
