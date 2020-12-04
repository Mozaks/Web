package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


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

    @GetMapping("{id}/edit")
    public String customerEdit(@PathVariable(value = "id") int id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        model.addAttribute("customer", customer);
        return "customer-edit";
    }

    @PostMapping("{id}/edit")
    public String saveCustomer(@RequestParam String customerName, @PathVariable(value = "id") int id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        if (!customerName.isEmpty()) {
            customer.setUsername(customerName);
        }
        customerRepository.save(customer);
        return "redirect:/customer";
    }

    @PostMapping("{id}/remove")
    public String removeCustomer(@PathVariable(value = "id") int id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        customerRepository.delete(customer);
        return "redirect:/customer";
    }


}
