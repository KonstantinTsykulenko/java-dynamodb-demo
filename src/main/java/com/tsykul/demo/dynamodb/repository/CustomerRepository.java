package com.tsykul.demo.dynamodb.repository;

import com.tsykul.demo.dynamodb.entity.Customer;
import java.util.List;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String>
{
    @EnableScan
    List<Customer> findByFirstName(String firstName);

    @Override
    @EnableScan
    List<Customer> findAll();

    List<Customer> findByPremium(boolean premium);
}
