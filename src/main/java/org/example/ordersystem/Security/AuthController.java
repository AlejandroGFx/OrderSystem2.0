package org.example.ordersystem.Security;

import org.example.ordersystem.Customer.Customer;
import org.example.ordersystem.Customer.CustomerRepository;
import org.example.ordersystem.User.RegisterRequest;
import org.example.ordersystem.User.UserAccount;
import org.example.ordersystem.User.UserRepository;
import org.example.ordersystem.User.UserRole;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository, CustomerRepository customerRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
            if (registerRequest.username() == null || registerRequest.username().isBlank() ||
                registerRequest.password() == null  || registerRequest.password().isBlank()) {

                return ResponseEntity.badRequest().body("username y password son obligatorios");
            }
            if (userRepository.existsByUsername(registerRequest.username())) {
                return ResponseEntity.badRequest().body("Username exists");
            }

            UserRole role = registerRequest.role() != null ? registerRequest.role() : UserRole.USER;

        // Para usuarios USER exigimos un customerId válido (enlazando con un Customer existente)
        Customer customer = null;
        if (role == UserRole.USER) {
            if (registerRequest.customerId() == null) {
                return ResponseEntity.badRequest().body("Customer id is needed for users with role USER");
            }
            customer = customerRepository.findById(registerRequest.customerId())
                    .orElse(null);
            if (customer == null) {
                return ResponseEntity.badRequest().body("Customer not found with id " + registerRequest.customerId());
          }
        }

        UserAccount userAccount = new UserAccount();
            userAccount.setUsername(registerRequest.username());
            userAccount.setPassword(passwordEncoder.encode(registerRequest.password()));
            userAccount.setRole(role);
            userAccount.setCustomer(customer);
            userRepository.save(userAccount);

            return ResponseEntity.ok().body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.username(), authRequestDTO.password())
        );
        UserAccount userAccount = userRepository.findByUsername(authRequestDTO.username())
                .orElseThrow();
        String token = jwtService.generateToken(userAccount);
        return ResponseEntity.ok(new AuthResponseDTO(token, 3600000L));
    }
}
