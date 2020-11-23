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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VacancyController {//чек
    @Autowired
    private VacancyRepository vacancyRepository;

    @GetMapping("/vacancy")
    public String vacancy(@AuthenticationPrincipal Customer customer, Model model) {
        return "vacancy";
    }


    @GetMapping("/vacancyList")
    public String vacancyList(@AuthenticationPrincipal Customer customer, Model model) {
        Iterable<Vacancy> vacancies = vacancyRepository.findByAuthor_Id(customer.getId());
        model.addAttribute("vacancies", vacancies);
        return "vacancyList";
    }

    @PostMapping("/vacancy")
    public String addVacancy(@AuthenticationPrincipal Customer customer, @RequestParam String title,
                             @RequestParam String text,
                             @RequestParam String tag, Model model) {
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(title);
        vacancy.setText(text);
        vacancy.setTag(tag);
        vacancy.setAuthor(customer);
        vacancyRepository.save(vacancy);

        model.addAttribute("message", "Вакансия была успешно добавлена");

        return "vacancy";
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
        return "hello";
    }



}
