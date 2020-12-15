package com.example.demo.service;

import com.example.demo.domain.ServerUrl;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Service
public class ProfileService {
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private CustomerRepository customerRepository;

    private static final Customer customerEdit;

    static {
        customerEdit = new Customer();
    }

    public void profileView(@AuthenticationPrincipal Customer customer, Model model) {
        ServicesUtil.showVacanciesAndTags(vacancyRepository, customer, tagRepository, model, true);
    }

    public void profileEditView(@AuthenticationPrincipal Customer customer, Model model) {
        model.addAttribute("customer", customer);
    }

    public void profileEdit(@AuthenticationPrincipal Customer customer, Model model,
                            @RequestParam(name = "newEmail", required = false) String newEmail,
                            @RequestParam(name = "newPassword", required = false) String newPassword,
                            @RequestParam(name = "newUsername", required = false) String newUsername) {

        customerEdit.setEmail(newEmail);
        customerEdit.setPassword(newPassword);
        customerEdit.setUsername(newUsername);

        if (!StringUtils.isEmpty(customer.getEmail())) {
            String message = String.format(
                    "Greeting, %s! \n" +
                            "The request was received to change your personal data. " +
                            "For confirmation click on the link: \n"
                            + ServerUrl.URL + "profile/edit/%s", customer.getUsername(), customer.getId()
            );
            mailSender.send(customer.getEmail(), "Assignment to complete an order", message);
        }

        customer.setActivationCode(UUID.randomUUID().toString());
    }


    public void profileEditViewFromMail(@PathVariable(name = "id") int id, Customer customer, Model model) {
        if (customer.getActivationCode() != null) {
            if (customerEdit.getPassword() != null && !customerEdit.getPassword().isEmpty()) {
                customer.setPassword(customerEdit.getPassword());
                customerEdit.setPassword("");
            }
            if (customerEdit.getUsername() != null && !customerEdit.getUsername().isEmpty()) {
                customer.setUsername(customerEdit.getUsername());
                customerEdit.setUsername("");
            }
            if (customerEdit.getEmail() != null && !customerEdit.getEmail().isEmpty()) {
                customer.setEmail(customerEdit.getEmail());
                customerEdit.setEmail("");
            }
            customer.setActivationCode(null);
            customerRepository.save(customer);
            model.addAttribute("message", "Your data was changed successfully")
                    .addAttribute("customer", customer);
        }
    }
}
