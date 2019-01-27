package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {

        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

        Bootstrap bootstrap =  new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        customerService =  new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void testPatchingFirstName() {
        Long id =  getFirstId();

        Customer original = customerRepository.getOne(id);

        assertThat(original, is(notNullValue()));

        String originalFirstName = original.getFirstName();
        String originalLastName = original.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Updated");

        customerService.patchCustomerByDTO(id, customerDTO);

        Customer savedCustomer = customerRepository.getOne(id);

        assertThat(savedCustomer.getLastName(), is(originalLastName));
        assertThat(savedCustomer.getFirstName(), not(originalFirstName));
    }

    @Test
    public void testPatchingLastName() {
        Long id =  getFirstId();

        Customer original = customerRepository.getOne(id);

        assertThat(original, is(notNullValue()));

       String originalFirstName = original.getFirstName();
       String originalLastName = original.getLastName();

       CustomerDTO customerDTO = new CustomerDTO();
       customerDTO.setLastName("Updated");

       customerService.patchCustomerByDTO(id, customerDTO);

       Customer savedCustomer = customerRepository.getOne(id);

        assertThat(savedCustomer.getFirstName(), is(originalFirstName));
        assertThat(savedCustomer.getLastName(), not(originalLastName));
    }

    private Long getFirstId() {
        return customerRepository.findAll().get(0).getId();
    }
}