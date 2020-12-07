package com.example.demo.service;

import com.example.demo.domain.ServerUrl;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByUsername(username);
    }

    public boolean addCustomer(Customer customer) {
        Customer customerDb = customerRepository.findByUsername(customer.getUsername());

        if (customerDb != null) {
            return false;
        }

        customer.setActive(true);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        customer.setRoles(roles);
        customer.setActivationCode(UUID.randomUUID().toString());
        customerRepository.save(customer);

        if (!StringUtils.isEmpty(customer.getEmail())) {
            String message = String.format(
                    "Greeting, %s! \n" +
                            "Please visit this link: " + ServerUrl.URL + "activate/%s", customer.getUsername(), customer.getActivationCode()
            );
            mailSender.send(customer.getEmail(), "Activation code", message);
        }

        return true;
    }

    public boolean activateCustomer(String code) {
        Customer customer = customerRepository.findByActivationCode(code);

        if (customer == null) {
            return false;
        }

        customer.setActivationCode(null);

        customerRepository.save(customer);

        return true;
    }
}
