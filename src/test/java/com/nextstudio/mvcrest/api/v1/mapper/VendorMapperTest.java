package com.nextstudio.mvcrest.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nextstudio.mvcrest.api.v1.model.VendorDTO;
import com.nextstudio.mvcrest.model.Vendor;

import org.junit.Test;

public class VendorMapperTest {

	VendorMapper vendorMapper = VendorMapper.INSTANCE;

	@Test
	public void shouldMapVendorToVendorDTO() throws Exception {
		Vendor vendor = new Vendor();
		vendor.setName("Fruit City");

		VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

		assertEquals("Fruit City", vendorDTO.getName());
	}
}