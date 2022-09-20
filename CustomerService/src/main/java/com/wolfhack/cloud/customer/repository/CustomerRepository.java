package com.wolfhack.cloud.customer.repository;

import com.wolfhack.cloud.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
