package com.wolfhack.cloud.product.repository.search;

import com.wolfhack.cloud.product.model.search.CpuSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CpuSearchRepository extends ElasticsearchRepository<CpuSearch, Long> {

	Page<CpuSearch> findByProductLine(String productLine, Pageable pageable);

}
