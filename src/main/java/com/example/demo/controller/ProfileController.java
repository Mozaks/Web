package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.IllegalIdException;
import com.example.demo.repository.VacancyRepository;
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
    private VacancyEditService vacancyEditService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private VacancyRepository vacancyRepository;

    @GetMapping
    public String profile(@AuthenticationPrincipal Customer customer, Model model) {
        profileService.profileView(customer, model);
        return "profile";
    }

    @GetMapping("vacancy/editor/{id}/edit")
    public String vacancyEdit(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id, Model model) throws CustomException, IllegalIdException {
        if (isWrongCustomer(customer, id)) {
            throw new IllegalIdException();
        } else {
            if (!vacancyEditService.isVacancyExists(id)) {
                return "redirect:/profile";
            }
            vacancyEditService.vacancyEditView(id, model);
            return "vacancy-edit";
        }
    }

    @PostMapping("vacancy/editor/{id}/edit")
    public String vacancyUpdate(@AuthenticationPrincipal Customer customer, @RequestParam(name = "vacancyTitle", required = false) String title, @RequestParam(name = "vacancyTag", required = false) String tag,
                                @RequestParam(name = "vacancyText", required = false) String text, @PathVariable(value = "id") int id) throws CustomException, IllegalIdException {
        if (isWrongCustomer(customer, id)) {
            throw new IllegalIdException();
        } else {
            vacancyEditService.vacancyEdit(title, text, id, tag);
            return "redirect:/profile";
        }
    }

    @PostMapping("vacancy/editor/{id}/remove")
    public String vacancyRemove(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id) throws CustomException, IllegalIdException {
        if (isWrongCustomer(customer, id)) {
            throw new IllegalIdException();
        } else {
            vacancyEditService.vacancyRemove(id);
            return "redirect:/profile";
        }
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
    public String profileEditViewFromMail(@PathVariable(name = "id") int id, @AuthenticationPrincipal Customer customer, Model model) throws IllegalIdException, CustomException {
        if (isWrongCustomer(customer, id)) {
            throw new IllegalIdException();
        } else {
            profileService.profileEditViewFromMail(id, customer, model);
            return "profile-edit";
        }
    }

    private boolean isWrongCustomer(Customer customer, int id) throws CustomException {
        return !customer.getId().equals(vacancyRepository.findById(id).orElseThrow(() -> new CustomException()).getAuthor().getId());
    }

}
