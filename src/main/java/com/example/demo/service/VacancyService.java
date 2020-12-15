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
    private VacancyRepository vacancyRepository;
    @Autowired
    private TagRepository tagRepository;

    public void addVacancy(@AuthenticationPrincipal Customer customer, @RequestParam String title,
                           @RequestParam String text,
                           @RequestParam String tag) {
        Vacancy vacancy = new
                Vacancy.Builder()
                .setText(text)
                .setTitle(title)
                .setAuthor(customer)
                .build();

        vacancyRepository.save(vacancy);


        if (tag.contains(",")) {
            List<String> tags =
                    Arrays.stream(tag.split(","))
                            .collect(Collectors.toList());
            for (String t : tags) {
                tagRepository.save(new Tag.Builder()
                        .setTag(tag)
                        .setVacancy(vacancy)
                        .build());
            }
        } else {
            tagRepository.save(new Tag.Builder()
                    .setTag(tag)
                    .setVacancy(vacancy)
                    .build());
        }
    }
}
