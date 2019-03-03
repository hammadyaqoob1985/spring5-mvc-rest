package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jt on 9/24/17.
 */
@Data
public class CustomerDTO {
    @ApiModelProperty(value = "first name", required = true)
    @JsonProperty("firstname")
    private String firstName;
    @ApiModelProperty(value = "last name", required = true)
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("customer_url")
    private String customerUrl;
}
