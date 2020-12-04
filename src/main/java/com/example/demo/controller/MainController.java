package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.SuggestionRepository;
import com.example.demo.repository.VacancyRepository;
import com.example.demo.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private MainService mainService;

    @GetMapping("/hello")
    public String vacancy(Model model) {
        mainService.mainView(model);
        return "main-page";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Model model) {
        mainService.filter(filter, model);
        return "main-page";
    }

    @GetMapping("/selected/{id}")
    public String selected(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id, Model model) {
        mainService.selected(customer, id, model);
        return "current-vacancy";
    }

    @PostMapping("/selected/{id}")
    public String setWorker(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id) {
        mainService.setWorker(customer, id);

        return "redirect:/hello";
    }


}
