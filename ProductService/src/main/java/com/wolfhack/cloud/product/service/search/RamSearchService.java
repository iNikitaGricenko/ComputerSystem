package com.wolfhack.cloud.product.service.search;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.RamNotFoundException;
import com.wolfhack.cloud.product.mapper.RamMapper;
import com.wolfhack.cloud.product.model.Ram;
import com.wolfhack.cloud.product.model.search.RamSearch;
import com.wolfhack.cloud.product.repository.search.RamSearchRepository;
import com.wolfhack.cloud.product.service.search.implement.RamSearchServiceInterface;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RamSearchService implements RamSearchServiceInterface {

	private final RamMapper ramMapper;
	private final RamSearchRepository ramSearchRepository;
	private final ElasticsearchOperations elasticsearchOperations;

	@Override
	public long save(Ram ram) {
		RamSearch searchModel = ramMapper.toSearch(ram);
		return ramSearchRepository.save(searchModel).getId();
	}

	@Override
	public List<Ram> findByTitle(String line, Pageable pageable) {
		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(line)
						.field("name")
						.field("model")
						.field("type")
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
						.fuzziness(Fuzziness.ONE)
						.prefixLength(3)).build();

		SearchHits<RamSearch> rams = elasticsearchOperations.search(searchQuery, RamSearch.class, IndexCoordinates.of("product-ram"));

		return rams.map(SearchHit::getContent).map(ramMapper::toEntity).toList();
	}

	@AopLog
	@Override
	public List<Ram> findByAllTextFields(String line, Pageable pageable) {
		String[] fields = Arrays.stream(Ram.class.getFields())
				.filter(field -> field.getType().isInstance(String.class))
				.map(Field::getName)
				.toArray(String[]::new);

		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(line, fields)
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
						.fuzziness(Fuzziness.ONE)
						.prefixLength(3)).build();

		SearchHits<RamSearch> rams = elasticsearchOperations.search(searchQuery, RamSearch.class, IndexCoordinates.of("product-ram"));

		return rams.map(SearchHit::getContent).map(ramMapper::toEntity).toList();
	}
	
	@AopLog
	@Override
	public List<Ram> findByAllFields(String line, Pageable pageable) {
		String[] fields = Arrays.stream(Ram.class.getFields())
				.map(Field::getName)
				.toArray(String[]::new);

		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(line, fields)
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
						.fuzziness(Fuzziness.ONE)
						.prefixLength(3)).build();

		SearchHits<RamSearch> rams = elasticsearchOperations.search(searchQuery, RamSearch.class, IndexCoordinates.of("product-ram"));

		return rams.map(SearchHit::getContent).map(ramMapper::toEntity).toList();
	}

	@Override
	public long update(Ram ram) {
		RamSearch ramSearch = ramSearchRepository.findById(ram.getId()).orElseThrow(RamNotFoundException::new);

		ramMapper.partialUpdate(ramSearch, ram);

		return ramSearchRepository.save(ramSearch).getId();
	}

	@Override
	public void delete(long ramId) {
		ramSearchRepository.deleteById(ramId);
	}

}
