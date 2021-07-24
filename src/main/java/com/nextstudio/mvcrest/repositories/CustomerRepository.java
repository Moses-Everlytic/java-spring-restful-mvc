package com.nextstudio.mvcrest.repositories;

import com.nextstudio.mvcrest.model.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
