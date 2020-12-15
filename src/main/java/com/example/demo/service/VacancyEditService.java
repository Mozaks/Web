package com.example.demo.service;

import com.example.demo.entity.Tag;
import com.example.demo.entity.Vacancy;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VacancyEditService {
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private MailSender mailSender;

    public boolean isVacancyExists(@PathVariable(value = "id") int id) {
        return vacancyRepository.existsById(id);
    }


    public void vacancyEditView(@PathVariable(value = "id") int id, Model model) {
        Optional<Vacancy> vacancy = vacancyRepository.findById(id);
        ArrayList<Vacancy> lst = new ArrayList<>();
        vacancy.ifPresent(lst::add);

        List<Tag> tags = tagRepository.findByVacancyId(id);

        model.addAttribute("lst", lst);
        model.addAttribute("tags", tags);
    }

    public void vacancyEdit(@RequestParam(name = "vacancyTitle", required = false) String title, @RequestParam(name = "vacancyText", required = false) String text,
                            @PathVariable(value = "id") int id, @RequestParam(name = "vacancyTag", required = false) String tag) throws CustomException {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() -> new CustomException());
        vacancy.setTitle(title);
        vacancy.setText(text);

        vacancyRepository.save(vacancy);

        List<Tag> tagList = tagRepository.findByVacancyId(vacancy.getId());
        List<String> tags =
                Arrays.stream(tag.split(","))
                        .collect(Collectors.toList());

        for (String t : tags) {
            if (!tagList.contains(t)) {
                tagRepository.save(new Tag.Builder()
                        .setTag(tag)
                        .setVacancy(vacancy)
                        .build());
            }
        }

    }

    public void vacancyRemove(@PathVariable(value = "id") int id) throws CustomException {
        List<Tag> lst = tagRepository.findByVacancyId(id);

        for (Tag tag : lst) {
            tagRepository.delete(tag);
        }

        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(() -> new CustomException());

        if (vacancy.getWorker() != null) {
            if (!StringUtils.isEmpty(vacancy.getWorker().getEmail())) {
                String message = String.format(
                        "Greeting, %s! \n" +
                                "One of the vacancy you were assigned to has been removed: " + vacancy.getTitle()
                );
                mailSender.send(vacancy.getWorker().getEmail(), "Activation code", message);
            }
        }
        vacancyRepository.delete(vacancy);
    }


}
