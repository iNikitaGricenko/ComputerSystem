package com.wolfhack.cloud.product.repository.search;

import com.wolfhack.cloud.product.model.search.GpuSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GpuSearchRepository extends ElasticsearchRepository<GpuSearch, Long> {

}
