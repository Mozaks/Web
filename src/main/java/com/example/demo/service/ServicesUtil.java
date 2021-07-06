package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Tag;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.VacancyRepository;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class ServicesUtil {


    static void showVacanciesAndTags(VacancyRepository vacancyRepository, Customer customer, TagRepository tagRepository, Model model, boolean isProfile) {
        Iterable<Vacancy> vacancies;
        if (isProfile) {
            vacancies = vacancyRepository.findByAuthorId(customer.getId());
        } else {
            vacancies = vacancyRepository.findAll();
        }
        List<List<Tag>> lst = new ArrayList<>();

        for (Vacancy vacancy : vacancies) {
            lst.add(tagRepository.findByVacancyId(vacancy.getId()));
        }

        model
                .addAttribute("vacancies", vacancies)
                .addAttribute("lst", lst);
        if (isProfile) {
            model.addAttribute("user", customer);
        }
    }
}
