package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vacancy")
public class VacancyController {
    @Autowired
    private VacancyRepository vacancyRepository;

    @GetMapping
    public String vacancy(@AuthenticationPrincipal Customer customer, Model model) {
        return "vacancy";
    }

    @PostMapping("/add")
    public String addVacancy(@AuthenticationPrincipal Customer customer, @RequestParam String title,
                             @RequestParam String text,
                             @RequestParam String tag, Model model) {
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(title);
        vacancy.setText(text);
        vacancy.setTag(tag);
        vacancy.setAuthor(customer);
        vacancyRepository.save(vacancy);

        return "redirect:/vacancy";
    }

    @GetMapping("/list")
    public String vacancyList(@AuthenticationPrincipal Customer customer, Model model) {
        Iterable<Vacancy> vacancies = vacancyRepository.findByAuthor_Id(customer.getId());
        model.addAttribute("vacancies", vacancies);
        return "vacancy-list";
    }

    @GetMapping("{id}/edit")
    public String vacancyEdit(@PathVariable(value = "id") int id, Model model) {
        if (!vacancyRepository.existsById(id))
        {
            return "redirect:/vacancy/list";
        }
        Optional<Vacancy> vacancy = vacancyRepository.findById(id);
        ArrayList<Vacancy> lst = new ArrayList<>();
        vacancy.ifPresent(lst::add);
        model.addAttribute("lst", lst);
        return "vacancy-edit";
    }

    @PostMapping("{id}/edit")
    public String vacancyUpdate(@RequestParam(name="vacancyTitle", required=false) String title , @RequestParam(name="vacancyTag", required=false) String tag ,
                              @RequestParam(name="vacancyText", required=false) String text , @PathVariable(value = "id") int id)
    {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        vacancy.setTitle(title);
        vacancy.setTag(tag);
        vacancy.setText(text);
        vacancyRepository.save(vacancy);
        return "redirect:/vacancy/list";
    }

    @PostMapping("{id}/remove")
    public String vacancyRemove(@PathVariable(value = "id") int id)
    {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        vacancyRepository.delete(vacancy);
        return "redirect:/vacancy/list";
    }


}
