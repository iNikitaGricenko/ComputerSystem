package com.wolfhack.cloud.product.repository;

import com.wolfhack.cloud.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository<S> extends MongoRepository<Product<S>, Long> {

}
