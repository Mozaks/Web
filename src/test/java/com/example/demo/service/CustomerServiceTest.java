package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.role.Role;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private MailSender mailSender;

    @Test
    public void addCustomer() {
        Customer customer = new Customer();
        customer.setEmail("mail@mail.ru");

        boolean isCustomerAdded = customerService.addCustomer(customer);

        Assert.assertTrue(isCustomerAdded);
        Assert.assertTrue(CoreMatchers.is(customer.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
        Mockito.verify(mailSender, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(customer.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    public void addCustomerFailTest() {
        Customer customer = new Customer();
        customer.setUsername("username");

        Mockito.doReturn(new Customer())
                .when(customerRepository)
                .findByUsername("username");

        boolean isCustomerAdded = customerService.addCustomer(customer);

        Assert.assertFalse(isCustomerAdded);

        Mockito.verify(customerRepository, Mockito.times(0)).save(ArgumentMatchers.any(Customer.class));
        Mockito.verify(mailSender, Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }
}