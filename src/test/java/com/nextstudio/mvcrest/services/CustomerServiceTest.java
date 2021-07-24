package com.nextstudio.mvcrest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.nextstudio.mvcrest.api.v1.mapper.CustomerMapper;
import com.nextstudio.mvcrest.api.v1.model.CustomerDTO;
import com.nextstudio.mvcrest.model.Customer;
import com.nextstudio.mvcrest.repositories.CustomerRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerServiceTest {
	
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";

	@Mock
	CustomerRepository customerRepository;
	
	private CustomerService customerService;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);

		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
	}

	@Test
	public void shouldGetAllCustomer() throws Exception {
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstname("Test1");
		customer1.setLastname("Lastname1");

		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setFirstname("Test2");
		customer2.setLastname("Lastname2");
		
		Customer customer3 = new Customer();
		customer3.setId(3L);
		customer3.setFirstname("Test1");
		customer3.setLastname("Lastname1");

		List<Customer> customers = Arrays.asList(customer1, customer2, customer3);

		when(customerRepository.findAll()).thenReturn(customers);

		List<CustomerDTO> customerDTOs = customerService.getAllCustomers();

		assertEquals(3, customerDTOs.size());
	}

	@Test
	public void shouldGetCustomerById() throws Exception {
		Customer customer = new Customer();
		customer.setFirstname(FIRST_NAME);
		customer.setLastname(LAST_NAME);

		when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

		CustomerDTO customerDTO = customerService.getCustomerById(1L);

		assertEquals(FIRST_NAME, customerDTO.getFirstname());
		assertEquals(LAST_NAME, customerDTO.getLastname());
	}

	@Test
	public void shouldCreateNewCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRST_NAME);
		customerDTO.setLastname(LAST_NAME);

		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname(customerDTO.getFirstname());
		savedCustomer.setLastname(customerDTO.getLastname());
		savedCustomer.setId(1L);

		when(customerRepository.save(any())).thenReturn(savedCustomer);

		CustomerDTO returnedcCustomerDTO = customerService.createNewCustomer(customerDTO);
		returnedcCustomerDTO.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());

		assertEquals(savedCustomer.getFirstname(), returnedcCustomerDTO.getFirstname());
		assertEquals("/api/v1/customers/1", returnedcCustomerDTO.getCustomerUrl());
	}

	@Test
	public void shouldUpdateCustomer() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRST_NAME);
		customerDTO.setLastname(LAST_NAME);

		Customer updatedCustomer = new Customer();
		updatedCustomer.setFirstname(customerDTO.getFirstname());
		updatedCustomer.setLastname(customerDTO.getLastname());
		updatedCustomer.setId(1L);

		when(customerRepository.save(any())).thenReturn(updatedCustomer);

		CustomerDTO returnedcCustomerDTO = customerService.updateCustomer(customerDTO, 1L);
		returnedcCustomerDTO.setCustomerUrl("/api/v1/customers/" + updatedCustomer.getId());

		assertEquals(updatedCustomer.getFirstname(), returnedcCustomerDTO.getFirstname());
		assertEquals("/api/v1/customers/1", returnedcCustomerDTO.getCustomerUrl());
	}

	@Test
	public void shouldUpdateCustomerById() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname("Dave");

		Customer returnedCustomer = new Customer();
		returnedCustomer.setFirstname(FIRST_NAME);
		returnedCustomer.setLastname(LAST_NAME);
		returnedCustomer.setId(1L);

		Customer updatedCustomer = new Customer();
		updatedCustomer.setFirstname("Dave");
		updatedCustomer.setLastname(returnedCustomer.getLastname());
		updatedCustomer.setId(returnedCustomer.getId());

		when(customerRepository.findById(anyLong())).thenReturn(Optional.of(returnedCustomer));
		when(customerRepository.save(any())).thenReturn(updatedCustomer);

		CustomerDTO returnedcCustomerDTO = customerService.updateCustomerById(customerDTO, 1L);
		returnedcCustomerDTO.setCustomerUrl("/api/v1/customers/" + updatedCustomer.getId());

		assertEquals("Dave", returnedcCustomerDTO.getFirstname());
		assertEquals(LAST_NAME, returnedcCustomerDTO.getLastname());
		assertEquals("/api/v1/customers/1", returnedcCustomerDTO.getCustomerUrl());
	}

	@Test
	public void shouldDelteCustomerById() throws Exception {
		Long id = 1L;

		customerRepository.deleteById(id);

		verify(customerRepository, times(1)).deleteById(anyLong());
	}
}


