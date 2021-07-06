package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    Customer customer = new Customer();

    @Before
    public void setUp() {
        customer.setEmail("mail@mail.ru");
        customer.setUsername("username");
    }

    @Test
    public void addCustomer() {
        boolean isCustomerAdded = customerService.addCustomer(customer);
        Assert.assertTrue(isCustomerAdded);
    }

    @Test
    public void checkSave() {
        customerService.addCustomer(customer);
        Mockito.verify(customerRepository, Mockito.times(1)).save(customer);
    }

    @Test
    public void checkSend() {
        customerService.addCustomer(customer);
        Mockito.verify(mailSender, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(customer.getEmail()),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }


    @Test
    public void addCustomerFailTest() {
        Mockito.doReturn(new Customer())
                .when(customerRepository)
                .findByUsername("username");
        boolean isCustomerAdded = customerService.addCustomer(customer);
        Assert.assertFalse(isCustomerAdded);
    }

    @Test
    public void checkSaveFailTest() {
        Mockito.doReturn(new Customer())
                .when(customerRepository)
                .findByUsername("username");
        customerService.addCustomer(customer);
        Mockito.verify(customerRepository, Mockito.times(0)).save(ArgumentMatchers.any(Customer.class));
    }


}