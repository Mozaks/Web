package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HelloController {
    @Autowired
    private VacancyRepository vacancyRepository;


    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Model model) {
        List<Vacancy> vacancyList;
        if (!filter.isEmpty()) {
            vacancyList = vacancyRepository.findByTag(filter);
        } else {
            vacancyList = vacancyRepository.findAll();
        }
        model.addAttribute("vacancies", vacancyList);
        return "hello";
    }

}
