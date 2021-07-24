package com.nextstudio.mvcrest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.nextstudio.mvcrest.api.v1.mapper.CategoryMapper;
import com.nextstudio.mvcrest.api.v1.model.CategoryDTO;
import com.nextstudio.mvcrest.model.Category;
import com.nextstudio.mvcrest.repositories.CategoryRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CategoryServiceTest {

	private static final Long ID = 1L;
	private static final String NAME = "Grace";

	private CategoryService categoryService;

	@Mock
	CategoryRepository categoryRepository;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);

		categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
	}

	@Test
	public void shouldGetAllCategories() throws Exception {
		List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

		when(categoryRepository.findAll()).thenReturn(categories);

		List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();

		assertEquals(3, categoryDTOs.size());
	}

	@Test
	public void shouldGetCategoryByName() throws Exception {
		Category category = new Category();
		category.setId(ID);
		category.setName(NAME);

		when(categoryRepository.findByName(anyString())).thenReturn(category);

		CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

		assertEquals(NAME, categoryDTO.getName());
		assertEquals(ID, categoryDTO.getId());

	}
}