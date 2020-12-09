package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdminService {
    @Autowired
    CustomerRepository customerRepository;

    public void adminView(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
    }

    public void selectedCustomerView(@PathVariable(value = "id") int id, Model model) throws CustomException {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomException());
        model.addAttribute("customer", customer);
    }

    public void saveCustomer(@RequestParam String customerName, @RequestParam(name = "roles") Role role, @PathVariable(value = "id") int id) throws CustomException {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomException());
        if (!customerName.isEmpty()) {
            customer.setUsername(customerName);
        }
        if (role != null) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            customer.setRoles(roleSet);
        }
        customerRepository.save(customer);
    }

    public void removeCustomer(@PathVariable(value = "id") int id) throws CustomException {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomException());
        customerRepository.delete(customer);
    }

}
