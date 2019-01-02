package guru.springfamework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by jt on 9/24/17.
 */
@Data
@AllArgsConstructor
public class CustomerListDTO {

    private List<CustomerDTO> customers;
}
