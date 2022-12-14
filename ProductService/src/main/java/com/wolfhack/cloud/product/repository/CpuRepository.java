package com.wolfhack.cloud.product.repository;

import com.wolfhack.cloud.product.model.Cpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuRepository extends MongoRepository<Cpu, Long> {

    @Query("{ $text: { $search: ?0 }}")
    Page<Cpu> searchCpusByQuery(String query, Pageable pageable);

}
