package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.domain.Customer;
import guru.springfamework.model.CustomerDTO;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    public static final String JOHN = "JOHN";
    public static final String DOE = "DOE";
    public static final long ID = 1L;

    @Mock
    CustomerRepository customerRepository;

    CustomerMapper customerMapper =  CustomerMapper.INSTANCE;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService =  new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    public void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer()));
        assertThat(customerService.getAllCustomers().size(), is(2));
    }

    @Test
    public void getCustomerById() {
        Customer customer =  new Customer();
        customer.setId(1L);
        customer.setFirstName(JOHN);
        customer.setLastName(DOE);

        when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));

        CustomerDTO customerReturned = customerService.getCustomerById(ID);

        assertThat(customerReturned.getFirstName(), is(JOHN));
        assertThat(customerReturned.getLastName(), is(DOE));
    }

    @Test
    public void addCustomer() {
        CustomerDTO customerDTO =  new CustomerDTO();
        customerDTO.setFirstName("Hammad");
        customerDTO.setLastName("Yaqoob");

        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO savedDTO = customerService.addCustomer(customerDTO);

        assertThat(savedDTO.getFirstName(), is(customerDTO.getFirstName()));
        assertThat(savedDTO.getLastName(), is(customerDTO.getLastName()));
        assertThat(savedDTO.getCustomerUrl(), is("/api/v1/customers/1"));
    }

    @Test
    public void updateCustomer() {
        CustomerDTO customerDTO =  new CustomerDTO();
        customerDTO.setFirstName("Hammad");
        customerDTO.setLastName("Yaqoob");

        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO savedDTO = customerService.saveCustomerByDTO(1L, customerDTO);

        assertThat(savedDTO.getFirstName(), is(customerDTO.getFirstName()));
        assertThat(savedDTO.getLastName(), is(customerDTO.getLastName()));
        assertThat(savedDTO.getCustomerUrl(), is("/api/v1/customers/1"));
    }

    @Test
    public void deleteCustomer() {
        customerService.deleteByCustomerId(1L);

        verify(customerRepository).deleteById(1L);
    }
}