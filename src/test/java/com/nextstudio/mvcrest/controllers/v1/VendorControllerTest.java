package com.nextstudio.mvcrest.controllers.v1;

import static com.nextstudio.mvcrest.controllers.v1.AbstractControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.nextstudio.mvcrest.api.v1.model.VendorDTO;
import com.nextstudio.mvcrest.controllers.RestResponseEntityExceptionHandler;
import com.nextstudio.mvcrest.services.VendorService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class VendorControllerTest {

	@Mock
	private VendorService vendorService;

	@InjectMocks
	private VendorController vendorController;

	MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
				.setControllerAdvice(RestResponseEntityExceptionHandler.class).build();
	}

	@Test
	public void shouldGetAllVendors() throws Exception {
		VendorDTO vendorDTO1 = new VendorDTO();
		vendorDTO1.setName("Vendor 1");
		vendorDTO1.setVendorUrl("/api/v1/vendors/1");

		VendorDTO vendorDTO2 = new VendorDTO();
		vendorDTO2.setName("Vendor 2");
		vendorDTO2.setVendorUrl("/api/v1/vendors/2");

		List<VendorDTO> vendorDTOs = Arrays.asList(vendorDTO1, vendorDTO2);

		when(vendorService.getAllVendors()).thenReturn(vendorDTOs);

		mockMvc.perform(get("/api/v1/vendors/")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.vendors", hasSize(2)));
	}

	@Test
	public void shouldGetVendorById() throws Exception {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName("Vendor 1");
		vendorDTO.setVendorUrl("/api/v1/vendors/1");

		when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

		mockMvc.perform(get("/api/v1/vendors/1")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.name", equalTo("Vendor 1")))
					.andExpect(jsonPath("$.vendor_url", equalTo("/api/v1/vendors/1")));
	}

	@Test
	public void shouldCreateNewVendor() throws Exception {
		String vendorName = "Vendor 1";
		String vendorUrl = "/api/v1/vendors/1";

		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(vendorName);

		VendorDTO returnedVendorDTO = new VendorDTO();
		returnedVendorDTO.setName(vendorDTO.getName());
		returnedVendorDTO.setVendorUrl(vendorUrl);

		when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(returnedVendorDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/vendors/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendorDTO)))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.name", equalTo(vendorName)))
					.andExpect(jsonPath("$.vendor_url", equalTo(vendorUrl)));

	}

	@Test
	public void shouldUpdateVendor() throws Exception {
		String patchedName = "Vendor";
		String vendorUrl = "/api/v1/vendors/1";

		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(patchedName);

		VendorDTO returnedVendorDTO = new VendorDTO();
		returnedVendorDTO.setName(patchedName);
		returnedVendorDTO.setVendorUrl(vendorUrl);

		when(vendorService.updateVendor(any(VendorDTO.class), anyLong())).thenReturn(returnedVendorDTO);

		mockMvc.perform(put("/api/v1/vendors/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendorDTO)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.name", equalTo(patchedName)));
	}

	@Test
	public void shouldPatchVendor() throws Exception {

		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName("Vendor Test");

		VendorDTO returnedVendorDTO = new VendorDTO();
		returnedVendorDTO.setName("Vendor Test");
		returnedVendorDTO.setVendorUrl("/api/v1/vendors/1");

		when(vendorService.patchVendor(any(VendorDTO.class), anyLong())).thenReturn(returnedVendorDTO);

		mockMvc.perform(patch("/api/v1/vendors/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(vendorDTO)))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.name", equalTo(vendorDTO.getName())));
	}

	@Test
	public void shouldDeleteVendor() throws Exception {
		mockMvc.perform(delete("/api/v1/vendors/1")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());

		verify(vendorService, times(1)).deleteVendorById(anyLong());
	}
}
