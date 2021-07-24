package com.nextstudio.mvcrest.api.v1.mapper;

import com.nextstudio.mvcrest.api.v1.model.VendorDTO;
import com.nextstudio.mvcrest.model.Vendor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VendorMapper {
  
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDTO vendorToVendorDTO(Vendor vendor);

    Vendor vendorDTOToVendor(VendorDTO vendorDTO);
}
