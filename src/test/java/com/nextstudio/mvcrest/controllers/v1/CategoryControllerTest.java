package com.nextstudio.mvcrest.controllers.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.nextstudio.mvcrest.api.v1.model.CategoryDTO;
import com.nextstudio.mvcrest.controllers.RestResponseEntityExceptionHandler;
import com.nextstudio.mvcrest.exceptions.ResourceNotFoundException;
import com.nextstudio.mvcrest.services.CategoryService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CategoryControllerTest {

	public static final String NAME = "Jim";

	@Mock
	private CategoryService categoryService;

	@InjectMocks
	private CategoryController categoryController;

	MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	@Test
	public void shouldGetCategories() throws Exception {
		CategoryDTO category1 = new CategoryDTO();
		category1.setId(1L);
		category1.setName(NAME);

		CategoryDTO category2 = new CategoryDTO();
		category2.setId(2L);
		category2.setName("Mike");

		List<CategoryDTO> categories = Arrays.asList(category1, category2);

		when(categoryService.getAllCategories()).thenReturn(categories);

		mockMvc.perform(get("/api/v1/categories/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.categories", hasSize(2)));
	}

	@Test
	public void shouldGetCategoryByName() throws Exception {
		CategoryDTO category = new CategoryDTO();
		category.setId(1L);
		category.setName(NAME);

		when(categoryService.getCategoryByName(anyString())).thenReturn(category);

		mockMvc.perform(get("/api/v1/categories/" + NAME).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME)));
	}

	@Test
	public void shouldGetCategoryByNameReturnNotFound() throws Exception {
		when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get("/api/v1/categories/987").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	} 
}
