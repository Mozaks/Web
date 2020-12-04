package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ProfileService {
    @Autowired
    VacancyRepository vacancyRepository;

    public void profileView(@AuthenticationPrincipal Customer customer, Model model) {
        Iterable<Vacancy> vacancies = vacancyRepository.findByAuthorId(customer.getId());
        model.addAttribute("vacancies", vacancies);
        model.addAttribute("user", customer);
    }
}
