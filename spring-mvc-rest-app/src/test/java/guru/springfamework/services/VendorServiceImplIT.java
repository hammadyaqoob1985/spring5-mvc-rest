package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Vendor;
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
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {

        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

        Bootstrap bootstrap =  new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        vendorService =  new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void testPatchingName() {
        Long id =  getFirstId();

        Vendor original = vendorRepository.getOne(id);

        assertThat(original, is(notNullValue()));

        String originalFirstName = original.getName();

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Updated");

        vendorService.patchVendorByDTO(id, vendorDTO);

        Vendor savedVendor = vendorRepository.getOne(id);

        assertThat(savedVendor.getName(), not(originalFirstName));
    }

    private Long getFirstId() {
        return customerRepository.findAll().get(0).getId();
    }
}