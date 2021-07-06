package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Suggestion;
import com.example.demo.entity.Tag;
import com.example.demo.entity.Vacancy;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.SuggestionRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.VacancyRepository;
import com.example.demo.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private TagRepository tagRepository;

    public void adminView(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
    }

    public void selectedCustomerView(@PathVariable(value = "id") int id, Model model) throws CustomException {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomException());
        model.addAttribute("customer", customer);
    }

    public void updateCustomer(@RequestParam String customerName, @RequestParam(name = "roles") Role role, @PathVariable(value = "id") int id) throws CustomException {
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
        List<Suggestion> suggestionList = suggestionRepository.findByWorkerId(customer.getId());
        if (suggestionList == null) {
            List<Suggestion> lst = suggestionRepository.findByAuthorId(customer.getId());
            for (Suggestion suggestion : lst) {
                suggestionRepository.delete(suggestion);
            }
        } else {
            List<Suggestion> lst = suggestionList;
            for (Suggestion suggestion : lst) {
                suggestionRepository.delete(suggestion);
            }
        }
        List<Vacancy> vacancies = vacancyRepository.findByWorkerId(customer.getId());
        for (Vacancy vacancy : vacancies) {
            vacancy.setWorker(null);
            vacancyRepository.save(vacancy);
        }
        vacancies = vacancyRepository.findByAuthorId(customer.getId());
        for (Vacancy vacancy : vacancies) {
            List<Tag> tags = tagRepository.findByVacancyId(vacancy.getId());
            for (Tag tag : tags) {
                tagRepository.delete(tag);
            }
            vacancyRepository.delete(vacancy);
        }
        customerRepository.delete(customer);

    }

}
