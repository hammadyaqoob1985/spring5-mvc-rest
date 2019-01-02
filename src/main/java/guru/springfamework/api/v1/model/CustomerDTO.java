package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jt on 9/24/17.
 */
@Data
public class CustomerDTO {
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("customer_url")
    private String customerUrl;
}
