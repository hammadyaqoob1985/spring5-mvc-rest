package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.hamcrest.Matchers;
import org.junit.Before;
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
        assertThat(customerDTO.getFirstName(), is(FIRST));
        assertThat(customerDTO.getLastName(), is(LAST));
    }
}