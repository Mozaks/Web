package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vacancy-edit")
public class VacancyEditController {
    @Autowired
    private VacancyRepository vacancyRepository;

    @GetMapping
    public String printVacancy(@AuthenticationPrincipal Customer customer,Model model) {
        model.addAttribute("vacancies", vacancyRepository.findByAuthor_Id(customer.getId()));
        return "vacancyList";
    }

    @GetMapping("{vacancy}")
    public String vacancyEdit(@PathVariable Vacancy vacancy, Model model) {
        model.addAttribute("vacancy", vacancy);
        return "vacancyEdit";
    }

    @PostMapping
    public String saveVacancy(@RequestParam(name="vacancyTitle", required=false) String title , @RequestParam(name="vacancyTag", required=false) String tag ,
                              @RequestParam(name="vacancyText", required=false) String text , @RequestParam("vacancyId") Vacancy vacancy)
    {
        if (title != null)
        vacancy.setTitle(title);
        if (tag != null)
        vacancy.setTag(tag);
        if (text != null)
        vacancy.setText(text);

        vacancyRepository.save(vacancy);
        return "redirect:/vacancy-edit";
    }

}
