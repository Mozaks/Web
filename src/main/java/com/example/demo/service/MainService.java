package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Suggestion;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.SuggestionRepository;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class MainService {
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private SuggestionRepository suggestionRepository;

    public void mainView(Model model) {
        Iterable<Vacancy> vacancies = vacancyRepository.findAll();
        model.addAttribute("vacancies", vacancies);
    }

    public void filter(@RequestParam String filter, Model model) {
        List<Vacancy> vacancyList;
        if (!filter.isEmpty()) {
            vacancyList = vacancyRepository.findByTag(filter);
        } else {
            vacancyList = vacancyRepository.findAll();
        }
        model.addAttribute("vacancies", vacancyList);
    }

    public void selected(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id, Model model) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        model.addAttribute("vacancy", vacancy);
        if (customer.getId().equals(vacancy.getAuthor().getId())) {
            model.addAttribute("yourself", false);
        } else {
            model.addAttribute("yourself", true);
        }
    }

    public void setWorker(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id) {
        Suggestion suggestion = new Suggestion();

        suggestion.setWorker(customer);
        suggestion.setVacancy(vacancyRepository.findById(id).orElseThrow());
        suggestion.setAuthor(vacancyRepository.findById(id).orElseThrow().getAuthor());

        suggestionRepository.save(suggestion);

    }

}
