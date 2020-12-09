package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.exception.CustomException;
import com.example.demo.service.ProfileService;
import com.example.demo.service.VacancyEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                                @RequestParam(name = "vacancyText", required = false) String text, @PathVariable(value = "id") int id) throws CustomException {
        vacancyEditService.vacancyEdit(title, text, id, tag);
        return "redirect:/profile";
    }

    @PostMapping("vacancy/editor/{id}/remove")
    public String vacancyRemove(@PathVariable(value = "id") int id) throws CustomException {
        vacancyEditService.vacancyRemove(id);
        return "redirect:/profile";
    }

    @GetMapping("/edit")
    public String profileEditView(@AuthenticationPrincipal Customer customer, Model model) {
        profileService.profileEditView(customer, model);
        return "profile-edit";
    }

    @PostMapping("/edit")
    public String profileEdit(@AuthenticationPrincipal Customer customer, Model model,
                              @RequestParam(name = "newEmail", required = false) String newEmail,
                              @RequestParam(name = "newPassword", required = false) String newPassword,
                              @RequestParam(name = "newUsername", required = false) String newUsername) {
        profileService.profileEdit(customer, model, newEmail, newPassword, newUsername);
        return "redirect:/profile/edit";
    }

    @GetMapping("/edit/{id}")
    public String profileEditViewFromMail(@PathVariable(name = "id") int id, @AuthenticationPrincipal Customer customer, Model model) {
        profileService.profileEditViewFromMail(id, customer, model);
        return "profile-edit";
    }

}
