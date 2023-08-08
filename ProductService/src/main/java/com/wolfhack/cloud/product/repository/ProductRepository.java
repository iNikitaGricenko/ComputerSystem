package com.wolfhack.cloud.product.repository;

import com.wolfhack.cloud.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ProductRepository<S> extends MongoRepository<Product<S>, Long> {

}
