package org.example.ordersystem.Customer;


import org.example.ordersystem.Order.OrderRepository;
import org.example.ordersystem.Errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerController(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResumeDTO>> getAllCustomers(Pageable pageable) {
        Page<CustomerResumeDTO> page =  this.customerRepository.findAll(pageable)
                .map(CustomerMapper::customerToCustomerResumeDTO);
        return ResponseEntity.ok(page);
    }



    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDetailDTO customer) {
       Customer newCustomer = new Customer();
       newCustomer.setFirstName(customer.firstName());
       newCustomer.setLastName(customer.lastName());
       newCustomer.setEmail(customer.email());
       return ResponseEntity.ok(this.customerRepository.save(newCustomer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDetailDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDetailDTO customerDetailDTO) {
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
        customer.setFirstName(customerDetailDTO.firstName());
        customer.setLastName(customerDetailDTO.lastName());
        customer.setEmail(customerDetailDTO.email());
        return ResponseEntity.ok(customerDetailDTO);



    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        this.customerRepository.deleteById(id);
    return ResponseEntity.noContent().build();}
}
