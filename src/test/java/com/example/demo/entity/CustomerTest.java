package com.example.demo.entity;

import com.example.demo.role.Role;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class CustomerTest {
    private Customer customerTest;

    @BeforeEach
    public void setUp() {
        customerTest = new Customer();
        customerTest.setId(1);
        customerTest.setUsername("username");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.USER);
        customerTest.setRoles(roleSet);
        customerTest.setEmail("email@email.com");
        customerTest.setFirstName("firstname");
        customerTest.setLastName("lastname");
        customerTest.setName("name");
    }

    @Test
    void getId_NO_NULL() {
        Integer expected = customerTest.getId();
        Assert.assertNotNull(expected);
    }

    @Test
    void getUsername_NO_NULL() {
        String expected = customerTest.getUsername();
        Assert.assertNotNull(expected);
    }

    @Test
    void getRoles_NO_NULL() {
        Set<Role> expected = customerTest.getRoles();
        Assert.assertNotNull(expected);
    }

    @Test
    void getEmail_NO_NULL() {
        String expected = customerTest.getEmail();
        Assert.assertNotNull(expected);
    }

    @Test
    void getFirstName_NO_NULL() {
        String expected = customerTest.getFirstName();
        Assert.assertNotNull(expected);
    }

    @Test
    void getLastName_NO_NULL() {
        String expected = customerTest.getLastName();
        Assert.assertNotNull(expected);
    }

    @Test
    void getName_NO_NULL() {
        String expected = customerTest.getName();
        Assert.assertNotNull(expected);
    }
}