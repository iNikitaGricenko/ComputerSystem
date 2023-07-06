package com.wolfhack.cloud.product.repository.search;

import com.wolfhack.cloud.product.model.search.MotherboardSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherboardSearchRepository extends ElasticsearchRepository<MotherboardSearch, Long> {

}
