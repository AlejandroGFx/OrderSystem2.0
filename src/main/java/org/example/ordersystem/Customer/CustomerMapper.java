package org.example.ordersystem.Customer;

public class CustomerMapper {
CustomerRepository customerRepository;

CustomerMapper(CustomerRepository customerRepository) {


}
    public static CustomerResumeDTO customerToCustomerResumeDTO(Customer customer){
        return new CustomerResumeDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail()
        );
    }
}
