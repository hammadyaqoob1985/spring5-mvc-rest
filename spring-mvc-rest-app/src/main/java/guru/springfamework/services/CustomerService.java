package guru.springfamework.services;

import guru.springfamework.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long Id);

    CustomerDTO addCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);

    CustomerDTO patchCustomerByDTO(Long id, CustomerDTO customerDTO);

    void deleteByCustomerId(Long id);
}
