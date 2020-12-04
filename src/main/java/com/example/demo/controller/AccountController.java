package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller

public class AccountController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VacancyRepository vacancyRepository;

    @GetMapping("/suggestion/{id}")
    public String vacancy(@PathVariable(value = "id") int id, Model model) {
        Customer customer = customerRepository.findById(id).orElseThrow();

        Set<Customer> setSug = customer.getSuggestions();
        model.addAttribute("suggestion", setSug);

        Set<Customer> setReq = customer.getRequests();
        model.addAttribute("request", setReq);

        Set<Vacancy> vacancies = new HashSet<>();

        for (Customer cust : setSug) {
            Customer worker = customerRepository.findById(cust.getId()).orElseThrow();
            Set<Vacancy> vac = worker.getVacancies();
            for (Vacancy vacancy : vac) {
                if (vacancy.getAuthor().getId().equals(customer.getId())) {
                    vacancies.add(vacancy);
                }
            }
        }
        model.addAttribute("vacancies", vacancies);

        model.addAttribute("suggestionCount", customer.getVacancies().size());
        return "suggestion";
    }

    @PostMapping("/appoint/{idSug}/vacancy/{idVac}")
    public String appoint(@PathVariable(value = "idSug") int idSug, @PathVariable(value = "idVac") int idVac, @AuthenticationPrincipal Customer customer) {
        Vacancy vacancy = vacancyRepository.findById(idVac).orElseThrow();
        vacancy.setWorker(customerRepository.findById(idSug).orElseThrow());
        vacancyRepository.save(vacancy);
        Customer cust = customerRepository.findById(idSug).orElseThrow();
        cust.getVacancies().remove(vacancy);
        if (cust.getVacancies().isEmpty()) {
            cust.getRequests().remove(vacancyRepository.findById(idVac).get().getAuthor());
        }
        customerRepository.save(cust);
        return String.format("redirect:/suggestion/%s", customer.getId());
    }

}
