package com.wrkspot.demo;

import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    Address getAddress(String city, String state);
    String createAddress(Address address);
}
