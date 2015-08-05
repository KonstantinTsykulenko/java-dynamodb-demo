package com.tsykul.demo.dynamodb.controller;

import com.tsykul.demo.dynamodb.entity.Customer;
import com.tsykul.demo.dynamodb.repository.CustomerDAO;
import com.tsykul.demo.dynamodb.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController
{
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private CustomerDAO        dao;

    @RequestMapping(value = "customers", method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody Customer customer)
    {
        //Uncomment to use mapper
        //return dao.saveWithMapper(customer);
        //Uncomment to use item api
        //return dao.saveWithItemApi(customer);
        return repository.save(customer);
    }

    @RequestMapping(value = "customers", method = RequestMethod.GET)
    public Iterable<Customer> getCustomers()
    {
        return repository.findAll();
    }

    @RequestMapping(value = "customers/search", method = RequestMethod.GET)
    public Iterable<Customer> customerSearch(@RequestParam("name") String name, @RequestParam("premium") Boolean premium)
    {
        if (null != premium)
        {
            //uncomment to use query api
            //return repository.findByPremium(true);
            return repository.findByPremium(true);
        }

        if (null != name)
        {
            //uncomment to use scan api
            //return dao.findByName(name)
            return repository.findByFirstName(name);
        }

        return repository.findAll();
    }
}
