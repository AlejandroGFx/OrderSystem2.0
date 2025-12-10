package org.example.ordersystem.Security;

import org.example.ordersystem.Order.OrderRepository;
import org.example.ordersystem.User.UserAccount;
import org.example.ordersystem.User.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("authz")
public class AuthorizationService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public AuthorizationService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public boolean canAccessCustomer(Long customerId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null) return false;
        if (isAdmin(authentication)) return true;

        String username = authentication.getName();
        Long myCustomerId = userRepository.findByUsername(username)
                .map(UserAccount::getCustomer)
                .map(c -> c != null ? c.getId() : null)
                .orElse(null);

        return myCustomerId != null && myCustomerId.equals(customerId);
    }

    private boolean isAdmin(Authentication authentication){
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_ADMIN"));
    }
}
