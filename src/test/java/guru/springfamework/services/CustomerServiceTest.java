package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
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
}