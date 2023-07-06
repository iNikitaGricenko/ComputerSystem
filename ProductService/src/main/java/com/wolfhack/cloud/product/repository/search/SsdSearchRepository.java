package com.wolfhack.cloud.product.repository.search;

import com.wolfhack.cloud.product.model.search.SsdSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SsdSearchRepository extends ElasticsearchRepository<SsdSearch, Long> {

}
