package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Suggestion;
import com.example.demo.entity.Tag;
import com.example.demo.entity.Vacancy;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.SuggestionRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private SuggestionRepository suggestionRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MailSender mailSender;

    public void mainView(Model model) {
        Iterable<Vacancy> vacancies = vacancyRepository.findAll();
        List<List<Tag>> lst = new ArrayList<>();

        for (Vacancy vacancy : vacancies) {
            lst.add(tagRepository.findByVacancyId(vacancy.getId()));
        }

        model.addAttribute("vacancies", vacancies);

        model.addAttribute("lst", lst);
    }

    public void filter(@RequestParam String filter, Model model) {
        List<Vacancy> vacancyList;

        if (!filter.isEmpty()) {
            vacancyList = vacancyRepository.findByTitle(filter);
        } else {
            vacancyList = vacancyRepository.findAll();
        }

        model.addAttribute("vacancies", vacancyList);
    }

    public void selected(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id, Model model) throws CustomException {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() -> new CustomException());
        List<Tag> lst = tagRepository.findByVacancyId(vacancy.getId());

        model.addAttribute("lst", lst);
        model.addAttribute("vacancy", vacancy);

        if (customer.getId().equals(vacancy.getAuthor().getId())) {
            model.addAttribute("yourself", false);
        } else {
            model.addAttribute("yourself", true);
        }
    }

    public void setWorker(@AuthenticationPrincipal Customer customer, @PathVariable(value = "id") int id) throws CustomException {

        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() -> new CustomException());
        Customer author = vacancy.getAuthor();

        if (!StringUtils.isEmpty(author.getEmail())) {
            String message = String.format(
                    "Greeting, %s! \n" +
                            "We have received an offer for one of your vacancies, please check your personal account"
                    , customer.getUsername());
            mailSender.send(author.getEmail(), "The offer for the vacancy", message);
        }

        Suggestion.Builder builder = Suggestion.Builder();
        Suggestion suggestion =
                builder
                        .setWorker(customer)
                        .setVacancy(vacancy)
                        .setAuthor(author)
                        .build();

        suggestionRepository.save(suggestion);

    }

}
