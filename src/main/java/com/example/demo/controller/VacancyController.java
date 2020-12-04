package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
