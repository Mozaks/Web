package com.example.demo.entity;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TagTest {
    Tag tagTest;

    @BeforeEach
    public void setUp() {
        tagTest = new Tag();
        tagTest.setId(1);
        tagTest.setTag("tag");
        Vacancy vacancy = new Vacancy();
        tagTest.setVacancy(vacancy);

    }

    @Test
    void getId_NO_NULL() {
        Integer expected = tagTest.getId();
        Assert.assertNotNull(expected);
    }

    @Test
    void getTag_NO_NULL() {
        String expected = tagTest.getTag();
        Assert.assertNotNull(expected);
    }

    @Test
    void getVacancy_NO_NULL() {
        Vacancy expected = tagTest.getVacancy();
        Assert.assertNotNull(expected);
    }


}