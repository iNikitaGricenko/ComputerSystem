package com.wolfhack.cloud.product.repository;

import com.wolfhack.cloud.product.model.Storage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends MongoRepository<Storage, String> {
}
