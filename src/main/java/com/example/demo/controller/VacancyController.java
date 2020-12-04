package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.VacancyRepository;
import com.example.demo.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/vacancy")
public class VacancyController {
    @Autowired
    private VacancyService VacancyService;

    @GetMapping
    public String vacancy() {
        return "vacancy";
    }

    @PostMapping("/add")
    public String addVacancy(@AuthenticationPrincipal Customer customer, @RequestParam String title,
                             @RequestParam String text,
                             @RequestParam String tag) {
        VacancyService.addVacancy(customer, title, text, tag);
        return "redirect:/profile";
    }


}
