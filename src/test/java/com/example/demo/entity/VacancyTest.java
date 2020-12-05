package com.example.demo.entity;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VacancyTest {
    private Vacancy vacancyTest;

    @BeforeEach
    public void setUp() {
        vacancyTest = new Vacancy();
        Customer worker = new Customer();
        worker.setUsername("worker");
        worker.setId(1);
        vacancyTest.setId(1);
        vacancyTest.setWorker(worker);
        vacancyTest.setText("text");
        vacancyTest.setTitle("title");
        Customer author = new Customer();
        author.setUsername("author");
        author.setId(1);
        vacancyTest.setAuthor(author);
    }

    @Test
    void getId_NO_NULL() {
        Integer expected = vacancyTest.getId();
        Assert.assertNotNull(expected);
    }

    @Test
    void getAuthor_NO_NULL() {
        Customer expected = vacancyTest.getAuthor();
        Assert.assertNotNull(expected);
    }

    @Test
    void getText_NO_NULL() {
        String expected = vacancyTest.getText();
        Assert.assertNotNull(expected);
    }

    @Test
    void getTitle_NO_NULL() {
        String expected = vacancyTest.getTitle();
        Assert.assertNotNull(expected);
    }

    @Test
    void getWorker_NO_NULL() {
        Customer expected = vacancyTest.getWorker();
        Assert.assertNotNull(expected);
    }

    @Test
    void getWorkerName_NO_NULL() {
        String expected = vacancyTest.getWorkerName();
        Assert.assertNotNull(expected);
    }

}