package com.wolfhack.cloud.product.repository;

import com.wolfhack.cloud.product.model.Ssd;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SsdRepository extends MongoRepository<Ssd, Long> {
}
