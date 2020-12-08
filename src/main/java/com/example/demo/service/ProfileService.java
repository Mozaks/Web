package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Tag;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.VacancyRepository;
import com.example.demo.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private TagRepository tagRepository;

    public void profileView(@AuthenticationPrincipal Customer customer, Model model) {
        Iterable<Vacancy> vacancies = vacancyRepository.findByAuthorId(customer.getId());
        List<List<Tag>> lst = new ArrayList<>();

        for (Vacancy vacancy : vacancies) {
            lst.add(tagRepository.findByVacancyId(vacancy.getId()));
        }

        model.addAttribute("vacancies", vacancies);

        model.addAttribute("lst", lst);

        model.addAttribute("user", customer);

    }
}
