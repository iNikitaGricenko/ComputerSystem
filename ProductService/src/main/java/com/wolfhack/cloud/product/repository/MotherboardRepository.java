package com.wolfhack.cloud.product.repository;

import com.wolfhack.cloud.product.model.Motherboard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherboardRepository extends MongoRepository<Motherboard, Long> {
}
