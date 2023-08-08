package com.wolfhack.cloud.repository;

import com.wolfhack.cloud.entity.EntityCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<EntityCustomer, Long> {
	Optional<EntityCustomer> findByEmail(String email);
}
