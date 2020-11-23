package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
@PreAuthorize("hasAuthority('ADMIN')")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String printCustomers(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customer-list";
    }

    @GetMapping("{customer}")
    public String customerEdit(@PathVariable Customer customer, Model model) {
        model.addAttribute("customer", customer);
        return "customer-edit";
    }

    @PostMapping
    public String saveCustomer(@RequestParam String customerName, @RequestParam("customerId") Customer customer)
    {
        customer.setUsername(customerName);
        customerRepository.save(customer);
        return "redirect:/customer";
    }


}
