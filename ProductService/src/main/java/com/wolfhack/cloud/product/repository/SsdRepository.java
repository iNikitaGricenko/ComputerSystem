package com.wolfhack.cloud.product.repository;

import com.wolfhack.cloud.product.model.Ssd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SsdRepository extends MongoRepository<Ssd, Double> {

    @Query("{ $text: { $search: ?0 }}")
    Page<Ssd> searchCpusByQuery(String query, Pageable pageable);
}
