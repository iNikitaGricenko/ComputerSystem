package com.wolfhack.cloud.product.repository;

import com.wolfhack.cloud.product.model.Gpu;
import com.wolfhack.cloud.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpuRepository extends MongoRepository<Product<Gpu>, Long> {
}
