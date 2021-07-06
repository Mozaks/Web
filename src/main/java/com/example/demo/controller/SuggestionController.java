package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.IllegalIdException;
import com.example.demo.repository.VacancyRepository;
import com.example.demo.role.Role;
import com.example.demo.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/suggestion")
public class SuggestionController {
    @Autowired
    private SuggestionService suggestionService;
    @Autowired
    private VacancyRepository vacancyRepository;

    @GetMapping()
    public String vacancy(@AuthenticationPrincipal Customer customer, Model model) {
        if (customer.getRoles().contains(Role.FREELANCER)) {
            suggestionService.showSuggestionToFreelancer(customer, model);
        } else {
            suggestionService.showSuggestionToNotFreelancer(customer, model);
        }
        return "suggestion";
    }

    @PostMapping("/appoint/{idSug}/vacancy/{idVac}")
    public String appoint(@PathVariable(value = "idSug") int idSug, @PathVariable(value = "idVac") int idVac, @AuthenticationPrincipal Customer customer) throws CustomException, IllegalIdException {
        if (!customer.getId().equals(vacancyRepository.findById(idVac).orElseThrow(() -> new CustomException()).getAuthor().getId())) {
            System.out.println(customer.getId());
            System.out.println(vacancyRepository.findById(idVac).orElseThrow(() -> new CustomException()).getAuthor().getId());
            throw new IllegalIdException();
        } else {
            suggestionService.appoint(idSug, idVac, customer);
            return "redirect:/suggestion";
        }
    }

}
