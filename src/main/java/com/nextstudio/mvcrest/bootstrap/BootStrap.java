package com.nextstudio.mvcrest.bootstrap;

import com.nextstudio.mvcrest.model.Category;
import com.nextstudio.mvcrest.model.Customer;
import com.nextstudio.mvcrest.model.Vendor;
import com.nextstudio.mvcrest.repositories.CategoryRepository;
import com.nextstudio.mvcrest.repositories.CustomerRepository;
import com.nextstudio.mvcrest.repositories.VendorRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public BootStrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategoryData();

        System.out.println("==================================================");

        loadCustomerData();

        System.out.println("==================================================");

        loadVednorData();

    }

    private void loadCustomerData() {
        Customer john = new Customer();
        john.setFirstname("John");
        john.setLastname("Doe");

        Customer max = new Customer();
        max.setFirstname("Max");
        max.setLastname("Well");

        Customer jenny = new Customer();
        jenny.setFirstname("Jenny");
        jenny.setLastname("Kim");

        customerRepository.save(john);
        customerRepository.save(max);
        customerRepository.save(jenny);

        System.out.println("Data Loaded For Customers = " + customerRepository.count());
    }

    private void loadCategoryData() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Data Loaded For Category = " + categoryRepository.count());
    }

    public void loadVednorData() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Fruit City Center");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Makro");

        Vendor vendor3 = new Vendor();
        vendor3.setName("Game Store");

        vendorRepository.save(vendor1);        
        vendorRepository.save(vendor2);        
        vendorRepository.save(vendor3);
        
        System.out.println("Data Loaded For Vendors = " + vendorRepository.count());
    }

}
