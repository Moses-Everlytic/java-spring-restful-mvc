package com.nextstudio.mvcrest.controllers.v1;

import static com.nextstudio.mvcrest.controllers.v1.AbstractControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.nextstudio.mvcrest.api.v1.model.CustomerDTO;
import com.nextstudio.mvcrest.controllers.RestResponseEntityExceptionHandler;
import com.nextstudio.mvcrest.exceptions.ResourceNotFoundException;
import com.nextstudio.mvcrest.services.CustomerService;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CustomerControllerTest {

    private static final String CUSTOMER_URL = "/api/v1/customers/1";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    @DisplayName("Should Get All Customers")
    public void shouldGetAllCustomers() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname("Test1");
		customer1.setLastname("Lastname1");

		CustomerDTO customer2 = new CustomerDTO();
		customer2.setFirstname("Test2");
		customer2.setLastname("Lastname2");
		
		CustomerDTO customer3 = new CustomerDTO();
		customer3.setFirstname("Test1");
		customer3.setLastname("Lastname1");

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2, customer3);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)));
    }

    @Test
    public void shouldGetCustomerById() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)));
    }

    @Test
    public void shouldCreateNewCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        CustomerDTO returnedCustomerDTO = new CustomerDTO();
        returnedCustomerDTO.setFirstname(customer.getFirstname());
        returnedCustomerDTO.setLastname(customer.getLastname());
        returnedCustomerDTO.setCustomerUrl(CUSTOMER_URL);

        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(returnedCustomerDTO);

        mockMvc.perform(post("/api/v1/customers/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void shouldUpdateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        CustomerDTO returnedCustomerDTO = new CustomerDTO();
        returnedCustomerDTO.setFirstname(customer.getFirstname());
        returnedCustomerDTO.setLastname(customer.getLastname());
        returnedCustomerDTO.setCustomerUrl(CUSTOMER_URL);

        when(customerService.updateCustomer(any(CustomerDTO.class), anyLong())).thenReturn(returnedCustomerDTO);

        mockMvc.perform(put("/api/v1/customers/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void shouldUpdateCustomerById() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Jimmy");

        CustomerDTO returnedCustomerDTO = new CustomerDTO();
        returnedCustomerDTO.setFirstname(customer.getFirstname());
        returnedCustomerDTO.setLastname(LAST_NAME);
        returnedCustomerDTO.setCustomerUrl(CUSTOMER_URL);

        when(customerService.updateCustomerById(any(CustomerDTO.class), anyLong())).thenReturn(returnedCustomerDTO);

        mockMvc.perform(patch("/api/v1/customers/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Jimmy")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void shouldDeleteCustomerById() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/1")
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                
        verify(customerService).deleteCustomerById(anyLong());
    }

    @Test
	public void shouldGetCustomerByIdReturnNotFound() throws Exception {
		when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get("/api/v1/customers/987").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	} 
}
