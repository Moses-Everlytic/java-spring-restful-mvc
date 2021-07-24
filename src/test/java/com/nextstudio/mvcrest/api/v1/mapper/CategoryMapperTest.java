package com.nextstudio.mvcrest.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nextstudio.mvcrest.api.v1.model.CategoryDTO;
import com.nextstudio.mvcrest.model.Category;

import org.junit.Test;

public class CategoryMapperTest {

	private static final long ID = 1L;
	private static final String NAME = "Beans";

	CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

	@Test
	public void shouldCompileCategoryToCategoryDTO() {
		Category category = new Category();
		category.setName(NAME);
		category.setId(ID);
		
		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

		assertEquals(Long.valueOf(ID), categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}
}