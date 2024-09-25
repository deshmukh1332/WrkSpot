package com.wrkspot.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class Controller {
    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public List<Customer> getCustomer(@RequestParam(required = false) String firstName,
                                      @RequestParam(required = false) String lastName,
                                      @RequestParam(required = false) String city,
                                      @RequestParam(required = false) String state) {
        return customerService.getCustomer(firstName, lastName, city, state);
    }

    @PostMapping("")
    public String createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

}
