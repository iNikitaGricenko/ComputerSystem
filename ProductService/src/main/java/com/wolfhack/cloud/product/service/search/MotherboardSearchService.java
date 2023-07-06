package com.wolfhack.cloud.product.service.search;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.MotherboardNotFoundException;
import com.wolfhack.cloud.product.mapper.MotherboardMapper;
import com.wolfhack.cloud.product.model.Motherboard;
import com.wolfhack.cloud.product.model.search.MotherboardSearch;
import com.wolfhack.cloud.product.repository.search.MotherboardSearchRepository;
import com.wolfhack.cloud.product.service.search.implement.MotherboardSearchServiceInterface;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
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
public class MotherboardSearchService implements MotherboardSearchServiceInterface {

	private final MotherboardMapper motherboardMapper;
	private final MotherboardSearchRepository motherboardSearchRepository;
	private final ElasticsearchOperations elasticsearchOperations;

	@Override
	public long save(Motherboard motherboard) {
		MotherboardSearch searchModel = motherboardMapper.toSearch(motherboard);
		return motherboardSearchRepository.save(searchModel).getId();
	}

	@Override
	public List<Motherboard> findByTitle(String line, Pageable pageable) {
		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(line)
						.field("name")
						.field("model")
						.field("type")
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
						.fuzziness(Fuzziness.ONE)
						.prefixLength(3)).build();

		SearchHits<MotherboardSearch> motherboards = elasticsearchOperations.search(searchQuery, MotherboardSearch.class, IndexCoordinates.of("product-motherboard"));

		return motherboards.map(SearchHit::getContent).map(motherboardMapper::toEntity).toList();
	}

	@AopLog
	@Override
	public List<Motherboard> findByAllTextFields(String line, Pageable pageable) {
		String[] fields = Arrays.stream(Motherboard.class.getFields())
				.filter(field -> field.getType().isInstance(String.class))
				.map(Field::getName)
				.toArray(String[]::new);

		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(line, fields)
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
						.fuzziness(Fuzziness.ONE)
						.prefixLength(3)).build();

		SearchHits<MotherboardSearch> motherboards = elasticsearchOperations.search(searchQuery, MotherboardSearch.class, IndexCoordinates.of("product-motherboard"));

		return motherboards.map(SearchHit::getContent).map(motherboardMapper::toEntity).toList();
	}
	
	@AopLog
	@Override
	public List<Motherboard> findByAllFields(String line, Pageable pageable) {
		String[] fields = Arrays.stream(Motherboard.class.getFields())
				.map(Field::getName)
				.toArray(String[]::new);

		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(line, fields)
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
						.fuzziness(Fuzziness.ONE)
						.prefixLength(3)).build();

		SearchHits<MotherboardSearch> motherboards = elasticsearchOperations.search(searchQuery, MotherboardSearch.class, IndexCoordinates.of("product-motherboard"));

		return motherboards.map(SearchHit::getContent).map(motherboardMapper::toEntity).toList();
	}

	@Override
	public long update(Motherboard motherboard) {
		MotherboardSearch motherboardSearch = motherboardSearchRepository.findById(motherboard.getId()).orElseThrow(MotherboardNotFoundException::new);

		motherboardMapper.partialUpdate(motherboardSearch, motherboard);

		return motherboardSearchRepository.save(motherboardSearch).getId();
	}

	@Override
	public void delete(long motherboardId) {
		motherboardSearchRepository.deleteById(motherboardId);
	}

}
