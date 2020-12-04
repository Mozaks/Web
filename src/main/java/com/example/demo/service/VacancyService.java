package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class VacancyService {
    @Autowired
    VacancyRepository vacancyRepository;

    public void addVacancy(@AuthenticationPrincipal Customer customer, @RequestParam String title,
                           @RequestParam String text,
                           @RequestParam String tag) {
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(title);
        vacancy.setText(text);
        vacancy.setTag(tag);
        vacancy.setAuthor(customer);
        vacancyRepository.save(vacancy);
    }
}
