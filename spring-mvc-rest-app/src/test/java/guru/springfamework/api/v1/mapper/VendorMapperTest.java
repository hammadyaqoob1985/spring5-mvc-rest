package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class VendorMapperTest {

    public static final String NAME = "first";
    public static final long ID = 1L;
    VendorMapper vendorMapper =  VendorMapper.INSTANCE;


    @Test
    public void vendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        VendorDTO venderDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertThat(venderDTO, Matchers.not(Matchers.nullValue()));
        assertThat(venderDTO.getName(), is(NAME));

    }

    @Test
    public void vendorDTOToVendor() {

        VendorDTO vendorDTO =  new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vender = vendorMapper.vendorDTOToVendor(vendorDTO);

        assertThat(vender, Matchers.not(Matchers.nullValue()));
        assertThat(vender.getName(), is(NAME));

    }
}