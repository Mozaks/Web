package com.example.demo.entity;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SuggestionTest {
    private Suggestion suggestionTest;

    @BeforeEach
    public void setUp() {
        suggestionTest = new Suggestion();
        suggestionTest.setId(1);
        Customer author = new Customer();
        suggestionTest.setAuthor(author);
        Customer worker = new Customer();
        suggestionTest.setWorker(worker);
        Vacancy vacancy = new Vacancy();
        suggestionTest.setVacancy(vacancy);
    }

    @Test
    void getId_NO_NULL() {
        Integer expected = suggestionTest.getId();
        Assert.assertNotNull(expected);
    }

    @Test
    void getAuthor_NO_NULL() {
        Customer expected = suggestionTest.getAuthor();
        Assert.assertNotNull(expected);
    }

    @Test
    void getWorker_NO_NULL() {
        Customer expected = suggestionTest.getWorker();
        Assert.assertNotNull(expected);
    }

    @Test
    void getVacancy_NO_NULL() {
        Vacancy expected = suggestionTest.getVacancy();
        Assert.assertNotNull(expected);
    }

}