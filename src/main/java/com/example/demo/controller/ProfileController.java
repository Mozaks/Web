package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.VacancyRepository;
import com.example.demo.service.ProfileService;
import com.example.demo.service.VacancyEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    VacancyEditService vacancyEditService;
    @Autowired
    ProfileService profileService;

    @GetMapping
    public String profile(@AuthenticationPrincipal Customer customer, Model model) {
        profileService.profileView(customer, model);
        return "profile";
    }

    @GetMapping("vacancy/editor/{id}/edit")
    public String vacancyEdit(@PathVariable(value = "id") int id, Model model) {
        if (!vacancyEditService.isVacancyExists(id)) {
            return "redirect:/profile";
        }
        vacancyEditService.vacancyEditView(id, model);
        return "vacancy-edit";
    }

    @PostMapping("vacancy/editor/{id}/edit")
    public String vacancyUpdate(@RequestParam(name = "vacancyTitle", required = false) String title, @RequestParam(name = "vacancyTag", required = false) String tag,
                                @RequestParam(name = "vacancyText", required = false) String text, @PathVariable(value = "id") int id) {
        vacancyEditService.vacancyEdit(title, tag, text, id);
        return "redirect:/profile";
    }

    @PostMapping("vacancy/editor/{id}/remove")
    public String vacancyRemove(@PathVariable(value = "id") int id) {
        vacancyEditService.vacancyRemove(id);
        return "redirect:/profile";
    }


}
