package com.example.demo.controller;

import com.example.demo.exception.CustomException;
import com.example.demo.role.Role;
import com.example.demo.service.AdminService;
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
    private AdminService adminService;

    @GetMapping
    public String printCustomers(Model model) {
        adminService.adminView(model);
        return "customer-list";
    }

    @GetMapping("{id}/edit")
    public String customerEdit(@PathVariable(value = "id") int id, Model model) throws CustomException {
        adminService.selectedCustomerView(id, model);
        return "customer-edit";
    }

    @PostMapping("{id}/edit")
    public String saveCustomer(@RequestParam String customerName, @RequestParam(name = "roles") Role role, @PathVariable(value = "id") int id) throws CustomException {
        adminService.saveCustomer(customerName, role, id);
        return "redirect:/customer";
    }

    @PostMapping("{id}/remove")
    public String removeCustomer(@PathVariable(value = "id") int id) throws CustomException {
        adminService.removeCustomer(id);
        return "redirect:/customer";
    }


}
