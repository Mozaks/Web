package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Tag;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacancyService {
    @Autowired
    VacancyRepository vacancyRepository;
    @Autowired
    TagRepository tagRepository;

    public void addVacancy(@AuthenticationPrincipal Customer customer, @RequestParam String title,
                           @RequestParam String text,
                           @RequestParam String tag) {
        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(title);
        vacancy.setText(text);
        vacancy.setAuthor(customer);
        vacancyRepository.save(vacancy);

        if (tag.contains(",")) {
            List<String> tags = Arrays.stream(tag.split(",")).collect(Collectors.toList());
            for (String t : tags) {
                Tag tagTmp = new Tag();
                tagTmp.setTag(t);
                tagTmp.setVacancy(vacancy);
                tagRepository.save(tagTmp);
            }
        } else {
            Tag tagTmp = new Tag();
            tagTmp.setTag(tag);
            tagTmp.setVacancy(vacancy);
            tagRepository.save(tagTmp);
        }
    }
}
