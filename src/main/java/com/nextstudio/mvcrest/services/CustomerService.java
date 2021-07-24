package com.nextstudio.mvcrest.services;

import java.util.List;

import com.nextstudio.mvcrest.api.v1.model.CustomerDTO;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id);

    CustomerDTO updateCustomerById(CustomerDTO customerDTO, Long id);

    void deleteCustomerById(Long id);
}
