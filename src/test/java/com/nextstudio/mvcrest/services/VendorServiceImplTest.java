package com.nextstudio.mvcrest.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.nextstudio.mvcrest.api.v1.mapper.VendorMapper;
import com.nextstudio.mvcrest.api.v1.model.VendorDTO;
import com.nextstudio.mvcrest.model.Vendor;
import com.nextstudio.mvcrest.repositories.VendorRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VendorServiceImplTest {

	@Mock
	private VendorRepository vendorRepository;

	VendorService vendorService;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);

		vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
	}

	@Test
	public void shouldGetVendors() throws Exception {
		Vendor vendor1 = new Vendor();
		vendor1.setName("Test 1");

		Vendor vendor2 = new Vendor();
		vendor2.setName("Test 2");

		Vendor vendor3 = new Vendor();
		vendor3.setName("Test 3");

		List<Vendor> vendors = Arrays.asList(vendor1, vendor2, vendor3);

		when(vendorRepository.findAll()).thenReturn(vendors);

		List<VendorDTO> vendorDTOs = vendorService.getAllVendors();

		assertEquals(3, vendorDTOs.size());
	}

	@Test
	public void shouldGetVendorById() throws Exception {
		Vendor vendor = new Vendor();
		vendor.setName("Test 1");
		vendor.setId(1L);

		when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

		VendorDTO vendorDTO = vendorService.getVendorById(1L);

		assertEquals("Test 1", vendorDTO.getName());
		assertEquals("/api/v1/vendors/1", vendorDTO.getVendorUrl());
	}

	@Test
	public void shouldCreateNewVendor() throws Exception {
		Vendor vendor = new Vendor();
		vendor.setName("Test 1");
		vendor.setId(1L);

		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(vendor.getName());

		when(vendorRepository.save(any())).thenReturn(vendor);

		VendorDTO actualVendorDTO = vendorService.createNewVendor(vendorDTO);

		assertEquals("Test 1", actualVendorDTO.getName());
		assertEquals("/api/v1/vendors/1", actualVendorDTO.getVendorUrl());		
	}

	@Test
	public void shouldUpdateVendor() throws Exception {

		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName("Test 1");

		Vendor updatedVendor = new Vendor();
		updatedVendor.setId(1L);
		updatedVendor.setName("New Name");

		when(vendorRepository.save(any())).thenReturn(updatedVendor);

		VendorDTO actualValue = vendorService.updateVendor(vendorDTO, 1L);

		assertEquals("New Name", actualValue.getName());
		assertEquals("/api/v1/vendors/1", actualValue.getVendorUrl());
	}

	@Test
	public void shouldPatchVendor() throws Exception {
		Vendor vendor = new Vendor();
		vendor.setId(1L);
		vendor.setName("Test 1");

		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(vendor.getName());

		Vendor updatedVendor = new Vendor();
		updatedVendor.setId(1L);
		updatedVendor.setName("New Name");

		when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
		when(vendorRepository.save(any())).thenReturn(updatedVendor);

		VendorDTO actualValue = vendorService.patchVendor(vendorDTO, 1L);

		assertEquals("New Name", actualValue.getName());
		assertEquals("/api/v1/vendors/1", actualValue.getVendorUrl());
	}

	@Test
	public void shouldDeleteVendorById() throws Exception {
		Long id = 1L;

		vendorService.deleteVendorById(id);

		verify(vendorRepository, times(1)).deleteById(anyLong());
	}
}
