package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.domain.Customer;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static guru.springfamework.controllers.v1.CategoryController.API_V1_CATEGORIES_URL;
import static guru.springfamework.controllers.v1.CustomerController.API_V1_CUSTOMERS_URL;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstName("Jake");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customerDTO, customerDTO1));

        mockMvc.perform(get(API_V1_CUSTOMERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get(API_V1_CUSTOMERS_URL + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", equalTo("John")));
    }

    @Test
    public void addCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Hammad");
        customerDTO.setLastName("Yaqoob");

        CustomerDTO returnedCustomerDTO = new CustomerDTO();
        returnedCustomerDTO.setFirstName(customerDTO.getFirstName());
        returnedCustomerDTO.setLastName(customerDTO.getLastName());
        returnedCustomerDTO.setCustomerUrl(API_V1_CUSTOMERS_URL + "1");

        when(customerService.addCustomer(customerDTO)).thenReturn(returnedCustomerDTO);

        mockMvc.perform(post(API_V1_CUSTOMERS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", equalTo("Hammad")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_url", equalTo(API_V1_CUSTOMERS_URL + "1")));
    }

    @Test
    public void updateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Hammad");
        customerDTO.setLastName("Yaqoob");

        CustomerDTO returnedCustomerDTO = new CustomerDTO();
        returnedCustomerDTO.setFirstName(customerDTO.getFirstName());
        returnedCustomerDTO.setLastName(customerDTO.getLastName());
        returnedCustomerDTO.setCustomerUrl(API_V1_CUSTOMERS_URL + "1");

        when(customerService.saveCustomerByDTO(1L, customerDTO)).thenReturn(returnedCustomerDTO);

        mockMvc.perform(put(API_V1_CUSTOMERS_URL + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", equalTo("Hammad")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer_url", equalTo(API_V1_CUSTOMERS_URL + "1")));
    }

    @Test
    public void deleteByCustomerId() throws Exception {
        mockMvc.perform(delete(API_V1_CUSTOMERS_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(customerService).deleteByCustomerId(1L);
    }

    @Test
    public void testNameNotFound() throws Exception {

        when(customerService.getCustomerById(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc
                .perform(get(API_V1_CUSTOMERS_URL + "50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}