package com.wolfhack.cloud.product.repository.search;

import com.wolfhack.cloud.product.model.search.RamSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamSearchRepository extends ElasticsearchRepository<RamSearch, Long> {

}
