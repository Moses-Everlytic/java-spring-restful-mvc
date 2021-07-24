package com.nextstudio.mvcrest.services;

import java.util.List;

import com.nextstudio.mvcrest.api.v1.model.VendorDTO;

public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO getVendorById(Long id);

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO updateVendor(VendorDTO vendorDTO, Long id);

    VendorDTO patchVendor(VendorDTO vendorDTO, Long id);

    void deleteVendorById(Long id);
}
