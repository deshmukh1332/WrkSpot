package com.wrkspot.demo;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

public class CustomerUtils {

    public static List<Customer> customersOnlyInListA(List<Customer> listA, List<Customer> listB) {
        Set<Customer> setB = new HashSet<>(listB);
        return listA.stream()
                   .filter(customer -> !setB.contains(customer))
                   .collect(Collectors.toList());
    }

    public static List<Customer> customersOnlyInListB(List<Customer> listA, List<Customer> listB) {
        Set<Customer> setA = new HashSet<>(listA);
        return listB.stream()
                   .filter(customer -> !setA.contains(customer))
                   .collect(Collectors.toList());
    }

    public static List<Customer> customersInBothLists(List<Customer> listA, List<Customer> listB) {
        Set<Customer> setB = new HashSet<>(listB);
        return listA.stream()
                   .filter(setB::contains)
                   .collect(Collectors.toList());
    }
}