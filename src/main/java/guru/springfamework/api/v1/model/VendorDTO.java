package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by jt on 9/24/17.
 */
@Data
public class VendorDTO {
    private String name;
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
