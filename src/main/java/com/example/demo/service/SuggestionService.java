package com.example.demo.service;

import com.example.demo.domain.ServerUrl;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Suggestion;
import com.example.demo.entity.Vacancy;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.SuggestionRepository;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
    @Autowired
    private MailSender mailSender;

    public void showSuggestionToFreelancer(@AuthenticationPrincipal Customer customer, Model model) {
        List<Suggestion> lst = suggestionRepository.findByWorkerId(customer.getId());

        List<Customer> lstAuthor =
                lst.stream()
                        .map(sug -> sug.getAuthor())
                        .collect(Collectors.toList());

        List<Vacancy> lstVac =
                lst.stream()
                        .map(sug -> sug.getVacancy())
                        .collect(Collectors.toList());

        model
                .addAttribute("authors", lstAuthor)
                .addAttribute("vacancies", lstVac)
                .addAttribute("user", customer);

    }

    public void showSuggestionToNotFreelancer(@AuthenticationPrincipal Customer customer, Model model) {
        List<Suggestion> lst = suggestionRepository.findByAuthorId(customer.getId());

        List<Customer> lstWork =
                lst.stream()
                        .map(sug -> sug.getWorker())
                        .collect(Collectors.toList());

        List<Vacancy> lstVac =
                lst.stream()
                        .map(sug -> sug.getVacancy())
                        .collect(Collectors.toList());

        model
                .addAttribute("workers", lstWork)
                .addAttribute("vacancies", lstVac)
                .addAttribute("user", customer);

    }


    public void appoint(@PathVariable(value = "idSug") int idSug, @PathVariable(value = "idVac") int idVac,
                        @AuthenticationPrincipal Customer customer) throws CustomException {
        Customer worker = customerRepository.findById(idSug).orElseThrow(() -> new CustomException());

        if (!StringUtils.isEmpty(worker.getEmail())) {
            String message = String.format(
                    "Greeting, %s! \n" +
                            "You were assigned as a performer for the order \n"
                            +
                            "Please visit this link: " + ServerUrl.URL + "selected/%s", worker.getUsername(),
                    vacancyRepository.findById(idVac).orElseThrow(() -> new CustomException()).getId().toString()
            );
            mailSender.send(worker.getEmail(), "Assignment to complete an order", message);
        }

        Vacancy vacancy = vacancyRepository.findById(idVac).orElseThrow(() -> new CustomException());
        vacancy.setWorker(customerRepository.findById(idSug).orElseThrow(() -> new CustomException()));

        vacancyRepository.save(vacancy);

        Suggestion suggestion =
                suggestionRepository.findByVacancyId(idVac).stream()
                        .filter(sug -> sug.getWorker().getId().equals(idSug))
                        .findAny().orElseThrow(() -> new CustomException());

        suggestionRepository.delete(suggestion);

    }
}
