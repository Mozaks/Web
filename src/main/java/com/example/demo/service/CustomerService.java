package com.example.demo.service;

import com.example.demo.domain.ServerUrl;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.role.Role;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class CustomerService implements UserDetailsService {
    private static final Logger log = Logger.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByUsername(username);
    }

    public boolean addCustomer(Customer customer) {
        Customer customerDb = customerRepository.findByUsername(customer.getUsername());

        if (customerDb != null) {
            log.info("An attempt was made to create a user with existing data!");
            return false;
        }

        customer.setActive(false);
        customer.setActivationCode(UUID.randomUUID().toString());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);

        if (!StringUtils.isEmpty(customer.getEmail())) {
            String message = String.format(
                    "Greeting, %s! \n" +
                            "Please visit this link: " + ServerUrl.URL + "activate/%s", customer.getUsername(), customer.getActivationCode()
            );
            mailSender.send(customer.getEmail(), "Activation code", message);
            log.info("The email was sent to the user's email address: " + customer.getEmail() + " with the activation code");
        }

        log.info("The user was successfully created");
        return true;
    }

    public boolean activateCustomer(String code) {
        Customer customer = customerRepository.findByActivationCode(code);

        if (customer == null) {
            log.warn("Attempt to activate a nonexistent user");
            return false;
        }

        customer.setActivationCode(null);
        customer.setActive(true);
        customerRepository.save(customer);
        log.info("The user was successfully activated");
        return true;
    }
}
