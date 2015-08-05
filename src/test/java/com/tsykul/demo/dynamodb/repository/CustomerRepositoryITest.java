package com.tsykul.demo.dynamodb.repository;

import com.tsykul.demo.dynamodb.App;
import com.tsykul.demo.dynamodb.entity.Customer;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@IntegrationTest
public class CustomerRepositoryITest
{
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testCreate()
    {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customerRepository.save(customer);

        List<Customer> customers = customerRepository.findByFirstName("John");

        assertEquals(1, customers.size());
        assertEquals("Doe", customers.get(0).getLastName());
    }
}
