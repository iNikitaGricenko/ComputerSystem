package com.wolfhack.cloud.customer.service.implement;

import com.wolfhack.cloud.customer.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductServiceInterface {
    Product save(Product product);

    Product findById(Long id);

    Page<Product> findAll(Pageable pageable);
}
