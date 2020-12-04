package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Suggestion;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.SuggestionRepository;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class MainController {
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private SuggestionRepository suggestionRepository;

    @GetMapping("/hello")
    public String vacancy(Model model) {
        Iterable<Vacancy> vacancies = vacancyRepository.findAll();
        model.addAttribute("vacancies", vacancies);

        return "main-page";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Model model) {
        List<Vacancy> vacancyList;
        if (!filter.isEmpty()) {
            vacancyList = vacancyRepository.findByTag(filter);
        } else {
            vacancyList = vacancyRepository.findAll();
        }
        model.addAttribute("vacancies", vacancyList);
        return "main-page";
    }

    @GetMapping("/selected/{id}")
    public String selected(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id, Model model) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        model.addAttribute("vacancy", vacancy);
        if (customer.getId().equals(vacancy.getAuthor().getId())) {
            model.addAttribute("yourself", false);
        } else {
            model.addAttribute("yourself", true);
        }
        return "current-vacancy";
    }

    @PostMapping("/selected/{id}")
    public String setWorker(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id) {
        Suggestion suggestion = new Suggestion();

        suggestion.setWorker(customer);
        suggestion.setVacancy(vacancyRepository.findById(id).orElseThrow());
        suggestion.setAuthor(vacancyRepository.findById(id).orElseThrow().getAuthor());

        suggestionRepository.save(suggestion);

        return "redirect:/hello";
    }


}
