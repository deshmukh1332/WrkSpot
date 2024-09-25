package com.wrkspot.demo;

import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Override
    public Address getAddress(String city, String state) {
        return null;
    }

    @Override
    public String createAddress(Address address) {
        return "";
    }
}
