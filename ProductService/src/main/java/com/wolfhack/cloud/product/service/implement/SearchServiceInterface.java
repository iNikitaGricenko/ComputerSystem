package com.wolfhack.cloud.product.service.implement;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface SearchServiceInterface {
    Map<String, Object> search(String query, Pageable pageable);
}
