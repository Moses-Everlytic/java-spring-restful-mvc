package com.nextstudio.mvcrest.services;

import java.util.List;
import java.util.stream.Collectors;

import com.nextstudio.mvcrest.api.v1.mapper.VendorMapper;
import com.nextstudio.mvcrest.api.v1.model.VendorDTO;
import com.nextstudio.mvcrest.controllers.v1.VendorController;
import com.nextstudio.mvcrest.exceptions.ResourceNotFoundException;
import com.nextstudio.mvcrest.model.Vendor;
import com.nextstudio.mvcrest.repositories.VendorRepository;

import org.springframework.stereotype.Service;

@Service
public class VendorServiceImpl implements VendorService {

    private VendorRepository vendorRepository;
    private VendorMapper vendorMapper;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
	}

	@Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(VendorController.BASE_URL + vendor.getId());

                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(VendorController.BASE_URL+ vendor.getId());

                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = new Vendor();
        vendor.setName(vendorDTO.getName());

        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO returnVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnVendorDTO.setVendorUrl(VendorController.BASE_URL + savedVendor.getId());

        return returnVendorDTO;
    }

    @Override
    public VendorDTO updateVendor(VendorDTO vendorDTO, Long id) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);

        return saveVendorReturnVendorDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(VendorDTO vendorDTO, Long id) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if(vendorDTO.getName() != null) {
                        vendor.setName(vendorDTO.getName());
                    }
                    
                    return saveVendorReturnVendorDTO(vendor);
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    private VendorDTO saveVendorReturnVendorDTO(Vendor vendor) {
        VendorDTO returnedVendorDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
        returnedVendorDTO.setVendorUrl(VendorController.BASE_URL+ vendor.getId());

        return returnedVendorDTO;
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }    
}
