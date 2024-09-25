package com.wrkspot.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<Customer> getCustomer(String firstName, String lastName, String city, String state);

    String createCustomer(Customer customer);
}
