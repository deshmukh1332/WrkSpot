package com.wrkspot.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String TOPIC = "customer_topic";
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public List<Customer> getCustomer(String firstName, String lastName, String city, String state) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<Customer> customerRoot = query.from(Customer.class);

        customerRoot.fetch("addresses", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(cb.equal(customerRoot.get("firstName"), firstName));
        }
        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(cb.equal(customerRoot.get("lastName"), lastName));
        }

        if (city != null && !city.isEmpty()) {
            Join<Customer, Address> addressJoin = customerRoot.join("addresses", JoinType.LEFT);
            predicates.add(cb.equal(addressJoin.get("city"), city));
        }
        if (state != null && !state.isEmpty()) {
            Join<Customer, Address> addressJoin = customerRoot.join("addresses", JoinType.LEFT);
            predicates.add(cb.equal(addressJoin.get("state"), state));
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        query.select(customerRoot).distinct(true);

        TypedQuery<Customer> typedQuery = entityManager.createQuery(query);
        log.info("Query: {}", typedQuery.getResultList());
        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public String createCustomer(Customer customer) {
        Set<Address> addresses = customer.getAddresses();
        for (Address address : addresses) {
            entityManager.persist(address);
        }
        entityManager.persist(customer);
        kafkaTemplate.send(TOPIC, "Customer created: " + customer.getId());
        return "Customer created successfully";
    }
}