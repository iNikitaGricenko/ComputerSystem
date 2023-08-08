package com.wolfhack.cloud.product.repository;

import com.wolfhack.cloud.product.model.Cpu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuRepository extends MongoRepository<Cpu, Long> {
}
