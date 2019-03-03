package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "This is my vendor controller")
@RestController
@RequestMapping(VendorController.API_V1_VENDORS_URL)
public class VendorController {

    public static final String API_V1_VENDORS_URL = "/api/v1/vendors/";
    private VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }


    @ApiOperation(value = "This will get a list of all vendors", notes = "Notes about the api")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @ApiOperation(value = "This will get a single vendor based on an id", notes = "Notes about the api")
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getAllVendors(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @ApiOperation(value = "This will add a vendor to the vendors list", notes = "Notes about the api")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO addVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.addVendor(vendorDTO);
    }

    @ApiOperation(value = "This will update an existing vendor in the vendors list", notes = "Notes about the api")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@RequestBody VendorDTO vendorDTO, @PathVariable Long id) {
        return vendorService.saveVendorByDTO(id,vendorDTO);
    }

    @ApiOperation(value = "This will update an existing vendor parameters in the vendors list", notes = "Notes about the api")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchCustomer(@RequestBody VendorDTO vendorDTO, @PathVariable Long id) {
        return vendorService.patchVendorByDTO(id,vendorDTO);
    }

    @ApiOperation(value = "This will delete and existing vendor", notes = "Notes about the api")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        vendorService.deleteByVendorId(id);
    }
}
