package com.nextstudio.mvcrest.repositories;

import com.nextstudio.mvcrest.model.Vendor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long>{
}
