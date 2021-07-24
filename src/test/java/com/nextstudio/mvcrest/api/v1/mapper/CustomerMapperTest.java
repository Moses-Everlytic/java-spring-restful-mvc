package com.nextstudio.mvcrest.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import com.nextstudio.mvcrest.api.v1.model.CustomerDTO;
import com.nextstudio.mvcrest.model.Customer;

import org.junit.Test;

public class CustomerMapperTest {
	// private static final String CUSTOMER_URL = "/api/v1/customers/1";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";

	CustomerMapper customerMapper = CustomerMapper.INSTANCE;

	@Test
	public void shouldCompileCustomerToCustomerDTO() throws Exception {
		Customer customer = new Customer();
		customer.setFirstname(FIRST_NAME);
		customer.setLastname(LAST_NAME);

		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

		assertEquals(FIRST_NAME, customerDTO.getFirstname());
		assertEquals(LAST_NAME, customerDTO.getLastname());
	}

	@Test
	public void shouldMapCustomerDTOToCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRST_NAME);
		customerDTO.setLastname(LAST_NAME);

		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

		assertEquals(FIRST_NAME, customer.getFirstname());
		assertEquals(LAST_NAME, customer.getLastname());
	}
}