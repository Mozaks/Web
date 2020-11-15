package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistrationController {
    @Autowired
    private CustomerRepository customerRepository;
    @GetMapping("/registration")
    public String registration()
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String addCustomer(Customer customer, Model model)
    {
        Customer customerDb = customerRepository.findByUsername(customer.getUsername());

        if (customerDb != null){
            model.addAttribute("message", "Customer alreage exists!");
            return "registration";
        }

        customer.setActive(true);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        roles.add(Role.USER);
        customer.setRoles(roles);
        customerRepository.save(customer);

        return "redirect:/login";
    }
}
