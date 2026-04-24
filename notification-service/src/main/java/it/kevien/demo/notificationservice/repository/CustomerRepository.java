package it.kevien.demo.notificationservice.repository;

import it.kevien.demo.notificationservice.model.Customer;

import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

    // simulation of a DB query - static value, only for demo
    public Customer findById(String id) {
        return new Customer(id, "test@to.com");
    }

}
