package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class VacancyEditService {
    @Autowired
    VacancyRepository vacancyRepository;

    public boolean isVacancyExists(@PathVariable(value = "id") int id) {
        return vacancyRepository.existsById(id);
    }


    public void vacancyEditView(@PathVariable(value = "id") int id, Model model) {
        Optional<Vacancy> vacancy = vacancyRepository.findById(id);
        ArrayList<Vacancy> lst = new ArrayList<>();
        vacancy.ifPresent(lst::add);
        model.addAttribute("lst", lst);
    }

    public void vacancyEdit(@RequestParam(name = "vacancyTitle", required = false) String title, @RequestParam(name = "vacancyTag", required = false) String tag,
                            @RequestParam(name = "vacancyText", required = false) String text, @PathVariable(value = "id") int id) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        vacancy.setTitle(title);
        vacancy.setTag(tag);
        vacancy.setText(text);
        vacancyRepository.save(vacancy);
    }

    public void vacancyRemove(@PathVariable(value = "id") int id) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow();
        vacancyRepository.delete(vacancy);
    }


}
