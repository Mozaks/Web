package com.example.demo.controller;

import com.example.demo.entity.Vacancy;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private VacancyRepository vacancyRepository;

    @GetMapping("/hello")
    public String vacancy(Model model) {
        Iterable<Vacancy> vacancies = vacancyRepository.findAll();
        model.addAttribute("vacancies", vacancies);

        return "hello";
    }

}