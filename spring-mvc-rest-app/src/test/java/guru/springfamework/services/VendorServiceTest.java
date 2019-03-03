package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceTest {

    private VendorMapper vendorMapper;
    public static final String NAME = "first";
    public static final long ID = 1L;

    @Mock
    private VendorRepository vendorRepository;

    private VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorMapper =  VendorMapper.INSTANCE;
        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    public void getAllVendors() {
        when(vendorRepository.findAll()).thenReturn(Arrays.asList(new Vendor(), new Vendor()));
        assertThat(vendorService.getAllVendors().size(), is(2));
    }

    @Test
    public void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);
        when(vendorRepository.findById(ID)).thenReturn(Optional.of(vendor));
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        assertThat(vendorDTO, not(nullValue()));
        assertThat(vendorDTO.getName(), is(NAME));
        assertThat(vendorDTO.getVendorUrl(), is("/api/v1/vendors/1"));
    }

    @Test
    public void addVendor() {

        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        VendorDTO vendorDTO =  new VendorDTO();
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl("/api/v1/vendors/1");

        when(vendorRepository.save(ArgumentMatchers.any(Vendor.class))).thenReturn(vendor);

        VendorDTO returnedVendorDTO = vendorService.addVendor(vendorDTO);

        assertThat(returnedVendorDTO, not(nullValue()));
        assertThat(returnedVendorDTO.getName(), is(NAME));
        assertThat(returnedVendorDTO.getVendorUrl(), is("/api/v1/vendors/1"));

    }

    @Test
    public void updateVendor() {
        VendorDTO vendorDTO =  new VendorDTO();
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl("/api/v1/vendors/1");

        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        when(vendorRepository.save(ArgumentMatchers.any(Vendor.class))).thenReturn(vendor);

        VendorDTO savedDTO = vendorService.saveVendorByDTO(1L, vendorDTO);

        assertThat(savedDTO.getName(), is(vendorDTO.getName()));
        assertThat(savedDTO.getVendorUrl(), is("/api/v1/vendors/1"));
    }

    @Test
    public void deleteCustomer() {
        vendorService.deleteByVendorId(1L);

        verify(vendorRepository).deleteById(1L);
    }

}