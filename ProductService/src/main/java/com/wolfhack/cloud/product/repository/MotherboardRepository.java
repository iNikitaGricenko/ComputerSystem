package com.wolfhack.cloud.product.repository;

import com.wolfhack.cloud.product.model.Motherboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherboardRepository extends MongoRepository<Motherboard, Double> {

    @Query("{ $text: { $search: ?0 }}")
    Page<Motherboard> searchCpusByQuery(String query, Pageable pageable);
}
