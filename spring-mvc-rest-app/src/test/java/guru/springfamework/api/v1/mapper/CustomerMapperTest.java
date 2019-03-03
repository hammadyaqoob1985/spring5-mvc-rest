package guru.springfamework.api.v1.mapper;

import guru.springfamework.domain.Customer;
import guru.springfamework.model.CustomerDTO;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class CustomerMapperTest {

    public static final String FIRST = "first";
    public static final String LAST = "last";
    public static final long ID = 1L;
    CustomerMapper customerMapper =  CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {

        Customer customer =  new Customer();
        customer.setFirstName(FIRST);
        customer.setLastName(LAST);
        customer.setId(ID);

        CustomerDTO customerDTO =  customerMapper.customerToCustomerDTO(customer);

        assertThat(customerDTO, not(nullValue()));
        assertThat(customerDTO.getFirstname(), is(FIRST));
        assertThat(customerDTO.getLastname(), is(LAST));
    }

    @Test
    public void customerDTOToCustomer() {

        CustomerDTO customerDTO =  new CustomerDTO();
        customerDTO.setFirstname(FIRST);
        customerDTO.setLastname(LAST);

        Customer customer =  customerMapper.customerDTOToCustomer(customerDTO);

        assertThat(customer, not(nullValue()));
        assertThat(customer.getFirstName(), is(FIRST));
        assertThat(customer.getLastName(), is(LAST));
    }
}