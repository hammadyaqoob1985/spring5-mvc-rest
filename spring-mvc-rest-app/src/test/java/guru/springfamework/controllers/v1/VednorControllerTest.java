package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static guru.springfamework.controllers.v1.VendorController.API_V1_VENDORS_URL;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VednorControllerTest {

    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getAllVendors() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("John");

        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName("Jake");

        when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendorDTO, vendorDTO1));

        mockMvc.perform(get(API_V1_VENDORS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("John");

        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

        mockMvc.perform(get(API_V1_VENDORS_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("John")));
    }

    @Test
    public void addVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Hammad");

        VendorDTO returnedVendorDTO = new VendorDTO();
        returnedVendorDTO.setName(vendorDTO.getName());
        returnedVendorDTO.setVendorUrl(API_V1_VENDORS_URL + "1");

        when(vendorService.addVendor(vendorDTO)).thenReturn(returnedVendorDTO);

        mockMvc.perform(post(API_V1_VENDORS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Hammad")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", equalTo(API_V1_VENDORS_URL + "1")));
    }

    @Test
    public void updateVendor() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Hammad");

        VendorDTO returnedVendorDTO = new VendorDTO();
        returnedVendorDTO.setName(vendorDTO.getName());
        returnedVendorDTO.setVendorUrl(API_V1_VENDORS_URL + "1");

        when(vendorService.saveVendorByDTO(1L, vendorDTO)).thenReturn(returnedVendorDTO);

        mockMvc.perform(put(API_V1_VENDORS_URL + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Hammad")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url", equalTo(API_V1_VENDORS_URL + "1")));
    }

    @Test
    public void deleteByVendorId() throws Exception {
        mockMvc.perform(delete(API_V1_VENDORS_URL + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(vendorService).deleteByVendorId(1L);
    }

    @Test
    public void testNameNotFound() throws Exception {

        when(vendorService.getVendorById(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc
                .perform(get(API_V1_VENDORS_URL + "50")
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