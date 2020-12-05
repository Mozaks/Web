package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Suggestion;
import com.example.demo.entity.Tag;
import com.example.demo.entity.Vacancy;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.SuggestionRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private SuggestionRepository suggestionRepository;
    @Autowired
    private TagRepository tagRepository;

    public void mainView(Model model) {
        Iterable<Vacancy> vacancies = vacancyRepository.findAll();
        List<List<Tag>> lst = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            lst.add(tagRepository.findByVacancyId(vacancy.getId()));
        }
        model.addAttribute("vacancies", vacancies);
        model.addAttribute("lst", lst);
    }

    public void filter(@RequestParam String filter, Model model) {
        List<Vacancy> vacancyList;
        if (!filter.isEmpty()) {
            vacancyList = vacancyRepository.findByTitle(filter);
        } else {
            vacancyList = vacancyRepository.findAll();
        }
        model.addAttribute("vacancies", vacancyList);
    }

    public void selected(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id, Model model) throws CustomException {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() -> new CustomException());
        model.addAttribute("vacancy", vacancy);
        if (customer.getId().equals(vacancy.getAuthor().getId())) {
            model.addAttribute("yourself", false);
        } else {
            model.addAttribute("yourself", true);
        }
    }

    public void setWorker(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id) throws CustomException {
        Suggestion suggestion = new Suggestion();

        suggestion.setWorker(customer);
        suggestion.setVacancy(vacancyRepository.findById(id).orElseThrow(() -> new CustomException()));
        suggestion.setAuthor(vacancyRepository.findById(id).orElseThrow(() -> new CustomException()).getAuthor());

        suggestionRepository.save(suggestion);

    }

}
