package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by jt on 9/24/17.
 */
@Data
public class VendorDTO {
    @ApiModelProperty(value = "name", required = true)
    private String name;
    @ApiModelProperty(value = "vendor url", readOnly = true)
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
