package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Suggestion;
import com.example.demo.entity.Vacancy;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.SuggestionRepository;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionService {
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SuggestionRepository suggestionRepository;

    public void showSuggestion(@AuthenticationPrincipal Customer customer, Model model) {
        List<Suggestion> lst = suggestionRepository.findByAuthorId(customer.getId());

        List<Customer> lstWork = lst.stream().map(x -> x.getWorker()).collect(Collectors.toList());

        List<Vacancy> lstVac = lst.stream().map(x -> x.getVacancy()).collect(Collectors.toList());

        model.addAttribute("workers", lstWork);

        model.addAttribute("vacancies", lstVac);
    }

    public void appoint(@PathVariable(value = "idSug") int idSug, @PathVariable(value = "idVac") int idVac, @AuthenticationPrincipal Customer customer) {
        Vacancy vacancy = vacancyRepository.findById(idVac).orElseThrow();
        vacancy.setWorker(customerRepository.findById(idSug).orElseThrow());
        vacancyRepository.save(vacancy);

        Suggestion suggestion = suggestionRepository.findByVacancyId(idVac).stream()
                .filter(x -> x.getWorker().getId().equals(idSug))
                .findAny().orElseThrow();

        suggestionRepository.delete(suggestion);
    }
}
