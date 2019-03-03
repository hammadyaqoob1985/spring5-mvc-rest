package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository,
                     CustomerRepository customerRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        addCategories();
        addCustomers();
        addVendors();

    }

    private void addVendors() {
        Vendor vendor = new Vendor();
        vendor.setName("Western Tasty Fruits Ltd.");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Exotic Fruits Company");

        vendorRepository.save(vendor);
        vendorRepository.save(vendor2);
    }

    private void addCustomers() {
        Customer customer = new Customer();
        customer.setFirstName("Michael");
        customer.setLastName("Lachappele");

        Customer customer2 = new Customer();
        customer2.setFirstName("David");
        customer2.setLastName("Winter");

        customerRepository.save(customer);
        customerRepository.save(customer2);

    }

    private void addCategories() {
        Category dried = new Category();
        dried.setName("Dried");

        Category fruits = new Category();
        fruits.setName("Fruits");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        Category fresh = new Category();
        fresh.setName("fresh");

        categoryRepository.save(dried);
        categoryRepository.save(fruits);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);
        categoryRepository.save(fresh);

        System.out.println(categoryRepository.count());
    }
}
